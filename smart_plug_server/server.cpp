#include<cstdio>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/ioctl.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>

#include <schedule.h>
#include <process_msg.h>

const int MSG_LEN = 1e4; char msg[MSG_LEN];

void run_server() {
  int sockfd;
  struct sockaddr_in addr;
  unsigned int port = 1856;

  sockfd = socket(AF_INET, SOCK_STREAM, 0);

  if(sockfd == -1) {
    perror("Could not create socket");
    exit(0);
  }
  
  addr.sin_family = AF_INET;
  addr.sin_port = htons(port);
  addr.sin_addr.s_addr = INADDR_ANY;

  if(bind(sockfd, (struct sockaddr*)&addr, sizeof(addr)) == -1) {
    perror("Error in bind function");
    exit(0);
  }
  if(listen(sockfd, 1) == -1) {
    perror("Error in listen function");
    exit(0);
  }

  while(1) {
    int sock_client = accept(sockfd, 0, 0);
    if(sock_client == -1) {
      perror("Error in accept function");
      continue;
    }

    // Get client info
    socklen_t addr_size = sizeof(struct sockaddr_in);
    int res = getpeername(sock_client, (struct sockaddr*)&addr, &addr_size);

    printf("\033[94mClient connected\033[0m: %d (%s)\n", sock_client, inet_ntoa(addr.sin_addr));

    int mlen = recv(sock_client, msg, MSG_LEN,0);

    printf("%d\n", mlen);

    if(mlen == -1 || mlen == 0) {
      printf("Message not received\n");
      close(sock_client);
      continue;
    }
    else {
      printf("%s\n", msg);
      process(istringstream(string(msg)));
    }

    printf("teste\n");

    close(sock_client);


    /*
    else {
      int type = msg[mlen-1]-'0';
      msg[mlen-1] = '\0';
      go(type, msg);
    }
    */
  }
}

int main() {
  run_server();
}
