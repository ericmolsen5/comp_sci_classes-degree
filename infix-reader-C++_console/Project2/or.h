/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#pragma once

class Or : public SubExpression
{
public:
	Or(Expression* left, Expression* right) :
		SubExpression(left, right)
	{
	}
	int evaluate()
	{
		//I'll evaluate the most restrictive condition first, in this case false
		if ((left->evaluate() == 0) && (right->evaluate() == 0)) //condition is false
		{
			return left->evaluate();  //return left, the value is already 0 if this is true
		}
		else //one condition is true, we'll arbitrarily pick left and turn it into a 1
		{
			return (left->evaluate() * 0) + 1;  //return 1
		}
	}
};