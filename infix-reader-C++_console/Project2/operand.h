/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#pragma once

//no real change
class Operand : public Expression
{
public:
	static Expression* parse();
};