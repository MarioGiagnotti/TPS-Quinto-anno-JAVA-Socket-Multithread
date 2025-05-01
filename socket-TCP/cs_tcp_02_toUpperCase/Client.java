package cs_tcp_02_toUpperCase;

/*
 * Esercizio. Creare un'applicazione di rete a livello socket in modo che:
 * il server rimarrà sempre in ascolto sulla porta 12345, 
 * il server riceverà una stringa dal client, la trasformerà in lettere maiuscole e la restituirà. 
 * Se il client invia il messaggio "0", il server saluterà il client e terminerà la connessione.
 * 
 * output atteso per il client:
 * Connesso al server sulla porta 12345
 * Inserisci un messaggio (0 per uscire): ciao
 * Risposta dal server: Server: CIAO
 * Inserisci un messaggio (0 per uscire): 0
 * Disconnessione dal server.
 */



 import java.io.*;
 import java.net.*;
 
 public class Client {
     public static void main(String[] args) {
         try {
             // Connettersi al server sulla porta 12345
             Socket socket = new Socket("localhost", 12345);
             System.out.println("Connesso al server sulla porta 12345");
 
             // Creare stream di input/output per la comunicazione con il server
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
 
             String message;
             while (true) {
                 // Leggere il messaggio dall'utente
                 System.out.print("Inserisci un messaggio (0 per uscire): ");
                 message = userInput.readLine();
 
                 // Inviare il messaggio al server
                 out.println(message);
 
                 // Se il messaggio è "0", esci dal ciclo
                 if (message.equals("0")) {
                     System.out.println("Disconnessione dal server.");
                     break;
                 }
 
                 // Ricevere la risposta dal server
                 String serverMessage = in.readLine();
                 System.out.println("Risposta dal server: " + serverMessage);
             }
 
             // Chiudere la connessione
             in.close();
             out.close();
             socket.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 }
 
 
