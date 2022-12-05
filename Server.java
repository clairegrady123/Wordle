/**
 * This class handles the serverSocket
 * @author Claire Grady
 * version ver 1.0.0
 */

import java.net.*;
import java.io.*;

public class Server {
    private static int count = 0;

    /**
     * This is the main method which starts the serverSocket, listens for client connection requests,
     * accepts connections and creates a new thread for each
     * @param args an array of Strings passed in as a command line argument
     */

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("You must enter a port number");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                new ServerThread(serverSocket.accept()).start();
                count++;
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }

    /**
     * This method gets the number of open sockets
     * @return Returns the number of open sockets as an int
     */
    public static int getCount(){
        return count;
    }

    /**
     * This method sets the number of sockets that are open
     * @param counter accepts the number of sockets as an integer
     */
    public static void setCount(int counter){
        count = counter;
    }
}