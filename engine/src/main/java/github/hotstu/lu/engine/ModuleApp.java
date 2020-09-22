package github.hotstu.lu.engine;

import android.content.Context;

import github.hotstu.lu.base.IModule;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/16/20
 */
public class ModuleApp implements IModule {
    public static Context application;
    @Override
    public void onCreate(Context context) {
        application = context;
    }
}
