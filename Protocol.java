import java.util.*;
import java.io.*;

public class Protocol {

    private static ArrayList<String> target, guess;
    private static Boolean flag;
    private static String targetWord;
    private static final int SENTHINT = 2;

    private static final int CORRECT = 3;

    private int state;


    /**
     * This method calls the readTarget, readGuess and chooseWord methods
     * @return Returns the target word as a String
     */
    public String run(){
        target = readTarget();
        guess = readGuess();
        targetWord = chooseWord(randomNumber(0, target.size()));
        return targetWord;
    }

    /**
     * This methhod populates an ArrayLists with the target words
     * @return Returns the target ArrayList
     */
    public ArrayList<String> readTarget()
    {
        target = new ArrayList<>();
        try(FileReader reader = new FileReader("target.txt"))
        {
            Scanner scan = new Scanner(reader);
            while(scan.hasNextLine()) {
                target.add(scan.nextLine());
            }
        }

        catch(Exception e) {
            System.out.println("There was a problem reading the target.txt file!");
        }
        return target;
    }

    /**
     * This methhod populates an ArrayLists with the guess words
     * @return Returns the guess ArrayList
     */
    public ArrayList<String> readGuess()
    {
        guess = new ArrayList<>();
        try(FileReader reader = new FileReader("guess.txt"))
        {
            Scanner scan = new Scanner(reader);
            while(scan.hasNextLine()) {
                guess.add(scan.nextLine());
            }
        }

        catch(Exception e) {
            System.out.println("There was a problem reading the guess.txt file!");
        }
        return guess;
    }


    /**
     * This method selects a random number between a given min and mac
     * @param min accepts an integer for the minimum of the range
     * @param max accepts an integer for the maximum of the range
     * @return returns the chosen random number as an int
     */
    public int randomNumber(int min, int max)
    {
        int random = (int)(Math.random() * (max - min + 1)) + min;
        return random;
    }

    /**
     * This method chooses a target word from the ArrayList
     * @param random accepts an int that is used to select the word
     * @return returns the selected target word as a String
     */
    public String chooseWord(int random){
        targetWord = target.get(random);
        return targetWord;
    }

    /**
     * This method checks if a word is in the guess word list
     * @param word accepts a String that is used to check if it is in the guess word list
     * @return returns true if the word is in the list and false if not
     */
    public Boolean checkWord(String word){
        if (guess.contains(word))
            flag = true;
        else
            flag = false;
        return flag;
    }

    /**
     * This method is the protocol by which the game adheres to
     * @param player is a Player object that contains information about the current state of the player
     * @return returns the output produced from the player's input
     */
    public String processInput(Player player) {
        if ("START GAME".equals(player.getCurrentInput())){
            player.setCurrentOutput("_____");
            state = SENTHINT;
        }
        else if (state == SENTHINT) {
            if (checkWord(player.getCurrentInput()) == true) {
                if (player.getCurrentInput().equals(player.getTargetWord())){
                    player.setNoOfGuesses(player.getNoOfGuesses() + 1);
                    player.setCurrentOutput("END");
                    state = CORRECT;
                }
                else if (!player.getCurrentInput().equals(player.getTargetWord())) {
                    player.setNoOfGuesses(player.getNoOfGuesses() + 1);
                    player.setCurrentOutput(createHint(player));
                    state = SENTHINT;
                }
            }else if (state == CORRECT){
                player.setCurrentOutput("GAME OVER");
            }
            else if (checkWord(player.getCurrentInput()) == false){
                player.setCurrentOutput("INVALID GUESS");
            }
        }

        player.setCurrentOutput(player.getCurrentOutput() + "\n");
        return player.getCurrentOutput();
    }


    /**
     * This method creates the hint to send to the Client
     * @param player is a PLayer object that contains information about the current state of the player
     * @return returns the hint
     */
    public String createHint(Player player){
        String guess = player.getCurrentInput().toLowerCase();
        String target = player.getTargetWord().toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append("");

        if (guess != target){
            for (int k = 0; k < 5; k++){
                if (target.charAt(k) == guess.charAt(k)) {
                    char t = guess.charAt(k);
                    t = Character.toUpperCase(t);
                    sb.append(t);
                }
                else {
                    sb.append("_");
                }
            }
            for (int i = 0; i < 5; i++) {
                if (sb.charAt(i) == '_') {
                    if ((target.indexOf(guess.charAt(i)) != -1) && String.valueOf(sb).toLowerCase().indexOf(guess.charAt(i)) == -1){
                        sb.setCharAt(i, guess.charAt(i));
                    }
                    else if ((target.indexOf(guess.charAt(i)) != -1 && String.valueOf(sb).toLowerCase().indexOf(guess.charAt(i)) != -1)) {
                        int finalI = i;
                        int countTarget = (int) target.chars().filter(ch -> ch == guess.charAt(finalI)).count();
                        int countOutput = (int) String.valueOf(sb).toLowerCase().chars().filter(ch -> ch == guess.charAt(finalI)).count();
                        if (countTarget > countOutput) {
                            sb.setCharAt(i, guess.charAt(i));
                        }
                    }
                }
            }
        }
        String output = String.valueOf(sb);
        return output;
    }
}
