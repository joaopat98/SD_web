package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface INotificationCenter extends Remote{
	void notify(String message) throws RemoteException;
	void isAlive() throws RemoteException;
}
