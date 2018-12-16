package Server;

import java.io.Serializable;
import java.util.HashMap;

public class Address implements Serializable {
	String host = null;
	int port = 0;
	String error;

	public Address(HashMap<String, String> props) {
		if (props == null)
			error = "file_null";
		else {
			this.error = props.get("status").equals("ok") ? null : props.get("status");
			if (error == null) {
				this.host = props.get("host");
				this.port = Integer.valueOf(props.get("port"));
			}
		}
	}
}
