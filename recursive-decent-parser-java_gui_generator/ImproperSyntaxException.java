//package com.olsen;
/*
 * File Name: ImproperSyntaxException.java
 * Author: Eric Olsen
 * UMUC, CMSC330, Project 1
 * Due  17 JUN 2018
 */

 //The intent of this class is to throw an exception that identifies what caused the syntax error
 //It has three constructors because they Syntax Checker is sometimes looking for more than one
 //possible token for input.
 
import javax.swing.JOptionPane;

public class ImproperSyntaxException extends Exception{

    public ImproperSyntaxException(Token tokenParsed, String section, String lexeme){
        String errorMessage = "The program received an invalid token while parsing." +
                "\nEncountered token: " + tokenParsed + "." +
                "\nError occurred in GUI section: " + section +
                "\nLexeme which triggered error was: ' " + lexeme + " '";
        JOptionPane.showMessageDialog(null, errorMessage, "Improper Syntax",
                JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    public ImproperSyntaxException(Token tokenParsed, Token tokenNeeded, String section, String lexeme){
        String errorMessage = "The program received an invalid token while parsing." +
                "\nEncountered token: " + tokenParsed + " while looking for token: " +
                tokenNeeded +
                "\nError occurred in GUI section: " + section +
                "\nLexeme which triggered error was: ' " + lexeme + " '";
        JOptionPane.showMessageDialog(null, errorMessage, "Improper Syntax",
                JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    public ImproperSyntaxException(Token tokenParsed, Token tokenNeeded1, Token tokenNeeded2,
                                   String section, String lexeme){
        String errorMessage = "The program received an invalid token while parsing." +
                "\nEncountered token: " + tokenParsed + " while looking for token: " +
                tokenNeeded1 + " or " + tokenNeeded2 +
                "\nError occurred in GUI section: " + section +
                "\nLexeme which triggered error was: ' " + lexeme + " '";
        JOptionPane.showMessageDialog(null, errorMessage, "Improper Syntax",
                JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

}
