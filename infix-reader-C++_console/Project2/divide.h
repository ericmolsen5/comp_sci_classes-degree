/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#pragma once

class Divide : public SubExpression
{
public:
	Divide(Expression* left, Expression* right) :
		SubExpression(left, right)
	{
	}
	int evaluate()
	{
		//pretty self explanatory
		return left->evaluate() / right->evaluate();
	}
};
