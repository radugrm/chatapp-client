package com.radu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your username:");
        Client client;
        while (true) {
            try {
                client = new Client(sc.nextLine());
                client.connect("localhost", 9999);
                break;
            } catch (UserAlreadyExistsException e) {
                System.out.print(
                        "Username already exists!\n" +
                                "Enter another username:\n");
            }
        }
        while (true) {
            System.out.println("Send to:");
            String to = sc.nextLine();
            System.out.println("Message");
            String message = sc.nextLine();
            client.sendMessage(to, message);
        }
    }
}
