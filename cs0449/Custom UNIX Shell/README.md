The project functions as a UNIX shell featuring several built-in functions.  These functions, consisting of "exit", "cd", and
"time", are handled directly by the shell rather than through a system call.  Other functions are handled through forking
a child process and replacing it with the "execvp" system call.  The shell is also able to handle input and output redirection.
This project was originally submitted on 04/24/2016.
