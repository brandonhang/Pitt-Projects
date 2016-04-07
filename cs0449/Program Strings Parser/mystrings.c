/*0=======================================================0
  |  Title:    mystrings.c                                |
  |  Author:   Brandon S. Hang                            |
  |  Version:  1.100                                      |
  |  Class:    CS 0449                                    |
  |            Project 2                                  |
  |            February 28, 2016                          |
  |                                                       |
  |  This program takes a file from the command line and  |
  |  reads it as byte data.  It searches through the      |
  |  file for strings of printable ASCII characters       |
  |  (ranging from 32 to 126).  These character           |
  |  sequences are only printed if they are at least 4    |
  |  characters long.  Strings end when an unprintable    |
  |  ASCII character is encountered.                      |
  |                                                       |
  |  Execution: mystrings FILENAME                        |
  0=======================================================0*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>

int print_string(char *arr, long start, long end);				// Prototype of the function that prints out strings

int main(int argc, char *argv[]) {
	
	/*/---------------------------------------------------------\
	 <                  Variable Declarations                    >
	  \---------------------------------------------------------/*/
	
	FILE *file;
	char *char_sequence;				// Pointer for the character array containing the file's bytes
	long file_size = 0;
	long index_start = 0;				// Denotes a starting index for printing
	long index_end = 0;					// Denotes an ending index for printing
	long loop_index = 0;
	char found_sequence = 0;			// Functions as a boolean
	
	
	if (1 == argc) {						// If argc's value is 1, then no parameter was passed to the program in the command line
		printf("\n***********************************************\n");
		printf("Error: No file was specified!  Exiting program.\n");
		printf("***********************************************\n\n");
		return 1;
	}
	
	if (-1 == access(argv[1], F_OK)) {			// Checks to see if the file exists.  If not, it displays this message and exits.
		printf("\n**********************************************************\n");
		printf("Error: The file specified was not found!  Exiting program.\n");
		printf("**********************************************************\n\n");
		return -1;
	}
	
	file = fopen(argv[1], "r");
	fseek(file, 0, SEEK_END);
	file_size = ftell(file);			// Combined with fseek/SEEK_END, returns the size of the file
	fseek(file, 0, SEEK_SET);
	
	char_sequence = malloc(sizeof(char) * file_size);		// Allocates memory the size of the file (in bytes)
	fread(char_sequence, file_size, 1, file);
	
	printf("\nFilename: %s\n", argv[1]);
	printf("File Size: %d bytes\n", file_size);
	printf("\n~~~~~~~~ List of Strings ~~~~~~~~\n\n");
	
	for (loop_index = 0; loop_index < file_size; loop_index++) {
		if (char_sequence[loop_index] >= 32 && char_sequence[loop_index] <= 126) {		// Conditional if the character is printable
			if (0 == found_sequence) {					// If the boolean is "false", sets the start index for printing
				found_sequence = 1;						// Sets the boolean to "true"
				index_start = loop_index;
			}
		}
		else {							// Otherwise, the character is not printable
			if (1 == found_sequence) {					// Conditional if the boolean is "true"
				found_sequence = 0;						// Sets the boolean back to "false"
				index_end = loop_index;
				if ((index_end - index_start) >= 4) {			// Only calls the print function if the string is at least 4 characters long
					print_string(char_sequence, index_start, index_end);
				}
			}
		}
	}
	free(char_sequence);
	printf("\n~~~~~~~~~~~ End List ~~~~~~~~~~~~\n\n");
	
	return fclose(file);					// Closes the file stream and returns 0 to main if successful;
}

/*/---------------------------------------------------------\
 <                      String Printing                      >
  \---------------------------------------------------------/
   | Prints the sequence of displayable ASCII characters.  |
   | Returns the length of the string as an int.           |
   /_______________________________________________________\*/

int print_string(char *string_array, long start, long end) {
	
	int i = 0;				// Generic int variable for loops
	
	for (i = start; i < end; i++) {
		printf("%c", string_array[i]);		// Loops through the array from the start and end indices to print a sequence of characters
	}
	printf("\n");
	
	return end - start;					// Returns the length of the character sequence
}
