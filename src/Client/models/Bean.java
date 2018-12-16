package Client.models;

import Server.IRMIServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static java.lang.System.exit;

public class Bean {

    IRMIServer server;
    private Registry registry;
    String username; // username and password supplied by the user
    private String password;
    static int MAX_TRIES = 10;
    static int MAX_RECONNECT = 20;
    static int WAIT_TIME = 500;

    Bean() {
        try {
            System.out.println("getting reg");
            registry = LocateRegistry.getRegistry(System.getenv("RMI_HOST").split(":")[0], Integer.valueOf(System.getenv("RMI_HOST").split(":")[1]));
            System.out.println("got reg");
        } catch (RemoteException e) {
            System.out.println("failed");
            System.out.println("Registry not running!\n");
            exit(1);
        }

        try {
            server = (IRMIServer) registry.lookup("server");
            server.testAlive();

        } catch (RemoteException | NotBoundException e) {
            System.out.println("test failed");
            System.out.println("Servers are down.\n");
        }
    }

    Bean(UserBean userBean) {
        this();
        this.username = userBean.username;
        this.password = userBean.password;
    }

    void reconnect() {
        for (int i = 0; i < MAX_TRIES; i++) {
            try {
                server.testAlive();
                return;
            } catch (RemoteException e) {
                try {
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    exit(1);
                }
            }
        }
        for (int i = 0; i < MAX_RECONNECT; i++) {
            try {
                server = (IRMIServer) registry.lookup("server");
                return;
            } catch (RemoteException | NotBoundException ignored) {
            }
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Servers are down, application will shutdown...");
        exit(1);
    }
}
