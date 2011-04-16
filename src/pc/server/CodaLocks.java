
package pc.server;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import pc.Print;

public class CodaLocks extends CodaSemplice {

    private final Lock myLock;
    private final Condition fullQueue;
    private final Condition emptyQueue;

    public CodaLocks(int capacity) {
        super(capacity);
        myLock = new ReentrantLock();
        fullQueue = myLock.newCondition();
        emptyQueue = myLock.newCondition();
    }

    @Override
    public void write(Integer id, Integer i) {

        myLock.lock();
        try {
//            Print.s(id + " write lock preso");

            while (size >= capacity)//attende se la coda è piena
                fullQueue.await();

            size++;
            coda.offer(i);

            emptyQueue.signal();

        } catch (InterruptedException ex) {
            Logger.getLogger(CodaLocks.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            Print.s(id + " write lock rilasciato");
            myLock.unlock();
        }

    }

    @Override
    public Integer read(Integer id) {
        Integer tmp = null;

        myLock.lock();
        try {
//            Print.s(id + " read lock preso");

            while (size <= 0) //attende se la coda è vuota
                emptyQueue.await();

            size--;
            tmp = coda.poll();

            fullQueue.signal();

        } catch (InterruptedException ex) {
            Logger.getLogger(CodaLocks.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            Print.s(id + " read lock rilasciato");
            myLock.unlock();
        }

        return tmp;

    }
}
