# Letter Boxed Puzzle Solver

This is a Java program for solving Letter Boxed puzzles. Letter Boxed is a New York Times word puzzle game where you have a square with four sides, and each side contains three letters. The goal is to find words that can be formed by connecting adjacent letters, both horizontally and vertically, to complete a circuit around the square. This program helps you find valid words and solutions for Letter Boxed puzzles.

## Files Included

1. **word_list.txt**: This file contains a list of English words used for checking the validity of words during the puzzle solving process.

2. **Dictionary.java**: This Java class represents a dictionary of English words and their prefixes. It allows you to check whether a given string is a valid word or a valid word prefix. This class is used to validate words while solving the Letter Boxed puzzle.

3. **LetterBoxed.java**: The heart of the project, this Java class represents the state of a Letter Boxed puzzle and solves it using recursive backtracking. It includes methods to create and manipulate the puzzle, validate words, and find solutions for the puzzle.

## Dictionary.java

The `Dictionary` class is responsible for storing words and word prefixes and checking if a given string is a valid word or prefix. It contains the following methods:

- `Dictionary()`: Constructor to create an empty dictionary.
- `Dictionary(String fileName)`: Constructor to create a dictionary from a text file with a list of words.
- `add(String word)`: Add a word and its prefixes to the dictionary.
- `hasString(String s)`: Check if a string is either a word or a prefix of a word in the dictionary.
- `hasFullWord(String s)`: Check if a string is a full word in the dictionary.

## LetterBoxed.java

The `LetterBoxed` class is responsible for solving Letter Boxed puzzles. It includes methods for creating and manipulating the puzzle, as well as finding solutions. Here are the key methods:

- `LetterBoxed(String[] sides)`: Constructor to create a Letter Boxed puzzle with the specified sides.
- `toString()`: Generate a string representation of the puzzle.
- `solve()`: The main method to solve the puzzle. It uses recursive backtracking to find solutions.
- Several helper methods for checking word validity, letter positioning, and more.

## How to Use

To use this project, you can follow these steps:

1. Compile the project if necessary: `javac Dictionary.java LetterBoxed.java`.

2. Run the Letter Boxed solver: `java LetterBoxed`.

3. Follow the prompts to input the sides of your Letter Boxed puzzle.

4. The program will attempt to find a solution for the given puzzle.

5. If a solution is found, it will be displayed. If not, the program will indicate that no solution was found.

## Note

Make sure to provide a valid list of English words in the `word_list.txt` file, which the program uses for word validation. You can replace this file with your own list if needed.

Feel free to use, modify, and extend this project to meet your specific requirements or integrate it into your own applications. Enjoy solving Letter Boxed puzzles!