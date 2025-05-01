package cs_tcp_01_primo;

/*
 * Esercizio. Creare un'applicazione di rete a livello socket in modo che:
 * il server rimarrà sempre in ascolto sulla porta 12345, 
 * il server riceverà una stringa dal client, la trasformerà in lettere maiuscole e la restituirà. 
 * Se il client invia il messaggio "0", il server saluterà il client e terminerà la connessione.
 * 
 * output atteso per il client:
 * Connesso al server
 * Messaggio inviato: Ciao, server!
 * Risposta dal server: Ciao /127.0.0.1, ho ricevuto da te questo messaggio: Ciao, server!
 */




 import java.io.*;
 import java.net.*;
 
 public class Client {
     public static void main(String[] args) {
         try {
             // Mi connetto al server sulla porta 12345 e stampo su console un messaggio di conferma di avvenuta connessione
             Socket socket = new Socket("localhost", 12345);
             System.out.println("Connesso al server");
 
             // Creo gli stream di input/output per inviare e ricevere dati
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
 
             // Invio il messaggio al server
             String message = "Ciao, server!";
             out.println(message);
             System.out.println("Messaggio inviato: " + message);
 
             // Ricevo la risposta dal server
             String serverMessage = in.readLine();
             System.out.println("Risposta dal server: " + serverMessage);
 
             // Chiudo le risorse
             in.close();
             out.close();
             socket.close();
 
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 }
 
 
 