/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#pragma once
class Ternary : public SubExpression
{
public:
	Ternary(Expression* left, Expression* right, Expression* condition) :
		SubExpression(left, right, condition)
	{
	}
	int evaluate()
	{
		int cond_check;
		cond_check = condition->evaluate(); //0 is false, everything else is positive

		if (cond_check != 0) //condition is true
		{
			return left->evaluate();
		}
		else //condition is false
		{
			return right->evaluate();
		}
	}
};