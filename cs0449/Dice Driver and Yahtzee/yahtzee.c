/*0=======================================================0
  |  Title:    Yahtzee Simulator                          |
  |  Author:   Brandon S. Hang                            |
  |  Version:  1.300                                      |
  |  Class:    CS 0449                                    |
  |            Project 4                                  |
  |            April 10, 2016                             |
  |                                                       |
  |  This program simulates the dice game "Yahtzee" and   |
  |  utilizes the module created from dice_driver.c.  It  |
  |  is a single-player game that awards the 35 bonus     |
  |  points for achieving at least 63 points in the       |
  |  upper section.  However, it does not award any       |
  |  Yahtzee bonuses or follow any joker rules.           |
  |                                                       |
  |  When compiling this program, please ensure that it   |
  |  is compiled in 32-bit mode and statically-linked.    |
  |                                                       |
  |  Compilation: gcc -o yahtzee yahtzee.c -m32 -static   |
  |  Execution:   ./yahtzee                               |
  0=======================================================0*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>

/*/---------------------------------------------------------\
 <                    Function Prototypes                    >
  \---------------------------------------------------------/*/

void roll_dice(char *rolled_dice, int num);						// Function prototype that rolls a user-defined number of die
int print_score(int *scorecard);									// Function prototype that prints out the player's scorecard
char match_type(int selection, char *dice, int *scorecard);				// Function prototype that scores upper section categories
char check_multi(int number, char *dice, int *scorecard);			// Function prototype that scores three and four-of-a-kinds
char check_full_house(char *dice, int *scorecard);					// Function prototype that scores a full house
char check_sm_straight(char *dice, int *scorecard);					// Function prototype that scores small straights
char check_lg_straight(char *dice, int *scorecard);					// Function prototype that scores large straights
char yahtzee(char *dice, int *scorecard);						// Function prototype that scores a Yahtzee
int qsort_helper(const void *a, const void *b);						// Function prototype of the qsort comparison function
void game_start(void);										// Function prototype that prints the introduction screen
void game_over(void);										// Function prototype that prints the game over screen


int main() {
	
	/*/---------------------------------------------------------\
	 <                  Variable Declarations                    >
	  \---------------------------------------------------------/*/
	
	char dice_in_hand[5];					// Holds the die in the player's possession
	char input[50];							// Gets input from the player
	char *reroll;							// A dynamically allocated array that holds rerolled die
	char *token;							// String token after tokenizing player input
	char next_round = 0;					// A "boolean" that determines if the next round should proceed
	char valid = 0;							// A "boolean" that determines if a player's input was valid
	char round = 0;							// Keeps track of the number of rounds
	int scorecard[13];						// Holds the player's score; "uninitialized" categories are set to -1
	int num_dice_rerolled = 0;				// Tracks how many die the player wants to reroll
	int dice_arr_indices[5];				// Stores the indices of rerolled die so they can be replaced in the player's hand
	int selection = 0;						// Holds the user's input for single number-based options
	int total_score = 0;					// Tracks the player's total score
	int i = 0;								// General variable for loops
	
	
	for (i = 0; i < 13; i++) {				// Initializes the scorecard to all -1 (because 0 is a valid score)
		scorecard[i] = -1;
	}
	
	game_start();				// Displays the game introduction screen
	
	for (round = 0; round < 13; round++) {							// Loops for 13 rounds
	
		/*/---------------------------------------------------------\
		 <                      Casting the Die                      >
		  \---------------------------------------------------------/*/
	
		printf("~~~~~~~~~~ Round %d ~~~~~~~~~~\n", round + 1);
		
		roll_dice(dice_in_hand, 5);					// Gets an initial set of 5 dice rolls
		printf("First roll\n");
		printf("Your roll:  %d   %d   %d   %d   %d\n", dice_in_hand[0], dice_in_hand[1],		// Prints out the player's first roll
				dice_in_hand[2], dice_in_hand[3], dice_in_hand[4]);
		printf("           (1) (2) (3) (4) (5)\n");
		printf("Which die numbers do you want to reroll?\n");
		printf("Enter a space between each number.  Enter '0' to pass.\n");			// Die numbers are separated by a single space
		
		fgets(input, 50, stdin);				// Gets the list of numbers from the player as a single string
		token = strtok(input, " ");				// Tokenizes the string based on spaces
		num_dice_rerolled = 0;					// Resets the number of die to reroll to zero
		
		while (token != NULL) {					// Loops while another token exists
			int index = atoi(token);				// Converts the string token into an int
			token = strtok(NULL, " ");				// Fetches the next token of the string
			
			if (num_dice_rerolled >= 5) {						// Exits the loop if 5 die have already been requested
				printf("You entered too many numbers!  Continuing on...\n");
				break;
			}
			if (0 == index) {					// Exits the loop if 0 was entered
				break;
			}
			if (index < 0 || index > 5) {			// Continues through the loop if an out of bounds index is entered
				continue;
			}
			
			dice_arr_indices[num_dice_rerolled] = index - 1;		// Stores the index value of the die in a separate array for retrieval
			num_dice_rerolled++;								// Increments the number of die to reroll
		}
		
		if (num_dice_rerolled > 0) {								// Proceeds to the second roll if the number of rerolled die is greater than 0
			reroll = malloc(num_dice_rerolled * sizeof(char));		// Allocates memory for the number of rerolled die
			roll_dice(reroll, num_dice_rerolled);					// Gets a new set of rolled die
			
			for (i = 0; i < num_dice_rerolled; i++) {					// Reinserts die values back into their appropriate index in the player's hand
				dice_in_hand[dice_arr_indices[i]] = reroll[i];
			}
			
			free(reroll);					// Frees the dynamically allocated memory
			
			printf("\nSecond roll\n");
			printf("Your roll:  %d   %d   %d   %d   %d\n", dice_in_hand[0], dice_in_hand[1],	// The second roll functions the same as the first
					dice_in_hand[2], dice_in_hand[3], dice_in_hand[4]);
			printf("           (1) (2) (3) (4) (5)\n");
			printf("Which die numbers do you want to reroll?\n");
			printf("Enter your numbers separated by a space.  Enter '0' to pass.\n");
			
			fgets(input, 50, stdin);				// Gets player input
			token = strtok(input, " ");
			num_dice_rerolled = 0;
			
			while (token != NULL) {					// Loops while a token exists
				int index = atoi(token);
				token = strtok(NULL, " ");
				
				if (num_dice_rerolled >= 5) {
					printf("You entered too many numbers!  Continuing on...\n");
					break;
				}
				if (0 == index) {
					break;
				}
				if (index < 0 || index > 5) {
					continue;
				}
				
				dice_arr_indices[num_dice_rerolled] = index - 1;			// Saves die indices
				num_dice_rerolled++;
			}
			
			if (num_dice_rerolled > 0) {
				reroll = malloc(num_dice_rerolled * sizeof(char));				// Gets a new set of rolled die
				roll_dice(reroll, num_dice_rerolled);
				
				for (i = 0; i < num_dice_rerolled; i++) {					// Places the rerolled die back into the player's hand
					dice_in_hand[dice_arr_indices[i]] = reroll[i];
				}
				
				free(reroll);
				
				printf("\nThird roll\n");
				printf("Your roll:  %d   %d   %d   %d   %d\n", dice_in_hand[0], dice_in_hand[1],		// There is no option for reroll after 3 rolls
						dice_in_hand[2], dice_in_hand[3], dice_in_hand[4]);
			}
		}
		
		/*/---------------------------------------------------------\
		 <                      Scoring the Die                      >
		  \---------------------------------------------------------/*/
		
		do {
			print_score(scorecard);				// Prints the player's current scorecard to aid in making decisions
			next_round = 0;						// Sets the next round "boolean" to false
			
			do {
				valid = 0;						// Sets the valid "boolean" to false
				
				printf("Which section do you want to place your die?\n");
				printf("Enter the number of the section.\n");
				printf("1.) Upper Section\n");
				printf("2.) Lower Section\n");
				
				fgets(input, 50, stdin);			// Gets user input and converts it to an int
				selection = atoi(input);
				
				if (selection < 1 || selection > 2) {				// This section loops if the input is neither 1 nor 2
					printf("You entered an invalid section.  Please try again.\n");
				}
				else {
					valid = 1;					// Otherwise, sets the valid "boolean" to true
				}
			} while (!valid);				// Loops while the input is invalid
			
			if (1 == selection) {			// Conditional to select upper section cagetories
				do {
					valid = 0;					// Sets the valid "boolean" back to false
					
					printf("Which category do you want to score your die?\n");
					printf("Enter the number of the category.\n");
					printf("1.) Aces\n");
					printf("2.) Twos\n");
					printf("3.) Threes\n");
					printf("4.) Fours\n");
					printf("5.) Fives\n");
					printf("6.) Sixes\n");
					
					fgets(input, 50, stdin);			// Gets user input and converts it to an int
					selection = atoi(input);
					
					if (selection < 1 || selection > 6) {				// This section loops if the input is not between 1 and 6
						printf("You entered an invalid category.  Please try again.\n");
					}
					else {
						valid = 1;				// Otherwise, sets the valid "boolean" to true
					}
				} while (!valid);			// Loops while the input is invalid
				
				next_round = match_type(selection, dice_in_hand, scorecard);			// Sets the score of an upper section category based on player input
			}
			else {						// Otherwise, selects lower section categories
				do {
					valid = 0;				// Sets the valid "boolean" back to false
					
					printf("Which category do you want to score your die?\n");
					printf("Enter the number of the category.\n");
					printf("1.) Three-Of-A-Kind\n");
					printf("2.) Four-Of-A-Kind\n");
					printf("3.) Full House\n");
					printf("4.) Small Straight\n");
					printf("5.) Large Straight\n");
					printf("6.) Yahtzee\n");
					printf("7.) Chance\n");
					
					fgets(input, 50, stdin);				// Gets user input and converts it to an int
					selection = atoi(input);
					
					if (selection < 1 || selection > 7) {				// This section loops if the input is not between 1 and 7
						printf("You entered an invalid category.  Please try again.\n");
					}
					else {
						valid = 1;				// Otherwise, sets the valid "boolean" to true
					}
				} while (!valid);			// Loops while the input is invalid
				
				switch (selection) {		// Scores the different categories based on the player's input
					case 1:
						next_round = check_multi(3, dice_in_hand, scorecard);			// Calls the function that scores three/four-of-a-kinds
						break;
					case 2:
						next_round = check_multi(4, dice_in_hand, scorecard);			// Calls the function that scores three/four-of-a-kinds
						break;
					case 3:
						next_round = check_full_house(dice_in_hand, scorecard);			// Calls the function that scores a full house
						break;
					case 4:
						next_round = check_sm_straight(dice_in_hand, scorecard);
						//next_round = check_straight(4, dice_in_hand, scorecard);		// Calls the function that scores straights
						break;
					case 5:
						next_round = check_lg_straight(dice_in_hand, scorecard);
						//next_round = check_straight(5, dice_in_hand, scorecard);		// Calls the function that scores straights
						break;
					case 6:
						next_round = yahtzee(dice_in_hand, scorecard);				// Calls the function that scores a Yahtzee
						break;
					default:						// Defaults to scoring the chance category
						if (scorecard[12] < 0) {			// Only scores the category if it does not already have a score
							scorecard[12] = 0;				// Resets the points to 0 (as it is initialized to -1)
							
							for (i = 0; i < 5; i++) {					// Loop that adds all die values as points
								scorecard[12] += (int)dice_in_hand[i];
							}
							
							printf("\nLeaving it all up to chance, eh?\n");
							next_round = 1;					// Sets the next round "boolean" to true
						}
						break;
				}
			}
			
			if (!next_round) {
				printf("The category you've chosen has already been taken!\n");
				printf("Let's start over and try again.\n");
				printf("Your roll:  %d   %d   %d   %d   %d\n", dice_in_hand[0], dice_in_hand[1],		// Redisplays the player's hand
						dice_in_hand[2], dice_in_hand[3], dice_in_hand[4]);
			}
		} while (!next_round);				// Restarts the entire scoring process if the next round "boolean" is false
		
		total_score = print_score(scorecard);			// Gets the total score from the player (only used after all rounds are completed)
	}
	
	printf("Your final score is %d!\n", total_score);
	game_over();				// Displays the game over screen
	return 0;
}


/*>>>>>>>>>>>>>>>>>>>>>> Die Rerolling Function <<<<<<<<<<<<<<<<<<<<<<
  | This function rerolls die using the /dev/dice module created     |
  | from dice_driver.c.                                              |
  |                                                                  |
  | @param rolled_dice -> A char array pointer to hold rerolled die  |
  | @param num -> The number of bytes to read from /dev/dice         |
  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
void roll_dice(char *rolled_dice, int num) {
	
	int dev_dice;			// The file descriptor for /dev/dice

	dev_dice = open("/dev/dice", O_RDONLY);			// Opens the file as read-only
	read(dev_dice, rolled_dice, num);				// Reads a number of bytes from /dev/dice and stores it in the array
	close(dev_dice);								// Closes the file
}


/*>>>>>>>>>>>>>>>>>>>>>>>> Scorecard Printing <<<<<<<<<<<<<<<<<<<<<<<<
  | This function prints out the player's score card in a nicely     |
  | formatted manner.  While it looks like a mess, this is because   |
  | lines of strings had to be broken up in order to use conditional |
  | statements.  The conditionals decide whether to print a number   |
  | or print whitespace instead.                                     |
  |                                                                  |
  | @param scorecard -> The player's scorecard                       |
  | @return -> The player's total score as an int                    |
  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
int print_score(int *scorecard) {
    
	int total_score = 0;								// Stores the player's total score
	int upper_score = scorecard[0] + scorecard[1] + scorecard[2]
			+ scorecard[3] + scorecard[4] + scorecard[5];			// Calculates the player's upper section score
	int i = 0;					// Int for loops
	
	for (i = 0; i < 13; i++) {		// Calculates the player's total score
		if (scorecard[i] > 0) {
			total_score += scorecard[i];
		}
	}
	
	printf("\n0============================Scorecard=====0\n");
	printf("| Upper Section                            |\n");
	printf("|   Aces:   ");
	
	if (scorecard[0] >= 0) {
		printf("%2d    Fours: ", scorecard[0]);			// Prints the aces score
	}
	else {
		printf("      Fours: ");
	}
	
	if (scorecard[3] >= 0) {
		printf("%2d     BONUS: ", scorecard[3]);		// Prints the fours score
	}
	else {
		printf("       BONUS: ");
	}
	
	if (upper_score >= 63) {					// Prints out the bonus points (if applicable)
		printf("35  |\n");
	}
	else {
		printf(" 0  |\n");
	}
	
	printf("|   Twos:   ");
	
	if (scorecard[1] >= 0) {
		printf("%2d    Fives: ", scorecard[1]);			// Prints out the twos score
	}
	else {
		printf("      Fives: ");
	}
	
	if (scorecard[4] >= 0) {
		printf("%2d                |\n", scorecard[4]);			// Prints out the fives score
	}
	else {
		printf("                  |\n");
	}
	
	printf("|   Threes: ");
	
	if (scorecard[2] >= 0) {
		printf("%2d    Sixes: ", scorecard[2]);			// Prints out the threes score
	}
	else {
		printf("      Sixes: ");
	}
	
	if (scorecard[5] >= 0) {
		printf("%2d                |\n", scorecard[5]);			// Prints out the sixes score
	}
	else {
		printf("                  |\n");
	}
	
	printf("|                                          |\n");
	printf("| Lower Section                            |\n");
	printf("|   Three-Of-A-Kind: ");
	
	if (scorecard[6] >= 0) {
		printf("%2d    Full House: ", scorecard[6]);			// Prints out the three-of-a-kind score
	}
	else {
		printf("      Full House: ");
	}
	
	if (scorecard[8] >= 0) {
		printf("%2d  |\n", scorecard[8]);				// Prints out the full house score
	}
	else {
		printf("    |\n");
	}
	
	printf("|   Four-Of-A-Kind:  ");
	
	if (scorecard[7] >= 0) {
		printf("%2d    Yahtzee:    ", scorecard[7]);			// Prints out the four-of-a-kind score
	}
	else {
		printf("      Yahtzee:    ");
	}
	
	if (scorecard[11] >= 0) {
		printf("%2d  |\n", scorecard[11]);				// Prints out the Yahtzee score
	}
	else {
		printf("    |\n");
	}
	
	printf("|   Small Straight:  ");
	
	if (scorecard[9] >= 0) {
		printf("%2d    Chance:     ", scorecard[9]);			// Prints out the small straight score
	}
	else {
		printf("      Chance:     ");
	}
	
	if (scorecard[12] >= 0) {
		printf("%2d  |\n", scorecard[12]);				// Prints out the chance score
	}
	else {
		printf("    |\n");
	}
	
	printf("|   Large Straight:  ");
	
	if (scorecard[10] >= 0) {
		printf("%2d                    |\n", scorecard[10]);			// Prints out the large straight score
	}
	else {
		printf("                      |\n");
	}
	
	printf("|                                          |\n");
	printf("|                              TOTAL: ");
	
	if (upper_score >= 63) {
		total_score += 35;
		printf("%3d  |\n", total_score);				// If the bonus conditions are met, prints out the total score with the bonus
	}
	else {
		printf("%3d  |\n", total_score);				// Otherwise, the bonus score is not added on
	}
	
	printf("0==========================================0\n");
	
	return total_score;
}


/*>>>>>>>>>>>>>>>>>>>>>> Upper Section Scoring <<<<<<<<<<<<<<<<<<<<<<<
  | This function scores the upper section categories.  The specific |
  | category that is scored is dependent on the selection parameter. |
  | Die values that are equal to that parameter are simply added to  |
  | the total number of category points.                             |
  |                                                                  |
  | @param selection -> The selected die number to match/score       |
  | @param dice -> The die in the player's hand                      |
  | @param scorecard -> The player's scorecard                       |
  | @return -> A char denoting whether to proceed to the next round  |
  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
char match_type(int selection, char *dice, int *scorecard) {
	
	int points = 0;			// Stores the points for the category
	int i = 0;				// An int for loops
	
	if (scorecard[selection - 1] >= 0) {		// If the category score is at least 0, returns as a failed score attempt
		return 0;
	}
	
	for (i = 0; i < 5; i++) {					// Finds all die matches and adds them to the category points total
		if (selection == (int)dice[i]) {
			points += selection;
		}
	}
	
	if (points > 0) {
		printf("\nLooks like you have some ");			// Prints a success message if the player scored points
		
		switch (selection) {
			case 1:
				printf("aces.\n");
				break;
			case 2:
				printf("twos.\n");
				break;
			case 3:
				printf("threes.\n");
				break;
			case 4:
				printf("fours.\n");
				break;
			case 5:
				printf("fives.\n");
				break;
			default:
				printf("sixes.\n");
				break;
		}
	}
	else {
		printf("\nYou got nothin'!\n");
	}
	
	scorecard[selection - 1] = points;			// Adds the points to the player's scorecard
	return 1;				// Returns successful
}


/*>>>>>>>>>>>>>>>>>>> Three/Four-Of-A-Kind Scoring <<<<<<<<<<<<<<<<<<<
  | This function scores three and four-of-a-kinds.  A tally is used |
  | to mark how many of a particular die's number has been seen as   |
  | it loops through the player's hand.  This function also has an   |
  | early exit implementation: Three-of-a-kinds only loop 3 times    |
  | while four-of-a-kinds loop only twice.                           |
  |                                                                  |
  | @param number -> The number of die to match (3 or 4)             |
  | @param dice -> The die in the player's hand                      |
  | @param scorecard -> The player's scorecard                       |
  | @return -> A char denoting whether to proceed to the next round  |
  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
char check_multi(int number, char *dice, int *scorecard) {// number pertains to how many of a number to match
	
	int index = 0;			// An int for the scorecard index
	int points = 0;			// Stores the category points
	int tally = 0;			// Tracks how many of a particular die's number has been seen
	int i = 0;				// Ints for loops
	int j = 0;
	
	if (3 == number) {			// Sets the index to 6 if the number is 3
		index = 6;
	}
	else {
		index = 7;			// Otherwise, sets the number as 4
	}
	
	if (scorecard[index] >= 0) {		// Returns as a failed attempt to score if the category is already used
		return 0;
	}
	
	for (i = 0; i < 6 - number; i++) {			// Nested loop to find equal die numbers
		for (j = 0; j < 5; j++) {
			if (dice[i] == dice[j]) {			// Increments the tally if die are equal
				tally++;
			}
		}
		
		if (tally >= number) {					// If the tally is at least 3 or 4, adds all die to the points total
			for (j = 0; j < 5; j++) {
				points += (int)dice[j];
			}
			
			printf("\nYou have at least a ");
			
			if (3 == number) {						// Prints a success message
				printf("three-of-a-kind!\n");
			}
			else {
				printf("four-of-a-kind!\n");
			}
			
			scorecard[index] = points;			// Stores the points in the scorecard
			return 1;				// Returns successful
		}
		
		tally = 0;			// Resets the tally for the next iteration
	}
	
	printf("\nYou got nothin'!\n");
	scorecard[index] = 0;		// Otherwise, stores 0 in the scorecard
	return 1;			// Returns successful
}


/*>>>>>>>>>>>>>>>>>>>>>>>> Full House Scoring <<<<<<<<<<<<<<<<<<<<<<<<
  | This function scores full houses.  It first sorts the die in the |
  | player's hand and then checks for matches of the first and last  |
  | die in the hand.  Because they are sorted, if the indiviaul      |
  | tallies are not some combination of 2 and 3, then there exists a |
  | die of a third number and a full house does not exist.           |
  |                                                                  |
  | @param dice -> The die in the player's hand                      |
  | @param scorecard -> The player's scorecard                       |
  | @return -> A char denoting whether to proceed to the next round  |
  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
char check_full_house(char *dice, int *scorecard) {
	
	int points = 0;				// Stores the category points
	int i = 0;					// Ints for loops
	int j = 0;
	int tally_1 = 0;			// Tallies for the first and second die numbers
	int tally_2 = 0;
	
	if (scorecard[8] >= 0) {		// Returns as a failed attempt if the category has already been used
		return 0;
	}
	
	qsort(dice, 5, sizeof(char), qsort_helper);			// Sorts the die
	
	for (i = 0, j = 4; i < 5, j >= 0; i++, j--) {			// Loops forwards and backwards simultaneously
		if (dice[0] == dice[i]) {
			tally_1++;
		}
		
		if (dice[4] == dice[j]) {
			tally_2++;
		}
	}
	
	if ((tally_1 == 2 && tally_2 == 3) || (tally_1 == 3 && tally_2 == 2)) {		// Conditional if the tallies are 2 and 3
		points = 25;				// Sets the points to 25
		printf("\nWow!  You have a full house!\n");
	}
	else {
		printf("\nYou got nothin'!\n");
	}
	
	scorecard[8] = points;			// Stores the points in the scorecard
	return 1;				// Returns successful
}


/*>>>>>>>>>>>>>>>>>>>>>> Small Straight Scoring <<<<<<<<<<<<<<<<<<<<<<
  | This function scores small straights.  This function first sorts |
  | the die and then checks that each sequential die is greater than |
  | the previous die by exactly 1.  It also detects if a repeated    |
  | pair of die have been encountered or not as a small straight is  |
  | afforded 1 pair of repeated numbers.                             |
  |                                                                  |
  | @param dice -> The die in the player's hand                      |
  | @param scorecard -> The player's scorecard                       |
  | @return -> A char denoting whether to proceed to the next round  |
  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
char check_sm_straight(char *dice, int *scorecard) {
	
	int points = 0;				// The category points
	int i = 0;					// Ints for loops
	int j = 0;
	char straight = 1;			// A "boolean" denoting if the die are arranged in a straight
	char repeat = 0;			// A "boolean" denoting if a repeated die has occurred
	
	if (scorecard[9] >= 0) {			// Returns as a failed scoring attempt if the category has already been used
		return 0;
	}
	
	qsort(dice, 5, sizeof(char), qsort_helper);			// Sorts the die
	
	for (i = 0; i < 2; i++) {				// Iterates through the player's hand twice
		straight = 1;				// Resets 
		repeat = 0;
		
		for (j = 0; j < 3; j++) {			// Sequentially loops through the die to check that the straight conditions hold
			if ((dice[j] - dice[j + 1]) != -1) {			// Conditional if sequential die are not increasing by exactly 1
				if (dice[j] == dice[j + 1] && !repeat) {		// Conditional if the compared die are equal and a repeat has not occurred yet
					repeat = 1;			// Sets the repeat "boolean" to true
					continue;				// A small straight is allowed 1 pair of repeated die
				}
				else {					// Otherwise, the straight conditions have been broken
					straight = 0;		// Sets the straight "boolean" to false
					break;			// Terminates the current iteration
				}
			}
		}
	}
	
	if (straight) {				// Conditional if the die were arranged in a small straight
		points = 30;				// Sets the points to 30
		printf("\nNice!  You have a small straight!\n");
	}
	else {
		printf("\nYou got nothin'!\n");
	}
	
	scorecard[9] = points;		// Saves the points in the player's scorecard
	return 1;				// Returns successful
}


/*>>>>>>>>>>>>>>>>>>>>>> Large Straight Scoring <<<<<<<<<<<<<<<<<<<<<<
  | This function scores large straights.  This function first sorts |
  | the die and then checks that each sequential die is greater than |
  | the previous die by exactly 1.  The algorithm is different       |
  | enough from that of small straights that it necessitated two     |
  | different functions.                                             |
  |                                                                  |
  | @param dice -> The die in the player's hand                      |
  | @param scorecard -> The player's scorecard                       |
  | @return -> A char denoting whether to proceed to the next round  |
  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
char check_lg_straight(char *dice, int *scorecard) {
	
	int points = 0;				// The category points
	int i = 0;					// An int for a loop
	char straight = 1;			// A "boolean" denoting if the die are arranged in a large straight
	
	if (scorecard[10] >= 0) {			// Returns as a failed scoring attempt if the category has already been used
		return 0;
	}
	
	qsort(dice, 5, sizeof(char), qsort_helper);			// Sorts the die
	
	for (i = 0; i < 4; i++) {			// Sequentially loops through the die to check that the straight conditions hold
		if ((dice[i] - dice[i + 1]) != -1) {
			straight = 0;			// If the conditions are broken, the "boolean" is set to false and the iteration is terminated
			break;
		}
	}
	
	if (straight) {			// Conditional if the die were arranged in a large straight
		points = 40;			// Sets the points to 40
		printf("\nUnbelievable!  You have a large straight!\n");
	}
	else {
		printf("\nYou got nothin'!\n");
	}
	
	scorecard[10] = points;			// Saves the points in the player's scorecard
	return 1;				// Returns successful
}


/*>>>>>>>>>>>>>>>>>>>>>>>>> Yahtzee Scoring <<<<<<<<<<<<<<<<<<<<<<<<<<
  | This function scores a Yahtzee.  It simply checks that each die  |
  | in the player's hand is the same as the next die in the hand.   |
  |                                                                  |
  | @param dice -> The die in the player's hand                      |
  | @param scorecard -> The player's scorecard                       |
  | @return -> A char denoting whether to proceed to the next round  |
  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
char yahtzee(char *dice, int *scorecard) {
	
	int i = 0;			// Int for a loop
	
	if (scorecard[11] >= 0) {		// Returns as a failed scoring attempt if the category has already been used
		return 0;
	}
	
	for (i = 0; i < 4; i++) {			// Loops through the die to check that they are all the same
		if (dice[i] != dice[i + 1]) {			// Conditional if die are unequal
			printf("\nYou got nothin'!\n");
			scorecard[11] = 0;				// The score is set to 0
			return 1;				// Returns successful
		}
	}
	
	printf("\nYAHTZEE!!!\n");
	scorecard[11] = 50;				// Stores 50 in the scorecard
	return 1;				// Returns successful
}


/*>>>>>>>>>>>>>>>>>>>> Qsort Comparison Function <<<<<<<<<<<<<<<<<<<<<
  | This function serves as the comparison function necessary for    |
  | qsort to work properly when sorting char datatypes.              |
  |                                                                  |
  | @param a -> Pointer to the first object that cannot be modified  |
  | @param b -> Pointer to the second object that cannot be modified |
  | @return -> An integer representing the comparison result         |
  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
int qsort_helper(const void *a, const void *b) {
	
	return (*(char *)a - *(char *)b);			// Returns -1, 0, or 1
}


/*>>>>>>>>>>>>>>>>>>>>>>> Yahtzee Intro Screen <<<<<<<<<<<<<<<<<<<<<<<
  | This function simply prints out ASCII art of the Yahtzee logo.   |
  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
void game_start(void) {
	
	printf("\n    Brandon S. Hang\n");
	printf("          presents:                                          `odNMNh/\n");
	printf("                                   .-:          `  :sdmdy/  /NMNysmMMo\n");
	printf("                       yhmo       yMMN `-/+oyhmNh.dMMdhmMMy:MMM++sdMMN\n");
	printf("          :/os-        MMMs   `  :dMMMNsMMMNmMMN-mMMo:/sMMMNMMMMNdyso/\n");
	printf("-:+os`   yMMMo      `.`MMMmdNMMN+yMMMN+.-` .dMN-+MMMMMMmdysmMMm   /ydo\n");
	printf("-mMMMN: yMMM+ :oyhmNMM-MMMmysNMMN yMMN    +MMy` oMMM:` .osy:MMMdsdMMy`\n");
	printf(" `yMMMMdMMM++NMMdyhMMM-MMMs  sMMN yMMN  `hMN/   .NMMd+sNMN- .smNNds-\n");
	printf("   /NMMMMM/sMMm.  /MMM-MMMs  sMMN yMMN `mMMysydmm.yNMMMdo`\n");
	printf("    .dMMM+.MMM+   /MMM-MMMs  sMMN yMMN yMMMMNdhs+   ``\n");
	printf("     +MMM//MMM+   +MMM-MMMs  sMMN smds +/-.\n");
	printf("     +MMM/.NMMNo+yMMMM-MMNo  :+:-\n");
	printf("     +MMM/ -dMMMMh+so/`.`\n");
	printf("     +Nmh-   `-.                          A Milton Bradley game\n");
	printf("                                                      written in C!\n\n");
}


/*>>>>>>>>>>>>>>>>>>>> Qsort Comparison Function <<<<<<<<<<<<<<<<<<<<<
  | This function simply prints out ASCII art of the game over       |
  | screen.
  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
void game_over(void) {
	
	printf("   _______\n");
	printf("  /\\ o o o\\\n");
	printf(" /o \\ o o o\\_______      ___   __   _  _  ____     __   _  _  ____  ____\n");
	printf("{    }------}   o /|    / __) / _\\ ( \\/ )(  __)   /  \\ / )( \\(  __)(  _ \\\n");
	printf(" \\ o/  o   /_____/o|   ( (_ \\/    \\/ \\/ \\ ) _)   (  O )\\ \\/ / ) _)  )   /\n");
	printf("  \\/______/     |oo|    \\___/\\_/\\_/\\_)(_/(____)   \\__/  \\__/ (____)(__\\_)\n");
	printf("        |   o   |o/\n");
	printf("        |_______|/\n\n");
}
