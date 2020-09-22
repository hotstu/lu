package github.hotstu.lu.play;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;

import java.util.HashMap;

import github.hotstu.lu.base.IPlayCallback;
import github.hotstu.lu.base.IPlayService;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 7/15/20
 */
public class HttpDaemonService extends Service {

    class MyBinder extends IPlayService.Stub {
        private final Object accessLock = new Object();
        private  IPlayCallback callback = null;


        public IPlayCallback getCallback() {
            return callback;
        }

        @Override
        public void setCallback(final IPlayCallback callback)  {
            synchronized (accessLock) {
                this.callback = callback;
                updateState();
            }

        }



        private void updateState() {

            boolean empty = callback == null;

            if (empty) {
                stopServer();
            } else {
                startServer();
            }

        }

    }

    MyBinder mBinder = null;
    Gson g = new Gson();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (mBinder == null) {
            mBinder = new MyBinder();
        }
        return mBinder;
    }

    Function<String, String> scriptRunner = input -> {
        HashMap<String, Object> ret = new HashMap<>();

        if ((mBinder != null && mBinder.getCallback() != null)) {

            try {
                Bundle bundle = mBinder.getCallback().onEvent(0, input);
                ret.put("success",  bundle.getBoolean("success", false));
                ret.put("data",  bundle.getString("data", null));
                ret.put("err", bundle.getString("err", null));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            ret.put("success", false);
            ret.put("data", null);
            ret.put("err", "LuEngine 未绑定");
        }
        return g.toJson(ret);
    };

    @Override
    public void onCreate() {
        super.onCreate();
        ServerApp.getInstance().setCallable(scriptRunner);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        ServerApp.getInstance().setCallable(null);
        super.onDestroy();
    }

    private void stopServer() {
        ServerApp.getInstance().shutdown();
    }

    private int startServer() {
        ServerApp.getInstance().startUp();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("default", "default", importance);
            channel.setDescription("default");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("lu")
                .setContentText("雍和宫")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        startForeground(1, mBuilder.build());
        return 0;
    }
}
