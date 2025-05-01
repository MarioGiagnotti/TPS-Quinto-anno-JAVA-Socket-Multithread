package cs_tcp_01_primo;

/*
 * Esercizio. Creare un'applicazione di rete a livello socket in modo che:
 * il server rimarrà sempre in ascolto sulla porta 12345, 
 * il server riceverà una stringa dal client, la trasformerà in lettere maiuscole e la restituirà. 
 * Se il client invia il messaggio "0", il server saluterà il client e terminerà la connessione.
 * 
 * output atteso per il server:
 * Server in ascolto sulla porta 12345...
 * Connessione accettata da /127.0.0.1
 * Messaggio dal client: Ciao, server!
 */

 import java.io.*;
 import java.net.*;
 
 public class Server {
     public static void main(String[] args) {
         try {
             // Creo un server socket che ascolta sulla porta 12345 e stampo sulla console il messaggio di server in ascolto
             ServerSocket serverSocket = new ServerSocket(12345);
             System.out.println("Server in ascolto sulla porta 12345...");
 
             // Accetto una connessione e stampo sulla console il messaggio di accettazione con l'IP del client
             Socket clientSocket = serverSocket.accept();
             System.out.println("Connessione accettata da " + clientSocket.getInetAddress());
 
             // Creo gli stream di input/output per ricevere e inviare dati
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
 
             // Ricevo il messaggio dal client
             String clientMessage = in.readLine();
             System.out.println("Messaggio dal client: " + clientMessage);
 
             // Rispondo al client
             out.println("Ciao " + clientSocket.getInetAddress() + ", ho ricevuto da te questo messaggio: " + clientMessage);
 
             // Chiudo le risorse
             in.close();
             out.close();
             clientSocket.close();
             serverSocket.close();
 
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 }
 