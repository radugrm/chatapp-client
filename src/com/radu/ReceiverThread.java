package com.radu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;

public class ReceiverThread implements Runnable {
    private Client client;

    public ReceiverThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            try {
                client.readMessage();
            } catch (SocketException e) {
                System.out.println("Server shutdown");
                break;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
