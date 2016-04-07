/*0=======================================================0
  |  Title:    mymalloc.h                                 |
  |  Author:   Brandon S. Hang                            |
  |  Version:  1.200                                      |
  |  Class:    CS 0449                                    |
  |            Project 3                                  |
  |            March 20, 2016                             |
  |                                                       |
  |  This is the header file for a custom memory          |
  |  allocation function.  It contains prototypes for     |
  |  allocating and freeing memory, as well as one that   |
  |  prints out the current linked list of how memory     |
  |  has been allocated thus far.  Additionally, this     |
  |  header defines the struct "node" to be used for the  |
  |  linked list list and declares several global         |
  |  variables for navigating and keeping track of the    |
  |  linked list.                                         |
  0=======================================================0*/

void *my_nextfit_malloc(int size);				// Custom malloc prototype
void my_free(void *ptr);						// Custom free prototype
void print_list();								// Prototype for printing the linked list

struct node {						// Defines the node used in the (doubly) linked list
	int mem_size;					// Stores memory size in bytes
	char in_use;					// 1 is in use, 0 is free
	struct node *next;
	struct node *last;
};

extern struct node *list_head;				// Global variable for the linked list head
extern struct node *list_tail;				// Global variable for the linked list tail
extern struct node *last_node;				// Global variable that tracks the last node allocated for the next fit algorithm
extern struct node *search_node;			// Global variable used to traverse the linked list
