package github.hotstu.lu.play;

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
