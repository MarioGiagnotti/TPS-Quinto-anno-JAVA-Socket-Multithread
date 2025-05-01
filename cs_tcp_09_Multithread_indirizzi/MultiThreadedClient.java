package cs_tcp_09_Multithread_indirizzi;

import java.io.*;
import java.net.*;

public class MultiThreadedClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 8080;

        try (Socket socket = new Socket(hostname, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;

            System.out.println("Inserisci un indirizzo IP da inviare al server (digita '0' per terminare):");

            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                String response = in.readLine();
                System.out.println("Risposta dal server: " + response);
                if ("0".equals(userInput)) {
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Host sconosciuto: " + hostname);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
