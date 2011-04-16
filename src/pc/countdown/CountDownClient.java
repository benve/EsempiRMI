package pc.countdown;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import pc.Client;
import pc.server.RMICoda;

public class CountDownClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        if (args.length != 3) {
            System.err.println("3 parametri: numero produttori, numero consumatori, indirizzo_IP_server");
            System.exit(1);
        }

        int nPro = Integer.valueOf(args[0]);
        int nCon = Integer.valueOf(args[1]);

        try {
            //Ottengo l'oggetto remoto RMICoda
            Registry registry = LocateRegistry.getRegistry(args[2], 1099);
            RMICoda coda = (RMICoda) registry.lookup("Server");

//        RMICoda coda = new CodaReadWriteLock(10);

            CountDownLatch start = new CountDownLatch(1);
            CountDownLatch end = new CountDownLatch(nCon + nPro);

            //creo i thread dei consumatori
            for (int i = 0; i < nCon; i++) {
                CountDownConsumer consumatore = new CountDownConsumer(i, coda, start, end);
                Thread mythread = new Thread(consumatore);
                mythread.start();
            }

            //creo i thread dei produttori
            for (int i = 0; i < nPro; i++) {
                CountDownProducer produttore = new CountDownProducer(i, coda, start, end);
                Thread mythread = new Thread(produttore);
                mythread.start();
            }

            //Faccio partire tutti i thread
            start.countDown();

            try {
                //Attendo la terminazione di tutti i Thread
                end.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(CountDownClient.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println();

        } catch (NotBoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
