package Server;

import java.io.IOException;
import java.net.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import static java.lang.System.exit;

public class RMIServer extends UnicastRemoteObject implements IRMIServer {
    private int server_num;
    private static String MULTICAST_ADDRESS;
    private static int PORT = 10000;
    private static int MAX_RETRIES = 5;
    private static int WAIT_TIME = 100;
    private static int CHECK_INTERVAL = 5000;
    private static int UPDATE_INTERVAL = 60000;
    private OnlineCleaner onlineCleaner;
    private IDChecker idChecker;
    private final HashMap<String, INotificationCenter> onlineUsers = new HashMap<>();
    private Random random;
    private ArrayList<String> multicastIDs;

    /**
     * Thread that checks the online users list for offline users
     */
    private class OnlineCleaner extends Thread {

        @Override
        public void run() {
            while (true) {
                synchronized (onlineUsers) {
                    for (String user : onlineUsers.keySet()) {
                        int tries = 0;
                        while (true) {
                            try {
                                onlineUsers.get(user).isAlive();
                                break;
                            } catch (RemoteException e) {
                                if (++tries == MAX_RETRIES) {
                                    onlineUsers.remove(user);
                                    break;
                                }
                                try {
                                    Thread.sleep(WAIT_TIME);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(CHECK_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class IDChecker extends Thread {

        @Override
        public void run() {
            while (true) {
                updateIDs();
                try {
                    Thread.sleep(UPDATE_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * get a random id from the list of muticast server ids
     *
     * @return
     */
    private String get_pref_id() {
        return multicastIDs.get(random.nextInt(multicastIDs.size()));
    }

    //<editor-fold desc="Auxiliary Methods">
    private DatagramPacket createSendPacket(String message) throws UnknownHostException {
        DatagramPacket packet;
        InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
        byte[] buffer = message.getBytes();
        packet = new DatagramPacket(buffer, buffer.length, group, PORT);
        return packet;
    }

    private DatagramPacket createReceivePacket() {
        DatagramPacket packet;
        byte[] buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
        return packet;
    }

    //Split by <, > and ><, then by  ,  , then by =
    public static ArrayList<HashMap<String, String>> subPropertiesHashmap(String message) {
        ArrayList<HashMap<String, String>> propsArr = new ArrayList<>();
        if (message.equals("/")) return propsArr;
        String[] tokens = message.split("[<>]+");
        for (String token : tokens) {
            if (!token.equals("")) {
                HashMap<String, String> props = new HashMap<>();
                String[] pairs = token.split("~");
                for (String pair : pairs) {
                    String[] keyVal = pair.split("»");
                    props.put(keyVal[0], keyVal[1]);
                }
                propsArr.add(props);
            }
        }
        return propsArr;
    }

    //Split by ; and :
    public static HashMap<String, String> createHashMap(String message) {
        HashMap<String, String> props;
        props = new HashMap<>();
        String[] pairs = message.split(";");
        for (String pair : pairs) {
            String[] keyVal = pair.split(":");
            props.put(keyVal[0], keyVal[1]);
        }
        return props;
    }

    //</editor-fold>

    private synchronized void updateIDs() {
        multicastIDs.clear();
        try {
            String request_id = UUID.randomUUID().toString();
            String message = "request_id:" + request_id + ";action:provide_id";
            MulticastSocket receiveSocket = new MulticastSocket(PORT);
            receiveSocket.setSoTimeout(2000);
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            receiveSocket.joinGroup(group);
            MulticastSocket sendSocket = new MulticastSocket();
            DatagramPacket packet = createSendPacket(message);
            sendSocket.send(packet);
            System.out.println("Packet sent with message: " + message);
            HashMap<String, String> props;
            while (true) {
                packet = createReceivePacket();
                try {
                    receiveSocket.receive(packet);
                } catch (SocketTimeoutException e) {
                    break;
                }
                message = new String(packet.getData(), 0, packet.getLength());
                props = createHashMap(message);
                if (props.get("request_id").equals(request_id) && props.containsKey("type")) {
                    multicastIDs.add(props.get("id"));
                }
            }
            receiveSocket.close();
            sendSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RMIServer() throws RemoteException {
        random = new Random();
        multicastIDs = new ArrayList<>();
        updateIDs();
        onlineCleaner = new OnlineCleaner();
        idChecker = new IDChecker();
        onlineCleaner.start();
        idChecker.start();
    }

    public static void main(String[] args) {
        IRMIServer mainServer;
        Registry registry = null;
        MULTICAST_ADDRESS = args[0];
        try {
            String IP = null;
            try (final DatagramSocket socket = new DatagramSocket()) {
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                IP = socket.getLocalAddress().getHostAddress();
            } catch (SocketException | UnknownHostException e) {
                e.printStackTrace();
            }
            System.out.println(IP);
            System.setProperty("java.rmi.server.hostname", IP);
            registry = LocateRegistry.getRegistry(7000);
        } catch (RemoteException e) {
            System.out.println("Registry not running!");
            e.printStackTrace();
            exit(1);
        }

        try {
            mainServer = (IRMIServer) registry.lookup("server");
            while (true) {
                try {
                    while (true) {
                        mainServer.testAlive();
                        Thread.sleep(250);
                    }
                } catch (RemoteException e) {
                    System.out.println("Exception caught! Retrying...");
                    if (!TestRetries(mainServer)) break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    exit(1);
                }
            }
            try {
                System.out.println("No server responded, starting self...");
                startServer(registry);

            } catch (RemoteException re) {
                re.printStackTrace();
            }
        } catch (RemoteException | NotBoundException e) {
            System.out.println("No server running!");
            try {
                startServer(registry);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static void startServer(Registry registry) throws RemoteException {
        RMIServer server = new RMIServer();
        Random random = new Random();
        server.server_num = Math.abs(random.nextInt());
        registry.rebind("server", server);
        System.out.println("Server #" + server.server_num + " ready.");
    }

    private static boolean TestRetries(IRMIServer mainServer) {
        int max_retries = 5;
        for (int retries = 0; retries < max_retries; retries++) {
            try {
                mainServer.testAlive();
                return true;
            } catch (RemoteException ignored) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public void testAlive() {
        System.out.println("IM ALIVE");
    }

    public HashMap<String, String> sendPacket(String message) throws IOException {
        return sendPacket(message, false);
    }

    public HashMap<String, String> sendPacket(String message, boolean pref_server) throws IOException {
        String requestID = UUID.randomUUID().toString();
        return sendPacket(message, requestID, pref_server, 0);
    }

    public HashMap<String, String> sendPacket(String message, String request_id, boolean pref_server, int socketTimeout) throws IOException {
        if (pref_server)
            message = message + ";pref_server:" + get_pref_id();
        message = "request_id:" + request_id + ";" + message;
        MulticastSocket receiveSocket = new MulticastSocket(PORT);
        if (socketTimeout != 0)
            receiveSocket.setSoTimeout(socketTimeout);
        InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
        receiveSocket.joinGroup(group);
        MulticastSocket sendSocket = new MulticastSocket();
        DatagramPacket packet = createSendPacket(message);
        sendSocket.send(packet);
        System.out.println("Packet sent with message: " + message);
        HashMap<String, String> props;
        do {
            packet = createReceivePacket();
            try {
                receiveSocket.receive(packet);
            } catch (SocketTimeoutException e) {
                return null;
            }
            message = new String(packet.getData(), 0, packet.getLength());
            props = createHashMap(message);
        } while (!props.get("request_id").equals(request_id) || !props.containsKey("type"));
        sendSocket.close();
        receiveSocket.close();
        if (!props.get("type").equals("provide_id"))
            System.out.println("oof");
        return props;
    }

    //<editor-fold desc="Register/Login">
    @Override
    public boolean register(String username, String password, INotificationCenter userNotifCenter) throws RemoteException {

        try {
            String message = "action:register;username:" + username + ";password:" + password;

            HashMap<String, String> props = sendPacket(message, false);

            if (props.get("status").equals("ok")) {
                synchronized (onlineUsers) {
                    onlineUsers.put(username, userNotifCenter);
                }
            }
            return props.get("status").equals("ok");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean login(String username, String password, INotificationCenter userNotifCenter) throws RemoteException {

        try {
            String message = "action:login;username:" + username + ";password:" + password;

            HashMap<String, String> props = sendPacket(message);

            if (props.get("status").equals("ok")) {
                synchronized (onlineUsers) {
                    onlineUsers.put(username, userNotifCenter);
                }
                flushNotifications(username, userNotifCenter);
            }
            return props.get("status").equals("ok");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void logout(String username) {
        synchronized (onlineUsers) {
            onlineUsers.remove(username);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Check/Make editor methods">
    @Override
    public boolean isEditor(String username) throws RemoteException {

        try {
            String message = "username:" + username + ";action:check_editor";
            HashMap<String, String> props = sendPacket(message);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean makeEditor(String username, String editor_username) throws RemoteException {

        try {
            String message = "username:" + username + ";action:make_editor;editor_username:" + editor_username;
            HashMap<String, String> props = sendPacket(message, false);
            if (props.get("status").equals("ok")) {
                ArrayList<String> arr = new ArrayList<>();
                arr.add(editor_username);
                sendNotification(arr.toArray(new String[arr.size()]), username, "editor", null, 0);
            }
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    //</editor-fold>

    //<editor-fold desc="Add methods">
    @Override
    public int createPlaylist(String username, String name) throws RemoteException {

        try {
            String message = "username:" + username + ";action:create_playlist;name:" + name;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok") ? Integer.valueOf(props.get("playlist_id")) : -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int addSong(String username, String name) throws RemoteException {

        try {
            String message = "username:" + username + ";action:add_song;name:" + name;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok") ? Integer.valueOf(props.get("song_id")) : -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int addAlbum(String username, String name, int artistID) throws RemoteException {

        try {
            String message = "username:" + username + ";action:add_album;artist_id:" + artistID +
                    ";name:" + name;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok") ? Integer.valueOf(props.get("album_id")) : -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean reviewAlbum(String username, int albumID, int score, String review) {

        try {
            String message = "username:" + username + ";action:review_album;album_id:" + albumID + ";score:" + score + ";detail:" + review;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int addArtist(String username, String name) throws RemoteException {


        try {
            String message = "username:" + username + ";action:add_artist;name:" + name;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok") ? Integer.valueOf(props.get("artist_id")) : -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int addLabel(String username, String name) throws RemoteException {


        try {
            String message = "username:" + username + ";action:add_label;name:" + name;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok") ? Integer.valueOf(props.get("label_id")) : -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int addStudio(String username, String name) throws RemoteException {


        try {
            String message = "username:" + username + ";action:add_studio;name:" + name;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok") ? Integer.valueOf(props.get("studio_id")) : -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int addMusician(String username, String name) throws RemoteException {


        try {
            String message = "username:" + username + ";action:add_musician;name:" + name;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok") ? Integer.valueOf(props.get("musician_id")) : -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean addComposerToSong(String username, int song_id, int composer_id) throws RemoteException {


        try {
            String message = "username:" + username + ";action:add_composer_to_song;song_id:" + song_id + ";composer_id:" + composer_id;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addSongToAlbum(String username, int song_id, int album_id) throws RemoteException {


        try {
            String message = "username:" + username + ";action:add_song_to_album;song_id:" + song_id + ";album_id:" + album_id;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addMusicianToArtist(String username, int artist_id, int musician_id, String role) throws RemoteException {


        try {
            String message = "username:" + username + ";action:add_musician_to_artist;artist_id:" + artist_id + ";musician_id:" + musician_id + ";role:" + role;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean addStudioToAlbum(String username, int album_id, int studio_id) throws RemoteException {


        try {
            String message = "username:" + username + ";action:add_studio_to_album;album_id:" + album_id + ";studio_id:" + studio_id;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addSongToPlaylist(String username, int song_id, int playlist_id) throws RemoteException {
        try {
            String message = "username:" + username + ";action:add_to_playlist;song_id:" + song_id + ";playlist_id:" + song_id;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //</editor-fold>

    //<editor-fold desc="Edit methods">

    @Override
    public boolean renamePlaylist(String username, int playlist_id, String name) throws RemoteException {

        try {
            String message = "username:" + username + ";action:rename_playlist;playlist_id:" + playlist_id + ";name:" + name;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changePlaylistPosition(String username, int song_id, int position) throws RemoteException {

        try {
            String message = "username:" + username + ";action:add_song;song_id:" + song_id + ";position:" + position;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editSong(String username, int songID, String update) throws RemoteException {


        try {
            String message = "username:" + username + ";action:update_song;song_id:" + songID + ";" + update;
            HashMap<String, String> props = sendPacket(message, false);
            if (props.get("status").equals("ok")) {
                String[] editorList = getSongEditors(username, songID);
                sendNotification(editorList, username, "song", props.get("name"), songID);
            }
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editAlbum(String username, int albumID, String update) throws RemoteException {


        try {


            String message = "username:" + username + ";action:update_album;album_id:" + albumID + ";" + update;
            HashMap<String, String> props = sendPacket(message, false);
            if (props.get("status").equals("ok")) {
                String[] editorList = getAlbumEditors(username, albumID);
                sendNotification(editorList, username, "album", props.get("name"), albumID);
            }
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editArtist(String username, int artistID, String update) throws RemoteException {


        try {


            String message = "username:" + username + ";action:update_artist;artist_id:" + artistID + ";" + update;
            HashMap<String, String> props = sendPacket(message, false);
            if (props.get("status").equals("ok")) {
                String[] editorList = getArtistEditors(username, artistID);
                sendNotification(editorList, username, "artist", props.get("name"), artistID);
            }
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editLabel(String username, int labelID, String update) throws RemoteException {


        try {


            String message = "username:" + username + ";action:update_label;label_id:" + labelID + ";" + update;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editStudio(String username, int studioID, String update) throws RemoteException {


        try {


            String message = "username:" + username + ";action:update_studio;studio_id:" + studioID + ";" + update;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editMusician(String username, int musicianID, String update) throws RemoteException {


        try {


            String message = "username:" + username + ";action:update_musician;musician_id:" + musicianID + ";" + update;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean togglePlaylistPublic(String username, int playlistID) throws RemoteException {

        try {


            String message = "username:" + username + ";action:toggle_playlist_public;playlist_id:" + playlistID;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //</editor-fold>

    //<editor-fold desc="Delete methods">
    @Override
    public boolean deleteSong(String username, int songID) throws RemoteException {


        try {


            String message = "username:" + username + ";action:remove_song;song_id:" + songID;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAlbum(String username, int albumID) throws RemoteException {


        try {


            String message = "username:" + username + ";action:remove_album;album_id:" + albumID;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteArtist(String username, int artistID) throws RemoteException {

        try {


            String message = "username:" + username + ";action:remove_artist;artist_id:" + artistID;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteLabel(String username, int labelID) throws RemoteException {

        try {


            String message = "username:" + username + ";action:remove_label;label_id:" + labelID;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteStudio(String username, int studioID) throws RemoteException {

        try {


            String message = "username:" + username + ";action:remove_studio;studio_id:" + studioID;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteMusician(String username, int musicianID) throws RemoteException {

        try {


            String message = "username:" + username + ";action:remove_musician;musician_id:" + musicianID;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePlaylist(String username, int playlistID) throws RemoteException {

        try {


            String message = "username:" + username + ";action:remove_playlist;playlist_id:" + playlistID;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeStudioFromAlbum(String username, int album_id, int studio_id) throws RemoteException {


        try {
            String message = "username:" + username + ";action:remove_studio_from_album;album_id:" + album_id + ";studio_id:" + studio_id;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeComposerFromSong(String username, int song_id, int composer_id) throws RemoteException {


        try {
            String message = "username:" + username + ";action:remove_composer_remove_song;song_id:" + song_id + ";composer_id:" + composer_id;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeMusicianFromArtist(String username, int artist_id, int musician_id) throws RemoteException {


        try {
            String message = "username:" + username + ";action:remove_musician_from_artist;artist_id:" + artist_id + ";musician_id:" + musician_id;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeSongFromAlbum(String username, int song_id, int album_id) throws RemoteException {


        try {
            String message = "username:" + username + ";action:remove_song_from_album;song_id:" + song_id + ";album_id:" + album_id;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeSongFromPlaylist(String username, int song_id, int playlist_id) throws RemoteException {
        try {
            String message = "username:" + username + ";action:remove_from_playlist;song_id:" + song_id + ";playlist_id:" + song_id;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    //</editor-fold>

    //<editor-fold desc="Detail methods">
    @Override
    public SongDetails songDetails(String username, int songID) throws RemoteException {


        try {


            String message = "username:" + username + ";action:song_detail;song_id:" + songID;
            HashMap<String, String> props = sendPacket(message);
            if (props.get("status").equals("ok"))
                return new SongDetails(props);
            else
                return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AlbumDetails albumDetails(String username, int albumID) throws RemoteException {


        try {


            String message = "username:" + username + ";action:album_detail;album_id:" + albumID;
            HashMap<String, String> props = sendPacket(message);
            if (props.get("status").equals("ok"))
                return new AlbumDetails(props);
            else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArtistDetails artistDetails(String username, int artistID) throws RemoteException {


        try {


            String message = "username:" + username + ";action:artist_detail;artist_id:" + artistID;
            HashMap<String, String> props = sendPacket(message);
            if (props.get("status").equals("ok"))
                return new ArtistDetails(props);
            else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LabelDetails labelDetails(String username, int labelID) throws RemoteException {


        try {


            String message = "username:" + username + ";action:label_detail;label_id:" + labelID;
            HashMap<String, String> props = sendPacket(message);
            if (props.get("status").equals("ok"))
                return new LabelDetails(props);
            else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public StudioDetails studioDetails(String username, int studioID) throws RemoteException {


        try {


            String message = "username:" + username + ";action:studio_detail;studio_id:" + studioID;
            HashMap<String, String> props = sendPacket(message);
            if (props.get("status").equals("ok"))
                return new StudioDetails(props);
            else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MusicianDetails musicianDetails(String username, int musicianID) throws RemoteException {


        try {


            String message = "username:" + username + ";action:musician_detail;musician_id:" + musicianID;
            HashMap<String, String> props = sendPacket(message);
            if (props.get("status").equals("ok"))
                return new MusicianDetails(props);
            else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PlaylistDetails playlistDetails(String username, int playlistID) throws RemoteException {


        try {


            String message = "username:" + username + ";action:playlist_detail;playlist_id:" + playlistID;
            HashMap<String, String> props = sendPacket(message);
            if (props.get("status").equals("ok"))
                return new PlaylistDetails(props);
            else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //</editor-fold>


    //<editor-fold desc="Search methods">

    @Override
    public SearchSongs searchSong(String username, String query) throws RemoteException {

        try {

            String message = "username:" + username + ";action:song_search;query:" + query;
            HashMap<String, String> props = sendPacket(message);
            return new SearchSongs(props);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SearchArtists searchArtist(String username, String query) throws RemoteException {

        try {

            String message = "username:" + username + ";action:artist_search;query:" + query;
            HashMap<String, String> props = sendPacket(message);
            return new SearchArtists(props);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SearchAlbums searchAlbum(String username, String query) throws RemoteException {

        try {

            String message = "username:" + username + ";action:album_search;query:" + query;
            HashMap<String, String> props = sendPacket(message);
            return new SearchAlbums(props);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SearchLabels searchLabel(String username, String query) throws RemoteException {

        try {

            String message = "username:" + username + ";action:label_search;query:" + query;
            HashMap<String, String> props = sendPacket(message);
            return new SearchLabels(props);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SearchStudios searchStudio(String username, String query) throws RemoteException {

        try {

            String message = "username:" + username + ";action:studio_search;query:" + query;
            HashMap<String, String> props = sendPacket(message);
            return new SearchStudios(props);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SearchMusicians searchMusician(String username, String query) throws RemoteException {

        try {

            String message = "username:" + username + ";action:musician_search;query:" + query;
            HashMap<String, String> props = sendPacket(message);
            return new SearchMusicians(props);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SearchPlaylists searchPlaylist(String username, String query) throws RemoteException {

        try {

            String message = "username:" + username + ";action:playlist_search;query:" + query;
            HashMap<String, String> props = sendPacket(message);
            return new SearchPlaylists(props);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //</editor-fold>

    //<editor-fold desc="Download/Upload methods">
    @Override
    public Address downloadSong(String username, int songID) throws RemoteException {

        try {

            String message = "username:" + username + ";action:download_url;song_id:" + songID;
            String request_id = UUID.randomUUID().toString();
            HashMap<String, String> props = sendPacket(message, request_id, false, 5000);
            return new Address(props);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Address uploadSong(String username, int songID) throws RemoteException {

        try {

            String message = "username:" + username + ";action:upload_url;song_id:" + songID;
            HashMap<String, String> props = sendPacket(message, false);
            return new Address(props);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold desc="Get Editors methods">

    /**
     * get the song's previous editors
     *
     * @param username
     * @param songID
     * @return
     */
    private String[] getSongEditors(String username, int songID) {


        try {


            String message = "username:" + username + ";action:song_editors;song_id:" + songID;
            HashMap<String, String> props = sendPacket(message);
            return props.get("editors").split(",");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get the album's previous editors
     *
     * @param username
     * @param albumID
     * @return
     */
    private String[] getAlbumEditors(String username, int albumID) {


        try {

            String message = "username:" + username + ";action:album_editors;album_id:" + albumID;


            HashMap<String, String> props = sendPacket(message);
            return props.get("editors").split(",");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get the artist's previous editors
     *
     * @param username
     * @param artistID
     * @return
     */
    private String[] getArtistEditors(String username, int artistID) {


        try {


            String message = "username:" + username + ";action:artist_editors;artist_id:" + artistID;
            HashMap<String, String> props = sendPacket(message);
            return props.get("editors").split(",");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold desc="Notification methods">

    /**
     * save the notification in the database if the users are not online
     *
     * @param username
     * @param type
     * @param editorUserList
     * @param mediaID
     * @return
     */
    private boolean saveNotif(String username, String type, ArrayList<String> editorUserList, int mediaID) {
        if (editorUserList.size() > 0) {
            try {

                String message;
                if (type.equals("editor"))
                    message = "username:" + username + ";action:save_editor_notif;receiver_username:";
                else
                    message = "username:" + username + ";action:save_" + type + "_notif;" + type + "_id:" + mediaID + ";notif_receivers:";
                for (String editorUserName : editorUserList)
                    message += editorUserName + ",";
                message = message.substring(0, message.length() - 1);

                HashMap<String, String> props = sendPacket(message);
                return props.get("status").equals("ok");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * send notification to the online users in the list
     *
     * @param editorList
     * @param username
     * @param type
     * @param mediaName
     * @param mediaID
     */
    private void sendNotification(String[] editorList, String username, String type, String mediaName, int mediaID) {
        ArrayList<String> offlineUsers = new ArrayList<>();
        for (String editor : editorList) {
            synchronized (onlineUsers) {
                if (onlineUsers.containsKey(editor)) {
                    if (!editor.equals(username)) {
                        String response = "User " + username + " ";
                        switch (type) {
                            case "song":
                                response += "edited song \"" + mediaName + "\" with ID " + mediaID;
                                break;
                            case "album":
                                response += "edited album \"" + mediaName + "\" with ID " + mediaID;
                                break;
                            case "artist":
                                response += "edited artist \"" + mediaName + "\" with ID " + mediaID;
                                break;
                            case "editor":
                                response += "gave you editor privileges!";
                                break;
                        }
                        int tries = 0;
                        try {
                            onlineUsers.get(editor).notify(response);
                            break;
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            if (tries++ == MAX_RETRIES) {
                                offlineUsers.add(editor);
                                onlineUsers.remove(editor);
                                break;
                            }
                            try {
                                Thread.sleep(WAIT_TIME);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                } else
                    offlineUsers.add(editor);
            }
        }
        saveNotif(username, type, offlineUsers, mediaID);
    }

    /**
     * deliver to the user the notifications he didnt receive while offline
     *
     * @param username
     * @param userNotifCenter
     */
    private void flushNotifications(String username, INotificationCenter userNotifCenter) {
        try {


            String message = "username:" + username + ";action:flush_notifs";
            HashMap<String, String> props = sendPacket(message);
            for (HashMap<String, String> notif : subPropertiesHashmap(props.get("notifs"))) {
                String response = "User " + notif.get("editor") + " ";
                switch (notif.get("type")) {
                    case "song":
                        response += "edited song \"" + notif.get("name") + "\" with ID " + notif.get("song_id");
                        break;
                    case "album":
                        response += "edited album \"" + notif.get("name") + "\" with ID " + notif.get("album_id");
                        break;
                    case "artist":
                        response += "edited artist \"" + notif.get("name") + "\" with ID " + notif.get("artist_id");
                        break;
                    case "editor":
                        response += "gave you editor privileges!";
                        break;
                }
                userNotifCenter.notify(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>

    @Override
    public boolean setDropBoxToken(String username, String token) throws RemoteException {


        try {


            String message = "username:" + username + ";action:set_dropbox_token;token:" + token;
            HashMap<String, String> props = sendPacket(message, false);
            return props.get("status").equals("ok");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String shareSong(String username, int songID, String shared_username) {
        try {
            String message = "action:share_song;username:" + username + ";song_id:" + songID + ";shared_username:" + shared_username;
            String request_id = UUID.randomUUID().toString();
            HashMap<String, String> props = sendPacket(message, request_id, false, 5000);
            if (props == null) {
                return "no_file";
            } else return props.get("status");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
