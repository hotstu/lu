package github.hotstu.lu.engine;

import android.util.Log;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/10/20
 */
public class ConsoleImpl {
    public void log(Object... objects) {
        StringBuilder sb = new StringBuilder();
        for (Object object : objects) {
            sb.append(object);
            sb.append(',');
        }
        sb.delete(sb.length() - 1, sb.length());
        Log.d("Console", sb.toString());
    }

    public void error(Object... objects) {
        StringBuilder sb = new StringBuilder();
        for (Object object : objects) {
            sb.append(object);
            sb.append(',');
        }
        sb.delete(sb.length() - 1, sb.length());
        Log.e("Console", sb.toString());
    }

}
