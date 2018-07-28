/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

//Per the text, Literal is a subclass of Operand. In the intermediary data tree format,
//all operand will be either variables or literals

#pragma once

//my only change was to make this evaluate integers instead of doubles
class Literal : public Operand
{
public:
	Literal(int value)
	{
		this->value = value;
	}
	int evaluate()
	{
		return value;
	}
private:
	int value;
};