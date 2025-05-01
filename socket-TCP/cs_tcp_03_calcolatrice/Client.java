package cs_tcp_03_calcolatrice;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader tastiera;

    // Costruttore
    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
        tastiera = new BufferedReader(new InputStreamReader(System.in));
    }

    // Metodo per connettersi al server
    private void connetti() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    // Metodo per la comunicazione con il server
    private void comunica() throws IOException {
        String risposta;
        while (true) {
            risposta = in.readLine();
            System.out.println(risposta);
            String operazione = tastiera.readLine();
            out.println(operazione);

            if (operazione.equals("0")) break; 

            risposta = in.readLine();
            System.out.println(risposta);
            String operando1 = tastiera.readLine();
            out.println(operando1);

            risposta = in.readLine();
            System.out.println(risposta);
            String operando2 = tastiera.readLine();
            out.println(operando2);

            risposta = in.readLine();
            System.out.println(risposta);
        }
        chiudiConnessione();
    }

    // Metodo per chiudere la connessione
    private void chiudiConnessione() throws IOException {
        in.close();
        out.close();
        socket.close();
        System.out.println("Connessione chiusa.");
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 1234);
        client.connetti();
        client.comunica();
    }
}


