package github.hotstu.lu.engine;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ScriptTask {
    public final String input;
    public final LuContext context;
    private final CountDownLatch lock;
    public String output = null;
    public String err = null;


    public ScriptTask(LuContext context, String input) {
        this.context = context;
        this.input = input;
        this.lock = new CountDownLatch(1);
    }

    public void await(long timeout) {
        try {
            lock.await(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendBack() {
        lock.countDown();
    }
}
