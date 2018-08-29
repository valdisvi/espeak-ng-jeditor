# Call C function from Java using JNI framework

### Background for JNI

[Java Native Interface] (https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/intro.html) (JNI) allows Java code that runs inside a Java Virtual Machine (VM)
to interoperate with applications and libraries written in other programming languages: C, 
C++, assembly.

You already have a library written in another language, and wish to make it accessible to Java code through the JNI.

By programming through the JNI, you can use native methods to:
Call Java methods.

Use JNI calling C from Java.


### Background for this project
(need a image)

1. I first started by declaring the native methods in the Java code.
	
	The native keyword transforms our method into a sort of abstract method:

	> private native void aNativeMethod();

2. I regenerate the header file for the JNI methods.

3. I immplemented method.

4. I create Unit test.
