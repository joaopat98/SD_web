package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AlbumDetails implements Serializable {
	Integer albumID, artistID, labelID;
	float average_score;
	String albumName, artistName, labelName, description;
	ArrayList<Song> songList;
	ArrayList<Studio> studioList;
	ArrayList<AlbumReview> reviewList;

	private void createSongList(String message){
	    songList = new ArrayList<>();
		for(HashMap<String, String> props:RMIServer.subPropertiesHashmap(message))
			songList.add(new Song(props));
	}

	private void createStudioList(String message){
		studioList = new ArrayList<>();
		for(HashMap<String, String> props:RMIServer.subPropertiesHashmap(message))
			studioList.add(new Studio(props));
	}

	private void createReviewList(String message){
	    reviewList = new ArrayList<>();
        for(HashMap<String, String> props:RMIServer.subPropertiesHashmap(message))
            reviewList.add(new AlbumReview(props));
	}

	public AlbumDetails(HashMap<String, String> props) {
		this.albumID = Integer.valueOf(props.get("album_id"));
		this.artistID = props.get("artist_id").equals("null") ? null : Integer.valueOf(props.get("artist_id"));
		this.labelID = props.get("label_id").equals("null") ? null : Integer.valueOf(props.get("label_id"));
		this.albumName = props.get("album_name");
		this.artistName = props.get("artist_name");
		this.labelName = props.get("label_name");
		this.description = props.get("description");
		this.average_score = Float.valueOf(props.get("score"));
		createSongList(props.get("songs"));
		createReviewList(props.get("reviews"));
		createStudioList(props.get("studios"));
	}

	public void printInfo() {
		System.out.println("Album ID: " + albumID + "\n" +
				"Album name: " + albumName + "\n" +
				"Artist ID: " + artistID + "\n" +
				"Artist name: " + artistName + "\n" +
				"Label ID: " + labelID + "\n" +
				"Label name: " + labelName + "\n" +
				"Average score: " + average_score + "\n" +
				"Description: " + description);
		if(!songList.isEmpty()) {
			printSongs(songList);
		}
		else
			System.out.println("No songs associated.");

		if(!studioList.isEmpty()) {
			printStudios(studioList);
		}
		else
			System.out.println("No studios associated.");
		
		if(!reviewList.isEmpty()) {
			System.out.println("Reviews:");
			for (int i = 0; i < reviewList.size(); i++) {
				System.out.println(reviewList.get(i).user + " review: Score - " + reviewList.get(i).score + "\tDetails: " + reviewList.get(i).details);
			}

		}
		else
			System.out.println("No reviews associated.");
	}

	static void printStudios(ArrayList<Studio> studioList) {
		System.out.println("Recorded in:");
		for (int i = 0; i < studioList.size(); i++) {
			System.out.println(i + " - Studio name: " + studioList.get(i).name + " Song ID: " + studioList.get(i).studioID);
		}
	}

	static void printSongs(ArrayList<Song> songList) {
		System.out.println("Songs:");
		for (int i = 0; i < songList.size(); i++) {
			System.out.println(i + " - Song name: " + songList.get(i).name + "; Song ID: " + songList.get(i).songID);
		}
	}

	public Integer getAlbumID() {
		return albumID;
	}

	public Integer getArtistID() {
		return artistID;
	}

	public Integer getLabelID() {
		return labelID;
	}

	public float getAverage_score() {
		return average_score;
	}

	public String getAlbumName() {
		return albumName;
	}

	public String getArtistName() {
		return artistName;
	}

	public String getLabelName() {
		return labelName;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<Song> getSongList() {
		return songList;
	}

	public ArrayList<Studio> getStudioList() {
		return studioList;
	}

	public ArrayList<AlbumReview> getReviewList() {
		return reviewList;
	}

}
