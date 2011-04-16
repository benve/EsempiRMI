
package pc.server;

import pc.Print;


public class CodaSincronizzata extends CodaSemplice {

    public CodaSincronizzata(int capacity) {
        super(capacity);
    }

    @Override
    public void write (Integer id, Integer i) {

        synchronized(coda) {
//            Print.s(id+" write");
//            try { Thread.sleep(1000); } catch (InterruptedException ex) { }
            while(size >= capacity) {//attende se la coda è piena
//                Print.s(id+" write.wait");
                try { coda.wait(); } catch (InterruptedException ex) { }
//                try { Thread.sleep(1000); } catch (InterruptedException ex) { }
            }
//            Print.s(id+" write ok");

        //Quì può avvenire l'errore di syncronizzazione
//            try { Thread.sleep(1000); } catch (InterruptedException ex) { }
            size++;
        

            coda.offer(i);
            

//            Print.s(id+" write notifico");
            coda.notify();
//            try { Thread.sleep(1000); } catch (InterruptedException ex) { }
//            Print.s(id+" endwrite");
        }

    }

    @Override
    public Integer read (Integer id) {
        
        Integer tmp;
        synchronized(coda) {
//            Print.s(id+" read");
//            try { Thread.sleep(1000); } catch (InterruptedException ex) { }
            while(size <= 0) {//attende se la coda è vuota
//                Print.s(id+" read.wait");
                try { coda.wait(); } catch (InterruptedException ex) { }
//                try { Thread.sleep(1000); } catch (InterruptedException ex) { }
            }
                
//            Print.s(id+" read ok");

        //Quì può avvenire l'errore di syncronizzazione
//        try { Thread.sleep(1000); } catch (InterruptedException ex) { }
            size--;
        

            tmp = coda.poll();
//            Print.s(id+" read notifico");
            coda.notify();
//            try { Thread.sleep(10000); } catch (InterruptedException ex) { }
//            Print.s(id+" endread");
        }

        return tmp;
    
    }

}
