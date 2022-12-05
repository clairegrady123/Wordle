import java.net.SocketAddress;

public class Player {

    private SocketAddress rSA;
    private String targetWord;
    private int noOfGuesses;
    private static String currentInput;
    private String currentOutput;

    /**
     * The non-default constructor sets values defined by the user/programmer
     * @param rSA is the remote socket address of the player's connection
     * @param targetWord the target word for that player
     * @param noOfGuesses the current number of valid guesses the player has had
     * @param currentInput the current input from the player
     * @param currentOutput the current output for the player
     */
    public Player(SocketAddress rSA, String targetWord, int noOfGuesses, String currentInput, String currentOutput){
        this.rSA = rSA;
        this.targetWord = targetWord;
        this.noOfGuesses = noOfGuesses;
        this.currentInput = currentInput;
        this.currentOutput = currentOutput;
    }


    /**
     * This method gets the player's current input
     * @return returns the player's current input
     */
    public static String getCurrentInput(){
        return currentInput;
    }

    /**
     * This method gets the player's current output
     * @return returns the player's current output
     */
    public String getCurrentOutput(){
        return currentOutput;
    }

    /**
     * This method gets the player's current number of guesses
     * @return returns the player's current number of guesses
     */
    public int getNoOfGuesses(){
        return noOfGuesses;
    }

    /**
     * This method gets the player's remote socket address
     * @return returns the player's remote socket address
     */
    public SocketAddress getrSA(){
        return rSA;
    }

    /**
     * This method gets the player's target word
     * @return returns the player's target word
     */
    public String getTargetWord(){
        return targetWord;
    }


    /**
     * This method sets the player's current input
     * @param currentInput accepts a String to use for the players' current input
     */
    public void setCurrentInput(String currentInput){
        this.currentInput = currentInput;
    }

    /**
     * This method sets the player's current output
     * @param currentOutput accepts a String to use for the players' current output
     */
    public void setCurrentOutput(String currentOutput){
        this.currentOutput = currentOutput;
    }

    /**
     * This method sets the player's current number of guesses
     * @param noOfGuesses accepts an int to use for the players' current number of guesses
     */
    public void setNoOfGuesses(int noOfGuesses){
        this.noOfGuesses = noOfGuesses;
    }

    /**
     * This method sets the player's current remote socket address
     * @param rSA accepts a remote socket address to use for the players' remote socket address
     */
    public void setrSA(SocketAddress rSA){
        this.rSA = rSA;
    }

    /**
     * This method sets the player's current target word
     * @param targetWord accepts a String to use for the players' current target word
     */
    public void setTargetWord(String targetWord){
        this.targetWord = targetWord;
    }

    public void print(){
        System.out.println("RSA: " + rSA + " Guesses: " + noOfGuesses + " Target Word: " + targetWord + " Input: " + currentInput + " Output: " + currentOutput);
    }
}
