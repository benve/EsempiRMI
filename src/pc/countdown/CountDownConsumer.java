package pc.countdown;

import java.util.concurrent.CountDownLatch;
import pc.Consumer;
import pc.server.RMICoda;

public class CountDownConsumer extends Consumer {

    private CountDownLatch start;
    private CountDownLatch end;

    public CountDownConsumer(Integer id, RMICoda coda, CountDownLatch start, CountDownLatch end) {
        super(id, coda);
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try { start.await(); } catch (InterruptedException ex) { }
        super.run();
        end.countDown();
    }




}
