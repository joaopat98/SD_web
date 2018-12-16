package Server;

import java.io.Serializable;
import java.util.HashMap;

public class Song implements Serializable {
	int songID;
	String name;

	public Song(HashMap<String, String> props) {
		this.songID = Integer.valueOf(props.get("song_id"));
		this.name = props.get("name");
	}

	public int getSongID() {
		return songID;
	}

	public String getName() {
		return name;
	}

	public String getOption() {
		return songID + ": " + name;
	}
}