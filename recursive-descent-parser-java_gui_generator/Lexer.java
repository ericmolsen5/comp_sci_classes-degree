//package com.olsen;

/*
 * File Name: Lexer.java
 * Author: Eric Olsen
 * UMUC, CMSC330, Project 1
 * Due  17 JUN 2018
 */

 //This Lexical Analyzer was initially build by comparison to the week 2 reading. Unused and unrelated parts were 
 //removed and then more specific parts for the GUI were added
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Lexer {

    private int charCnt = 0;
    private String inputString;
    private Token currentToken, lastToken;
    private String currentLexeme, lastLexeme;
    private char character;
    private String line = "";       //for string reading in nextChar()
    private BufferedReader file;

	//main method catches the IOException
    public Lexer(String filePath) throws IOException {

        file = new BufferedReader(new FileReader(filePath));  //create the file reader object
        character = nextChar(); //actual file reading occurs in this private method
        lastToken = Token.NONE; //for the initial run
        currentLexeme = "";
    }

    //method to close the file reader after program completion
    public void closeReader(){
        try{
            file.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Encountered an error while " +
                            "closing reader.", "Error with file reader", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        System.out.println("File Reader closed.");
    }

    //this returns the current lexeme so it can be added to the GUI interface
    public String getCurrentLexeme(){
        return currentLexeme;
    }

    //This method does the bulk of the work for this class and called by the Syntax Analyzer
    public Token getNextToken() {

        lastLexeme = currentLexeme;

        do {
            currentLexeme = ""; //reset the lexeme

            //skips over white space 
            while (character != 0 && Character.isWhitespace(character)){ //if there's white space
                character = nextChar();
            }

            //lookup of non-terminals defined in our testToken method below. String data used if not terminal located
            if (Character.isLetter(character) || character == '_'){  //if a letter is identified
                while (Character.isLetter(character) || Character.isDigit(character) || character == '_'){
                    currentLexeme += character;
                    character = nextChar();
                }
                //call to token generator below
                if ((currentToken = testToken(currentLexeme)) != null){
                    return currentToken;
                } else {
					//I'm leaving this in place, but don't think the program ever made it here
                    currentToken = Token.STRING_DATA;
                }
            }

            //if an integer is identified
            else if (Character.isDigit(character)) {
                while (Character.isDigit(character)) {
                    currentLexeme += character;
                    character = nextChar();
                }
                currentToken = Token.NUMBER_DATA;
                return currentToken;
            }

            //check for double-quote reference to integer or string data
            else if (character == '"') {
                currentLexeme += character;
                character = nextChar();
                currentToken = Token.DOUBLE_QUOTE;
                return currentToken;
            }

            //check for parenthetical parameter references
            else if (character == '(') {
                currentLexeme += character;
                character = nextChar();
                currentToken = Token.OPENING_PARENTHESIS;
                return currentToken;
            }

			//check for end of parenthetical references
            else if (character == ')'){
                currentLexeme += character;
                character = nextChar();
                currentToken = Token.CLOSING_PARENTHESIS;
                return currentToken;
            }

			//comma separator
            else if (character == ',') {
                currentLexeme += character;
                character = nextChar();
                currentToken = Token.COMMA_SEPARATOR;
                return currentToken;
            }

			//colon separator
            else if (character == ':') {
                currentLexeme += character;
                character = nextChar();
                currentToken = Token.COLON;
                return currentToken;
            }

			//semi-colon separator
            else if (character == ';') {
                currentLexeme += character;
                character = nextChar();
                currentToken = Token.SEMI_COLON;
                return currentToken;
            }

			//Period ending token for program
			//In future program runs, this will probably throw an error if put in a string
            else if (character == '.') {
                currentLexeme += character;
                character = nextChar();
                currentToken = Token.PERIOD;
                return currentToken;
            }

            else if (character == 0) { //if there's nothing left to read
                currentToken = Token.END_OF_FILE;
                return Token.END_OF_FILE;
            }

            else {
                currentToken = Token.NOT_FOUND;
            }
        } while (currentToken != Token.END_OF_FILE); // this should abort the lookup at the end of file
        //I'll leave this for any miss of a lookup condition
        System.out.println("*********ERROR!!!**************");
        return currentToken; //I've missed a condition if this is called
    }

    //character reader, much of this is borrowed from the class reading
    private char nextChar() {
        try {
            if (line == null) {  // <-- no line to read, return 0
                return 0;
            }
            if (charCnt == line.length()) {  // <--we're at the end of the line
                line = file.readLine();
                charCnt = 0;
                return '\n';
            }
            return line.charAt(charCnt++);  // <-- return character at incremented counter
        } catch (IOException exception) {
            return 0;
        }
    }


    //There is a lot of code below, but I still prefer to keep this in the format of a switch
	//statement due to the detailed lookups that the Lexical Analyzer needs to perform
    private Token testToken(String lexeme) {
        switch(lexeme.charAt(0)){

            case 'B':
                if (lexeme.equals("Button"))
                    return Token.BUTTON;
                else
                    return Token.STRING_DATA;
            case 'E':
                if (lexeme.equals("End"))
                    return Token.END;
                else
                    return Token.STRING_DATA;
            case 'F':
                if (lexeme.equals("Flow"))
                    return Token.FLOW;
                else
                    return Token.STRING_DATA;
            case 'G':
                if (lexeme.equals("Grid"))
                    return Token.GRID;
                else if (lexeme.equals("Group"))
                    return Token.GROUP;
                else
                    return Token.STRING_DATA;
            case 'L':
                if (lexeme.equals("Layout"))
                    return Token.LAYOUT;
                else if (lexeme.equals("Label"))
                    return Token.LABEL;
                else
                    return Token.STRING_DATA;
            case 'P':
                if (lexeme.equals("Panel"))
                    return Token.PANEL;
                else
                    return Token.STRING_DATA;
            case 'R':
                if (lexeme.equals("Radio"))
                    return Token.RADIO;
                else
                    return Token.STRING_DATA;
            case 'T':
                if (lexeme.equals("Textfield"))
                    return Token.TEXTFIELD;
                else
                    return Token.STRING_DATA;

            case 'W':
                if (lexeme.equals("Window"))
                    return Token.WINDOW;
                else
                    return Token.STRING_DATA;
        }
        //if all cases are missed, default to SRING_DATA
        return Token.STRING_DATA;
    }


}