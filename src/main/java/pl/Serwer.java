package pl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serwer {
    private ServerSocket serverSocket;
    public Serwer() {
        try {
            serverSocket = new ServerSocket(55555);
            System.out.println("Serwer ok");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
