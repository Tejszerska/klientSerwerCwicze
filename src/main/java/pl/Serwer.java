package pl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Serwer {
    private static final int PORT = 55555;
    private static final CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<ClientHandler>();

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running");

            // Thread to handle server admin input
            new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String serverMessage = scanner.nextLine();
                    broadcast("[Server]: " + serverMessage, null);
                }
            }).start();


            // przyjmowanie polączeń
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                System.out.println("New client: " + clientHandler.nick);
                clients.add(clientHandler);

                new Thread(clientHandler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) client.sendMessage(message);
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private String nick;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                out.println("Podaj nick: ");
                nick = in.readLine();
                System.out.printf("'%s' is connected. \n", nick);
                out.println(nick + " is connected.");
                out.println("Type your message: ");

                String message;
                while ((message = in.readLine()) != null) {
                    if (clients.size() < 2) this.sendMessage("There's nobody to chat to!");
                    broadcast("[" + nick + "]: " + message, this);
                }

                clients.remove(this);
                System.out.println(nick + " is disconnected");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendMessage(String string) {
            out.println(string);
        }
    }
}

