package Server;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class TransferClient {
    public static void main(String[] args) {
        try {
            
            Scanner sc = new Scanner(System.in);
            Socket socket = new Socket(args[0], Integer.valueOf(args[1]));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            out.writeUTF("action:upload;username:joao;song_id:3");
            System.out.println(in.readUTF());
            sc.nextLine();
            File file = new File("test/test.mp3");
            BufferedInputStream fileStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            long remaining = file.length();
            while (remaining > 0) {
                System.out.print("\r" + remaining + " bytes remaining");
                int readBytes = fileStream.read(buffer);
                out.write(buffer);
                remaining -= readBytes;
            }
            fileStream.close();
            out.flush();
            socket.close();


            sc.nextLine();

            socket = new Socket(args[0], Integer.valueOf(args[1]));
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            out.writeUTF("action:download;username:joao;song_id:3");;
            System.out.println(in.readUTF());
            file = new File("test/test_receive.mp3");
            file.createNewFile();
            BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(file));
            int readBytes;
            int totalbytes = 0;
            while((readBytes = in.read(buffer)) != -1){
                totalbytes += readBytes;
                System.out.print("\r" + totalbytes + " bytes read");
                outStream.write(buffer, 0, readBytes);
            }
            outStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
