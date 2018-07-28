/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

//no chnage

#pragma once

class Variable : public Operand
{
public:
	Variable(string name)
	{
		this->name = name;
	}
	int Variable::evaluate();
private:
	string name;
};