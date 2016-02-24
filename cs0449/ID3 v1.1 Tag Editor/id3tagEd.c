/*0=======================================================0
  |  Title:    ID3 v1.1 mp3 Tag Editor                    |
  |  Author:   Brandon S. Hang                            |
  |  Version:  1.800                                      |
  |  Class:    CS 0449                                    |
  |            Project 1                                  |
  |            February 7, 2016                           |
  |                                                       |
  |  This program can read and write ID3 v1.1 tags to     |
  |  mp3 files.  It can operate in read-only mode or      |
  |  read-write mode depending on the arguments passed.   |
  |  If a tag does not exist for the file, the program    |
  |  will create a new one by appending the last 128      |
  |  bytes of the file.  The program takes a number of    |
  |  different arguments following this format:           |
  |                                                       |
  |       id3tagEd FILENAME -FIELD VALUE                  |
  |                                                       |
  |  id3tagEd ....... The name of the program             |
  |  FILENAME ....... The mp3 file                        |
  |  -FIELD ......... The field of the tag e.g., "title"  |
  |  VALUE .......... The field's entry for the mp3       |
  |                                                       |
  |  The possible arguments for field include "-title,"   |
  |  "-artist", "-album", "-year", "comment", and         |
  |  "-track".  The program is able to take these fields  |
  |  in any order and number.                             |
  0=======================================================0*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>

struct id3tag {				// Structure that holds the 128-byte tag and separates it into their fields
	char tag[3];
	char title[30];
	char artist[30];
	char album[30];
	char year[4];
	char comment[28];
	char byte_sep;			// Unused in this program
	unsigned char track;
	char genre;				// Unused in this program
};

void print_tag(struct id3tag tag);				// Prototype of the function that displays tag information

int main(int argc, char *argv[]) {
	
	/*/---------------------------------------------------------\
	 <                  Variable Declarations                    >
	  \---------------------------------------------------------/*/
	
	int i = 0;						// Generic int variable for loops
	int j = 0;						// Generic int variable for loops
	int str_len = 0; 
	unsigned int track_num = 0;			// Holds the track number after conversion from a string
	FILE *music_file;
	struct id3tag tag;
	
	/*/---------------------------------------------------------\
	 <                    Program Scenarios                      >
	  \---------------------------------------------------------/*/
	
	switch (argc) {
		case 1:							// If argc's value is 1, then no parameter was passed to the program in the command line
			printf("\n***************************************************\n");
			printf("Error: No music file was entered!  Exiting program.\n");
			printf("***************************************************\n\n");
			return 1;
		
		/*>>>>>>>>>>>>>>>> Read-Only Mode <<<<<<<<<<<<<<<<*/
		
		case 2:							// If argc's value is 2, then only a filename was entered.  Thus, read-only mode.
			if (-1 == access(argv[1], F_OK)) {			// Checks to see if the file exists.  If not, it displays this message and exits.
				printf("\n**************************************************************\n");
				printf("Error: The music file entered was not found!  Exiting program.\n");
				printf("**************************************************************\n\n");
				return -1;
			}
			
			music_file = fopen(argv[1], "r");			// Opens the file in read-only mode
			printf("\nOpening %s in read-only mode.\n\n", argv[1]);
			fseek(music_file, -128, SEEK_END);				// Skips to the last 128 bytes of the file
			fread(&tag, 128, 1, music_file);				// Copies the last 128 bytes from the file to the structure
			
			if (strncmp(tag.tag, "TAG", 3) != 0) {			// Conditional if an ID3 v1.1 tag does not exist
				printf("***********************************************************************\n");
				printf("Error: Music file not tagged using ID3 v1.1 standards. Exiting program.\n");
				printf("***********************************************************************\n\n");
				return 1;
			}
			
			print_tag(tag);				// Calls the function to print the tag
			break;
			
		/*>>>>>>>>>>>>>>>> Read-Write Mode <<<<<<<<<<<<<<<<*/
			
		default:
			if (-1 == access(argv[1], F_OK)) {			// Checks to see if the file exists.
				printf("\n**************************************************************\n");
				printf("Error: The music file entered was not found!  Exiting program.\n");
				printf("**************************************************************\n\n");
				return -1;
			}
		
			if (1 == argc % 2) {		// Conditional that if argc is not a multiple of 2, then a parameter is missing
				printf("\n*********************************************************************\n");
				printf("Error: There is a missing field or value parameter!  Exiting program.\n");
				printf("*********************************************************************\n\n");
				return 1;
			}
			
			music_file = fopen(argv[1], "r+");			// Opens the file in read-write mode
			printf("\nOpening %s in read-write mode.\n\n", argv[1]);
			fseek(music_file, -128, SEEK_END);			// Skips to the last 128 bytes of the file
			fread(&tag, 128, 1, music_file);			// Copies the last 128 bytes from the file to the structure
			
			if (strncmp(tag.tag, "TAG", 3) != 0) {			// Creates a new tag if one does not exist
				printf("No ID3 v1.1 tag exists.  Creating a new one...\n\n");
				fseek(music_file, 0, SEEK_END);			// Points to the end of the file rather than the last 128 bytes
				strncpy(tag.tag, "TAG", 3);				// Copies "TAG" into the structure and fills the rest with zeros
				strncpy(tag.title, "", 30);
				strncpy(tag.artist, "", 30);
				strncpy(tag.album, "", 30);
				strncpy(tag.year, "", 4);
				strncpy(tag.comment, "", 28);
				tag.byte_sep = '\0';
				tag.track = '\0';
				tag.genre = '\0';
			}
			else {									// If a tag exists, the stream's position is reset to the last 128 bytes
				fseek(music_file, -128, SEEK_END);
			}
			
			/*/---------------------------------------------------------\
			 <           Write Parameter Values to Structure             >
			  \---------------------------------------------------------/*/
			
			for (i = 2; i < argc; i += 2) {			// Loops through argv attempting to match field parameters
				if (0 == strcmp(argv[i], "-title")) {			// This is what enables the arguments to be in any order
					str_len = strlen(argv[i + 1]);
					
					if (str_len > 30) {			// If the title is 31+ characters, it won't be written to the tag
						printf("\n***********************************************\n");
						printf("Error: The title must be 30 characters or less.\n");
						printf("       The entered title was not saved.\n");
						printf("***********************************************\n\n");
						continue;
					}
					
					strncpy(tag.title, argv[i + 1], 30);
					continue;
				}
				if (0 == strcmp(argv[i], "-artist")) {
					str_len = strlen(argv[i + 1]);
					
					if (str_len > 30) {			// If the artist is 31+ characters, it won't be written to the tag
						printf("\n************************************************\n");
						printf("Error: The artist must be 30 characters or less.\n");
						printf("       The entered artist was not saved.\n");
						printf("************************************************\n\n");
						continue;
					}
					
					strncpy(tag.artist, argv[i + 1], 30);
					continue;
				}
				if (0 == strcmp(argv[i], "-album")) {
					str_len = strlen(argv[i + 1]);
					
					if (str_len > 30) {			// If the album is 31+ characters, it won't be written to the tag
						printf("\n***********************************************\n");
						printf("Error: The album must be 30 characters or less.\n");
						printf("       The entered album was not saved.\n");
						printf("***********************************************\n\n");
						continue;
					}
					
					strncpy(tag.album, argv[i + 1], 30);
					continue;
				}
				if (0 == strcmp(argv[i], "-year")) {
					str_len = strlen(argv[i + 1]);
					
					if (str_len != 4) {			// If the year is not exactly 4 characters long, it won't be written
						printf("\n***********************************************\n");
						printf("Error: The year must be exactly 4 numbers long.\n");
						printf("       The entered year was not saved.\n");
						printf("***********************************************\n\n");
						continue;
					}
					
					strncpy(tag.year, argv[i + 1], 4);
					continue;
				}
				if (0 == strcmp(argv[i], "-comment")) {
					str_len = strlen(argv[i + 1]);
					
					if (str_len > 28) {			// If the comment is 29+ characters, it won't be written to the tag
						printf("\n*************************************************\n");
						printf("Error: The comment must be 28 characters or less.\n");
						printf("       The entered comment was not saved.\n");
						printf("*************************************************\n\n");
						continue;
					}
					
					strncpy(tag.comment, argv[i + 1], 28);
					continue;
				}
				if (0 == strcmp(argv[i], "-track")) {
					track_num = atoi(argv[i + 1]);
					
					if (track_num > 255) {		// If the track number exceeds 255 (largest unsigned byte value), it won't be written
						printf("\n**********************************************\n");
						printf("Error: The track number must be less than 256.\n");
						printf("       The entered track number was not saved.\n");
						printf("**********************************************\n\n");
						continue;
					}
					
					tag.track = track_num;
				}
			}
			
			print_tag(tag);				// Calls the function to display the tag information
			fwrite(&tag, 128, 1, music_file);		// Writes the tag structure to the file
			
			break;
	}
	
	return fclose(music_file);				// Closes the file stream and returns 0 to main if successful;
}

