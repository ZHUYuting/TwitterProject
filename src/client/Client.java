/**
 * Created by ZHU Yuting on 2015/05/07
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.management.remote.rmi.RMIConnection;

import serveur.Distant;
import user.User;

public class Client {

    public Client() {
    }

    public static void main(String[] args) {
        try {
            System.out.println("Client running...");
            Distant l = (Distant) Naming
                    .lookup("rmi://127.0.0.1:8888/LoginServeur");
            System.out.println("connect success!");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    System.in));

            // lancer les services
            System.out.println("Welcome, new client, please enter:");
            System.out.print("your username: ");
            String newname = br.readLine();
            System.out.print("your password: ");
            String password = br.readLine();
            User user1 = l.addUser(newname, password);

            // login
            System.out.println("Please login:");
            System.out.print("your username: ");
            String loginname = br.readLine();
            System.out.print("your password: ");
            String loginpwd = br.readLine();

            l.newChatroom();
            Boolean login = l.login(loginname, loginpwd);
            // ajouter un client et le lancer
            if (login) {
                String topics[] = { "a", "b" };
                l.publier(user1, "hello", topics);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            Logger.getLogger(RMIConnection.class.getName()).log(Level.SEVERE,
                    null, e);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
