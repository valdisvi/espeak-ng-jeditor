# Call C function from Java using JNI framework 

### Background for JNI

If you want to call C from Java, you'll need to use JNI - Java Native Interface. 

JNI allows Java code that runs inside a Java Virtual Machine (VM)
to interoperate with applications and libraries written in other programming languages: C, 
C++, assembly.`


### How to call C from Java

JNI general workflow

![Image of Jni](https://github.com/AneteKlavina/espeak-ng-jeditor/blob/master/docs/images/Jni.png)


1. Declare the native method in the Java code.

 `private native void aNativeMethod();`

SRC/main/java -> org.espeaking.jeditor.jni -> ESpeakService.java

2. Regenerate the header file for the JNI methods.

espeak-ng-jeditor -> SRC -> main -> C -> speakC

3. Immplement method.

espeak-ng-jeditor -> SRC -> java -> org -> espeakng -> jeditor -> jni -> eSpekService

4. Create Unit test.

espeak-ng-jeditor -> SRC/test/java -> org.espeaking.jeditor.jni -> ESpeakserviceTest.java


**Future work** 

Interesting C function: static espeak_ng_STATUS Synthesize(unsigned int unique_identifier, const void *text, int flags) (speech.c)
