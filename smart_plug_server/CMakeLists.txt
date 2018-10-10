# Minimum CMake version required to generate
# our build system
cmake_minimum_required(VERSION 3.0)
include_directories(include)

# Name of our Project
project(inteligent_plugs)

add_executable(turn_on turn_on.cpp)
target_link_libraries(turn_on wiringPi)

add_executable(turn_off turn_off.cpp)
target_link_libraries(turn_off wiringPi)

add_executable(server server.cpp schedule.cpp process_msg.cpp change_mode.cpp)

set (CMAKE_CXX_STANDARD 11)
