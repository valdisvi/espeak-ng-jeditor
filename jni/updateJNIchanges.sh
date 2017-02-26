#!/bin/bash
pdir=$(pwd)
cdir=$(dirname "$0")
cd $cdir # change to script directory
cdir=$(pwd)
echo $cdir
espeakDir=${cdir}/../../espeak-ng
if [ ! -d ${espeakDir} ]; then
 echo "eSpeak NG directory was not found!"
 exit 1
fi
cd ../src
echo "Compiling ESpeakService.java..."
javac eSpeakServices/ESpeakService.java
echo "Generating eSpeakServices_ESpeakService.h in jni/include..."
javah -jni -d ../jni/include eSpeakServices.ESpeakService
echo "Compiling eSpeakService.c..."
gcc -Wall -fPIC -O -g -I/usr/lib/jvm/java-8-openjdk-amd64/include -I/usr/lib/jvm/java-8-openjdk-amd64/include/linux -I/usr/include -I/usr/include/x86_64-linux-gnu -I/usr/lib/gcc/x86_64-linux-gnu/6/include -I/usr/lib/gcc/x86_64-linux-gnu/6/include-fixed -I/usr/local/include -I${espeakDir}  ${cdir}/src/eSpeakService.c -c -o ${cdir}/src/eSpeakService.o
echo "Compiling libespeakservice.so shared library..."
#gcc -shared ${cdir}/src/eSpeakService.o -L${cdir}/lib -lespeak-ng-fork  $(find ${espeakDir} -name "*.o"|tr "\n" " ") -o ${cdir}/lib/libespeakservice.so
ld --allow-multiple-definition -G ${cdir}/src/eSpeakService.o $(find /home/valdis/code/espeak-ng -name "*.o"|tr "\n" " ") -o ${cdir}/lib/libespeakservice.so
echo "OK"
cd $pdir  # go bach to previous directory
