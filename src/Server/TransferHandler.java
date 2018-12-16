package Server;

import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TransferHandler extends Thread {
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    int bufSize;
    QueryExecutor q;
    DBUtils util;
    

    public TransferHandler(Socket socket, int bufSize) throws SQLException {
        this.socket = socket;
        this.bufSize = bufSize;
        this.q = QueryExecutor.newConnection();
        this.util = new DBUtils(q);
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String request = in.readUTF();
            HashMap<String, String> props = Dictionary.mainDict(request);
            switch (props.get("action")) {
                case "download":
                    download(props);
                    break;
                case "upload":
                    upload(props);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void download(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String song_id = props.get("song_id");
                if (util.songExists(song_id)) {
                    ResultSet rs = q.executeQuery(
                            "SELECT file_name, file_path\n" +
                                    "FROM file\n" +
                                    "WHERE song_id = " + song_id + "\n" +
                                    "AND creator_username = '" + username + "'\n" +
                                    "UNION\n" +
                                    "SELECT file_name, file_name\n" +
                                    "FROM file\n" +
                                    "WHERE (song_id, creator_username) IN (\n" +
                                    "    SELECT song_id, creator_username\n" +
                                    "    FROM shared_file\n" +
                                    "    WHERE shared_username = '" + username + "'\n" +
                                    "    AND song_id = " + song_id + "\n" +
                                    ");"
                    );
                    if (rs.next()) {
                        try {
                            out.writeUTF("type:download;status:ok;file_name:" + rs.getString(2));
                            String file_path = rs.getString(1);
                            File file = new File(file_path);
                            BufferedInputStream fileStream = new BufferedInputStream(new FileInputStream(file));
                            byte[] buffer = new byte[bufSize];
                            long remaining = file.length();
                            while (remaining > 0) {
                                int readBytes = fileStream.read(buffer);
                                out.write(buffer);
                                remaining -= readBytes;
                            }
                            out.flush();
                            socket.close();
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else
                        status = "no_file";
                } else
                    status = "song_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        try {
            out.writeUTF("type:download;status:" + status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: 28-11-2018 check methods

    private void upload(HashMap<String, String> props) {
        String status;
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String song_id = props.get("song_id");
                if (util.songExists(song_id)) {
                    File file;
                    ResultSet rs = q.executeQuery(
                            "SELECT file_path\n" +
                                    "FROM file\n" +
                                    "WHERE song_id = " + song_id + "\n" +
                                    "AND creator_username = '" + username + "';"
                    );
                    String file_path;
                    if (rs.next()) {
                        file_path = rs.getString(1);
                        file = new File(file_path);
                    } else {
                        String fileDir = "files";
                        new File(fileDir).mkdirs();
                        file_path = fileDir + "/" + username + "_" + song_id + ".mp3";
                        file = new File(file_path);
                        file.createNewFile();

                        q.executeQuery(
                                "INSERT INTO file (file_path, file_name, creator_username, song_id)\n" +
                                        "VALUES ('" + file_path + "','" + props.get("file_name") + "','" + username + "'," + song_id + ");"
                        );
                    }
                    out.writeUTF("type:upload;status:ok");
                    BufferedOutputStream fileStream = new BufferedOutputStream(new FileOutputStream(file));
                    byte[] buffer = new byte[bufSize];
                    int readBytes;
                    int totalbytes = 0;
                    while ((readBytes = in.read(buffer)) != -1) {
                        totalbytes += readBytes;
                        System.out.print("\r" + totalbytes + " bytes read");
                        fileStream.write(buffer, 0, readBytes);
                    }
                    socket.close();
                    return;
                } else
                    status = "song_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        } catch (IOException e) {
            e.printStackTrace();
            status = "io_error";
        }
        try {
            out.writeUTF("type:download;status:" + status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
