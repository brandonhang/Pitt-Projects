/*0=======================================================0
  |  Title:    mymalloc.h                                 |
  |  Author:   Brandon S. Hang                            |
  |  Version:  1.100                                      |
  |  Class:    CS 0449                                    |
  |            Project 3                                  |
  |            March 20, 2016                             |
  |                                                       |
  |  This program tests the custom memory allocation      |
  |  functions defined and implemented in "mymalloc.h"    |
  |  and "mymalloc.c".  It allocates and deallocates      |
  |  a number of strings whilst checking the current brk  |
  |  address with the linked list of memory blocks.       |
  |  This program was set up to use dynamic linking in a  |
  |  manner similar to the Shared Object Lab done in      |
  |  recitation.  This program is to be compiled as a     |
  |  32-bit program.                                      |
  |                                                       |
  |  Compliation and Execution:                           |
  |     gcc -fPIC -c -m32 mymalloc.c                      |
  |     ld -shared -soname mymalloc.so.1 -o               |
  |        mymalloc.so.1.0 -lc -melf_i386 mymalloc.o      |
  |     ln -s mymalloc.so.1.0 mymalloc.so                 |
  |     export LD_LIBRARY_PATH=.:$LD_LIBRARY_PATH         |
  |     gcc -rdynamic -o malloc_test malloc_test.c -ldl   |
  |        -m32 -g                                        |
  |     ./malloc_test                                     |
  0=======================================================0*/

#include <stdio.h>
#include <string.h>
#include <dlfcn.h>
#include <unistd.h>
#include "mymalloc.h"

