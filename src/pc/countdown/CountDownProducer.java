package pc.countdown;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import pc.Producer;
import pc.server.RMICoda;

public class CountDownProducer extends Producer {

    private CountDownLatch start;
    private CountDownLatch end;


    public CountDownProducer(Integer id, RMICoda coda, CountDownLatch start, CountDownLatch end) {
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
