package Client.models;

import Server.IRMIServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class UserBean extends Bean {
    String username; // username and password supplied by the user
    String password;
    private NotificationCenter notifCenter;


    public UserBean() {
        super();
    }

    public boolean login(String username, String password) {
        NotificationCenter notifCenter = new NotificationCenter();
        while (true) {
            try {
                boolean logged_in = server.login(username, password, notifCenter);
                setUserCredentials(username, password, notifCenter);
                return logged_in;
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public boolean register(String username, String password) {
        NotificationCenter notifCenter = new NotificationCenter();
        while (true) {
            try {
                boolean logged_in = server.register(username, password, notifCenter);
                setUserCredentials(username, password, notifCenter);
                return logged_in;
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public boolean isEditor() {
        while (true) {
            try {
                return server.isEditor(username);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    private void setUserCredentials(String username, String password, NotificationCenter notifCenter) {
        this.username = username;
        this.password = password;
        this.notifCenter = notifCenter;
    }

    public String getUsername() {
        return username;
    }
}
