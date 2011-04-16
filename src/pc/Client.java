package pc;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pc.server.RMICoda;

public class Client {

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
            
//            RMICoda coda = new CodaSemplice(5); //Per non usare RMI

            //Tengo una lista dei thread creati per attenderne in seguito la terminazione
            LinkedList<Thread> threadList = new LinkedList<Thread>();

            //faccio partire i thread dei consumatori
            for (int i = 0; i < nCon; i++) {
                Consumer consumatore = new Consumer(i, coda);
                Thread mythread = new Thread(consumatore);
                mythread.start();
                threadList.add(mythread);
            }

            //faccio partire i thread dei produttori
            for (int i = 0; i < nPro; i++) {
                Producer produttore = new Producer(i, coda);
                Thread mythread = new Thread(produttore);
                mythread.start();
                threadList.add(mythread);
            }

            //Attendo la terminazione di tutti i Thread
            for (Thread thread : threadList) {
                try {
                    thread.join();
                } catch (InterruptedException ex) {
                }
            }

        } catch (NotBoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
