#eSpeak-ng-jeditor
##This is an attempt to re-do the espeak-edit project on Java.

##At the moment these things are implemented:

-###Interface: 
Design repeats the old espeak-edit project, no significant changes were done!

###-Opening and storing phonemes:
~~It is possible to open and work with every singly byte of information from original phoneme files,
all of those are store into the similarly called or completely same variables even though NOT all
of them used. The only type of phonemes which is not recognised is SPECSCP2 which is stated to be very
old and might not even exist among phonemes in folder 'phsource'.~~

###Update
Now phoneme opening is done trough espeak-ng library (which is in C ) 
Saving is not implemented. There is function *phonemeSave* which is not called in code and is outdated, but it can be refference to how it would be done.

###-Commands from espeak-ng library hooked to buttons
It is possible to 'translate', 'speak', 'show rules', select different pronuciation from Voice->Select Voice sub menu,
switch pronouncing of characters or punctuation symbols. Note that installed espeak-ng library is needed for this.
Currently  these functions are done in a *hacky* way  - programm calls espeak-ng trough command line, - > text output is saved in .txt file -> .txt file is read in programm and deleted.
*This also could be done trough same princible as opening phoneme is - calling C code from espeak-ng*

###-Translation of menu to EN, RU or LV
Raw but works good enough, though expanding or editing of translation files may be painful.
Translation files are stored in 'languages' folder.

###-Help tab
Takes you to the original espeak-ng documentation which might be useful.



##Building


To build eSpeak-Jedit, first you need to install all dependencies, start  with
 [this espeak-ng repo] (https://github.com/marcismajors/espeak-ng) and go recursively.
Once it is installed make bash file outside espeak-ng directory which includes following 
```
    #!/bin/bash

    cd /home/your-folder/your-workspace/espeak-ng

    read -r -p "Reconfigure entire project [Y/n]? " response

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

```
then execute bash script

### *REMEMBER TO CHANGE  `   cd /home/your-folder/your-workspace/espeak-ng `  TO YOUR PATH *

Now you should be able to use it



##Task list

 - []