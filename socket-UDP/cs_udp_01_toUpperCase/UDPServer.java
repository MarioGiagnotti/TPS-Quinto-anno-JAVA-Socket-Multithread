package cs_udp_01_toUpperCase;

import java.io.*;
import java.net.*;

public class UDPServer {
    public static void main(String[] args) {
        try {
            DatagramSocket UDPserver = new DatagramSocket(12345);
            DatagramPacket datagrammaRicevuto;
            DatagramPacket datagrammaDaInviare;
            boolean attivo = true;
            byte[] bufferIN = new byte[1024];
            byte[] bufferOUT;
        
            System.out.println("Server avviato...");

            while(attivo){
                // Definizione del datagramma di ricezione
                datagrammaRicevuto = new DatagramPacket(bufferIN, bufferIN.length);
                // Attesa ricezione pacchetto
                UDPserver.receive(datagrammaRicevuto);    

                // Estrazione del messaggio ricevuto
                String ricevuto = new String(datagrammaRicevuto.getData(), 0, datagrammaRicevuto.getLength());
                System.out.println("Messaggio ricevuto: " + ricevuto);

                // Riconoscimento parametri del socket del client
                InetAddress IPClient = datagrammaRicevuto.getAddress();
                int portaClient = datagrammaRicevuto.getPort();

                // Preparazione della risposta
                String messaggioOUT = ricevuto.toUpperCase();
                bufferOUT = messaggioOUT.getBytes();
                datagrammaDaInviare = new DatagramPacket(bufferOUT, bufferOUT.length, IPClient, portaClient);

                // Invio del datagramma al client
                UDPserver.send(datagrammaDaInviare);

                // Se il client digita "0", termina l'esecuzione
                if (ricevuto.equals("0")){
                    System.out.println("Arrivederci...");
                    attivo = false;
                }
            }
            UDPserver.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
