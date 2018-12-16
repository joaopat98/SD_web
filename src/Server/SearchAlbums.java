package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchAlbums implements Serializable{
    ArrayList<Album> albumList;

    public SearchAlbums(HashMap<String, String> props) {
        createAlbumList(props.get("albums"));
    }

    private void createAlbumList(String message) {
        albumList = new ArrayList<>();
        for (HashMap<String, String> props : RMIServer.subPropertiesHashmap(message)) {
            albumList.add(new Album(props));
        }
    }

    public void printList() {
        if (!albumList.isEmpty())
            for (int i = 0; i < albumList.size(); i++) {
                Album album = albumList.get(i);
                System.out.println(i+" - Album ID: " + album.albumID + "\tAlbum name: " + album.name);
            }
        else
            System.out.println("No albums found.");
    }

    public ArrayList<Album> getAlbumList() {
        return albumList;
    }
}
