package cs_tcp_06_Multithread_toUpperCase_runnable;


import java.io.*;
import java.net.*;

public class Client1 {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader tastiera;
    private static final String SERVER_ADDRESS = "localhost"; // Indirizzo del server
    private static final int PORT = 12345; // Porta del server

    // Costruttore
    public Client1(String host, int port) throws IOException {
        socket = new Socket(SERVER_ADDRESS, PORT);
        tastiera = new BufferedReader(new InputStreamReader(System.in));
    }

    // Metodo per creare i canali di comunicazione
    private void creaCanali() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    // Metodo per la comunicazione con il server
    private void comunica() throws IOException {
        String messaggioDalServer;
        String userInput;

        // Ricezione messaggio di benvenuto
        messaggioDalServer = in.readLine();
        System.out.println(messaggioDalServer);

        // Invia il nome al server        
        userInput = tastiera.readLine();
        out.println(userInput); // Invio del nome al server

        // Ciclo per inviare messaggi al server    
        while (true) {
            // Ricevi il messaggio dal server (invito a scrivere una stringa)
            messaggioDalServer = in.readLine();
            System.out.println(messaggioDalServer);

            // Leggi input dell'utente da tastiera
            userInput = tastiera.readLine();
            out.println(userInput); // Invia la stringa al server

            // Se l'input è "0", termina la connessione
            if (userInput.equals("0")) {
                System.out.println("Disconnessione dal server...");
                break;
            }

            // Ricevi e stampa la risposta del server
            messaggioDalServer = in.readLine();
            System.out.println("Risposta dal server: " + messaggioDalServer);
        }

        // Chiudi le risorse alla fine
        chiudiConnessione();
    } 

    // Metodo per chiudere le risorse
    private void chiudiConnessione() throws IOException {
        in.close();
        out.close();
        socket.close();
        tastiera.close();
        System.out.println("Connessione chiusa.");
    }

    public static void main(String[] args) throws IOException {
        Client1 client1 = new Client1(SERVER_ADDRESS, PORT);
        client1.creaCanali();
        client1.comunica();
    }
}
