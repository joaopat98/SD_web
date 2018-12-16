package Server;

import java.io.IOException;
import java.net.*;
import java.sql.SQLException;
import java.util.UUID;

public class MultiCastServer {
    private static int BUF_SIZE = 1024;


    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            QueryExecutor.Init("jdbc:postgresql://localhost:5432/Projeto_SD",
                    args[3], args[4]);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        String IP = null;
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            IP = socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        TCPServer transferServer = new TCPServer(BUF_SIZE, Integer.valueOf(args[1]));
        transferServer.start();
        String id = UUID.randomUUID().toString();
        String[] vals = args[0].split(":");
        try {
            MulticastSocket socket = new MulticastSocket(Integer.valueOf(vals[1]));
            MulticastSocket sendSocket = new MulticastSocket();
            socket.joinGroup(new InetSocketAddress(InetAddress.getByName(vals[0]),Integer.valueOf(vals[1])),NetworkInterface.getByInetAddress(InetAddress.getByName(args[2])));
            while (true) {
                byte[] buffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                System.out.println("Received packet from " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " with message:");
                String request = new String(packet.getData(), 0, packet.getLength());
                System.out.println(request);
                new RequestHandler(request, sendSocket, vals[0], Integer.valueOf(vals[1]), IP, Integer.valueOf(args[1]), id).start();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }
}