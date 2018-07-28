/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#pragma once

class  Minus : public SubExpression
{
public:
	Minus(Expression* left, Expression* right) :
		SubExpression(left, right)
	{
	}
		int evaluate()
		{
			return left->evaluate() - right->evaluate();
		}
};
