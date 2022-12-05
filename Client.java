import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Client {
    private static String input, output;

    /**
     * This is the main method which starts the socket, printwriter and bufferedreader objects. It also
     * implements the protocol and handles communication between the server
     * @param args an array of Strings passed in as a command line argument
     */
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "You must enter the hostname and port number");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket sock = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(sock.getInputStream()));
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            Client c = new Client();
            String fromServer, fromUser, check;

            int counter = 0;
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            String str = in.readLine();
            if ("CONNECTED".equals(str)){
                System.out.println("START GAME");
                fromUser = "START GAME";
                out.println(fromUser);
                System.out.println(c.prompts(0));
            }

            while ((fromServer = in.readLine()) != null) {
                if("INVALID GUESS".equals(fromServer)){
                    System.out.println("INVALID GUESS");
                }
                else if (fromServer == "GAME OVER"){
                    Server.setCount(Server.getCount() - 1);
                    System.exit(0);
                }
                else if (pattern.matcher(fromServer).matches()){
                    System.out.println(fromServer);
                    System.out.println("GAME OVER");
                    Server.setCount(Server.getCount() - 1);
                    sock.close();
                    System.exit(0);
                }
                else if (fromServer.length() == 5){
                    if (c.checkHint(fromServer) == false){
                        System.out.println("There was a problem with the hint. Closing Connection");
                        System.exit(0);
                    }
                    else{
                        int count = c.checkChars(fromServer);
                        if (count == 0){
                            counter++;
                            System.out.println(fromServer);
                            if (counter > 1) {
                                System.out.println(c.prompts(1));
                            }
                        }
                        else if (count == 1){
                            counter++;
                            System.out.println(fromServer);
                            if (counter > 1){
                                System.out.println(c.prompts(2));
                            }
                        }
                        else{
                            counter++;
                            System.out.println(fromServer);
                            if (counter > 1) {
                                String temp = String.format("Great effort, you managed to figure out %d letters that are in the word!", count);
                                System.out.printf(temp + "\n");
                            }
                        }
                    }
                }
                else{
                    System.out.println("There was an error with the response from the server. Closing connection");
                    sock.close();
                    System.exit(0);
                }

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    check = c.validateWord(fromUser);

                    if (check == "INVALID GUESS"){
                        out.println("INVALID GUESS");
                    }
                    else{
                        out.println(fromUser);
                    }
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about this host - " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Could not connect to " +
                    hostName + " on port number " + portNumber);
            System.exit(1);
        }
    }

    /**
     * This method checks the current hint to make sure it is a valid hint
     * @param fromServer accepts a String that is the current hint
     * @return returns true if the hint is valid, false if not
     */
    public Boolean checkHint(String fromServer){
        Boolean flag = true;
        ArrayList<Character> al = new ArrayList<Character>();

            for (int k = 0; k < 5; k++){
                if (fromServer.charAt(k) != '_') {
                    al.add(fromServer.charAt(k));
                }
            }
            for (int j = 0; j < al.size(); j++){
                String out = output.toLowerCase();
                if (!out.contains(al.get(j).toString().toLowerCase())){
                    flag = false;
                }
            }
        return flag;
    }

    /**
     * This method validates the Client's message to ensure they are valid messages before sending to the server
     * @param input accepts a String that is the Client's current message
     * @return returns a String. START GAME, INVALID GUESS or another valid message from the Client
     */
    public String validateWord(String input){
        if (input == "START GAME"){
            output = "START GAME";
        }
        else if (input.length() == 5){
            if (input.matches("[a-zA-Z]+")){
                output = input;
            }
            else{
                output = "INVALID GUESS";
            }
        }
        else{
            output = "INVALID GUESS";
        }
        return output;
    }

    /**
     * This method stores a range of prompts to provide the client with
     * @param index accepts in int used to select the particular prompt
     * @return returns the chosen prompt
     */
    public String prompts(int index){
        String[] prompts = {"Please enter a 5 letter word. It does not matter if you use capitals or lowercase.",
                "Nice try but none of those letters were correct. Have another go!",
                "Good work, you have managed to figure out 1 letter that is in the word!"};
        return prompts[index];
    }

    /**
     * This method checks the number of letters that are in both the Client's guess and their target word
     * @param input accepts a String which is the Client's current guess
     * @return returns the number of characters in both the Client's guess and their target word
     */
    public int checkChars(String input){
        int count = 0;
        for (int i = 0; i < 5; i++){
            if (input.charAt(i) != 95){
                count++;
            }
        }
        return count;
    }
}