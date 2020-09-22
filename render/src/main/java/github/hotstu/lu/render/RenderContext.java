package github.hotstu.lu.render;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;

import java.util.ArrayList;

import github.hotstu.lu.engine.LuContext;
import github.hotstu.lu.engine.LuEngine;
import github.hotstu.lu.render.component.Frame;
import github.hotstu.lu.render.style.SimpleStyleEngine;


/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/11/20
 */
public class RenderContext extends LuContext {
    private final Context mContext;
    private final String id;
    private final Handler mMainHandler;
    private Handler mWokerHandler;
    private final NativeRenderAPI api;
    private Frame frame;
    private SimpleStyleEngine mStyle;

    public RenderContext(Context ctx, String id) {
        super();
        this.mContext = ctx;
        this.id = id;
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mWokerHandler = new Handler(LuEngine.getInstance().getWorkerHandler().getLooper());
        this.api = new NativeRenderAPI(this);
        this.mStyle = new SimpleStyleEngine();
    }

    public Frame wrap(LinearLayout view) {
        if (frame != null) {
            throw new IllegalStateException("context already wrapped with a root view");
        }
        frame = new Frame(this, view);
        return frame;
    }

    public NativeComponent<?> getRootComponent() {
        return frame;
    }

    public Context getContext() {
        return mContext;
    }

    public SimpleStyleEngine getStyle() {
        return mStyle;
    }

    public NativeRenderAPI getApi() {
        return api;
    }

    public void destroy() {
        //TODO
    }

    private final ArrayList<Runnable> pendingTasks = new ArrayList<>();
    private final Runnable paddingRunnable = () -> {
        ArrayList<Runnable> tasks;
        synchronized (pendingTasks) {
             tasks = new ArrayList<>(pendingTasks);
            pendingTasks.clear();
        }
        Log.d("RC", "post run async in batch：" + tasks.size());

        TransitionManager.beginDelayedTransition((ViewGroup) getRootComponent().mView);
        for (Runnable task : tasks) {
            task.run();
        }
    };
    /**
     * run in ui thread
     *
     * @param runnable
     */
    public void post(Runnable runnable) {
        if (Looper.myLooper() == mMainHandler.getLooper()) {
            Log.d("RC", "post run sync");
            runnable.run();
        } else {
            synchronized (this.pendingTasks) {
                this.pendingTasks.add(runnable);
            }
            this.mMainHandler.removeCallbacks(paddingRunnable);
            this.mMainHandler.post(paddingRunnable);
        }
    }

    /**
     * run in worker Thread
     * @param runnable
     */
    public void postWorker(Runnable runnable) {
        if (Looper.myLooper() == mWokerHandler.getLooper()) {
            runnable.run();
        } else {
            this.mWokerHandler.post(runnable);
        }
    }

    @Override
    protected void onContextScopeCreated(Scriptable scope) {
        LuEngine.getInstance().putProperty(scope, "$context", this);
    }

    public void attachStyles(Object o) {
        mStyle.attach((NativeObject) o);
    }
}
