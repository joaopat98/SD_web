package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ArtistDetails implements Serializable {
    String artistName, description;
    int artistID;
    ArrayList<Album> albumList;
    ArrayList<ArtistMember> memberList;

    private void createAlbumList(String message) {
        albumList = new ArrayList<>();
        for (HashMap<String, String> props : RMIServer.subPropertiesHashmap(message)) {
            albumList.add(new Album(props));
        }
    }

    private void createMemberList(String message) {
        memberList = new ArrayList<>();
        for (HashMap<String, String> props : RMIServer.subPropertiesHashmap(message)) {
            memberList.add(new ArtistMember(props));
        }
    }

    public ArtistDetails(HashMap<String, String> props) {
        this.artistName = props.get("name");
        this.description = props.get("description");
        this.artistID = Integer.valueOf(props.get("artist_id"));
        createAlbumList(props.get("albums"));
        createMemberList(props.get("members"));
    }

    public void printInfo() {
        System.out.println("Artist ID: " + artistID + "\n" +
                "Artist name: " + artistName + "\n" +
                "Description: " + description);
        if (!albumList.isEmpty()) {
            printAlbums(albumList);
        } else
            System.out.println("No albums associated.");

        if (!memberList.isEmpty()) {
            printMusicians(memberList);
        } else
            System.out.println("No members associated.");

    }

    static void printMusicians(ArrayList<ArtistMember> memberList) {
        System.out.println("Members:");
        for (int i = 0; i < memberList.size(); i++) {
            ArtistMember member = memberList.get(i);
            System.out.println(i+" - Musician name: " + member.name + "; Musician ID: " + member.musician_id);
        }
    }

    static void printAlbums(ArrayList<Album> albumList) {
        System.out.println("Albums:");
        for (int i = 0; i < albumList.size(); i++) {
            Album album = albumList.get(i);
            System.out.println(i + " - Album name: " + album.name + "; Album ID: " + album.albumID);
        }
    }

    static ArrayList<Musician> musiciansFromMembers(ArrayList<ArtistMember> members) {
        ArrayList<Musician> musicians = new ArrayList<>();
        for (ArtistMember member: members) {
            musicians.add(new Musician(member));
        }
        return musicians;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getDescription() {
        return description;
    }

    public int getArtistID() {
        return artistID;
    }

    public ArrayList<Album> getAlbumList() {
        return albumList;
    }

    public ArrayList<ArtistMember> getMemberList() {
        return memberList;
    }
}