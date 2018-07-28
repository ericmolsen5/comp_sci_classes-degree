/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#pragma once


class SymbolTable
{
public:
	SymbolTable() {}
	void insert(string variable, int value);
	int lookUp(string variable) const;
	void flush(); //added to clear data for multiple line reads
private:
	struct Symbol
	{
		Symbol(string variable, int value)
		{
			this->variable = variable;
			this->value = value;
		}
		string variable;
		int value;
	};
	vector <Symbol> elements;
};