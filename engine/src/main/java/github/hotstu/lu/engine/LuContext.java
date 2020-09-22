package github.hotstu.lu.engine;

import android.os.Bundle;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.concurrent.CountDownLatch;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/16/20
 */
public abstract class LuContext {

    private Scriptable mContextScope;
    private final Scriptable mShared;

    public LuContext() {
        this.mShared = LuEngine.getInstance().getSharedScope();
    }
    public LuContext(Scriptable sharedScope) {
        this.mShared = sharedScope;
    }

    public Scriptable getContextScope() {
        return mContextScope;
    }


    public CountDownLatch resetContextScope() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        LuEngine.getInstance().getWorkerHandler().post(() -> {
            this.mContextScope = resetContextScope(mShared);
            this.onContextScopeCreated(this.mContextScope);
            countDownLatch.countDown();
        });
        return countDownLatch;
    }


    protected abstract void onContextScopeCreated(Scriptable scope);


    public Bundle exec(String script) {
        ScriptTask task = new ScriptTask(this, script);
        LuEngine.getInstance().enqueueTask(task);
        task.await(3*1000);
        Bundle bundle = new Bundle();
        bundle.putBoolean("success", task.err == null);
        bundle.putString("data", task.output);
        bundle.putString("err", task.err);
        return bundle;
    }

    private Scriptable resetContextScope(Scriptable shared) {
        LuEngine.getInstance().enforceWorkerThread();
        // We can share the scope.
        Scriptable threadScope = Context.getCurrentContext().newObject(shared);
        threadScope.setPrototype(shared);

        // We want "threadScope" to be a new top-level
        // scope, so set its parent scope to null. This
        // means that any variables created by assignments
        // will be properties of "threadScope".
        threadScope.setParentScope(null);
        return threadScope;
    }
}
