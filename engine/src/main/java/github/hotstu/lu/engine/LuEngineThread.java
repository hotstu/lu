package github.hotstu.lu.engine;

import android.os.HandlerThread;

import com.faendir.rhino_android.RhinoAndroidHelper;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.WrapFactory;

import java.util.Locale;

public class LuEngineThread extends HandlerThread {

    public LuEngineThread(String name) {
        super(name);
    }

    public LuEngineThread(String name, int priority) {
        super(name, priority);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        init();
    }


    public void init() {
        if (Context.getCurrentContext() == null) {
            Context context = RhinoAndroidHelper.prepareContext();
            context.setOptimizationLevel(-1);
            context.setLanguageVersion(Context.VERSION_ES6);
            context.setLocale(Locale.getDefault());
            context.setWrapFactory(new WrapFactory() {
                @Override
                public Object wrap(Context cx, Scriptable scope, Object obj, Class<?> staticType) {
                    return super.wrap(cx, scope, obj, staticType);
                }

                @Override
                public Scriptable wrapNewObject(Context cx, Scriptable scope, Object obj) {
                    return super.wrapNewObject(cx, scope, obj);
                }

                @Override
                public Scriptable wrapAsJavaObject(Context cx, Scriptable scope, Object javaObject, Class<?> staticType) {
                    return super.wrapAsJavaObject(cx, scope, javaObject, staticType);
                }

                @Override
                public Scriptable wrapJavaClass(Context cx, Scriptable scope, Class<?> javaClass) {
                    return super.wrapJavaClass(cx, scope, javaClass);
                }
            });
        }
    }
}