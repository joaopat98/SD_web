package Server;

import java.io.IOException;
import java.net.*;
import java.sql.SQLException;

public class TCPServer extends Thread{
    private int BUF_SIZE;
    private int port;


    public TCPServer(int BUF_SIZE, int port) {
        this.BUF_SIZE = BUF_SIZE;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket sock = new ServerSocket(port);
            while (true){
                Socket transferSocket = sock.accept();
                TransferHandler handler = new TransferHandler(transferSocket, BUF_SIZE);
                handler.start();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
