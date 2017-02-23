#!/bin/bash
pathToEspeakDir=/home/student/workspace-c/espeak-ng
pathToJedit=/home/student/workspace/eSpeak-Jedit
pdir=$(pwd)
cdir=$(dirname "$0")
if [ $cdir != "." ]; then
  cd $cdir # change to current directory
fi
cd ../src
echo "Compiling ESpeakService.java.."
javac eSpeakServices/ESpeakService.java
echo "(Re)Generate eSpeakServices_ESpeakService.h in jni/include.."
javah -jni -d ../jni/include eSpeakServices.ESpeakService
echo "(Re)Compiling eSpeakService.c .."
gcc -Wall -fPIC -O -g -I/usr/lib/jvm/java-8-openjdk-amd64/include -I/usr/lib/jvm/java-8-openjdk-amd64/include/linux -I/usr/include -I/usr/include/x86_64-linux-gnu -I/usr/lib/gcc/x86_64-linux-gnu/6/include -I/usr/lib/gcc/x86_64-linux-gnu/6/include-fixed -I/usr/local/include -I${pathToEspeakDir}  ${pathToJedit}/jni/src/eSpeakService.c -c -o ${pathToJedit}/jni/src/eSpeakService.o
echo "(Re)Compiling libespeakservice.so shared library .."
gcc -shared ${pathToJedit}/jni/src/eSpeakService.o -L${pathToJedit}/jni/lib -lespeak-ng-fork -o ${pathToJedit}/jni/lib/libespeakservice.so 
echo "hopefully that is done"
cd $pdir  # go bach to previous directory