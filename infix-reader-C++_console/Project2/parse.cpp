/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/

#include <cctype> 
#include <iostream> 
#include <string> 
#include <fstream> //fstream for character parsing

using namespace std;

#include "parse.h"

extern ifstream inFile; //reference to Main.cpp

//only change was the program requirement for fileReading
string parseName()
{
	char alnum;
	string name = "";

	inFile >> ws;
	while (isalnum(inFile.peek()))
	{
		inFile >> alnum;
		name += alnum;
	}

	return name;
}