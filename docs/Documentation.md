## eSPEAK NG DOCUMENTATION FOR ALL RECENT CHANGES

#INSTALLATION

All of the necessary installation guidelines are written in README.md file, found already in this project.


# GUI 

To make program easier to use, the order of Speak and Text in GUI interface has been changed.
	
First - use Speak or Translate as a function, and after it shows the details for frequency, amplitude etc. in the graph.

Changes made in GUI to translate all menu bar buttons and system buttons to user preferred language.

According to users preference, default text box now is available in 7 different languages in eSpeak NG java editor, user can change the
 
preferred language, using

	`Options – Language – (English/ Latvian/ Russian/ Tamil/ Korean/ Japaneese/ Spanish)`.
 
Three new languages were added in the User Interface : Japanese, Korean,Spanish.


For different GUI languages improved support using Java `\*. properties` files.

Added new GUI feature for highlighting active peak text fields.



# VOICE


For selecting the voice, event listeners were added and the primary code was eddited. 

By checking the letter and the number available in the text file that is used in voice modulation, it checks if the file used is proper.

Added voice variant to the program. It uses terminal command to execute the requested variants. Using voice as a parameter to command line

program is compiled to check the selected file and to change voice in the program.


# GRAPH 

Amplitude (y-axis) and time (x-axis) added to the graph, as well as frequency in hz sound's vowels based in different graphs background. 

The direction of the graph is opposite in comparison with normal amplitude graphs.

Graph can be imported, using 

	`File – Open – (dz_pzd/dzh/xdz)` variants respectively.

Graph can be exported to any user intended target file 

	`File – Export graph – File chooser ||  Right Click – Export – File chooser`.


# SPECTRUM GRAPH


Spectrum graph implemented in the left lower corner of the Spect tab. For this Spectrum Graph Class was written and has to be used to draw

a pitch line. This graph shows frequencies of the first 3 formants (red circles) and peaks (black lines) of all frames placed in the right

panel. Orange lines represent length (time in milliseconds) of correspondent frames from the right panel. This graph changes when different 

tab with different phoneme is chosen. 


StateChanged listener implemented to redraw spectrum graph at tab change. 


# PROSODY TAB


If changes are made in the Prosody tab, then clicking Speak will speak the modified prosody while Translate will revert to the default prosody

settings for the text. Results are shown in the Prosody tab after the Speak button is clicked to speak the text. Click on a vowel phoneme which

is displayed in the Prosody tab. A red line appears under it to indicate that it has been selected.
	
Useful Prosody editor shortcuts available only in prosody tab

\*\*Left\*\* : Move to previous phoneme.

\*\*Right\*\* : Move to next phoneme.

\*\*Up\*\* : Increase pitch.

\*\*Down\*\* : Decrease pitch.

\*\*Ctrl Up\*\* : Increase pitch range.

\*\*Ctrl Down\*\* : Decrease pitch range.

\*\*&gt;\*\* : Increase length.

\*\*&lt;\*\* : Decrease length.


## PHONEME LIST GRAPH

When language is changed (e.g., for us), phoneme list graph coordinates do not fit into the Prosody graph table, because of y-axis value,

which has been changed accordingly to 250, so it fits properly. 


## PHONEME LIST DATA


This function is not used in the project yet.                                                                                             
                                                                                                                                               
This function acquires PHONEME_LIST data form "C" espeak-ng project PHONEME_LIST which is not publicly available, if you want this use this 

function, you need to add line:                                                                                                                           
                                                                                                                                               
> extern PHONEME_LIST *getPhonemeList();                                                                                                   
                                                                                                                                               
to synthesize.h in espeak-ng project and add these lines:
                                                                                                                          
 #pragma GCC visibility push(default)                                                                                                     
PHONEME_LIST * getPhonemeList(){                                                                                                    
return &phoneme_list;                                                                                                            
} #pragma GCC visibility pop                                                                                                              
                                                                                                                                               
to synthesize.c in espeak-ng project and recompile libespeakservice.so library you can do this with command 

`./updateJNIchanges.sh` in this project.
                                                                         
Note that both "espeak-ng" and "espeak-ng-jeditor" projects should be located in the same folder to compile library

(e.g../workspace/espeak-ng	../workspace/eskeap-ng-jeditor)                                                                                   
                                                                                                                                               
This function not just gives back PHONEME_DATA for each Phoneme in your text, but also voices it out.This conflicts with already existing 

function, so when you press "Speak" its speaks out twice.

Possible solution is to somehow disable speech for this function, or replace existing one (found in EvenHandlers.java:298) with this one.

Unfortunately due to Thread problems and time limits i was not able to do this. 
                                                              
For this, 3 classes were created - 

> PhonemeList.java, PhonemeList2.java and PhonemeTab.java 

which right now do nothing, but could be used to store this data.  
	

# THREADS

Thread functionality added to help program to run better and allowing user to  perform various activities even when software is in speaking

process. Espeak NG before used only single thread to run every program in the software. 



# BUTTONS

## COMMAND BUTTONS


Reconfigured command buttons – Speak/ Stop/ Pause, so now they work properly. Now it allows you to stop and pause speaking process even for