int main() {
	char *str_1;				// Declares a number of string pointers
	char *str_2;
	char *str_3;
	char *str_4;
	char *str_5;
	
	void *handle;
	void *(*custom_malloc)(int);		// Defines method names from the shared object
	void (*custom_free)(void *);
	void (*print_ll)();
	char *error;
	
	handle = dlopen("mymalloc.so", RTLD_LAZY);
	if (!handle) {
		printf("%s\n", dlerror());
		return 1;
	}
	dlerror();
	
	custom_malloc = dlsym(handle, "my_nextfit_malloc");			// custom_malloc is the my_nextfit_malloc function
	if ((error = dlerror()) != NULL) {
		printf("$s\n", error);
		return 1;
	}
	
	custom_free = dlsym(handle, "my_free");					// custom_free is the my_free function
	if ((error = dlerror()) != NULL) {
		printf("$s\n", error);
		return 1;
	}
	
	print_ll = dlsym(handle, "print_list");					// print_ll is the print_list function
	if ((error = dlerror()) != NULL) {
		printf("$s\n", error);
		return 1;
	}
	
	printf("\nNode size: %#x\n", sizeof(struct node));			// Displays the node size
	printf("Starting brk: %#08x\n", sbrk(0));					// Shows the starting brk
	print_ll();											// Asserts that the linked list is empty
	
	printf("Allocating 7 bytes...\n");				// Allocates 7 bytes
	str_1 = custom_malloc(7 * sizeof(char));
	strcpy(str_1, "Subaru");
	printf("\tString 1: %s\n", str_1);
	printf("brk: %#08x\n", sbrk(0));				// Gets the current brk
	print_ll();										// Prints the linked list
	
	printf("Deallocating string 1...\n");			// Deallocates the first string
	custom_free(str_1);
	printf("brk: %#08x\n", sbrk(0));
	print_ll();
	
	printf("Allocating 7 bytes...\n");				// Allocates 7 bytes
	str_1 = custom_malloc(7 * sizeof(char));
	strcpy(str_1, "Hello ");
	printf("\tString 1: %s\n", str_1);
	printf("brk: %#08x\n", sbrk(0));
	
	printf("Allocating 7 bytes...\n");				// Allocates 7 bytes
	str_2 = custom_malloc(7 * sizeof(char));
	strcpy(str_2, "world!");
	printf("\tStrings 1 & 2: %s%s\n", str_1, str_2);
	printf("brk: %#08x\n", sbrk(0));
	
	printf("Deallocating string 1...\n");
	custom_free(str_1);
	printf("\tString 2: %s\n", str_2);
	printf("brk: %#08x\n", sbrk(0));			// The brk should not change
	print_ll();									// Shows a 2 node list with the nodes that are free and in use
	
	printf("Allocating 4 bytes...\n");				// Allocates 4 bytes
	str_1 = custom_malloc(4 * sizeof(char));
	strcpy(str_1, "Hi ");
	printf("brk: %#08x\n", sbrk(0));
	printf("\tStrings 1 & 2: %s%s\n", str_1, str_2);
	
	printf("Allocating 57 bytes...\n");				// Allocates 57 bytes
	str_3 = custom_malloc(57 * sizeof(char));
	strcpy(str_3, "2009 Subaru Impreza WRX Hatchback in dark gray metallic.");
	printf("\tString 3: %s\n", str_3);
	printf("brk: %#08x\n", sbrk(0));
	
	printf("Allocating 22 bytes...\n");				// Allocates 22 bytes
	str_4 = custom_malloc(22 * sizeof(char));
	strcpy(str_4, "Pagani be like VROOM!");
	printf("\tString 4: %s\n", str_4);
	printf("brk: %#08x\n", sbrk(0));				// Gets the current brk
	print_ll();										// Shows a 4 node list with all nodes filled
	
	printf("Deallocating string 3...\n");			// Deallocates string 3
	custom_free(str_3);
	print_ll();								// Asserts that the memory was freed properly
	
	printf("Allocating 10 bytes...\n");				// Allocates 10 bytes
	str_5 = custom_malloc(10 * sizeof(char));
	strcpy(str_5, "Piano man");
	printf("\tString 5: %s\n", str_5);
	printf("brk: %#08x\n", sbrk(0));				// Should show no change to brk
	print_ll();									// Should display proper dividing of an existing node for allocation
	
	printf("Deallocating string 4...\n");			// Deallocates string 4
	custom_free(str_4);
	printf("brk: %#08x\n", sbrk(0));
	print_ll();								// Should show proper coalescing of free nodes (3 node linked list)
	
	printf("Deallocating strings 1, 2, 5...\n");
	custom_free(str_1);
	custom_free(str_2);
	custom_free(str_5);
	printf("brk: %#08x\n", sbrk(0));			// The brk should be the same as the starting brk
	print_ll();								// The linked list should be empty
	
	printf("Allocating 53 bytes...\n");				// Allocates 53 bytes
	str_1 = custom_malloc(53 * sizeof(char));
	strcpy(str_1, "This is a test of a custom malloc and free function.");
	printf("\tString 1: %s\n", str_1);
	
	printf("Allocating 26 bytes...\n");				// Allocates 26 bytes
	str_2 = custom_malloc(26 * sizeof(char));
	strcpy(str_2, "CS0449 - Systems Software");
	
	printf("Allocating 16 bytes...\n");				// Allocates 16 bytes
	str_3 = custom_malloc(16 * sizeof(char));
	strcpy(str_3, "Brandon S. Hang");
	printf("brk: %#08x\n", sbrk(0));			// Shows the current brk
	print_ll();									// Prints a 3 node linked list with all nodes in use
	
	printf("Deallocating string 1...\n");			// Deallocates string 1
	custom_free(str_1);
	
	printf("Allocating 33 bytes...\n");				// Allocates 33 bytes
	str_4 = custom_malloc(33 * sizeof(char));
	strcpy(str_4, "Assignment 3 - A custom malloc()");
	
	printf("Allocating 4 bytes...\n");				// Allocates 4 bytes
	str_5 = custom_malloc(4 * sizeof(char));
	strcpy(str_5, "BSH");
	printf("brk: %#08x\n", sbrk(0));			// The brk should not change
	printf("\tString 3: %s\n\tString 2: %s\n\tString 4: %s\n\tString 5: %s\n", str_3, str_2, str_4, str_5);
	print_ll();							// Demonstrates proper allocation of existing node space (4 node linked list)
	
	printf("Deallocating strings 4 and 5...\n");
	custom_free(str_5);
	custom_free(str_4);
	printf("brk: %#08x\n", sbrk(0));			// The brk should not change
	print_ll();								// The free spaces should coalesce into a single free node
	
	printf("Deallocating string 2...\n");		// Deallocates string 2
	custom_free(str_2);
	printf("brk: %#08x\n", sbrk(0));			// The brk should not change
	print_ll();								// Free spaces should coalesce
	
	printf("Deallocating string 3...\n");			// Deallocates string 3
	custom_free(str_3);
	printf("brk: %#08x\n", sbrk(0));				// The brk should be the same as the starting brk
	print_ll();										// The linked list should be empty
	
	printf("Allocating 50 bytes...\n");				// Allocates 26 bytes
	str_1 = custom_malloc(50 * sizeof(char));
	strcpy(str_1, "Computer science at the University of Pittsburgh.");
	
	printf("Allocating 13 bytes...\n");				// Allocates 13 bytes
	str_2 = custom_malloc(13 * sizeof(char));
	strcpy(str_2, "Hello world!");
	
	printf("Allocating 11 bytes...\n");				// Allocates 11 bytes
	str_3 = custom_malloc(11 * sizeof(char));
	strcpy(str_3, "Bob Marley");
	printf("\tString 1: %s\n\tString 2: %s\n\tString 3: %s\n", str_1, str_2, str_3);
	printf("brk: %#08x\n", sbrk(0));				// Shows the current brk address
	print_ll();								// Shows the linked list of 3 nodes
	
	printf("Deallocating string 1...\n");		// Deallocates string 1
	custom_free(str_1);
	printf("Allocating 34 bytes...\n");				// Allocates 34 bytes
	str_4 = custom_malloc(34 * sizeof(char));
	strcpy(str_4, "It is time to go play basketball.");
	printf("\tString 4: %s\n", str_4);
	print_ll();							// Shows that the deallocated node split to form a node of size 0
	
	printf("Deallocating string 2...\n");			// Deallocates string 2
	custom_free(str_2);
	print_ll();							// Shows that the size 0 node coalesced with the deallocated node
	
	printf("Deallocating string 3...\n");			// Deallocates string 3
	custom_free(str_3);
	print_ll();						// Shows a linked list of just 1 node
	printf("brk: %#08x\n", sbrk(0));
	printf("addr: %#08x\n", (void *)str_2 - sizeof(struct node));		// Shows that brk is where str_2's node was formerly
	
	printf("Deallocating string 4...\n");			// Deallocates string 4
	custom_free(str_4);
	printf("brk: %#08x\n", sbrk(0));				// The brk should be the same as the starting brk
	print_ll();									// Shows that the linked list is empty
	
	return 0;
}
