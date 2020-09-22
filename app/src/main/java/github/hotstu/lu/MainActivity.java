package github.hotstu.lu;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import github.hotstu.lu.base.IPlayCallback;
import github.hotstu.lu.base.IPlayService;
import github.hotstu.lu.engine.LuEngine;
import github.hotstu.lu.play.HttpDaemonService;
import github.hotstu.lu.render.RenderContext;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {

    private ServiceConnection conn;
    private EventHandler mEventHandler = new EventHandler();
    private IPlayService engineService;
    private RenderContext defaultContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LuEngine.getInstance().start();
        defaultContext = new RenderContext(this, "root");
        try {
            defaultContext.resetContextScope().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        defaultContext.wrap(findViewById(R.id.root));
        InputStream open = null;
        try {
            open = new BufferedInputStream(getAssets().open("main.js"));
            ByteString read = ByteString.read(open, open.available());
            defaultContext.exec(read.utf8());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (open != null) {
                try {
                    open.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        bindService();
    }

    public class EventHandler extends IPlayCallback.Stub {
        public EventHandler() {
        }

        @Override
        public Bundle onEvent(int what, String msg) throws RemoteException {
            Log.d("MainActivity", Thread.currentThread().getName() + "event=>" + msg);
            return defaultContext.exec(msg);
        }
    }


    private void bindService() {
        Intent i = new Intent(this, HttpDaemonService.class);
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                engineService = IPlayService.Stub.asInterface(service);
                try {
                    engineService.setCallback(mEventHandler);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }

            @Override
            public void onBindingDied(ComponentName name) {
                unbindService(this);

                bindService();
            }
        };
        bindService(i, conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (engineService != null) {
            try {
                engineService.setCallback(null);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(conn);
        LuEngine.getInstance().shutDown();
        super.onDestroy();
    }
}