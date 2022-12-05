/**
 * This class handles the communication to the client
 * @author Claire Grady
 * version ver 1.0.0
 */
import java.net.*;
import java.io.*;
import java.util.regex.Pattern;

public class ServerThread extends Thread {
    private Socket socket = null;
    private SocketAddress rSA;

    /**
     * The non-default constructor sets values defined by the user/programmer
     * @param socket accepts the socket object
     */
    public ServerThread(Socket socket) {
        super("ServerThread");
        this.socket = socket;
        //remote socket address is unique for each client
        rSA = this.socket.getRemoteSocketAddress();
    }

    /**
     * This method creates the printwriter and bufferedreader objects, implements the protocol
     * and communicates with the Client
     */
    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            out.println("CONNECTED");
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            String inputLine, outputLine;
            Protocol pro = new Protocol();
            String target = pro.run();
            Player p = new Player(rSA, target, 0, "", "");

            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.toUpperCase();
                p.setCurrentInput(inputLine);
                p.setCurrentOutput("");
                outputLine = pro.processInput(p);
                outputLine = outputLine.trim();

                if (outputLine != null) {
                    if ("GAME OVER".equals(outputLine)) {
                        out.println(outputLine);
                        System.exit(0);
                    }
                    else if ("END".equals(outputLine)){
                        out.println(p.getNoOfGuesses());
                        if (Server.getCount() == 1){
                            System.out.println("All games are over so the server is closing");
                            System.exit(0);
                        }
                    }
                    else{
                        out.println(outputLine);
                    }
                }

            }
            socket.close();
            Server.setCount(Server.getCount() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}