/*
Author: Eric Olsen
Program: C++ expression extractor
Due Date: 15 JUL 18
*/
#pragma once

//This is a lot like a high level abstract class in java
class Expression
{
public:
	virtual int evaluate() = 0;
};