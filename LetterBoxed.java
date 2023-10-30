/*
 * LetterBoxed - represents the state of a Letter Boxed puzzle,
 * and solves it using recursive backtracking.
 * 
 */

import java.util.*;

public class LetterBoxed {
    public static final int MOST_WORDS = 10;
    public static final String WORDS_FILE = "word_list.txt";
    public static final Dictionary dictionary = new Dictionary(WORDS_FILE);
    
    // The sides of the puzzle, given as four strings of length 3.
    private String[] sides;
    
    // The individual letters in the puzzle.
    // Each element of this array is a single-character string.
    private String[] letters;
    
    // The words in the solution.
    private String[] words;
    
    /*
     * Constructor for a puzzle with the specified sides, where each
     * side is a string containing the 3 letters from one side of the square.
     */
    public LetterBoxed(String[] sides) {
        if (sides == null || sides.length != 4) {
            throw new IllegalArgumentException(
                        "parameter must be an array of 4 strings");
        }
        
        this.sides = sides;
        this.letters = new String[12];
        int letterNum = 0;
        for (int i = 0; i < sides.length; i++) {
            if (sides[i] == null || sides[i].length() != 3) {
                throw new IllegalArgumentException(
                        "invalid side string: " + sides[i]);
            }
            
            for (int j = 0; j < 3; j++) {
                this.letters[letterNum] = this.sides[i].substring(j, j+1);
                letterNum++;
            }
        }
        
        this.words = new String[MOST_WORDS];
        for (int i = 0; i < this.words.length; i++) {
            this.words[i] = "";
        }
    }
    
    /*
     * Returns a string that shows the letters of the puzzle in "square" form.
     * For example, if the sides are {"cat", "dog", "fry", "pie"}, it will
     * return a string that when printed looks like this:
     *  c a t
     * d     f
     * o     r
     * g     y
     *  p i e
     */
    public String toString() {
        String s = "";
        
        // top of the square (i.e., sides[0])
        for (int i = 0; i < 3; i++) {
            s += " " + this.sides[0].charAt(i);
        }
        s += "\n";
        
        // left and right sides (sides[1] and sides[2])
        for (int i = 0; i < 3; i++) {
            s += this.sides[1].charAt(i);
            s += "     " + this.sides[2].charAt(i);
            s += "\n";
        }
        
        // bottom of the square (i.e., sides[3])
        for (int i = 0; i < 3; i++) {
            s += " " + this.sides[3].charAt(i);
        }
        s += "\n";
        
        return s;
    }
    
    /*
     * lastLetter - returns a single-character string containing
     * the last letter in the specified word
     * 
     * assumes that word is a String with at least one character
     */
    private static String lastLetter(String word) {
        return word.substring(word.length() - 1);
    }
    
    /*
     * removeLast - returns the string that is formed by removing
     * the last character of the specified word
     * 
     * assumes that word is a String with at least one character
     */
    private static String removeLast(String word) {
        return word.substring(0, word.length() - 1);
    }
    
    /*
     * addLetter - adds the specified letter as the next letter 
     * in the word at position wordNum in the solution
     * and also updates the solnString accordingly
     */
    private void addLetter(String letter, int wordNum) {
        this.words[wordNum] += letter;
    }
    
    /*
     * removeLetter - removes the specified letter from the end of  
     * the word at position wordNum in the solution
     * and also updates the solnString accordingly
     */
    private void removeLetter(int wordNum) {
        this.words[wordNum] = this.removeLast(this.words[wordNum]);
    }
    
    /*
     * alreadyUsed - returns true if the specified word is already 
     * one of the words in the solution, and false otherwise.
     */
    private boolean alreadyUsed(String word) {
        for (String w : this.words) {
            if (w.equals(word)) {
                return true;
            }
        }
        return false;
    }
    
    /*
     * onSameSide - returns true if the single-character strings 
     * letter1 and letter2 come from the same side of the puzzle,
     * and false otherwise
     */
    private boolean onSameSide(String letter1, String letter2) {
        for (String side : this.sides) {
            if (side.contains(letter1) && side.contains(letter2)) {
                return true;
            }
        }
        
        return false;
    }
    