/*/---------------------------------------------------------\
 <                      Tag Printing                         >
  \---------------------------------------------------------/
   | The following loops allow the tag information to be   |
   | printed to the console in a formatted manner.  Loops  |
   | were used to print the strings as a contingency in    |
   | chance that strings are exactly 30 characters long.   |
   | In that situation, a null terminator is not present.  |
   | The loops then ensure that strings always print       |
   | correctly.  Information for the field sizes were      |
   | found on both the CS 0449 project page and on the ID3 |
   | standard's website at                                 |
   | http://id3.org/ID3v1#What_is_ID3v1.1.3F               |
   /_______________________________________________________\*/

void print_tag(struct id3tag tag) {
	int i = 0;			// Generic int variable for loops
	
	printf("Title:\t\t");
	for (i = 0; i < 30; i++) {
		printf("%c", tag.title[i]);
	}
	printf("\n");
	
	printf("Artist:\t\t");
	for (i = 0; i < 30; i++) {
		printf("%c", tag.artist[i]);
	}
	printf("\n");
	
	printf("Album:\t\t");
	for (i = 0; i < 30; i++) {
		printf("%c", tag.album[i]);
	}
	printf("\n");
	
	printf("Year:\t\t");
	for (i = 0; i < 4; i++) {
		printf("%c", tag.year[i]);
	}
	printf("\n");
	
	printf("Comment:\t");
	for (i = 0; i < 28; i++) {
		printf("%c", tag.comment[i]);
	}
	printf("\n");
	
	printf("Track #:\t%u\n\n", tag.track);		// The track number prints an integer, not a character
	
	return;
}