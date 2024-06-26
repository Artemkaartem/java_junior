package ru.gb;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class Client {

    public static void main(String[] args) {
        try {
            Socket serverSocket = new Socket("localhost", Server.PORT);
            System.out.println("Подключились к серверу: tcp://localhost:" + Server.PORT);
            new PrintWriter(serverSocket.getOutputStream(), true).println(UUID.randomUUID());
            new Thread(new ServerReader(serverSocket)).start();
            new Thread(new ServerWriter(serverSocket)).start();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось подключиться к серверу: " + e.getMessage(), e);
        }
    }

}

class ServerWriter implements Runnable {
    private final Socket serverSocket;

    public ServerWriter(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        Scanner consoleReader = new Scanner(System.in);
        try (PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true)) {
            boolean isRunning = true;
            while (isRunning) {
                String msgFromConsole = consoleReader.nextLine();
                out.println(msgFromConsole);
                if (msgFromConsole.equals("/exit")) {
                    System.out.println("Отключаемся...");
                    isRunning = false;
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при отправке на сервер: " + e.getMessage());
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Ошибка при отключении от сервера: " + e.getMessage());
        }
    }

}

class ServerReader implements Runnable {
    private final Socket serverSocket;

    public ServerReader(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try (Scanner in = new Scanner(serverSocket.getInputStream())) {
            while (in.hasNext()) {
                String input = in.nextLine();
                System.out.println(input);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при отключении чтении с сервера: " + e.getMessage());
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Ошибка при отключении от сервера: " + e.getMessage());
        }
    }

}