package cs_udp_01_toUpperCase;

import java.io.*;
import java.net.*;

public class UDPClient2 {
    public static void main(String[] args) {
        try {
            int portaServer = 12345;
            InetAddress IPServer = InetAddress.getByName("localhost");
            byte[] bufferIN = new byte[1024];
            byte[] bufferOUT;
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket clientSocket;
            DatagramPacket datagrammaDaInviare;
            DatagramPacket datagrammaRicevuto;
        
           //Creazione del socket 
           clientSocket = new DatagramSocket();
           System.out.println("Inserisci una stringa: \n");

           //leggiamo la stringa inserita e predisponiamo lo stream di OUT
           String messaggioDaInviare = input.readLine();
           bufferOUT =messaggioDaInviare.getBytes();

            //Creiamo il Datagramma da inviare
            datagrammaDaInviare = new DatagramPacket(bufferOUT, bufferOUT.length, IPServer, portaServer);
            clientSocket.send(datagrammaDaInviare);

            //Attesa risposta dal server
            datagrammaRicevuto = new DatagramPacket(bufferIN, bufferIN.length);
            clientSocket.receive(datagrammaRicevuto);
            String ricevuto = new String(datagrammaRicevuto.getData());


            // Elaborazione dati ricevuti
            int numCaratteri = datagrammaRicevuto.getLength();
            ricevuto = ricevuto.substring(0, numCaratteri);
            System.out.println("Messaggio ricevuto dal server: " + ricevuto);
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