    /*
     * allLettersUsed - returns true if all of the letters in the puzzle
     * are currently being used in the solution to the puzzle,
     * and false otherwise
     */
    private boolean allLettersUsed() {
        for (String letter : this.letters) {
            boolean anyWordHasLetter = false;
            
            for (String w : this.words) {
                if (w.contains(letter)) {
                    anyWordHasLetter = true;
                    break;
                }
            }
            
            if (!anyWordHasLetter) {
                return false;
            }
        }
        
        return true;
    }
    
    /*
     * printSolution - prints the words in the soln array from 
     * position 0 up to and including position wordNum
     */
    private void printSolution(int wordNum) {
        for (int i = 0; i <= wordNum; i++) {
            System.out.println(this.words[i]);
        }
    } 
    
    /* 
     * isValid - returns true if the specified letter (a one-character string)
     * is a valid choice for the letter in position charNum of the word at 
     * position wordNum in the soln, and false otherwise.
     * 
     * Since this is a private helper method, we assume that only 
     * appropriate values will be passed in. 
     * In particular, we assume that letter is one of the letters of the puzzle.
     */
    private boolean isValid(String letter, int wordNum, int charNum) {        
        if (wordNum == 0 && charNum == 0) {
            return true;
        } else if (charNum == 0) {
            if (lastLetter(this.words[wordNum - 1]).equals(letter)) {
                return true;
            }
        } else {
            String last = lastLetter(this.words[wordNum]);
            String word = this.words[wordNum];
            if (!onSameSide(last, letter) && dictionary.hasString(word + letter) && !alreadyUsed(word + letter)) {
                return true;
            }
        }

        return false;
    }
    
    /*
     * solveRB - the key recursive backtracking method.
     * Handles the process of adding one letter to the word at position
     * wordNum as part of a solution with at most maxWords words.
     * Returns true if a solution has been found, and false otherwise.
     * 
     * Since this is a private helper method, we assume that only 
     * appropriate values will be passed in.
     */
    private boolean solveRB(int wordNum, int charNum, int maxWords) {
        if (this.allLettersUsed() && dictionary.hasFullWord(this.words[wordNum])) {
            this.printSolution(wordNum);
            return true;
        } else if (wordNum >= maxWords) {
            return false;
        }

        for (int i = 0; i < this.letters.length; i++) {
            String letter = this.letters[i];
            if (this.isValid(letter, wordNum, charNum)) {
                this.addLetter(letter, wordNum);
                if (this.solveRB(wordNum, charNum + 1, maxWords)) {
                    return true;
                } else if (dictionary.hasFullWord(this.words[wordNum]) && this.words[wordNum].length() >= 3) {
                    if (this.solveRB(wordNum + 1, 0, maxWords)) {
                        return true;
                    }
                }
                this.removeLetter(wordNum);
            }
        }
        return false;
    }
    
    /*
     * solve - the method that the client calls to solve the puzzle.
     * Serves as a wrapper method for solveRB(), which it repeatedly calls 
     * with a gradually increasing limit for the number of words in the solution.
     */  
    public void solve() {
        int maxWords = 1;
        
        while (maxWords <= MOST_WORDS) {
            System.out.println("Looking for a solution of length " 
                               + maxWords + "...");
            if (this.solveRB(0, 0, maxWords)) {
                return;
            }
            maxWords++;
        }
        
        System.out.println("No solution found using up to " 
                           + MOST_WORDS + " words.");
    }
    
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        
        /* get the sides of the square from the user */
        String[] sides = new String[4];
        String[] prompts = {"top side: ", "left side: ", 
            "right side: ", "bottom side: "};
        for (int i = 0; i < 4; i++) {
            System.out.print(prompts[i]);
            sides[i] = console.nextLine();
        }
        
        LetterBoxed puzzle = new LetterBoxed(sides);
        System.out.println("Here is the puzzle:");
        System.out.println(puzzle);
        puzzle.solve();
    }
}