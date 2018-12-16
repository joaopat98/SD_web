package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRMIServer extends Remote {
    /**
     * Test if the server is running
     *
     * @throws RemoteException
     */
    void testAlive() throws RemoteException;

    /**
     * Register a new user
     * @param username the username to register
     * @param password the password to register
     * @param userNotifCenter an interface to be used for callbacks (specifically notifications)
     * @return
     * @throws RemoteException
     */
    boolean register(String username, String password, INotificationCenter userNotifCenter) throws RemoteException;

    /**
     * login the specified user
     * @param username the username to login
     * @param password  the user's password
     * @param userNotifCenter an interface to be used for callbacks (specifically notifications)
     * @return
     * @throws RemoteException
     */
    boolean login(String username, String password, INotificationCenter userNotifCenter) throws RemoteException;

    /**
     * Logout from the server
     * @param username the user to logout
     * @throws RemoteException
     */
    void logout(String username) throws RemoteException;

    /**
     * Check if the user is an editor
     * @param username the user to check
     * @return
     * @throws RemoteException
     */
    boolean isEditor(String username) throws RemoteException;

    /**
     * Give editor privileges to another user
     * @param username username of the current user
     * @param editor_username the user to make editor
     * @return
     * @throws RemoteException
     */
    boolean makeEditor(String username, String editor_username) throws RemoteException;

    //<editor-fold desc="Add methods">
    int createPlaylist(String username, String name) throws RemoteException;

    /**
     * Add a new song to the database
     * @param username username of the current user
     * @param name
     * @param albumID
     * @param composerID
     * @param genre
     * @param minutes
     * @param seconds
     * @return
     * @throws RemoteException
     */
    int addSong(String username, String name) throws RemoteException;

    /**
     * Add a new album to the database
     * @param username username of the current user
     * @param name
     * @param artistID
     * @param description
     * @return
     * @throws RemoteException
     */
    int addAlbum(String username, String name, int artistID) throws RemoteException;

    /**
     * Add a new artist to the database
     * @param username username of the current user
     * @param name
     * @param description
     * @return
     * @throws RemoteException
     */
    int addArtist(String username, String name) throws RemoteException;

    int addLabel(String username, String name) throws RemoteException;

    int addStudio(String username, String name) throws RemoteException;

    int addMusician(String username, String name) throws RemoteException;

    //<editor-fold desc="Edit methods">

    boolean addComposerToSong(String username, int song_id, int composer_id) throws RemoteException;

    boolean addSongToAlbum(String username, int song_id, int album_id) throws RemoteException;

    boolean addMusicianToArtist(String username, int artist_id, int musician_id, String role) throws RemoteException;

    boolean addStudioToAlbum(String username, int album_id, int studio_id) throws RemoteException;

    boolean addSongToPlaylist(String username, int song_id, int playlist_id) throws RemoteException;

    boolean renamePlaylist(String username, int playlist_id, String name) throws RemoteException;

    boolean changePlaylistPosition(String username, int song_id, int position) throws RemoteException;

    boolean editSong(String username, int songID, String update) throws RemoteException;

    boolean editAlbum(String username, int albumID, String update) throws RemoteException;

    boolean editArtist(String username, int artistID, String update) throws RemoteException;

    boolean editLabel(String username, int labelID, String update) throws RemoteException;

    boolean editStudio(String username, int studioID, String update) throws RemoteException;

    boolean editMusician(String username, int musicianID, String update) throws RemoteException;

    boolean togglePlaylistPublic(String username, int playlistID) throws RemoteException;

    /**
     * delete a song
     * @param username
     * @param songID
     * @return
     * @throws RemoteException
     */
    boolean deleteSong(String username, int songID) throws RemoteException;

    /**
     * delete an album
     * @param username
     * @param albumID
     * @return
     * @throws RemoteException
     */
    boolean deleteAlbum(String username, int albumID) throws RemoteException;

    /**
     * delete an artist
     * @param username
     * @param artistID
     * @return
     * @throws RemoteException
     */
    boolean deleteArtist(String username, int artistID) throws RemoteException;

    /**
     * leave a review on an album
     * @param username
     * @param albumID
     * @param score
     * @param review
     * @return
     * @throws RemoteException
     */
    boolean reviewAlbum(String username, int albumID, int score, String review) throws RemoteException;

    boolean deleteLabel(String username, int labelID) throws RemoteException;

    boolean deleteStudio(String username, int studioID) throws RemoteException;

    boolean deleteMusician(String username, int musicianID) throws RemoteException;

    boolean deletePlaylist(String username, int playlistID) throws RemoteException;

    boolean removeStudioFromAlbum(String username, int album_id, int studio_id) throws RemoteException;

    boolean removeComposerFromSong(String username, int song_id, int composer_id) throws RemoteException;

    boolean removeMusicianFromArtist(String username, int artist_id, int musician_id) throws RemoteException;

    boolean removeSongFromAlbum(String username, int song_id, int album_id) throws RemoteException;

    boolean removeSongFromPlaylist(String username, int song_id, int playlist_id) throws RemoteException;

    /**
     * See details for a specific song
     * @param username
     * @param songID
     * @return
     * @throws RemoteException
     */
    SongDetails songDetails(String username, int songID) throws RemoteException;

    /**
     * See details for a specific album, including the list of songs
     * @param username
     * @param albumID
     * @return
     * @throws RemoteException
     */
    AlbumDetails albumDetails(String username, int albumID) throws RemoteException;

    /**
     * See details for a specific artist
     * @param username
     * @param artistID
     * @return
     * @throws RemoteException
     */
    ArtistDetails artistDetails(String username, int artistID) throws RemoteException;

    LabelDetails labelDetails(String username, int labelID) throws RemoteException;

    StudioDetails studioDetails(String username, int studioID) throws RemoteException;

    MusicianDetails musicianDetails(String username, int musicianID) throws RemoteException;

    PlaylistDetails playlistDetails(String username, int playlistID) throws RemoteException;

    /**
     * search for a song by a query, looks for the query in the song's name, artist, composer and album
     * @param username
     * @param query
     * @return
     * @throws RemoteException
     */
    SearchSongs searchSong(String username, String query) throws RemoteException;

    SearchArtists searchArtist(String username, String query) throws RemoteException;

    SearchAlbums searchAlbum(String username, String query) throws RemoteException;

    SearchLabels searchLabel(String username, String query) throws RemoteException;

    SearchStudios searchStudio(String username, String query) throws RemoteException;

    SearchMusicians searchMusician(String username, String query) throws RemoteException;

    SearchPlaylists searchPlaylist(String username, String query) throws RemoteException;

    /**
     * get an ip address to the server containing the song
     * @param username
     * @param songID
     * @return
     * @throws RemoteException
     */
    Address downloadSong(String username, int songID) throws RemoteException;

    /**
     * get an ip address to download a song
     * @param username
     * @param songID
     * @return
     * @throws RemoteException
     */
    Address uploadSong(String username, int songID) throws RemoteException;

    boolean setDropBoxToken(String username, String token) throws RemoteException;

    /**
     * share a song file with another user
     * @param username
     * @param songID
     * @param shared_username
     * @return
     * @throws RemoteException
     */
    String shareSong(String username, int songID, String shared_username) throws RemoteException;
}
