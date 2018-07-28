/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#pragma once

//only change is the integer value
class Plus : public SubExpression
{
public:
	Plus(Expression* left, Expression* right) :
		SubExpression(left, right)
	{
	}
	int evaluate()
	{
		return left->evaluate() + right->evaluate();
	}
};