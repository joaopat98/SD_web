/**
 * Raul Barbosa 2014-11-07
 */
package Client.actions;

import Client.dropbox.DropBoxAPI;
import Client.models.UserBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class UserAction extends Action {
    private static String NO_USER = "no_user";
    private static String ALREADY_EXISTS = "already_exists";
    private static String NULL_FIELD = "null_field";


    private static final long serialVersionUID = 4L;
    private String username = null, password = null;

    public String login() {
        // any username is accepted without confirmation (should check using RMI)
        if (this.username != null && !username.equals("") && this.password != null && !password.equals("")) {

            if (getUserBean().login(this.username, this.password)) {
                session.put("isEditor", getUserBean().isEditor());
                return SUCCESS;
            } else {
                return NO_USER;
            }
        } else
            return NULL_FIELD;
    }

    public String register() {
        // any username is accepted without confirmation (should check using RMI)
        if (this.username != null && !username.equals("") && this.password != null && !password.equals("")) {

            if (getUserBean().register(this.username, this.password)) {
                session.put("isEditor", getUserBean().isEditor());
                return SUCCESS;
            } else {
                return ALREADY_EXISTS;
            }
        } else
            return NULL_FIELD;
    }

    public String loadUserMenu() {
        session.put("DropBoxUrl", DropBoxAPI.getAuthorizeUrl(getUserBean().getUsername()));
        return SUCCESS;
    }

    public void setUsername(String username) {
        this.username = username; // will you sanitize this input? maybe use a prepared statement?
    }

    public void setPassword(String password) {
        this.password = password; // what about this input?
    }
}
