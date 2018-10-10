#include<schedule.h>

bool isEqual(string args1[args_size], string args2[args_size]) {
  for(int i = 0; i < args_size; i++) if(args1[i] != args2[i]) return false;
  return true;
}

bool getArgs(string args[args_size], istream &ist) {
  for(int i = 0; i < args_size; i++) if(!(ist>>args[i])) return false;
  return true;
}

void putArgs(string args[args_size], ostream &ost) {
  for(int i = 0; i < args_size; i++) ost << args[i] << ((i == args_size-1)?'\n': ' '); }

bool add(string args[args_size], istream &ist, ostream &ost) {
  string args2[args_size];
  bool ok = true;

  while(getArgs(args2, ist)) {
    if(isEqual(args2, args)) ok = false;
    putArgs(args2, ost);
  }

  if(ok) {
    putArgs(args, ost);
    return true;
    //return "event successfully added!!";
  }
  return false;
  //return "event could not be added!!\n    verify if it already exists.";
}

bool erase(string args[args_size], istream &ist, ostream &ost) {
  string args2[args_size];
  bool ok = false;

  for(int i = 0; i < args_size; i++) cout << args[i] << endl;

  while(getArgs(args2, ist)) {
    if(!isEqual(args2, args))
      putArgs(args2, ost);
    else 
      ok = true;
  }

  if(ok) {
    return true;
    //return "event successfully erased!!";
  }
  return false;
  //return "event could not be erased!!\n    probably it doesn't exists.";
}

bool go(string args[args_size], int type) {
  bool ans;
  system("crontab -l > /home/pi/Documents/smart_plug_server/crontab.txt.tmp");
  ifstream ifs;
  ofstream ofs;

  ifs.open("/home/pi/Documents/smart_plug_server/crontab.txt.tmp");
  ofs.open("/home/pi/Documents/smart_plug_server/crontab.txt");

  //system("ls /home/pi/Documents/smart_plug");

  if(type == 0) ans = erase(args, ifs, ofs);
  else ans = add(args, ifs, ofs);

  ifs.close();
  ofs.close();

  system("crontab /home/pi/Documents/smart_plug_server/crontab.txt");
  system("rm /home/pi/Documents/smart_plug_server/crontab.txt");
  system("rm /home/pi/Documents/smart_plug_server/crontab.txt.tmp");

  return ans;
}
