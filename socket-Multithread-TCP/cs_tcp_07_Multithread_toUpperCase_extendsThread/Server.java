package cs_tcp_07_Multithread_toUpperCase_extendsThread;


import java.io.*;
import java.net.*;

// Classe principale del server
public class Server {
    private static final int PORT = 12345; // Porta su cui il server ascolta
    private ServerSocket serverSocket;

    // Costruttore
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    // Metodo per iniziare ad ascoltare le connessioni
    public void Connetti() {
        System.out.println("Server in ascolto sulla porta " + PORT);
        try {
            while (true) {
                // Attende una connessione da un client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connesso: " + clientSocket.getInetAddress());

                // Assegna un nuovo thread per gestire la comunicazione con il client
                ServerThread thread = new ServerThread(clientSocket);
                thread.start(); // Avvia il thread
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close(); // Chiude il ServerSocket alla fine
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(PORT);
            server.Connetti();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Sottoclasse di Thread per gestire la comunicazione con un client
class ServerThread extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    //costruttore
    public ServerThread(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Crea i canali di comunicazione
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Benvenuto e richiesta nome al client
            String benvenuto = "Ciao, dimmi il tuo nome: ";
            out.println(benvenuto);
            // Lettura del nome del client
            String nome = in.readLine();
            System.out.println(nome + " si è connesso! IP: " + clientSocket.getInetAddress() + " Porta: " + clientSocket.getPort());

            // Ciclo per leggere le stringhe inviate dal client
            String inputLine;
            while (true) {
                out.println("Scrivi una stringa (0=Esci): ");
                inputLine = in.readLine();
                if (inputLine.equals("0")) {
                    System.out.println(nome + " si è disconnesso");
                    break; // Termina la connessione se il client invia "0"
                }
                // Risponde con la stringa in maiuscolo
                out.println(inputLine.toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Chiude le risorse
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

