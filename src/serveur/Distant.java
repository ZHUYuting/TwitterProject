/**
 * Created by ZHU Yuting on 2015/05/07
 */
package serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.jms.JMSException;

import user.User;

public interface Distant extends Remote {
    public void newChatroom() throws RemoteException;

    public User addUser(String name, String password) throws RemoteException;

    public boolean login(String name, String password) throws RemoteException;

    public void publier(User user, String message, String[] topics)
            throws JMSException, RemoteException;
}
