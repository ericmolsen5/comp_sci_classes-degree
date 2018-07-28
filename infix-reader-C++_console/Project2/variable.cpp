/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

//no change

#include <strstream>
#include <vector>

using namespace std;

#include "expression.h"
#include "operand.h"
#include "variable.h"
#include "symboltable.h"

extern SymbolTable symbolTable;

int Variable::evaluate()
{
	return symbolTable.lookUp(name);
}