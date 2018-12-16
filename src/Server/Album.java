package Server;

import java.io.Serializable;
import java.util.HashMap;

public class Album implements Serializable{
	String name;
	int albumID;

	public Album(HashMap<String,String> props) {
		this.name = props.get("name");
		this.albumID = Integer.valueOf(props.get("album_id"));
	}

	public String getName() {
		return name;
	}

	public int getAlbumID() {
		return albumID;
	}

	public String getOption() {
		return albumID + ": " + name;
	}
}
