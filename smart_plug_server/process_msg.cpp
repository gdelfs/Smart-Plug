#include<process_msg.h>
#include<schedule.h>
#include<change_mode.h>

string process(istringstream command) {
  string args[args_size];
  int type;

  command >> type;

  if(type == 0) {
    for(int i = 0; i < args_size; i++) command >> args[i];
    command >> type;

    if(args[5] == string("1")) args[5] = "/home/pi/Documents/smart_plug_server/build/turn_on";
    if(args[5] == string("0")) args[5] = "/home/pi/Documents/smart_plug_server/build/turn_off";

    printf("=]\n");
    if(go(args, type)) return "ok";
    return "fail";
  }
  else {
    command >> type;
    if(change_mode(type)) return "ok";
    return "fail";
  }
}
