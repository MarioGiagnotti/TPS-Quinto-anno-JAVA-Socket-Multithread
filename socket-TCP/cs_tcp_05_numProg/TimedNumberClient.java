package cs_tcp_05_numProg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class TimedNumberClient {

    public static void main(String[] args) {
        try {
            // Connessione al server sulla porta 12345
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connesso al server, in attesa di numeri...");

            // Leggi i numeri inviati dal server
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            while ((message = reader.readLine()) != null) {
                // Stampa il numero ricevuto o il messaggio di saluto
                System.out.println("Ricevuto dal server: " + message);

                // Se il server invia un messaggio di addio, chiudi la connessione
                if (message.contains("Addio")) {
                    System.out.println("Il server ha chiuso la connessione.");
                    break;
                }
            }

            // Chiudi il socket
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
