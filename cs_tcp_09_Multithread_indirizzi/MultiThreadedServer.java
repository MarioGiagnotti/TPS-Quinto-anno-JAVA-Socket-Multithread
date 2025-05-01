package cs_tcp_09_Multithread_indirizzi;

import java.io.*;
import java.net.*;

public class MultiThreadedServer {
    public static void main(String[] args) {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server in ascolto sulla porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo client connesso: " + clientSocket.getInetAddress());

                // Crea un nuovo thread per gestire la richiesta del client
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String ipAddress;
            while ((ipAddress = in.readLine()) != null) {
                System.out.println("Ricevuto: " + ipAddress);
                if ("0".equals(ipAddress)) {
                    out.println("Connessione chiusa");
                    break;
                }
                out.println(valutaClasseIP(ipAddress));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Connessione chiusa: " + clientSocket.getInetAddress());
        }
    }

    private String valutaClasseIP(String ipAddress) {
        String[] parts = ipAddress.split("\\.");
        if (parts.length != 4) {
            return "indirizzo non valido";
        }

        try {
            int firstOctet = Integer.parseInt(parts[0]);
            if (firstOctet >= 1 && firstOctet <= 126) {
                return "Classe A";
            } else if (firstOctet >= 128 && firstOctet <= 191) {
                return "Classe B";
            } else if (firstOctet >= 192 && firstOctet <= 223) {
                return "Classe C";
            } else if (firstOctet >= 224 && firstOctet <= 239) {
                return "Classe D (Multicast)";
            } else if (firstOctet >= 240 && firstOctet <= 255) {
                return "Classe E (Riservato)";
            } else {
                return "indirizzo non valido";
            }
        } catch (NumberFormatException e) {
            return "indirizzo non valido";
        }
    }
}
    