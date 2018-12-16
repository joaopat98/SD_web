package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchMusicians implements Serializable{
    ArrayList<Musician> musicianList;

    public SearchMusicians(HashMap<String, String> props) {
        createMusicianList(props.get("musicians"));
    }

    private void createMusicianList(String message) {
        musicianList = new ArrayList<>();
        for (HashMap<String, String> props : RMIServer.subPropertiesHashmap(message)) {
            musicianList.add(new Musician(props));
        }
    }

    public void printList() {
        if (!musicianList.isEmpty()) {
            printMusicians(musicianList);
        } else
            System.out.println("No musicians found.");
    }

    static void printMusicians(ArrayList<Musician> musicianList) {
        for (int i = 0; i < musicianList.size(); i++) {
            Musician musician = musicianList.get(i);
            System.out.println(i+" - Musician ID: " + musician.musicianID + "\tMusician name: " + musician.name);
        }
    }

    public ArrayList<Musician> getMusicianList() {
        return musicianList;
    }
}
