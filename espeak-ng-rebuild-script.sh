#!/bin/bash
#cd /path/to/your/espeak-ng
read -r -p "Reconfigure entire project [y/N]? " response
response=${response,,} # tolower
if [[ $response =~ ^(y|yes) ]]; then
  ./autogen.sh
  CFLAGS='-O0 -DDEBUG -fPIC' ./configure --prefix=/usr --with-extdict-ru --with-extdict-zh --with-extdict-zhy --with-gradle=/usr/bin/gradle --with-async=no
  make -B
fi
cd dictsource/
../src/espeak-ng --compile-debug=en
../src/espeak-ng --compile-debug=lv
../src/espeak-ng --compile-debug=ky
cd ..
make
sudo make LIBDIR=/usr/lib/x86_64-linux-gnu install