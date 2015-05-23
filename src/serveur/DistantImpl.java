/**
 * Created by ZHU Yuting on 2015/05/07
 */
package serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.jms.JMSException;

import user.Chatroom;
import user.User;
import user.UserOperation;

public class DistantImpl extends UnicastRemoteObject implements Distant {

    protected DistantImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean login(String name, String password) throws RemoteException {
        return UserOperation.login(name, password);
    }

    @Override
    public User addUser(String name, String password) throws RemoteException {
        return UserOperation.addUser(name, password);
    }

    @Override
    public void newChatroom() throws RemoteException {
        new Chatroom();
    }

    @Override
    public void publier(User user, String message, String[] topics)
            throws JMSException, RemoteException {
        user.getPublisher().publier(message, topics);
    }

}
