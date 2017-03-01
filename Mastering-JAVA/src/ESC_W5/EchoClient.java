package ESC_W5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.io.*;
import java.net.*;


import java.net.*;
import java.io.*;

class EchoServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(4321);
        ServerSocket serverSocket1 = new ServerSocket(4322);
        System.out.println("(... expecting connection ...)");

        Socket clientSocket = serverSocket.accept();

        System.out.println("(... connection established ...)");
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        BufferedReader stdIn =
                new BufferedReader(
                        new InputStreamReader(System.in));
        Socket clientSocket1 = serverSocket1.accept();

        System.out.println("(... connection established with Client 2 ...)");
        PrintWriter out1 =
                new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in1 = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        BufferedReader stdIn1 =
                new BufferedReader(
                        new InputStreamReader(System.in));

        String inputLine, inputLine1;
        while (!((inputLine1 = in1.readLine()).equals("Bye"))|((inputLine = in.readLine()).equals("Bye"))) {
            System.out.println("Wife says:" + inputLine1);
            System.out.println("Client 2:" +inputLine);
            out.println(stdIn.readLine());
            out.println(stdIn1.readLine());
            out.flush();
        }
        out.println(inputLine1);
        out.println(inputLine);
        serverSocket.close();
        clientSocket.close();
        out.close();
        in.close();
    }
}

public class EchoClient {
    public static void main(String[] args) throws Exception {
        String hostName = "localhost";
        //String hostIP = "10.11.3.28";
        //String hostName = "fe80::7517:c1af:b2bb:da73%4";
        int portNumber = 4321;


        Socket echoSocket = new Socket();
        SocketAddress sockaddr = new InetSocketAddress("localhost", portNumber);
        echoSocket.connect(sockaddr, 100);
        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(echoSocket.getInputStream()));
        BufferedReader stdIn =
                new BufferedReader(
                        new InputStreamReader(System.in));
        String userInput;
        do {
            userInput = stdIn.readLine();
            out.println(userInput);
            out.flush();
            System.out.println("Husband says: " + in.readLine());
        } while (!userInput.equals("Bye"));

        echoSocket.close();
        in.close();
        out.close();
        stdIn.close();
    }
}

class EchoClient2 extends EchoClient{

        public static void main(String[] args) throws Exception {
            String hostName = "localhost";
            //String hostIP = "10.11.3.28";
            //String hostName = "fe80::7517:c1af:b2bb:da73%4";
            int portNumber = 4322;

//        Socket echoSocket = new Socket(hostName, portNumber);
            Socket echoSocket = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("localhost", portNumber);
            echoSocket.connect(sockaddr, 101);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn =
                    new BufferedReader(
                            new InputStreamReader(System.in));
            String userInput;
            do {
                userInput = stdIn.readLine();
                out.println(userInput);
                out.flush();
                System.out.println("Server says: " + in.readLine());
            } while (!userInput.equals("Bye"));

            echoSocket.close();
            in.close();
            out.close();
            stdIn.close();
        }


}
