package pl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Klient {
    private PrintWriter out;
    private BufferedReader in;
    public Klient(){
        try {
            Socket socket = new Socket("localhost", 55555);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("hej");
        } catch (IOException e) {
            System.out.println("ej");
            System.out.println(e.getMessage());
        }
    }
}
