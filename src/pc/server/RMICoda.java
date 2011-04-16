/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pc.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author benve
 */
public interface RMICoda extends Remote {

    public Integer read(Integer id) throws RemoteException;

    public void write(Integer id, Integer i) throws RemoteException;

}
