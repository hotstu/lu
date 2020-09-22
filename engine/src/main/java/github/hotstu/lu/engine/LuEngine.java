package github.hotstu.lu.engine;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.commonjs.module.Require;
import org.mozilla.javascript.commonjs.module.RequireBuilder;
import org.mozilla.javascript.commonjs.module.provider.SoftCachingModuleScriptProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class LuEngine implements Handler.Callback {
    public static final int EVENT_TASK = 0;
    public static final int EVENT_EXIT_CONTEXT = 1;

    private static LuEngine sInstance;
    private LuEngineThread workerThread;
    private Handler workerHandler;
    private Scriptable mSharedScope;


    private LuEngine() { }

    public Scriptable getSharedScope() {
        return mSharedScope;
    }

    public Handler getWorkerHandler() {
        return workerHandler;
    }

    public LuContext createDefaultContext() {
        return new DefaultLuContext(mSharedScope);
    }
    public synchronized void start() {
        workerThread = new LuEngineThread("rhino-worker");
        workerThread.start();
        workerHandler = new Handler(workerThread.getLooper(), this);
        CountDownLatch latch = new CountDownLatch(1);
        workerHandler.post(() -> {
            try {
                mSharedScope = initScope();
            } finally {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutDown() {
        workerHandler.sendEmptyMessage(EVENT_EXIT_CONTEXT);
        workerThread.quitSafely();
    }

    public void enqueueTask(ScriptTask task) {
        workerHandler.sendMessage(workerHandler.obtainMessage(EVENT_TASK, task));
    }


    public void enforceWorkerThread() {
        if (Looper.myLooper() != workerHandler.getLooper()) {
            throw new IllegalStateException("should call in rhino-worker thread, current:" + Thread.currentThread());
        }
    }

    public static LuEngine getInstance() {
        if (sInstance == null) {
            synchronized (LuEngine.class) {
                if (sInstance == null) {
                    sInstance = new LuEngine();
                }
            }
        }
        return sInstance;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        if (msg.what == EVENT_TASK) {
            ScriptTask poll = (ScriptTask) msg.obj;
            if (poll != null) {
                run(poll);
                poll.sendBack();
            }
        } else if (msg.what == EVENT_EXIT_CONTEXT) {
            if (Context.getCurrentContext() != null) {
                Context.exit();
            }
        }

        return true;
    }

    private void run(ScriptTask task) {
        try {
            Object result = Context.getCurrentContext().evaluateString(task.context.getContextScope(), task.input, "<cmd>", 1, null);
            task.output = Context.toString(result);
        } catch (Exception e) {
            e.printStackTrace();
            task.err = e.getMessage();
        }
    }

    private Scriptable initScope() {
        enforceWorkerThread();
        ImporterTopLevel scope = new ImporterTopLevel();
        scope.initStandardObjects(Context.getCurrentContext(), false);
        initRequireModule(scope);
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(ModuleApp.application.getAssets().open("init.js"));
            Script script = Context.getCurrentContext().compileReader(reader, "<init>", 1, null);
            script.exec(Context.getCurrentContext(), scope);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        putProperty(scope,"console", new ConsoleImpl());
        //scope.sealObject(); //TODO java.lang.RuntimeException: XML parser (DocumentBuilderFactory) cannot be securely configured.
        return scope;

    }

    public void putProperty(Scriptable scope, String name, Object value) {
        ScriptableObject.putProperty(scope, name, Context.javaToJS(value, scope));
    }

    private final String MODULES_PATH = "modules";

    private void initRequireModule(Scriptable scope) {
        File file = new File("/");
        List<URI> urls = new ArrayList<>();
        urls.add(file.toURI());
        AssetAndUrlModuleSourceProvider provider = new AssetAndUrlModuleSourceProvider(MODULES_PATH, urls);
        Require require = new RequireBuilder()
                .setModuleScriptProvider(new SoftCachingModuleScriptProvider(provider))
                .setSandboxed(true)
                .createRequire(Context.getCurrentContext(), scope);
        require.install(scope);

    }
}
