package Client.models;

import Server.INotificationCenter;

import java.io.Serializable;
import java.rmi.RemoteException;

public class NotificationCenter implements INotificationCenter, Serializable {
    @Override
    public void notify(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void isAlive() throws RemoteException {

    }
}
