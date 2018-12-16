package Server;

import java.util.HashMap;

public class Dictionary {
    public static HashMap<String, String> mainDict(String request) {
        String[] pairs = request.split(";");
        HashMap<String, String> props = new HashMap<>();
        for (String pair : pairs) {
            String[] keyVal = pair.split(":");
            try {
                props.put(keyVal[0], keyVal[1]);
            } catch (ArrayIndexOutOfBoundsException ex){
                ex.printStackTrace();
            }
        }
        return props;
    }

    public static HashMap<String, String> subDict(String request) {
        String[] pairs = request.split(",");
        HashMap<String, String> props = new HashMap<>();
        for (String pair : pairs) {
            String[] keyVal = pair.split("=");
            props.put(keyVal[0], keyVal[1]);
        }
        return props;
    }
}
