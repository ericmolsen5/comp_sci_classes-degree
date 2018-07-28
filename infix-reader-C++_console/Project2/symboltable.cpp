/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#include <iostream>
#include <string> 
#include <vector> 

using namespace std;

#include "symboltable.h"


void SymbolTable::insert(string variable, int value)
{
	const Symbol& symbol = Symbol(variable, value);
	elements.push_back(symbol);
}

int SymbolTable::lookUp(string variable) const
{
	for (int i = 0; i < elements.size(); i++)
		if (elements[i].variable == variable)
			return elements[i].value;
	return -1;
}

//added this line here to clear the symbol table for its next round of entries
//simply calling the '.clear()' method was failing in Main.cpp
void SymbolTable::flush()
{
	elements.clear();
}
