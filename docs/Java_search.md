# Java search

### Background
 
I do Java search in particular project (open source project eSpeakNG program) to find references to and declarations of Java elements (methods) so that I can declare and implement particular method using JNI framework.

I need to add new native methods and do C and Java integration (using JNI framework) because of lack of a system functionality (functions that a system or component must perform to create synthesized speech).


### Where to start

If you don't know in which class the function is, then use the text search - "File search". 



### How to open Search dialog

Open the Search dialog by either:

   * Click the **Search button** in the toolbar or
	![Image of Search button](https://github.com/AneteKlavina/espeak-ng-jeditor/blob/master/docs/images/Search_button.png)

   * Press **Ctrl + H** or

   * Select **Search > Search...** from the menu bar. 
	

### How to fill Search dialog

Open the Search dialog 
![Image of File_search_c_empty](https://github.com/AneteKlavina/espeak-ng-jeditor/blob/master/docs/images/File_search_c_emty.png)

1. In the **Containing text:** field, type the name for which you want to search. 
2. Select **Regular expresion** which is a is a special sequence of characters that helps you match or find other strings or sets of strings, using a specialized syntax held in a pattern. They can be used to search, edit, or manipulate text and data.
3. In the **File name patterns:** field limit the search to **"*.c"** **"*.cpp"** files.
4. In the **Scope** narrow the scope of your search - select **Workspace**.
5. Press **Search**. 

![Image of File_search_c_fill](https://github.com/AneteKlavina/espeak-ng-jeditor/blob/master/docs/images/File_search_c_fill.png)


### Use `F3` and `Ctrl+Shift+G`

`F3` - open declaration

`Ctrl+Shift+G`- search references in workspace


### Check if method is already used

1. Open Search dialog
2. In the **Containing text:** field, type the name for a method.
3. Select **Regular expresion**.
4. In the **File name patterns:** field limit the search to **"*.java"** files.
5. In the **Scope** narrow the scope of your search - select **Workspace**.
6. Press **Search**. 


