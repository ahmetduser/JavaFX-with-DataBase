/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.TripCalculator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author ahmetduser
 */
//MultiThreaded Server
public class Server {

    public static double costOfFuel;

    //ExecutorService automatically provides a pool of threads
    private static final ExecutorService POOL = Executors.newFixedThreadPool(4);
    private static Socket socket;

    public static void main(String[] args) {

        System.out.println("Server is ready");
        try (ServerSocket ss = new ServerSocket(9999)) {
            System.out.println("Server is waiting for client request");
            while (true) {
                socket = ss.accept();
                System.out.println("Client connected");
                // Create an object of ClientHandler class
                ClientHandler clientSocket = new ClientHandler(socket);
                
                POOL.execute(clientSocket);

            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
        }

    }

}
