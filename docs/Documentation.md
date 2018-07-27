eSpeak NG Documentation for recent changes
==========================================

## Voice: -

Edited the primary code and Added event listeners for selecting the voice.

It also checks the file if its legit by checking letter and number available in text file the is used in voice modulation.

Voice variant was added to the program.

It uses terminal command to execute the requested variants. Using voice as parameter to command line program is compiled to check the selected file and to change voice in the program.

## GUI: -

Improve support for different GUI languages using Java `\*. properties` files.

Default text box word is now available in 4 languages according to user’s preference. User can change the language in eSpeak NG java editor

`Options - Language - (English/ Latvian/ Russian/ Tamil)`

Changes made in GUI to translate all menu bar buttons and system buttons to User preferred language.

Adding fourth language Tamil in User Interface.

### Adding Functions to Buttons: -

Speak Characters – this function returns the given word in separate characters

> E.g. “Hello” as “H”, ”E”, ”L”, ”L", ”O”

Speak Character Name – This function returns given words separated by vowels to stress on space or word ending.

>  E.g. “Because” as “be - cause”

Speak Punctuations - This function returns just punctuation from the given word. The alphabets and numbers from the word are ignored.

> E.g. “hello\*” only \* is returned

### Importing Graph: -

Graph can be imported using

`File – Open – (dz\_pzd/dzh/xdz) variants respectively.`

### Added Mouse Listeners: -

* Right Click - Pop up menu – Open – To open graphs and folders.

* Right Click - Pop up menu – Export – To export the graph on screen to a file.

* Right Click - Pop up menu – Close Graph – To close only selected graph in the panel.

* Right Click - Pop up menu – Close all Graph – To close all the graphs in the panel.

* Right Click - Pop up menu – Quit – To quit the Program.

**Interactive Buttons: -**

1. * Menu bar - Speak – Pause (or) 
    * Pause button in Text tab - To pause speaking part of the program.

2. * Menu bar - Speak –Stop (or)
    * Stop button in Text tab - To stop speaking part of the program.

3. * Menu bar - Speak - Speak (or)
    * Play button in Text tab - To start speaking the user inputted characters.

If Pause button is pressed then to resume the speaking process click,

> Resume button in Text tab - To resume the speaking process in the program.

### Prosody Tab: -

If changes are made in the Prosody tab, then clicking Speak will speak the modified prosody while Translate will revert to the default prosody settings for the text.

Results are shown in Prosody tab after the Speak button is clicked to speak the text.

Click on a vowel phoneme which is displayed in the Prosody tab. A red line appears under it to indicate that it has been selected.

These are the useful prosody editor shortcuts available only in prosody tab,

\*\*Left\*\* : Move to previous phoneme.

\*\*Right\*\* : Move to next phoneme.

\*\*Up\*\* : Increase pitch.

\*\*Down\*\* : Decrease pitch.

\*\*Ctrl Up\*\* : Increase pitch range.

\*\*Ctrl Down\*\* : Decrease pitch range.

\*\*&gt;\*\* : Increase length.

\*\*&lt;\*\* : Decrease length.

### Mbrola: -

Multi-Band Resynthesis Overlap Add algorithm it provides the databases on diphone for major spoken language in the world.

But first user needs to install it before expecting output. Linux code for installing mbrola.

> `sudo apt install mbrola mbrola-en1`

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

Displays the result in Prosody tab and the software console.

### Speak File: -

> Menu bar – Speak – Speak file

### Compile: -

>Menu bar – Compile – Compile dictionary” ----

To open file chooser for finding dictionary to compile.

> Menu bar – Compile – Compile dictionary (debug) ----

To open file chooser for finding dictionary to debug in the program.

> Menu bar – Compile – Compile phoneme data ----

To compile *phoneme* data using *phoneme* and phSource to provide details about references of *phoneme* data, reused *phoneme* data and errors in compiled *phoneme* data.

### Exporting Graph: -

Exporting graph to user indented target file was not possible in previous version of eSpeak NG. It can only export graph in workspace file of eSpeak NG.

> Menu bar – File – Export graph – File chooser || Right Click – Export – File chooser

To Export graph anywhere in the computer.

### Spectrum graph and Listeners: -

Implemented spectrum graph in the left lower corner of the Spect tab.

This graph shows frequencies of the first 3 formants (red circles) and peaks (black lines) of all frames placed in the right panel. Orange lines represent length (time in milliseconds) of correspondent frames from the right panel. This graph changes then different tab with different phoneme is chosen.

To create this graph, Spectrum Graph Class was written. This class have to be used to draw a pitch line.

Implemented stateChanged listener to redraw spectrum graph at tab change.

Some corrections made to the visual appearance of the Spect tab (aligned text labels for rows, added %rms spinner).

### RMS Amplitude: -

For simple periodic waves, the Root Mean Square amplitude is equal to the peak amplitude multiplied by 0.707. For complex periodic waves, RMS amplitude cannot be derived directly from peak measurements. Peak measurements differ from RMS amplitude in that they give a measure of acoustic amplitude, whereas RMS amplitude is a measure of acoustic intensity (because perceived loudness is linked more closely to acoustic intensity than to acoustic amplitude). To calculate RMS amplitude, each sample in a waveform window is squared; then the average of the squares is calculated; and then, the square root is taken.

This info is necessary to make rms graph for frames. Some frames have data for this graph, but some – not.

### Log4j: -

Replacing previously used java logger by new Log4j.

Log4j which has more performance than java util logging which was previously used. And also level are changed according to log4j.

### Splash Screen: -

Added splash screen to software as a welcome screen that will be in and out while program take some time to load and appear.

### Thread Functionality: -

 eSpeak NG used single thread before to run every program in the software. Because of this user cannot pause or the stop the speaking process if the screen doesn’t respond till the speaking process end.

Whereas adding thread functionality to the software that runs program smoothly and allows user to perform various activities even when software is in speaking process.

### Word Occurrence.: -

Added count word occurrences button to the menu. That counts number of words used in the text area as an input.

## Documentation: -
Fixed show documentation button. Now it opens documentation in your default web browser.
### Removed Unused buttons: -

Removed Compile at sample rate this function is removed from official release so it's no longer needed in Espeak NG Software.

Layout `\_rules` files, Sort `\_rules` files these are unnecessary buttons so it was removed.

Convert to UTF8 button removed because its outdated.

Test coverage improved from 1% to at least 71%.
