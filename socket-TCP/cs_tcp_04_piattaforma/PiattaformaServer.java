package cs_tcp_04_piattaforma;

import java.io.*;
import java.net.*;

public class PiattaformaServer {
    
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    // Soglie critiche per i sensori
    private static final double SOGLIA_TEMPERATURA = 100.00;
    private static final double SOGLIA_PRESSIONE = 120.00;
    private static final double LIMITE_TEMPERATURA_MINIMA = -30.00;
    private static final double LIMITE_PRESSIONE_MINIMA = 90.00;
        
    
    // Costruttore
    public PiattaformaServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
    
    // Metodo che attende la connessione del client
    private void attendiConnessione() throws IOException {
        System.out.println("Server in attesa di connessione...");
        clientSocket = serverSocket.accept();
        System.out.println("Client " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " connesso.");
    }
    
    // Metodo che crea i canali di comunicazione
    private void creaCanali() throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    //Metodo per formattare i valori a due digit
    private static String formatta(double val){
        String formattato = String.format("%.2f", val);
        return formattato;
    }

     // Metodo per chiudere la connessione
     private void chiudiConnessione() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("Connessione chiusa.");
    }
    
    private void comunica() { 
        try{ 
            while (true) {
                String tipoSensore = in.readLine();
                String valoreSensoreStr = in.readLine();

                if (tipoSensore == null || valoreSensoreStr == null) {
                    break; // Connessione chiusa dal client
                }
               
                double valoreSensore = Double.parseDouble(valoreSensoreStr);
                String valSenFor = formatta(valoreSensore);
                if (tipoSensore.equals("temperatura")){
                    System.out.println("Dati ricevuti dal sensore " + tipoSensore + ": " + valSenFor + " °C");
                }
                else {
                    System.out.println("Dati ricevuti dal sensore " + tipoSensore + ": " + valSenFor + " kPa");
                }

                // Verifica se i valore sono al di fuori del range consentito
                if (tipoSensore.equals("temperatura") && valoreSensore < LIMITE_TEMPERATURA_MINIMA) {
                    System.out.println("Temperatura sotto il limite critico, sensore in avaria, chiusura della connessione...");
                    out.println("Temperatura troppo bassa! Connessione chiusa.");
                    chiudiConnessione();
                    break;
                } else if (tipoSensore.equals("pressione") && valoreSensore < LIMITE_PRESSIONE_MINIMA) {
                    System.out.println("Pressione sotto il limite critico, probabile avaria, chiusura della connessione...");
                    out.println("Pressione troppo bassa! Connessione chiusa.");
                    chiudiConnessione();
                    break;
                }

                // Risposta del server
                if (tipoSensore.equals("temperatura") && valoreSensore > SOGLIA_TEMPERATURA) {
                    out.println("Allarme! Temperatura critica: " + valSenFor);
                } else if (tipoSensore.equals("pressione") && valoreSensore > SOGLIA_PRESSIONE) {
                    out.println("Allarme! Pressione critica: " + valSenFor);
                } else {
                    out.println("Valore del sensore " + tipoSensore + " nella norma: " + valSenFor);
                }
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    chiudiConnessione();
                } catch (IOException e) {
                    System.err.println("Errore nella chiusura della connessione: " + e.getMessage());
                }
            } 
        }

   

    public static void main(String[] args) throws IOException {
        PiattaformaServer server = new PiattaformaServer(1234);
        server.attendiConnessione();
        server.creaCanali();
        server.comunica();
    }
}

