package cs_tcp_04_piattaforma;

import java.io.*;
import java.net.*;
import java.util.*;

public class SensoreClient {

    //definisco gli oggetti 
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Timer timer;
    private static final Random random = new Random();
    // Soglie critiche per i sensori
    private static final double SOGLIA_TEMPERATURA = 110.00;
    private static final double SOGLIA_PRESSIONE = 130.00;
    private static final double TEMPERATURA_MINIMA = -40.00;
    private static final double PRESSIONE_MINIMA = 80.00;

    // Costruttore
    public SensoreClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
    }

    // Metodo per creare i canali di comunicazione con il server
    private void creaCanali() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    //Metodo per formattare i valori a due digit
    private static String formatta(double val){
        String formattato = String.format("%.2f", val);
        return formattato;
    }

    // Metodo per generare valori casuali di temperatura nel range -30.0 a 60.0
    private static double generaValoreTemperatura() {
        return TEMPERATURA_MINIMA + (SOGLIA_TEMPERATURA - (TEMPERATURA_MINIMA)) * random.nextDouble();
    }

    // Metodo per generare valori casuali di pressione nel range -10.0 a 110.0
    private static double generaValorePressione() {
        return PRESSIONE_MINIMA + (SOGLIA_PRESSIONE - (PRESSIONE_MINIMA)) * random.nextDouble();
    }

    // Metodo per chiudere la connessione
    private void chiudiConnessione() throws IOException {
        in.close();
        out.close();
        socket.close();
        System.out.println("Connessione chiusa.");
    }

    // Metodo per inviare i dati e gestire la risposta
    private void inviaDatiSensore(String tipoSensore, double valoreSensore) throws IOException {
        // Invia il tipo e il valore del sensore
        out.println(tipoSensore);
        out.println(valoreSensore);

        // Formattare il valore del sensore con 2 cifre decimali
        String valoreFormattato = formatta(valoreSensore);

        // Stampa il valore inviato al server
        if (tipoSensore.equals("temperatura")) {
            System.out.println("Dati inviati -> Sensore: " + tipoSensore + ", Valore: " + valoreFormattato + " °C");
        } else {
            System.out.println("Dati inviati -> Sensore: " + tipoSensore + ", Valore: " + valoreFormattato + " kPa");
        }

        // Ricevi la risposta dal server
        String risposta = in.readLine();
        if (risposta == null) {
            System.out.println("Connessione chiusa dal server.");
            chiudiConnessione();
            timer.cancel(); // annulla il timer
            return;
        }

        // Stampa la risposta del server
        System.out.println("Centro di Controllo: " + risposta);
    }

    
    // Metodo per comunicare con il server
    private void comunica() throws IOException {
        System.out.println("Connessione al Centro di Controllo...");

        // Definisco un oggetto timer di classe Timer
        timer = new Timer();

        TimerTask invio = new TimerTask() {
            // Evento per l'invio dei dati
            public void run() {
                try {
                    // Invio i dati di temperatura e pressione
                    inviaDatiSensore("temperatura", generaValoreTemperatura());
                    inviaDatiSensore("pressione", generaValorePressione());
                } catch (IOException e) {
                    System.err.println("Errore nella comunicazione con il server: " + e.getMessage());
                    try {
                        chiudiConnessione();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    this.cancel(); // Ferma il TimerTask in caso di errore
                }
            }
        };
        
        timer.schedule(invio, 0, 2000);
    }

    public static void main(String[] args) throws IOException {
        SensoreClient client = new SensoreClient("localhost", 1234);
        client.creaCanali();
        client.comunica();
    }
}