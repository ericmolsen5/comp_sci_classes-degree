/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

//This is where most of my program changes occured

#include <iostream>
#include <fstream> 

using namespace std;

#include "expression.h"
#include "subexpression.h"
#include "operand.h"
#include "plus.h"
#include "minus.h"
//headers below were what I added to the program
#include "times.h"
#include "divide.h"
#include "negate.h"
#include "greater.h"
#include "less.h"
#include "equals.h"
#include "and.h"
#include "or.h"
#include "ternary.h"

extern ifstream inFile; //reference back to Main.cpp

//unchanged
SubExpression::SubExpression(Expression* left, Expression* right)
{	
	this->left = left;
	this->right = right;
}

//I stumbled into this extra constructor to accomodate the 3 parameter inputs for
//the conditional expressions
SubExpression::SubExpression(Expression* left, Expression* right, Expression* condition)
{
	this->left = left;
	this->right = right;
	this->condition = condition;

}


Expression* SubExpression::parse()
{
	Expression* left;
	Expression* right;
	Expression* condition; //added this for ternary expressions

	char operation, paren, ternary;

	//in all cases, we will use a left operand - this is consequently unchanged
	left = Operand::parse();
	
	inFile >> operation; //this operation symbol is our queue for the selection statemetns below

	if (operation == '!') //first we check for negation since it's a single term expression
	{
		right = left; //this is not ideal, but duplicating the object assisted with passing it
		inFile >> paren; //with correct syntax there should always be a ')'

		return new Negate(left, right); //return our leaf node data structure
	} 
	
	// conditional expression is defined by: ( <true_result> : <false_result> ? <cond> )
	else if (operation == ':')		  //colon is our queue to run the ternary operation
	{
		right = Operand::parse();        //right is our false condition
		inFile >> ternary;			     // ternary = '?'
		condition = Operand::parse();	 //this will extract the operand like left and right
		inFile >> paren;				//infix closing parenthesis

		return new Ternary(left, right, condition); //returns the leaf node

	}
	//for all other expressions that only take two operands
	else
	{
		right = Operand::parse();
		inFile >> paren;
		switch (operation)
		{
		case '+':
			return new Plus(left, right);
		case '-':
			return new Minus(left, right);
		case '*':
			return new Times(left, right);
		case '/':
			return new Divide(left, right);
		case '>':								//there is no >= in this language
			return new Greater(left, right);
		case '<':								//there is no <= in this language
			return new Less(left, right);
		case '=':								//same as '==' in C-family languages
			return new Equals(left, right);
		case '&':								//same as '&&' in C-family, not a bitwise operator
			return new And(left, right);
		case '|':								//same as '||' in C-family
			return new Or(left, right);
		}
		return 0;
	}
	
}
