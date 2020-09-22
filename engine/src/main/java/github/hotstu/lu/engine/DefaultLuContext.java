package github.hotstu.lu.engine;

import org.mozilla.javascript.Scriptable;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/18/20
 */
public class DefaultLuContext extends LuContext {
    public DefaultLuContext(Scriptable sharedScope) {
        super(sharedScope);
    }

    @Override
    protected void onContextScopeCreated(Scriptable scope) {
        LuEngine.getInstance().putProperty(scope, "$app", ModuleApp.application);
        LuEngine.getInstance().putProperty(scope, "$context", this);

    }


}
