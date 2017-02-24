#eSpeak-ng-jeditor
##This is an attempt to re-do the espeak-edit project on Java.

##At the moment these things are implemented:

###-Interface: 
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

### *REMEMBER TO CHANGE*  `   cd /home/your-folder/your-workspace/espeak-ng `  *TO YOUR PATH*

Now you should be able to use it



##Working button shortcuts

- **0-8** select peaks in keyframe
- **UP-arrowkey** - increase peaks height
- **DOWN-arrowkey** - decrease peaks height
- **LEFT-arrowkey** - increase peaks frequency
- **RIGHT-arrowkey** - decrease peaks frequency
- **[** - decrease peaks width from left side 
- **]** - increase peaks width from left side 
- **<** - decrease peaks width
- **>** - increase peaks width
- **/** - make selected peak symetrical
- **X** - select next peak
- **Z** - select previous peak
- **PageUp** - select previous keyframe
- **PageDown** - select next keyframe

- **CTRL-A** - select all keyframes
- **CTRL-B** - toggle bass reduction
- **CTRL-C** - copy selected keyframes
- **CTRL-G** - toggle grid
- **CTRL-I** - interpolate adjacent keyframes
- **CTRL-N** - add new marker
- **CTRL-M** - toggle first marker
- **CTRL-V** - paste copied keyframes
- **CTRL-Z** - zero all peaks in selected keyframe

- **CTRL-C** - copy selected keyframes
- **CTRL-N** - add new marker
- **CTRL-M** - toggle first marker
- **CTRL-SHIFT-V** - paste copied keyframes after existing keyframes
- **CTRL-Z** - zero all peaks in selected keyframe


##Task list
Prepare environment
- [x]      2017.02.14 repository made - https://github.com/adrianson/eSpeak-Jedit
- [x]      2017.02.15 first commits (with actual code changes) pushed
- [x]      2017.02.17 addressing first merge conflicts
- [x]      2017.02.24 All major commits have been done
Research
- [x]      2017.02.14 found out how to draw a sinusoid
- [x]      Java C integration (was long proccess, but now it works)
Practical tasks
- [x]	         2017.02.14 buttons replaced with panels corresponding to various keyframes
- [x]	         2017.02.14 in keyframe panels dots corresponding to formants are drawn, although they are connected with lines instead of curves (Bezier?)
- [x]		 2017.02.14 Java program calls .cpp, in which a Java object is made, which is in turn returned to the Java program
- [x]	         2017.02.15 in keyframe panels triangles corresponding to formants are drawn with the specified width
- [x]	         2017.02.15 working on keylisteners for keyframe sequences
- [x]	         2017.02.15 started implementing key commands for keyframe sequence operations
- [x]	         2017.02.19 Green line of graph is accuratly displayed
- [x]	         2017.02.22 Zoom function is working correctly
- [x]          2017.02.22 Markers on graph are implemented
- [x]	         2017.02.23 If filetype is not supported, program shows popup window that informs that this filetype is not supported and doesn't load it
- [x]	         2017.02.23 We are able to call espeak-ng C code and use its loader to get proper values for graph creation
- [x]	         2017.02.23 Various file format support
- [x]	         2017.02.23 Black line of graph is accuratly displayed
- [x]          2017.02.23 when last or all phoneme files are closed, all text windows are cleared
- [x]	         2017.02.24 Blue formants in graph are accuratly displayed
- [x]	         2017.02.24 *ms* and *hz* is accuratly displayed on graph

###TODO
- [ ]		 Need to calculate rms
- [ ]		 Need to fill left panel's bottom part with correct values
- [ ]		 "Amplitude frame" in bottom left corner still needs to be implemented
- [ ]		 Scrollbar needs to be added to opened phonemes
- [ ]   Implement listeners for all menu items and all buttons
- [ ]   Implement espeak-ng API for translating and speaking
- [ ]   Make Fourier in text panel
- [ ]   Update phonem saving

