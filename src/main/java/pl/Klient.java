package pl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Klient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 55555;

    public static void main(String[] args) {
        try {
            /* boilerplate code*/
            Socket socket = new Socket(SERVER_ADDRESS,SERVER_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("klient polaczony.");


            // wątek odczytujacy odpowiedz z serwera na konsole
            new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println(serverResponse);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // wczytawanie i wysylanie na serwer tgekstu z konsoli
            Scanner scanner = new Scanner(System.in);
            String userInput;
            while (true) {
                userInput = scanner.nextLine();
                out.println(userInput);
            }

        } catch (IOException e) {
            System.out.println("Klient miał błąd.");
            System.out.println(e.getMessage());
        }
    }
}
