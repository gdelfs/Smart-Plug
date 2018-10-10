#pragma once

#include<iostream>
#include<cstdlib>
#include<string>
#include<fstream>
#include<sstream>

using namespace std;

const int args_size = 6;

bool isEqual(string args1[args_size], string args2[args_size]);
bool getArgs(string args[args_size], istream &ist);
void putArgs(string args[args_size], ostream &ost);
bool add(string args[args_size], istream &ist, ostream &ost);
bool erase(string args[args_size], istream &ist, ostream &ost);
bool go(string args[args_size], int type);
