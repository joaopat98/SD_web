package Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIStart {
    private static class KeepAlive extends UnicastRemoteObject implements RMIKeepAlive{

        protected KeepAlive() throws RemoteException {
        }
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(7000);
            registry.rebind("keepalive", new KeepAlive());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
