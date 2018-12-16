package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchSongs implements Serializable{
	ArrayList<Song> songList;

	public SearchSongs(HashMap<String, String> props) {
		createSongList(props.get("songs"));
	}

	private void createSongList(String message) {
		songList = new ArrayList<>();
		for (HashMap<String, String> props : RMIServer.subPropertiesHashmap(message)) {
			songList.add(new Song(props));
		}
	}

	public void printList() {
		if (!songList.isEmpty())
			for (int i = 0; i < songList.size(); i++) {
				Song song = songList.get(i);
				System.out.println(i + " - Song ID: " + song.songID + "\tSong name: " + song.name);
			}
		else
			System.out.println("No songs found.");
	}

	public ArrayList<Song> getSongList() {
		return songList;
	}
}
