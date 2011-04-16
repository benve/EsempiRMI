
package pc;

import pc.server.RMICoda;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Consumer implements Runnable {

    private RMICoda server;
    private int id;

    public Consumer (int id, RMICoda server) {
        this.server = server;
        this.id = id;
    }

    //Toglie un valore dal server e controlla che non sia null
    private void consuma() {
            try {
//                System.out.print(server.read()+" ");
                if(server.read(id) == null) {
                    System.err.println("Valore null");
                    System.exit(-1);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
          try { Thread.sleep(1000); } catch (InterruptedException ex) { }
          consuma();
       }
    }

}

