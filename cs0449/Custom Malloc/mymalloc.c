/*0=======================================================0
  |  Title:    mymalloc.h                                 |
  |  Author:   Brandon S. Hang                            |
  |  Version:  1.500                                      |
  |  Class:    CS 0449                                    |
  |            Project 3                                  |
  |            March 20, 2016                             |
  |                                                       |
  |  This source file contains the implementations for    |
  |  the custom memory allocation functions declared in   |
  |  "mymalloc.h".  The memory allocation function uses   |
  |  the "Next-Fit" algorithm for determining memory      |
  |  availability.                                        |
  0=======================================================0*/

#include <stdio.h>
#include <unistd.h>
#include "mymalloc.h"					// The custom memory allocation header

struct node *list_head = NULL;				// Initializes all global variables to NULL
struct node *list_tail = NULL;
struct node *last_node = NULL;
struct node *search_node = NULL;


/*/---------------------------------------------------------\
 <                Next-Fit Memory Allocation                 >
  \---------------------------------------------------------/*/

void *my_nextfit_malloc(int size) {				// Takes a parameter of the requested size in bytes
	
	if (list_head == NULL) {						// Creates a new linked list if none exists
		list_head = sbrk(sizeof(struct node));		// Returns the current brk as the address of the list list_head and sets brk to the address above the node
		list_head->mem_size = size;					// Stores the memory size requested
		list_head->in_use = 1;					// Sets the node as "in use"
		list_head->next = NULL;
		list_head->last = NULL;					// Sets the next and previous nodes to NULL pointers
		
		list_tail = list_head;					// Sets this node as the linked list tail
		last_node = list_head;				// Marks this node as the last node allocated for the Next-Fit algorithm
		search_node = last_node;
		
		return sbrk(size);					// Returns the address above the node and sets brk above the memory size requested
	}
	else {					// Otherwise, will search through the existing linked list
		do {
			if (search_node->mem_size >= size && 0 == search_node->in_use) {		// Conditional if the current node is a large enough size and not in use
				struct node *free_node;
				int free_size = search_node->mem_size - size;				// Determines the remaining free space after allocating the node
				
				if (free_size >= sizeof(struct node)) {				// If the remaining space at least the size of a node, splits the current node into 2
					struct node *after_free_node = search_node->next;				// Used to simplify linked list position adjustments
					
					free_node = (void *)search_node + sizeof(struct node) + size;		// Sets the free node to the address after the current node
					free_node->mem_size = free_size - sizeof(struct node);				// Sets the free node's size
					free_node->in_use = 0;
					search_node->mem_size = size;						// Sets the current node's size
					
					free_node->next = search_node->next;			// Adjusts the current and free node's position in the linked list
					free_node->last = search_node;
					search_node->next = free_node;
					after_free_node->last = free_node;
				}
				search_node->in_use = 1;
				
				last_node = search_node;					// Sets this node as the last node allocated for the Next-Fit algorithm
				return (void *)search_node + sizeof(struct node);		// Returns the address above the size of the current node
			}
			else {
				search_node = search_node->next;			// Otherwise, gets the next node in the linked list
				
				if (search_node == NULL) {
					search_node = list_head;				// Sets the current node as the head if the traversal results in a null pointer
				}
			}
		} while (search_node != last_node);					// Loops until it reaches the starting node of the search
		
		{											// Creates a new node if no suitable node was found
			struct node *new_node = sbrk(sizeof(struct node));				// Sets a new node at the current brk address and increases it
			struct node *old_list_tail = list_tail;						// The old linked list tail
			
			new_node->mem_size = size;
			new_node->in_use = 1;
			new_node->next = NULL;
			new_node->last = old_list_tail;					// Links the new node's previous node as the old linked list tail
			
			old_list_tail->next = new_node;
			list_tail = new_node;						// Sets the new node as the linked list tail
			last_node = list_tail;						// Marks this node as the last node allocated for the Next-Fit algorithm
			search_node = last_node;
			
			return sbrk(size);					// Returns the address above the node and increases brk
		}
	}
}

/*/---------------------------------------------------------\
 <                    Memory Deallocation                    >
  \---------------------------------------------------------/*/

