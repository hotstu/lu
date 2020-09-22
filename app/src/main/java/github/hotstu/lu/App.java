package github.hotstu.lu;

import android.app.Application;

import java.util.Iterator;
import java.util.ServiceLoader;

import github.hotstu.lu.base.IModule;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/16/20
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ServiceLoader<IModule> load = ServiceLoader.load(IModule.class);
        Iterator<IModule> iterator = load.iterator();
        while (iterator.hasNext()) {
            try {
                IModule next = iterator.next();
                next.onCreate(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
