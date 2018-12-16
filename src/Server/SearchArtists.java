package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchArtists implements Serializable{
    ArrayList<Artist> artistList;

    public SearchArtists(HashMap<String, String> props) {
        createArtistList(props.get("artists"));
    }

    private void createArtistList(String message) {
        artistList = new ArrayList<>();
        for (HashMap<String, String> props : RMIServer.subPropertiesHashmap(message)) {
            artistList.add(new Artist(props));
        }
    }

    public void printList() {
        if (!artistList.isEmpty()) {
            printArtists(artistList);
        } else
            System.out.println("No artists found.");
    }

    static void printArtists(ArrayList<Artist> artistList) {
        for (int i = 0; i < artistList.size(); i++) {
            Artist artist = artistList.get(i);
            System.out.println(i+" - Artist ID: " + artist.artistID + "\tArtist name: " + artist.name);
        }
    }

    public ArrayList<Artist> getArtistList() {
        return artistList;
    }
}
