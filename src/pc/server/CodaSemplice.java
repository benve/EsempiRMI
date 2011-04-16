package pc.server;

import java.util.LinkedList;
import java.util.Queue;
import java.rmi.RemoteException;
import pc.Print;

public class CodaSemplice implements RMICoda {

    protected Queue<Integer> coda;
    protected Integer size;           //elementi attualmente nella coda
    protected Integer capacity; //capacità massima della coda


    public CodaSemplice (int capacity) {
        this.capacity = capacity;
        coda = new LinkedList<Integer>();
        size = 0;//all'inizio la coda è vuota
    }

    public void write (Integer id, Integer i) throws RemoteException {
        Print.s(id+" write");
        while(size >= capacity)//attende se la coda è piena
            try { Thread.sleep(1000); } catch (InterruptedException ex) { }

        //Quì può avvenire l'errore di syncronizzazione
        try { Thread.sleep((int)(Math.random()*1000)); } catch (InterruptedException ex) { }
        size++;
        try { Thread.sleep((int)(Math.random()*1000)); } catch (InterruptedException ex) { }

        coda.offer(i);
            
    }

    public Integer read (Integer id) throws RemoteException {
        Print.s(id+" read");
        while(size <= 0)//attende se la coda è vuota
            try { Thread.sleep(1000); } catch (InterruptedException ex) { }

        //Quì può avvenire l'errore di syncronizzazione
        try { Thread.sleep((int)(Math.random()*1000)); } catch (InterruptedException ex) { }
        size--;
        try { Thread.sleep((int)(Math.random()*1000)); } catch (InterruptedException ex) { }

        return coda.poll();   
    }


}

