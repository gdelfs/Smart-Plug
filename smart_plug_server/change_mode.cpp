#include<change_mode.h>

bool change_mode(int type) {
  bool ans;

  if(type) system("/home/pi/Documents/smart_plug_server/build/turn_on");
  else system("/home/pi/Documents/smart_plug_server/build/turn_off");

  return true;
}
