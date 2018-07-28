/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#pragma once

class SubExpression : public Expression
{
public:
	//adding a single Expression here created some weird downstream errors, so I've ommited it
	SubExpression(Expression* left, Expression* right);
	//added to accomodate the ternary expression
	SubExpression(Expression* left, Expression* right, Expression* condition);
	static Expression* parse();
protected:
	Expression* left;
	Expression* right;
	Expression* condition; //added for the ternary expression
};