/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#include <cctype>
#include <iostream>
#include <list>
#include <string>
#include <fstream> //added for the inFile stream inputs

using namespace std;

#include "expression.h"
#include "subexpression.h"
#include "operand.h"
#include "variable.h"
#include "literal.h"
#include "parse.h"

extern ifstream inFile; //extern needed to reach back to Main.cpp

//the only real changes here was swapping 'cin' with 'inFile' methods
Expression* Operand::parse()
{
	char paren;
	int value;
		
	inFile >> ws;

	if (isdigit(inFile.peek()))
	{
		inFile >> value;
		Expression* literal = new Literal(value);
		return literal;
	}
	if (inFile.peek() == '(')
	{
		inFile >> paren;
		return SubExpression::parse();
	}
	else 
	{
		return new Variable(parseName());
	}
		
	
	return 0;
}