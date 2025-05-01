package cs_tcp_03_calcolatrice;



import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Calcolatrice calcolatrice;

    // Costruttore
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        calcolatrice = new Calcolatrice();
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

    // Metodo per la comunicazione con il client
    private void comunica() throws IOException {
        String operazione = "";
        while (true) {
            out.println("Scegli un'operazione (0=Esci, 1=Addizione, 2=Sottrazione, 3=Moltiplicazione, 4=Divisione): ");
            operazione = in.readLine();
            
            // Controllo se l'utente vuole uscire
            if (operazione.equals("0")) {
                out.println("Chiusura della connessione...");
                break;
            }

            double a = 0;
            double b = 0;

            // Richiesta del primo operando con gestione dell'eccezione
            while (true) {
                out.println("Inserisci il primo operando: ");
                String inputA = in.readLine();
                if (inputA.isEmpty()) {
                    out.println("Input non valido. Riprova.");
                    continue;
                }
                try {
                    a = Double.parseDouble(inputA);
                    break; // Esci dal ciclo se l'input è valido
                } catch (NumberFormatException e) {
                    out.println("Input non valido. Riprova con un numero decimale.");
                }
            }

            // Richiesta del secondo operando con gestione dell'eccezione
            while (true) {
                out.println("Inserisci il secondo operando: ");
                String inputB = in.readLine();
                if (inputB.isEmpty()) {
                    out.println("Input non valido. Riprova.");
                    continue;
                }
                try {
                    b = Double.parseDouble(inputB);
                    break; // Esci dal ciclo se l'input è valido
                } catch (NumberFormatException e) {
                    out.println("Input non valido. Riprova con un numero decimale.");
                }
            }

            double risultato = 0;

            switch (operazione) {
                case "1":
                    risultato = calcolatrice.addizione(a, b);
                    break;
                case "2":
                    risultato = calcolatrice.sottrazione(a, b);
                    break;
                case "3":
                    risultato = calcolatrice.moltiplicazione(a, b);
                    break;
                case "4":
                    try {
                        risultato = calcolatrice.divisione(a, b);
                    } catch (ArithmeticException e) {
                        out.println("Errore: " + e.getMessage());
                        continue;
                    }
                    break;
                default:
                    out.println("Operazione non valida.");
                    continue;
            }
            out.println("Il risultato è: " + risultato);
        }
        chiudiConnessione();
    }

    // Metodo per chiudere la connessione
    private void chiudiConnessione() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("Connessione chiusa.");
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(1234);
        server.attendiConnessione();
        server.creaCanali();
        server.comunica();
    }
}

