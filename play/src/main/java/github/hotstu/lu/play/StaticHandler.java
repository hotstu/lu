package github.hotstu.lu.play;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fi.iki.elonen.NanoHTTPD;

public class StaticHandler {

    private final Context context;
    Pattern pattern = Pattern.compile("^/static/([\\s|\\S]*)$", Pattern.CASE_INSENSITIVE);

    public StaticHandler(Context context) {
        this.context = context;
    }

    public boolean isHandle(NanoHTTPD.IHTTPSession session) {
        final String uri = session.getUri();
        final Matcher matcher = pattern.matcher(uri);
        return matcher.find();
    }

    public NanoHTTPD.Response handle(NanoHTTPD.IHTTPSession session) throws IOException {
        final String uri = session.getUri();
        final Matcher matcher = pattern.matcher(uri);
        if (matcher.matches()) {
            final String subPath = matcher.group(1);
            //TODO 文件访问控制
            String mimeType = "application/octet-stream";
            if (subPath.endsWith("js")) {
                mimeType = "text/javascript";
            } else if (subPath.endsWith("css")) {
                mimeType = "text/css";
            } else if (subPath.endsWith("html")) {
                mimeType = "text/html";
            }
            final InputStream inputStream = context.getAssets().open("web/" + subPath);
            return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, mimeType, inputStream);
        }
        return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.NO_CONTENT,"application/octet-stream","");
    }
}
