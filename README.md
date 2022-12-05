Project Name: COSC540 Claire Grady Wordle-like Game Assignment 2

Run:

The simplest way to run the program is to use the two shell scripts provided.
Start by opening a terminal and running sh startServer.sh followed by a port number of your choosing.
Then in a separate terminal window run sh startClient.sh followed by an IP address and the port number
you selected for startServer.sh

If the port is busy or there are other connection issues, you will receive an error message. Otherwise
the game will start and you will be provided with prompts to help you play the game.

Game Rules:

For anyone who has never played Wordle before, the idea is to guess a 5 letter word that has been
selected by the server. Each time you enter your guess, the server will respond with a hint.
For each letter that you enter, it will compare it with the target word. If the letter you entered
was not in the target word the hint will consist of an underscore _ where that letter was in your guess,
if the letter is in the target word but not in the correct place the hint will include that letter in
lowercase where the letter was in your guess and finally if the letter is in the target word and is in
the correct place the hint will include that letter in capitals where the letter was in your guess.
An example is shown below. You have an unlimited number of guesses and your guesses are case insensitive.
If your guess word is an invalid word you will receive an error indicating this. Good luck!!!

Target Word: APPLE

START GAME
CATCH
_a___
Taste
_a__E
ALIKE
Al__E
APPLE
4
GAME OVER