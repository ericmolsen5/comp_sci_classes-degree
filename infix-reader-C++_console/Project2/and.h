/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#pragma once

class And : public SubExpression
{
public:
	And(Expression* left, Expression* right) :
		SubExpression(left, right)
	{
	}
	int evaluate()
	{
		//I'll evaluate the most restrictive condition first, in this case true
		if ((left->evaluate() != 0) && (right->evaluate() != 0)) //condition is true
		{
			return (left->evaluate() * 0) + 1;  //return 1
		}
		else //one condition equals 0 and is false, AND statement must also be false
		{
			return (left->evaluate() * 0);  //return 0
		}
	}
};