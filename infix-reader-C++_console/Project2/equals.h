/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#pragma once

class Equals : public SubExpression
{
public:
	Equals(Expression* left, Expression* right) :
		SubExpression(left, right)
	{
	}
	int evaluate()
	{
		if (left->evaluate() == right->evaluate()) //condition is true
		{
			return (left->evaluate() * 0) + 1;  //return 1
		}
		else //left is equal or less than right
		{
			return (left->evaluate() * 0);  //return 0
		}
	}
};