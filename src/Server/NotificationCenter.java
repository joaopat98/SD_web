package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class NotificationCenter extends UnicastRemoteObject implements INotificationCenter{

	protected NotificationCenter() throws RemoteException {

	}

	@Override
	public void notify(String message) {
		System.out.println("Notification: " + message);
	}

	@Override
	public void isAlive() {

	}
}
