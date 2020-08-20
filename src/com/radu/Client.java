package com.radu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private String username;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Client(String username) {
        this.username = username;
    }

    public void connect(String ip, int port) throws UserAlreadyExistsException {
        try {
            socket = new Socket(ip, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            attemptConnect();
            Thread t = new Thread(new ReceiverThread(this));
            t.start();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void attemptConnect() throws IOException, ClassNotFoundException, UserAlreadyExistsException {
        oos.writeObject(username);
        oos.flush();
        ois = new ObjectInputStream(socket.getInputStream());
        boolean connected = (boolean) ois.readObject();
        if (!connected) {
            throw new UserAlreadyExistsException();
        }
    }


    public void readMessage() throws IOException, ClassNotFoundException {
        Message message = (Message) ois.readObject();
        System.out.println(message);
    }

    public void sendMessage(String receiver, String message) {
        try {
            oos.writeObject(new Message(username, receiver, message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
