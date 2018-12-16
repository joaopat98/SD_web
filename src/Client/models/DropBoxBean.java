package Client.models;

import java.rmi.RemoteException;

public class DropBoxBean extends Bean {

    public DropBoxBean(UserBean userBean) {
        super(userBean);
    }

    public boolean setDropBoxToken(String token) {
        while (true) {
            try {
                return server.setDropBoxToken(username, token);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

}
