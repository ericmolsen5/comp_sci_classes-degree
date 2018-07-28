/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#pragma once

//I probably don't need to use two expressions, but I encountered some strange
//issues when building the extra overloaded constructors.
class Negate : public SubExpression
{
public:
	Negate(Expression* left, Expression* right) :
		SubExpression(left, right)
	{
	}
	int evaluate()
	{
		if (left->evaluate() != 0) //positive
		{
			//try replacing this with (left * 0)
			return (left->evaluate()*0); //returns zero
		}
		else //left equals 0
		{
			//no multiplication by zero needed since we already validated this value
			return left->evaluate() + 1; //returns 1
		}
	}
};