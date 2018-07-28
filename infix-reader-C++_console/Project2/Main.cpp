/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#include <iostream>
#include <string>
#include <fstream> 
#include <vector>

using namespace std;

#include "expression.h"
#include "subexpression.h"
#include "symboltable.h"
#include "parse.h"

SymbolTable symbolTable;

ifstream inFile;

void parseAssignments(); //so the main method can call on it

int main()
{

	Expression* expression;
	char paren, comma, end_line;
	
	inFile.open("input_file.txt"); 
	if (inFile.is_open()) //if statement is our only error control
	{
		while (true) //not the ideal loop, but it works
		{
			//most instances of 'cin' are replaced with 'inFile'
			inFile.get(paren);
			expression = SubExpression::parse(); //unchanged from original program
			inFile >> comma;
			parseAssignments(); //unchanged from original
			cout << "Value = " << expression->evaluate() << endl;
			end_line = inFile.peek(); 

			//check to see if there's a line break
			if (end_line == '\n') //additional expression detected
			{
				inFile.get(paren); //passes over the '('
			}
			else 
			{
				break; //program is complete, or there are syntax errors
			}
		}

		inFile.close(); 
		cout << "\nreader closed\n";
	}
	else
		cout << "file is not open." << "\n";
	
	return 0;
}


void parseAssignments()
{
	char assignop, delimiter;
	string variable;
	int value;
	symbolTable.flush(); //additional method which clears the symbol table
	do
	{
		variable = parseName();
		inFile >> ws >> assignop >> value >> delimiter; //the only change was the cin method
		symbolTable.insert(variable, value);
	} while (delimiter == ',');
}