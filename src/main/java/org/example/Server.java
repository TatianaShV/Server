package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.example.ServerConfig.PORT;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT);) {
            System.out.println("Сервер запущен");
            int client = 0;
            String firstCity = "";
            String city = "";
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    System.out.println("New connection accepted");
                    System.out.println("Подключен клиент " + clientSocket.getPort());
                    client++;

                    if (client == 1) {
                        out.println("Это первое подключение");
                        firstCity = in.readLine();
                        out.println("Ok");

                    } else if (client >= 2) {
                        out.println(firstCity);
                        city = in.readLine();
                        if (city.charAt(0) == firstCity.charAt(firstCity.length() - 1)) {
                            out.println("Ok");
                            firstCity = city;

                        }
                        if (city.charAt(0) != firstCity.charAt(firstCity.length() - 1)) {
                            out.println("Not ok");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();

        }
    }
}