larger amount of words/characters, and resume it easily, whereas before it only allowed you to play the user input.

To pause the speaking process, use Pause button in the Text tab or

	`Menu bar - Speak – Pause`

To stop – use Stop button in the Text tab or
 
	`Menu bar - Speak –Stop`

To start speaking the user input – use Play button in the Text tab or 

	`Menu bar - Speak – Speak`

To resume the speaking process, when paused, use Resume button in the Text tab or 

	`Menu bar – Speak – Resume`

Count word occurrences button added to the menu, which counts number of words used in the text area as an input.
 

###Working button shortcuts

- **0-8** select peaks in keyframe
- **UP-arrow key** - increase peaks height
- **DOWN-arrow key** - decrease peaks height
- **LEFT-arrow key** - increase peaks frequency
- **RIGHT-arrow key** - decrease peaks frequency
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


### Adding Functions to the Buttons

Speak Characters – returns the given word in separate characters, for example:

	“speak” as “s”, ”p”, ”e”, ”a", ”k”

Speak Character Name – returns given words separated by vowels to stress on space or word ending, e.g.:

	“because” as “be - cause”

Speak Punctuation - returns only punctuation from the given word. The alphabets and numbers from the word are ignored, e.g.:

	“speak\*” -  only \* is returned
	
	
### MOUSE LISTENERS

Added Mouse Listeners as follows:

	`Right Click - Pop up menu – Open` – open graphs and folders
	`Right Click - Pop up menu – Export` – export the graph on screen to a file
	`Right Click - Pop up menu – Close Graph` – close only selected graph in the panel
	`Right Click - Pop up menu – Close all Graph` – close all the graphs in the panel
	`Right Click - Pop up menu – Quit` – quit the Program
	
	
	
## UNUSED BUTTONS

Unused, unnecessary and outdated buttons and functions have been removed -   

	Compile at sample rate, Layout_rules files, Sort_rules files, Convert to UTF8. 
	

## NOT WORKING BUTTONS

Software still has some unfixed issues, here are listed some not working buttons:

`Tools – Process Lexicon – Languages`

`Help – eSpeak Documentation` - takes you to the original eSpeak-NG documentation in your default web browser
	

### Mbrola: -


It automatically analyzes the inputted text in the text area algorithm returns the time period of the character spelled and the graph coordinated example

Output message:

 1. h 70 
    - time period=70

2. @ 24 0 94 20 95 40 96 59 97 80 99 100 99 
    - time period=24
    - graph coordinates x=0, y=94 ….x=100, y=99

3. l 65
    - time period=65

4. @U 175 0 102 80 76 100 76
    - time period=175
    - graph coordinates x=0, y=102 ….x=100, y=76

Results are displayed in the Prosody tab and the software console.


# OTHER UPDATES 


Java util logger replaced with Log4j, which is more suitable and functional, accordingly, levels were changed.

For user purposes, Open and Open2 remembers two different last source directories.

Splash screen was added as a welcome screen, while program opens/loads.

Phoneme opening is done trough eSpeak-ng library, written in C.

File pom.xml is used to build executable .jar file.

The only type of phonemes which is not recognised is SPECSCP2 which is outdated and might not exist among phonemes in folder 'phsource'.

It is possible to change phoneme peak data using text fields

Corrections made to the visual appearance of the Spect tab - aligned text labels for rows, added %rms spinner.


#TEST COVERAGE 

Test coverage improved from 1% to 99%.


#FUTURE WORK

## PHONEME LIST DATA


This function is not used in the project yet.                                                                                             
                                                                                                                                               
This function acquires PHONEME_LIST data form "C" espeak-ng project PHONEME_LIST which is not publicly available, so if you want this use this 

function, you need to add line:                                                                                                                           
                                                                                                                                               
> extern PHONEME_LIST *getPhonemeList();                                                                                                   
                                                                                                                                               
to synthesize.h in espeak-ng project and add these lines:
                                                                                                                          
 #pragma GCC visibility push(default)                                                                                                     
PHONEME_LIST * getPhonemeList(){                                                                                                    
return &phoneme_list;                                                                                                            
} #pragma GCC visibility pop                                                                                                              
                                                                                                                                               
to synthesize.c in espeak-ng project and recompile libespeakservice.so library you can do this with command 

`./updateJNIchanges.sh` in this project.
                                                                         
Note that both "espeak-ng" and "espeak-ng-jeditor" projects should be located in the same folder to compile library

(e.g../workspace/espeak-ng	../workspace/eskeap-ng-jeditor)                                                                                   
                                                                                                                                               
This function not just gives back PHONEME_DATA for each Phoneme in your text, but also voices it out.This conflicts with already existing 

function, so when you press "Speak" its speaks out twice.

Possible solution is to somehow disable speech for this function, or replace existing one (found in EvenHandlers.java:298) with this one.

Unfortunately due to Thread problems and time limits i was not able to do this. 
                                                              
For this, 3 classes were created - 

> PhonemeList.java, PhonemeList2.java and PhonemeTab.java 

which right now do nothing, but could be used to store this data.  

