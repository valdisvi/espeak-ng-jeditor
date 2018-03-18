#!/bin/bash
# set -x
pdir=$(pwd)
cdir=$(dirname "$0")
cd $cdir # change to script directory
cdir=$(pwd)
echo $cdir
pathToJavaLib=/usr/lib/jvm/java-8-openjdk-amd64
espeakDir=${cdir}/../espeak-ng
if [ ! -d ${espeakDir} ]; then
 echo "eSpeak NG directory was not found!"
 exit 1
fi
echo "Compiling ESpeakService.java..."
javac -cp target/classes src/main/java/org/espeakng/jeditor/jni/ESpeakService.java
echo "Generating ESpeakService.h..."
javah -cp target/classes -jni -d target/classes/org/espeakng/jeditor/jni org.espeakng.jeditor.jni.ESpeakService
echo "Compiling eSpeakService.c..."
gcc -Wall -fPIC -O -std=c99 -g -I${pathToJavaLib}/include -I${pathToJavaLib}/include/linux -I/usr/include -I/usr/include/x86_64-linux-gnu -I/usr/lib/gcc/x86_64-linux-gnu/6/include -I/usr/lib/gcc/x86_64-linux-gnu/6/include-fixed -I/usr/local/include -I${espeakDir} ${cdir}/src/main/java/org/espeakng/jeditor/jni/eSpeakService.c -c -o ${cdir}/lib/eSpeakService.o
echo "Compiling libespeakservice.so shared library..."
#gcc -shared ${cdir}/src/eSpeakService.o -L${cdir}/lib -lespeak-ng-fork  $(find ${espeakDir} -name "*.o"|tr "\n" " ") -o ${cdir}/lib/libespeakservice.so
#ld --allow-multiple-definition -G ${cdir}/lib/eSpeakService.o $(find ${espeakDir} -name "*.o"|tr "\n" " ") -o ${cdir}/lib/libespeakservice.so
# i had problems when using ld aproach, all that is needed is in espeak-ng library (if it is rebuilded with necessary changes)
gcc -shared ${cdir}/lib/eSpeakService.o -lespeak-ng -o ${cdir}/lib/libespeakservice.so 
echo "OK"
cd $pdir  # go bach to previous directory
