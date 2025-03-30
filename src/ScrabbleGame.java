//Ryan Sowersby
//Alejandro Perez
//Gongyu Yan
//Added: Adding points to the correctly guessed words 
//Added: Changing letters out when the user wants to exchange a letter for a new one
//Added: Replay system to allow the user to play again after each round
//Added: Total score after each round to keep track of the users score
//Added: Unique values for each letter and the amount used per word

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ScrabbleGame {

    
    public static void main(String[] args) {
      
        // Initialize 
        ArrayList<Word> words = new ArrayList<>();
        try (Scanner inputScanner = new Scanner(System.in)) {
            boolean keepPlaying = true;
            int totalScore = 0;
            // reading from the collins words file
            try (Scanner scanner = new Scanner(new File("CollinsScrabbleWords_2019.txt"))) {
                while (scanner.hasNextLine()) {
                    String word = scanner.nextLine().trim().toLowerCase();
                    words.add(new Word(word));
                }
            } catch (FileNotFoundException e) {//if the file does not exist and/or cant be found
                System.out.println("File not found!");
                return; 
            }
            //Adds replay feature
      while (keepPlaying) {
             // WIll produce 4 random letter for the user to unscramble
            //using a string
            String randLetters = "";  
            String letters = "abcdefghijklmnopqrstuvwxyz";
            Random rand = new Random();
            for (int i = 0; i < 4; i++) {//4 letters
                randLetters += letters.charAt(rand.nextInt(letters.length())); // append to the random string
            }
            System.out.println("Random letters: " + randLetters);
            // Allows user to opt in for changing a letter in their given set
            System.out.println("would you like to change a letter? (y/n)");
            String exchangeChoice = inputScanner.nextLine().trim().toLowerCase();

                if (exchangeChoice.equals("y")) {
                    System.out.println("Enter the position of the letter to exchange (1-" + randLetters.length() + "):");
                    int position = inputScanner.nextInt();
                    inputScanner.nextLine(); // consume newline

                    if (position >= 1 && position <= randLetters.length()) {
                        // Generate new random letter
                        char newLetter = letters.charAt(rand.nextInt(letters.length()));
                        
                        // Create new string with exchanged letter
                        randLetters = randLetters.substring(0, position-1) + 
                                    newLetter + 
                                    randLetters.substring(position);
                                    
                        System.out.println("Your new letters are: " + randLetters);
                    } else {
                        System.out.println("Invalid position. Please enter a number between 1 and " + randLetters.length());
                    }
                }
                
                System.out.print("Enter a word: ");
                String userInput = inputScanner.nextLine().trim().toLowerCase();
                
                // Calculate points for valid words
                int points = calculatePoints(userInput);
                if (binarySearch(words, userInput) >= 0) {
                    System.out.println("Valid word! Points earned: " + points);
                    totalScore += points;
                } else {
                    System.out.println("Not a valid word!");
                }
                System.out.print("Do you want to keep playing? (y/n)");
                String playAgain = inputScanner.nextLine().trim().toLowerCase();
                keepPlaying = playAgain.equals("y");
            }
             System.out.println("Thanks for playing! Final score: " + totalScore);
             inputScanner.close();
        }
       
    }

// Adds different point values for each letter
    public static int calculatePoints(String word) {
        Map<Character, Integer> letterPoints = new HashMap<>();
        letterPoints.put('a', 1); letterPoints.put('b', 3); letterPoints.put('c', 3);
        letterPoints.put('d', 2); letterPoints.put('e', 1); letterPoints.put('f', 4);
        letterPoints.put('g', 2); letterPoints.put('h', 4); letterPoints.put('i', 1);
        letterPoints.put('j', 8); letterPoints.put('k', 5); letterPoints.put('l', 1);
        letterPoints.put('m', 3); letterPoints.put('n', 1); letterPoints.put('o', 1);
        letterPoints.put('p', 3); letterPoints.put('q', 10); letterPoints.put('r', 1);
        letterPoints.put('s', 1); letterPoints.put('t', 1); letterPoints.put('u', 1);
        letterPoints.put('v', 4); letterPoints.put('w', 4); letterPoints.put('x', 8);
        letterPoints.put('y', 4); letterPoints.put('z', 10);

        int points = 0;
        for (char c : word.toLowerCase().toCharArray()) {
            points += letterPoints.getOrDefault(c, 0 );
        }
        return points;
        
    }
    // binary search method
    public static int binarySearch(ArrayList<Word> words, String target) {
        int left = 0;
        int right = words.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midWord = words.get(mid).getText(); // Fix method name

            if (midWord.equals(target)) {
                return mid;
            }
            if (midWord.compareTo(target) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1; // Word is not found on the list
    
    
       
    
        
            
        
    }
    
}
