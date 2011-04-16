

package pc.server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class CodaBloccante implements RMICoda {

    protected BlockingQueue<Integer> coda;

    public CodaBloccante(int capacity) {
        //Coda bloccante con capacità massima
        coda = new ArrayBlockingQueue<Integer>(capacity);
    }

       public void write (Integer id, Integer i) {
        try {
            coda.put(i);
        } catch (InterruptedException ex) { }

    }

    public Integer read (Integer id) {
        Integer tmp = null;
        try {
            tmp = coda.take();
        } catch (InterruptedException ex) { }
        return tmp;
    }

}
