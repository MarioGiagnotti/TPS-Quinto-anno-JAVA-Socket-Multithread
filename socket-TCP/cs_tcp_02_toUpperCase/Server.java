package cs_tcp_02_toUpperCase;

/*
 * Esercizio. Creare un'applicazione di rete a livello socket in modo che:
 * il server rimarrà sempre in ascolto sulla porta 12345, 
 * il server riceverà una stringa dal client, la trasformerà in lettere maiuscole e la restituirà. 
 * Se il client invia il messaggio "0", il server saluterà il client e terminerà la connessione.
 * 
 * output atteso per il server:
 * Server in ascolto sulla porta 12345...
 * Connessione accettata per /127.0.0.1: <porta>
 * Messaggio ricevuto: ciao
 * Risposta inviata: CIAO
 * Il client si è disconnesso.
 */


 import java.io.*;
 import java.net.*;
 
 public class Server {
     public static void main(String[] args) {
         try {
             // Crea un server socket che ascolta sulla porta 12345
             ServerSocket serverSocket = new ServerSocket(12345);
             System.out.println("Server in ascolto sulla porta 12345...");
 
             // Accetta una connessione dal client
             Socket clientSocket = serverSocket.accept();
             int portaClient = clientSocket.getPort(); //utilizzo del metodo getPort() (scopro qual è la porta utilizzata dal client)
             System.out.println("Connessione accettata da " + clientSocket.getInetAddress() + ":"+portaClient);
 
             // Creare stream di input/output per la comunicazione con il client
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
 
                 String clientMessage;
                 while ((clientMessage = in.readLine()) != null) {
                     // Se il client invia "0", termina la connessione
                     if (clientMessage.equals("0")) {
                         out.println("Connessione terminata. Addio!");
                         System.out.println("Il client si è disconnesso.");
                         break;
                     }
 
                     // Converti il messaggio del client in maiuscolo e invialo indietro
                     String response = clientMessage.toUpperCase();
                     out.println("Server: " + response);
                     System.out.println("Messaggio ricevuto: " + clientMessage);
                     System.out.println("Risposta inviata: " + response);
                 }
 
             // Chiudere la connessione con il client
             clientSocket.close();
             serverSocket.close();
             
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 }
 
 