void my_free(void *ptr) {				// Takes the pointer of allocated memory as a parameter
	
	struct node *node_to_free = ptr - sizeof(struct node);			// Gets the address of the current node from the pointer
	struct node *node_before_ptr = node_to_free->last;				// Gets the node before the current node
	struct node *node_after_ptr = node_to_free->next;				// Gets the node after the current node
	
	node_to_free->in_use = 0;						// Marks the node as free
	
	if (node_after_ptr != NULL) {					// Coalesces free space if the node after the current one is not null and is free
		if (0 == node_after_ptr->in_use) {
			struct node *list_node = node_after_ptr->next;				// Gets the node 2 nodes away from the current node
			int size = sizeof(struct node) + node_after_ptr->mem_size + node_to_free->mem_size;			// Gets the combined memory of the current and next node
			
			node_to_free->mem_size = size;				// Sets the current node to have the combined memory size
			node_to_free->next = list_node;				// Removes linked list pointers to the node after the current one
			list_node->last = node_to_free;
			
			if (last_node == node_after_ptr) {			// If the last node for the Next-Fit algorithm points to the node being removed, sets it to the current node
				last_node = node_to_free;
				search_node = last_node;
			}
		}
	}
	
	if (node_before_ptr != NULL) {				// Coalesces free space if the node before the current one is not null and is free
		if (0 == node_before_ptr->in_use) {
			int size = sizeof(struct node) + node_before_ptr->mem_size + node_to_free->mem_size;		// Gets the combined memory of the current and previous node
			
			node_before_ptr->mem_size = size;			// Sets the previous node to have the combined memory size
			node_before_ptr->next = node_after_ptr;		// Removes linked list pointers to the current node
			
			if (node_after_ptr != NULL) {				// If the node after the current one is not null, links the previous and next nodes in the list
				node_after_ptr->last = node_before_ptr;
			}
			else {					// Otherwise, sets the previous node as the linked list tail
				list_tail = node_before_ptr;
			}
			
			if (last_node == node_to_free) {			// If the last node for the Next-Fit algorithm points to the node being removed, sets it to the previous node
				last_node = node_before_ptr;
				search_node = last_node;
			}
			node_to_free = node_before_ptr;			// Highlights the previous node as the current node (semantic)
		}
	}
	
	if (node_to_free == list_tail) {				// If the current node is the list tail, shrinks the heap size by decreasing brk
		sbrk((void *)node_to_free - sbrk(0));			// Decreases brk by getting the node's address and subtracting it by brk's current position
		
		if (node_to_free == list_head) {			// If the current node is also the head, completely resets the linked list
			list_head = NULL;
			list_tail = NULL;
			last_node = NULL;
			search_node = NULL;
		}
		else {									// Otherwise, reassigns the tail to the previous node
			struct node *list_node = node_to_free->last;
			list_tail = list_node;
			list_node->next = NULL;
			
			if (node_to_free == last_node) {		// If the last node for the Next-Fit algorithm points to the node being removed, sets it to the previous node
				last_node = list_node;
				search_node = last_node;
			}
		}
	}
}


/*/---------------------------------------------------------\
 <                    Linked List Printing                   >
  \---------------------------------------------------------/
   | This function prints out the linked list in a semi-   |
   | graphical manner.  This is not an essential function  |
   | for the set of memory allocation functions and is     |
   | only used as a diagnostic tool in determining if      |
   | memory was allocated correctly.                       |
   /_______________________________________________________\*/

void print_list() {
	
	if (list_head == NULL) {					// Prints a message if the linked list is empty
		printf("The linked list is empty\n");
	}
	else {									// Otherwise, iterates through the linked list whilst printing node information
		struct node *list_node = list_head;
		int number = 0;
		printf("~Allocated Memory~\n");
		
		while (list_node != NULL) {
			printf("        |\n");
			printf("        |\n");
			printf("        V\n");
			printf("================\n");
			printf("| Node #: %d\n", number);
			printf("| Size: %d bytes\n", list_node->mem_size);
			printf("| In Use: %d\n", list_node->in_use);
			printf("================\n");
			
			number++;
			list_node = list_node->next;
		}
		printf("\n");
	}
}
