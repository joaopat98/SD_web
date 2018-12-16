package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SongDetails implements Serializable {
    Integer songID, lyricWriterID, length;
    String name, lyrics, lyricWriterName, genre;
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

    public SongDetails(HashMap<String, String> props) {
        this.songID = Integer.valueOf(props.get("song_id"));
        this.name = props.get("name");
        this.genre = props.get("genre");
        this.length = props.get("length").equals("null") ? null : Integer.valueOf(props.get("length"));
        this.lyrics = props.get("lyrics");
        this.lyricWriterID = props.get("lyric_writer_id").equals("null") ? null : Integer.valueOf(props.get("lyric_writer_id"));
        this.lyricWriterName = props.get("lyric_writer");
        createAlbumList(props.get("albums"));
        createMemberList(props.get("composers"));
    }

    public void printInfo() {
        System.out.println("Song ID: " + songID);
        System.out.println("Name: " + name);
        System.out.println("Lyric Writer ID: " + lyricWriterID);
        System.out.println("Lyric Writer Name: " + lyricWriterName);
        System.out.println("Length: " + getLengthFormatted());
        System.out.println("Lyrics: " + lyrics);
        System.out.println("Genre: " + genre);


        if (!albumList.isEmpty()) {
            printAlbums(albumList);
        } else
            System.out.println("No albums associated.");

        if (!memberList.isEmpty()) {
            System.out.println("Composers:");
            for (ArtistMember member : memberList)
                System.out.println("Musician name: " + member.name + "; Musician ID: " + member.musician_id);
        } else
            System.out.println("No composers associated.");
    }

    static void printAlbums(ArrayList<Album> albumList) {
        System.out.println("Appears in:");
        for (int i = 0; i < albumList.size(); i++) {
            Album album = albumList.get(i);
            System.out.println(i+" - Album name: " + album.name + "; Album ID: " + album.albumID);
        }
    }

    public Integer getSongID() {
        return songID;
    }

    public Integer getLyricWriterID() {
        return lyricWriterID;
    }

    public Integer getLength() {
        return length;
    }

    public String getLengthFormatted() {
        return (length == null ? null : length / 60 + "m" + length % 60 + "s");
    }

    public String getName() {
        return name;
    }

    public String getLyrics() {
        return lyrics;
    }

    public String getLyricWriterName() {
        return lyricWriterName;
    }

    public String getGenre() {
        return genre;
    }

    public ArrayList<Album> getAlbumList() {
        return albumList;
    }

    public ArrayList<ArtistMember> getMemberList() {
        return memberList;
    }
}
