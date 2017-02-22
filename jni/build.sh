#!/bin/bash
# Preconditions:
cd pcaudiolib/
make
sudo make install
sudo reboot
espeak-ng "sddd"
dpkg-query -W
dpkg-query -W|cut -d " " -f1
dpkg-query -W|awk '{print $1}'
dpkg-query -W|awk '{print $1}' > packages.txt
meld packages.txt packages1.txt 
sudo apt install meld
meld packages.txt packages1.txt 
espeak-ng "sddd"
cd workspace-c/
./espeak-ng-setup.sh 
sudo apt-get install alsa-tools alsa-utils
espeak-ng "sddd"
cd espeak-ng
git remote -v
ESPEAK_DATA_PATH=`pwd` LD_LIBRARY_PATH=src:${LD_LIBRARY_PATH} src/espeak-ng "dds"
xinput -disable 12
cd workspace-c
sudo apt-get install make autoconf automake libtool pkg-config
sudo apt-get install libsound2-dev
sudo apt-get install libasound2-dev
sudo apt-get install libpulse-dev
cd pul*
diur
dir
cd pc*
./autogen.sh
./configure --prefix=/usr
make
sudo make install
cd ..
espeak-ng aa
espeak-ng aaaaa
sudo apt-get purge libpulse-dev
espeak-ng aaaaa
cd pcaudiolib/
make
./autogen.sh 
./configure --prefix=/usr
make
sudo make install
espeak-ng aaaaa
sudo apt-get install --reinstall libpulse-dev
./autogen.sh 
./configure --prefix=/usr
make
sudo make install
espeak-ng aaaaa
cd ..
dir
./*setup.sh
espeak-ng aaaaa
./*setup.sh
cd ..
cd workspace
git clone http://odo.lv/git/espeak-jni/

