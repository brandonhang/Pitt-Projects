/*0=======================================================0
  |  Title:    Custom UNIX Shell                          |
  |  Author:   Brandon S. Hang                            |
  |  Version:  1.400                                      |
  |  Class:    CS 0449                                    |
  |            Project 5                                  |
  |            April 24, 2016                             |
  |                                                       |
  |  This program is an implementation of a custom UNIX   |
  |  shell.  It has the built-in commands of "exit",      |
  |  "cd", and "time".  It also handles other UNIX        |
  |  commands with a number of arguments.  For            |
  |  simplicity, the maximum input length is capped at    |
  |  1,024 characters while the number of arguments       |
  |  is capped at 128.                                    |
  |                                                       |
  |  Compilation: gcc -o myshell myshell.c                |
  |  Execution:   ./myshell                               |
  0=======================================================0*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <stdint.h>
#include <sys/times.h>
#include <sys/wait.h>
#include <time.h>
#include <errno.h>

#define MAXSIZE 1024							// Defines the maximum input size
#define MAXARGS 128							// Defines the maximum number of arguments
#define DELIMS " \t\n\r\v()<>|&;"

void print_error(char *err);				// Prototypes the function that prints error messages

int main() {
	
	char input[MAXSIZE];						// Input from the user
	char *args[MAXARGS];					// Array of arguments
	char *command;								// Holds the result of the strtok function
	char *redir_in;							// Holds the input redirection file
	char *redir_out;							// Holds the output redirection file
	struct tms timer_start;					// Holds the CPU start time struct
	struct tms timer_end;					// Holds the CPU end time struct
	time_t real_start;						// Stores the starting real time
	time_t real_end;							// Stores the ending real time
	int counter = 1;							// A counter next to the input line
	int status = 0;							// Stores the error status
	int num_args = 0;							// Stores the number of arguments in the current command
	char timer = 0;							// Boolean if "time" was entered first
	char read = 0;								// Boolean for input redirection
	char write = 0;							// Boolean for output redirection
	
	
	while (1) {
		printf("(%d) blueshell $ ", counter);			// "blueshell" pays homage to the notorious Mario Kart item
		
		if (!fgets(input, 1000, stdin)) {				// Exits the shell if fgets returns NULL
			return 1;
		}
		if (strlen(input) <= 1) {				// Continues the loop if an empty string is entered
			continue;
		}
		
		num_args = 0;					// Resets the associated accumulators and booleans
		timer = 0;
		read = 0;
		write = 0;
		errno = 0;
		status = 0;
		counter++;						// Increments the shell counter
		
		redir_in = strchr(input, '<');				// Searches for the input redirection character
		redir_out = strchr(input, '>');				// Searches for the output redirection character
		
		if (redir_in != NULL) {									// Stores the input file if redir_in is not null
			redir_in = strtok(redir_in, DELIMS);
			read = 1;
		}
		if (redir_out != NULL) {								// Stores the output file if redir_in is not null
			redir_out = strtok(redir_out, DELIMS);
			write = 1;
		}
		
		command = strtok(input, DELIMS);					// Delimits the first token in the input
		
		if (command == NULL) {					// Continues the loop if the token is null
			continue;
		}
		if (0 == strcmp(command, "exit")) {			// Exits the shell if the token is "exit"
			exit(0);
		}
		if (0 == strcmp(command, "time")) {			// Starts the CPU timer if the token is "time"
			command = strtok(NULL, DELIMS);			// Gets the next token (since time is not included as a command)
			timer = 1;										// Sets the time boolean to "true"
			real_start = time(NULL);					// Sets the realtime start
			times(&timer_start);							// Sets the CPU times start
		}
		
		while (command != NULL) {						// Loops while there is something to tokenize
			char valid = 1;						// Boolean validating the token
			
			if (read) {
				if (0 == strcmp(command, redir_in)) {			// Keeps the input redirection file out of the arguments
					valid = 0;
				}
			}
			if (write) {
				if (0 == strcmp(command, redir_out)) {			// Keeps the output redirection file out of the arguments
					valid = 0;
				}
			}
			
			if (valid) {
				args[num_args++] = command;			// If the argument is valid, it is added to the array and the arg_count is incremeneted
			}
			
			command = strtok(NULL, DELIMS);				// Gets the next token of the input
		}
		
		if (num_args < MAXARGS) {
			args[num_args] = NULL;					// Sets the pointer after the last argument to null
		}
		else {
			printf("-blueshell: %s: Arg list too long\n", args[0]);		// Prints an error if the maximum number of arguments have been reached
			continue;
		}
		
		if (args[0] != NULL) {							// Checks various conditions if a command has been entered
			if (0 == strcmp(args[0], "cd")) {				// Conditional if the command is "cd"
				if (num_args > 1) {								// Conditional if there are arguments for "cd"
					if ('~' == args[1][0]) {					// Conditional if the first character is a tilde
						char *path;							// Holds the Pitt Net path prefix
						char *argument;					// Stores the modified argument
						int len = 0;						// Length of the argument
						
						argument = strtok(args[1], "~");				// Tokenizes the argument of the tilde
						len = strlen(argument);						// Gets the length of the modified argument
						path = malloc(len * sizeof(char) + 24 * sizeof(char));		// Allocates memory for the full path string
						
						strcpy(path, "/afs/pitt.edu/home/");				// Builds a string of the full pathname
						strncat(path, argument, 1);
						strncat(path, "/", 1);
						strncat(path, argument + 1 * sizeof(char), 1);
						strncat(path, "/", 1);
						strcat(path, argument);
						
						status = chdir(path);					// Attempts to change the current directory
						free(path);						// Frees memory allocated for the string
					}
					else {
						status = chdir(args[1]);			// Otherwise, changes the directory to the argument specified
					}
				}
				else {							// Otherwise, changes to the default directory of the user
					char *path;					// Pointer to the pathname
					char *user;					// Pointer to the username
					int len = 0;				// Length of the full pathname
					
					user = getlogin();			// Gets the username
					len = strlen(user);			// Gets the length of the username
					
					path = malloc(len * sizeof(char) + 24 * sizeof(char));			// Allocates memory for the path
					
					strcpy(path, "/afs/pitt.edu/home/");					// Builds the default pathname
					strncat(path, user, 1);
					strncat(path, "/", 1);
					strncat(path, user + 1 * sizeof(char), 1);
					strncat(path, "/", 1);
					strcat(path, user);
					
					status = chdir(path);				// Attempts to change the current directory
					free(path);								// Frees memory allocated for the string
				}
				
				if (status < 0) {						// If chdir() returned -1, prints the errno message
					print_error(args[1]);
				}
			}
			else {								// Otherwise, attempts to fork the process and call execvp()
				int pid = fork();				// Gets the process ID
				
				if (0 == pid) {				// The child process has an ID of 0
					if (read) {									// If input redirection is true
						if (freopen(redir_in, "r", stdin) == NULL) {			// Prints an error if the file descriptor is null
							print_error(redir_in);
							_exit(-1);
						}
					}
					if (write) {								// If output redirection is true
						if (freopen(redir_out, "w", stdout) == NULL) {		// Prints an error if the file descriptor is null
							print_error(redir_out);
							_exit(-1);
						}
					}
					
					status = execvp(args[0], args);			// Attempts to run the command and associated arguments
					
					if (status < 0) {						// Prints an eror if execvp returns -1
						print_error(args[0]);
						_exit(-1);
					}
					
					_exit(0);
				}
				else if (pid < 0) {						// If the process ID is negative, prints an error
					print_error(args[0]);
					_exit(-1);
				}
				else {									// Otherwise, the process is the parent
					wait(&status);						// Waits for the child process to complete
					
					if (status < 0) {							// Prints an error if the status is -1
						print_error(args[0]);
					}
				}
			}
		}
		
		if (timer) {						// Gets the end real, user, and system times
			times(&timer_end);					// Gets the ending CPU times
			real_end = time(NULL);						// Gets the realtime end
			printf("real\t%lds\n", real_end - real_start);					// Prints the elapsed real time
			printf("user\t%jds\n", (intmax_t)(timer_end.tms_cutime - timer_start.tms_cutime));		// Prints the user CPU time
			printf("sys\t%jds\n", (intmax_t)(timer_end.tms_cstime - timer_start.tms_cstime));		// Prints the system CPU time
		}
	}
}


/*>>>>>>>>>>>>>>>>>>>>>> Error Printing Function <<<<<<<<<<<<<<<<<<<<<
  | This function takes a pointer to a string and prints out the     |
  | message associated with the set errno value.                     |
  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
void print_error(char *err) {
	
	if (errno == EACCES) {
		printf("-blueshell: %s: Permission denied\n", err);
	}
	else if (errno == EBADF) {
		printf("-blueshell: %s: Bad file number\n", err);
	}
	else if (errno == EINTR) {
		printf("-blueshell: %s: Interrupted system call\n", err);
	}
	else if (errno == EISDIR) {
		printf("-blueshell: %s: Is a directory\n", err);
	}
	else if (errno == ELOOP) {
		printf("-blueshell: %s: Too many symbolic links encountered\n", err);
	}
	else if (errno == EMFILE) {
		printf("-blueshell: %s: Too many open files\n", err);
	}
	else if (errno == ENAMETOOLONG) {
		printf("-blueshell: %s: File name too long\n", err);
	}
	else if (errno == ENFILE) {
		printf("-blueshell: %s: File table overflow\n", err);
	}
	else if (errno == ENOENT) {
		printf("-blueshell: %s: No such file or directory\n", err);
	}
	else if (errno == ENOSPC) {
		printf("-blueshell: %s: No space left on device\n", err);
	}
	else if (errno == ENOTDIR) {
		printf("-blueshell: %s: Not a directory\n", err);
	}
	else if (errno == ENXIO) {
		printf("-blueshell: %s: No such device or address\n", err);
	}
	else if (errno == EOVERFLOW) {
		printf("-blueshell: %s: Value too large for defined data type\n", err);
	}
	else if (errno == EROFS) {
		printf("-blueshell: %s: Read-only file system\n", err);
	}
	else if (errno == EINVAL) {
		printf("-blueshell: %s: Invalid argument\n", err);
	}
	else if (errno == ENOMEM) {
		printf("-blueshell: %s: Out of memory\n", err);
	}
	else if (errno == ETXTBSY) {
		printf("-blueshell: %s: Text file busy\n", err);
	}
	else if (errno == E2BIG) {
		printf("-blueshell: %s: Arg list too long\n", err);
	}
	else if (errno == EFAULT) {
		printf("-blueshell: %s: Bad address\n", err);
	}
	else if (errno == EIO) {
		printf("-blueshell: %s: I/O error\n", err);
	}
	else if (errno == ELIBBAD) {
		printf("-blueshell: %s: Accessing a corrupted shared library\n", err);
	}
	else if (errno == ENOEXEC) {
		printf("-blueshell: %s: Exec format error\n", err);
	}
	else if (errno == EPERM) {
		printf("-blueshell: %s: Operation not permitted\n", err);
	}
	else if (errno == EAGAIN) {
		printf("-bluesheel: %s: Try again\n", err);
	}
	else {
		printf("-blueshell: %s: An error occurred\n", err);
	}
}
