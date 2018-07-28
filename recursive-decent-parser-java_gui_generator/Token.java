//package com.olsen;

/*
 * File Name: Token.java
 * Author: Eric Olsen
 * UMUC, CMSC330, Project 1
 * Due  17 JUN 2018
 */

public enum Token {
    NONE,
    END,
    END_OF_FILE,
    NOT_FOUND,
    DOUBLE_QUOTE,
    OPENING_PARENTHESIS,
    CLOSING_PARENTHESIS,
    COMMA_SEPARATOR,
    COLON,
    SEMI_COLON,
    PERIOD,
    WINDOW,
    GROUP,
    TEXTFIELD,
    PANEL,
    RADIO,
    LAYOUT,  //non-terminal
    GRID,
    FLOW,
    BUTTON,
    LABEL,
    STRING_DATA,  //note: defining an enum of STRING in not allowed by the compiler in java
    NUMBER_DATA,
}
