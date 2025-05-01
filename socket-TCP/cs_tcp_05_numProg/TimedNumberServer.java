package cs_tcp_05_numProg;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class TimedNumberServer {
    private int currentNumber = 0;
    private Timer timer;

    public static void main(String[] args) {
        new TimedNumberServer().startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server in ascolto sulla porta 12345...");

            // Accetta una nuova connessione dal client
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connesso!");

            // Avvia un timer per inviare i numeri al client ogni 3 secondi
            startTimedNumberSending(clientSocket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startTimedNumberSending(Socket clientSocket) {
        timer = new Timer();

        // Definisci il compito da eseguire ogni 3 secondi
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (currentNumber >= 30) {
                        // Invia messaggio di saluto al client e termina
                        sendGoodbye(clientSocket);
                        timer.cancel();  // Ferma il timer
                        clientSocket.close();  // Chiude la connessione con il client
                        System.out.println("Connessione terminata con il client.");
                    } else {
                        sendNumber(clientSocket);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    timer.cancel();
                }
            }
        };

        // Programma il TimerTask per eseguire ogni 3 secondi
        timer.schedule(task, 0, 3000);
    }

    private synchronized void sendNumber(Socket clientSocket) throws IOException {
        currentNumber++;
        OutputStream output = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(currentNumber);  // Invia il numero al client
        System.out.println("Numero inviato al client: " + currentNumber);
    }

    private void sendGoodbye(Socket clientSocket) throws IOException {
        OutputStream output = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println("Ciao! Hai ricevuto 30 numeri. Addio!");  // Messaggio di saluto
        System.out.println("Inviato messaggio di addio al client.");
    }
}
