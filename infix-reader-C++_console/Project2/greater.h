/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#pragma once

class Greater : public SubExpression
{
public:
	Greater(Expression* left, Expression* right) :
		SubExpression(left, right)
	{
	}
	int evaluate()
	{
		//pretty self explanatory. Return value is a 1 for true and 0 for false
		if (left->evaluate() > right->evaluate()) //condition is true
		{
			return (left->evaluate()*0) + 1;  //return 1
		}
		else //left is equal or less than right
		{
			return (left->evaluate()*0);  //return 0
		}
	}
};