/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#pragma once

//this was just a simple extension of the program's existing approach
//I did switch the output of evaluate to int
class Times : public SubExpression
{
public:
	Times(Expression* left, Expression* right) :
		SubExpression(left, right)
	{
	}
		int evaluate()
		{
			return left->evaluate() * right->evaluate();
		}
};