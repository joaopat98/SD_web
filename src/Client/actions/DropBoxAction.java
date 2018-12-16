package Client.actions;

import Client.dropbox.DropBoxAPI;

public class DropBoxAction extends Action{

    private String code = null, state = null;

    public String setUserCode() {
        System.out.println(code);
        session.remove("DropBoxUrl");
        getDropBoxBean().setDropBoxToken(DropBoxAPI.getAccessToken(code));
        return SUCCESS;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
