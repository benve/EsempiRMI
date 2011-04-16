package pc;

import pc.server.RMICoda;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Producer implements Runnable {

    private RMICoda server;
    int id;

    public Producer(int id, RMICoda server) {
        this.server = server;
        this.id = id;
    }

    //Inserisce un Intero random nel server
    private void produci() {
            try {
                server.write(id, 
                        (int)(Math.random()*10)//crea un intero random
                        );
            } catch (RemoteException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }

        
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            try { Thread.sleep(1000); } catch (InterruptedException ex) { }
            produci();
        }
    }
}

