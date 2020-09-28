package github.hotstu.lu.play;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

import fi.iki.elonen.NanoHTTPD;
import okio.ByteString;

public class ServerApp extends NanoHTTPD {

    private static ServerApp sInstance;
    private final Gson g;
    private final StaticHandler staticHandler;
    private Function<String, String> scriptRunner;
    private String msg;

    public static ServerApp getInstance() {
        if (sInstance == null) {
            synchronized (ServerApp.class) {
                if (sInstance == null) {
                    sInstance = new ServerApp();
                }
            }
        }
        return sInstance;
    }

    public ServerApp() {
        super("0.0.0.0", 8080);
        g = new Gson();

        staticHandler = new StaticHandler(ModuleApp.application);
    }

    public void startUp() {
        new Thread(() -> {
            try {
                WifiManager wifiManager = (WifiManager) ModuleApp.application.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                int ipAddress = connectionInfo.getIpAddress();
                String address = Formatter.formatIpAddress(ipAddress);
                start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
                System.out.println(String.format("\nRunning! Point your browsers to http://%s:8080/ \n", address));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void shutdown() {
        stop();
    }

    @Override
    public Response serve(IHTTPSession session) {
        final String uri = session.getUri();
        if (uri.matches("^/run$")) {
            HashMap<String, String> files = new HashMap<>();
            try {
                session.parseBody(files);
                final String postData = files.get("postData");
                if (postData != null && !"".equals(postData)) {
                    if (scriptRunner != null) {
                        String result = scriptRunner.apply(postData);
                        return newFixedLengthResponse(Response.Status.OK, "application/json", result);
                    }
                }
            } catch (IOException | ResponseException e) {
                e.printStackTrace();
            }
            return newFixedLengthResponse(Response.Status.OK, "application/json", "{\"success\": false}");
        } else if (uri.matches("^/reset$")) {
            if (scriptRunner != null) {
                scriptRunner.apply("$context.resetContextScope()");
            }
            return newFixedLengthResponse(Response.Status.OK, "application/json", "{\"success\": true}");
        } else if (uri.matches("^/$")) {
            if (msg == null) {
                try (BufferedInputStream inputStream = new BufferedInputStream(ModuleApp.application.getAssets().open("form.html"));) {
                    ByteString read = ByteString.read(inputStream, inputStream.available());
                    msg = read.string(Charset.forName("UTF-8"));
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            return newFixedLengthResponse(msg);
        } else if (staticHandler.isHandle(session)) {
            try {
                return staticHandler.handle(session);
            } catch (IOException e) {
                e.printStackTrace();
                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/html", "Error!");
            }
        } else {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/html", "Page Not Found");
        }


    }

    public void setCallable(Function<String, String> function) {
        this.scriptRunner = function;
    }
}