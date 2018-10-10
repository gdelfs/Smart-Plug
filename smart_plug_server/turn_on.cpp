#include <iostream>
#include <wiringPi.h>
#include <unistd.h>

using namespace std;

#include <unistd.h> // Required for Sleep();

const int led = 8;
const int sleepTime = 2e5;

int main(int argc, char** argv)
{
  wiringPiSetup();

  pinMode(led, OUTPUT);

  digitalWrite(led, LOW);

  return 0;
}
