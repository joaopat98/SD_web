package Client.actions;


import Client.models.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public abstract class Action extends ActionSupport implements SessionAware {


    Map<String, Object> session;

    public AlbumBean getAlbumBean() {
        if (!session.containsKey("AlbumBean"))
            this.setAlbumBean(new AlbumBean((UserBean) session.get("UserBean")));
        return (AlbumBean) session.get("AlbumBean");
    }

    public void setAlbumBean(AlbumBean albumBean) {
        this.session.put("AlbumBean", albumBean);
    }

    public ArtistBean getArtistBean() {
        if (!session.containsKey("ArtistBean"))
            this.setArtistBean(new ArtistBean((UserBean) session.get("UserBean")));
        return (ArtistBean) session.get("ArtistBean");
    }

    public void setArtistBean(ArtistBean artistBean) {
        this.session.put("ArtistBean", artistBean);
    }

    public SongBean getSongBean() {
        if (!session.containsKey("SongBean"))
            this.setSongBean(new SongBean((UserBean) session.get("UserBean")));
        return (SongBean) session.get("SongBean");
    }

    public void setSongBean(SongBean songBean) {
        this.session.put("SongBean", songBean);
    }

    public StudioBean getStudioBean() {
        if (!session.containsKey("StudioBean"))
            this.setStudioBean(new StudioBean((UserBean) session.get("UserBean")));
        return (StudioBean) session.get("StudioBean");
    }

    public void setStudioBean(StudioBean studioBean) {
        this.session.put("StudioBean", studioBean);
    }

    public LabelBean getLabelBean() {
        if (!session.containsKey("LabelBean"))
            this.setLabelBean(new LabelBean((UserBean) session.get("UserBean")));
        return (LabelBean) session.get("LabelBean");
    }

    public void setLabelBean(LabelBean labelBean) {
        this.session.put("LabelBean", labelBean);
    }

    public MusicianBean getMusicianBean() {
        if (!session.containsKey("MusicianBean"))
            this.setMusicianBean(new MusicianBean((UserBean) session.get("UserBean")));
        return (MusicianBean) session.get("MusicianBean");
    }

    public void setMusicianBean(MusicianBean musicianBean) {
        this.session.put("MusicianBean", musicianBean);
    }

    public UserBean getUserBean() {
        if (!session.containsKey("UserBean"))
            this.setUserBean(new UserBean());
        return (UserBean) session.get("UserBean");
    }

    public void setUserBean(UserBean userBean) {
        this.session.put("UserBean", userBean);
    }

    public DropBoxBean getDropBoxBean() {
        if (!session.containsKey("DropBoxBean"))
            this.setDropBoxBean(new DropBoxBean((UserBean) session.get("UserBean")));
        return (DropBoxBean) session.get("DropBoxBean");
    }

    public void setDropBoxBean(DropBoxBean dropBoxBean) {
        this.session.put("DropBoxBean", dropBoxBean);
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}
