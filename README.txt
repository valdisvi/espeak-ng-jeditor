This is an attempt to re-do the espeak-edit project on Java.
---------------------------------------
At the moment these things are implemented:

-Interface: 
Design repeats the old espeak-edit project, no significant changes were done!

-Opening and storing phonemes:
It is possible to open and work with every singly byte of information from original phoneme files,
all of those are store into the similarly called or completely same variables even though NOT all
of them used. The only type of phonemes which is not recognised is SPECSCP2 which is stated to be very
old and might not even exist among phonemes in folder 'phsource'.

-Commands from espeak-ng library hooked to buttons
It is possible to 'translate', 'speak', 'show rules', select different pronuciation from Voice->Select Voice sub menu,
switch pronouncing of characters or punctuation symbols. Note that installed espeak-ng library is needed for this.

-Translation of menu to EN, RU or LV
Raw but works good enough, though expanding or editing of translation files may be painful.
Translation files are stored in 'languages' folder.

-Help tab
Takes you to the original espeak-ng documentation which might be useful.
---------------------------------------

