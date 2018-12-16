package Server;

import java.io.Serializable;
import java.util.HashMap;

public class AlbumReview implements Serializable {
	String user, details;
	int score;

	public AlbumReview(HashMap<String,String> props) {
		this.score = Integer.valueOf(props.get("score"));
		this.user = props.get("username");
		this.details = props.get("detail");
	}
}
