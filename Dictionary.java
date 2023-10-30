/*
 * Dictionary - represents a collection of English words and word prefixes.
 * For each full word that is added, all prefixes of that word are also included.
 * For example, adding the full word "puzzle" also adds the following
 * prefixes: "p", "pu", "puz", "puzz", and "puzzl".
 * 
 */

import java.io.*;
import java.util.*;

public class Dictionary {
    // key = a prefix or full word
    // value = false if the key is a prefix
    //         true if the key is a full word
    private HashMap<String, Boolean> contents;
    
    /*
     * constructor for a Dictionary object that can be used to test if a
     * string is either a word or a prefix of a word in a collection of words
     */
    public Dictionary() {
        this.contents = new HashMap<String, Boolean>();
    }
    
    /*
     * constructor for a Dictionary object that is built using from a text file
     * with the specified file name. In the text file, the words should appear
     * one word per line.
     */
    public Dictionary(String fileName) {
        this();
        try {
            Scanner f = new Scanner(new File(fileName));
            while (f.hasNextLine()) {
                String word = f.nextLine();
                this.add(word);
            }
            
            f.close();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("could not process file of words");
        }
    }
    
    /*
     * add - adds the specified word and all of its prefixes to the Dictionary
     */
    private void add(String word) {
        String prefix = "";
        for (int i = 0; i < word.length(); i++) {
            prefix += word.charAt(i);
            if (! this.contents.containsKey(prefix)) {
                this.contents.put(prefix, false);
            }
        }
                
        // true indicates a full word
        this.contents.put(prefix, true);
    }
    
    /*
     * hasString - returns true if the specified string s is either a word
     * or a prefix of a word in the Dictionary, and false otherwise
     */
    public boolean hasString(String s) {
        if (s == null || s.equals("")) {
            return false;
        } else {
            return this.contents.containsKey(s.toLowerCase());
        }
    }
    
    /*
     * hasFullWord - returns true if the specified string s is a "full word"
     * (i.e., a word that can stand on its own) in the Dictionary, 
     * and false otherwise
     */
    public boolean hasFullWord(String s) {
        if (s == null || s.equals("")) {
            return false;
        } else {
            s = s.toLowerCase();
            return (this.contents.containsKey(s)
                    && this.contents.get(s));
        }
    }
}