//package com.olsen;
/*
 * File Name: Main.java
 * Author: Eric Olsen
 * UMUC, CMSC330, Project 1
 * Due  17 JUN 2018
 */

import java.io.IOException;

public class Main {


    public static void main(String[] args) {

        String fileRelPath = "input_file.txt";
		
		Lexer lexer = null;  //Used a null object in order to reference it from the try-statement
        try {
            lexer = new Lexer(fileRelPath);
        } catch (IOException e) {
            e.printStackTrace();
        }		
		
        SyntaxChecker syntaxChecker = new SyntaxChecker(lexer); //Syntax checker only talks to lexer

        //Note, please only buildGUI or printTokensAndLexemes() 
		//Running both generates a null-pointer exception

//        syntaxChecker.printTokensAndLexemes(); //used to generate Token Stream and for debugging
        syntaxChecker.buildGUI();
        lexer.closeReader();

    }










}
