/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author benve
 */
public class Server {

    public static void main(String[] args) throws Exception {
        try {

            if (args.length != 1) {
                System.err.println("1 parametro: dimensione coda");
                System.exit(1);
            }

            int size = Integer.valueOf(args[0]);
            //Decommentare il tipo di Coda

            CodaSemplice remoteServer = new CodaSemplice(size);//Non sincronizzata !
            //CodaSincronizzata remoteServer = new CodaSincronizzata(5);
            //CodaBloccante remoteServer = new CodaBloccante(5);
            //CodaLocks remoteServer = new CodaLocks(5);

            RMICoda stub = (RMICoda) UnicastRemoteObject.exportObject(remoteServer, 0);//la porta è scelta da registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Server", stub);
            System.err.println("Server ready");

        } catch (Exception ex) {
            Logger.getLogger(CodaSemplice.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
