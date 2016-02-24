/*0========================================================0
  |  Title:    Blackjack Simulator                        |
  |  Author:   Brandon S. Hang                            |
  |  Version:  2.100                                      |
  |  Class:    CS 0449                                    |
  |            Project 1                                  |
  |            February 7, 2016                           |
  |                                                       |
  |  This program simulates the card game "Blackjack"     |
  |  between a dealer and a single player.  The dealer    |
  |  is controlled by the computer and player input is    |
  |  limited to 'hit' or 'stand'.  The program only       |
  |  simulates a single round and no bets are placed.     |
  |                                                       |
  |  I chose to create a deck of cards using an array     |
  |  in order to ensure that cards already in play        |
  |  aren't drawn again.  I used the Durstenfeld version  |
  |  of the Fisher-Yates shuffle in order to pseudo-      |
  |  randomize the deck of cards (I actually used this    |
  |  algorithm in a past project for CS 0445 last         |
  |  semester).  Additionally, I chose to represent the   |
  |  cards themselves as ASCII values within the array    |
  |  rather than their integer values.  This allowed me   |
  |  to print the actual card to the screen rather than   |
  |  an integer value using a function (e.g., a king      |
  |  will display as 'K' instead of '10' and an ace will  |
  |  display as 'A' instead of '1' or '11').  A           |
  |  function returns the integer value for gameplay      |
  |  purposes.                                            |
  0=======================================================0*/

#include <stdio.h>
#include <string.h>
#include <ctype.h>

void card_to_print(char card);			// Prototype of the function that prints a card's appearance
int card_to_sum(char card);				// Prototype of the function that returns a card's integer value

int main() {
	
	/*/---------------------------------------------------------\
	 <                  Variable Declarations                    >
	  \---------------------------------------------------------/*/
	
	char card_values[] = {65, 50, 51, 52, 53, 54, 55, 56, 57, 48, 74, 81, 75};		// Representation of cards as ASCII values
	char deck[52];
	char player_hand[12];			// For both the player and dealer hand, the maximum number of cards a hand can have
	char dealer_hand[12];			//   is 11.  Mathematically speaking, the 12th card will always bust the hand.
	int player_num_cards = 0;
	int dealer_num_cards = 0;
	int player_sum = 0;
	int dealer_sum = 0;
	int player_num_aces = 0;		// Keeps track of the number of aces in the hand.  Specifically, it tracks the number
	int dealer_num_aces = 0;		//   of aces that are functioning with a value of 11 (will never exceed 1).
	int index = 0;						// Tracks the position of the next card in the deck.
	int temp = 0;						// Only used when shuffling the deck
	int random_num = 0;				// Only used when shuffling the deck
	int i = 0;							// Generic int variable for loops
	int j = 0;							// Generic int variable for loops
	char response[100];
	int str_len = 0;
	char match_hit = 0;					// Boolean if the player's response matches "HIT"
	char match_stand = 0;				// Boolean if the player's response matches "STAND"
	char player_blackjack = 0;		// Boolean if the player has a blackjack.  A blackjack consists of an ace and any face card.
	char dealer_blackjack = 0;		// Boolean if the dealer has a blackjack
	
	/*/---------------------------------------------------------\
	 <                      Game Initialization                  >
	  \---------------------------------------------------------/*/
	
	printf("\nLet's play some blackjack!\n");
	
	for (i = 0; i < 4; i++) {				// Creates a deck of cards (without suits) using their values
		for (j = 0; j < 13; j++) {
			deck[index] = card_values[j];
			index++;
		}
	}
	
	index = 0;
	srand((unsigned int)time(NULL));
	printf("Shuffling and dealing cards...\n\n");
	
	for (i = 51; i > 0; i--) {				// Shuffles the deck using the Durstenfeld version of the Fisher-Yates shuffle
		random_num = rand() % (i + 1);			// Generates a random number from 0 to i
		temp = deck[i];							// Temporarily stores the card in the deck at i
		deck[i] = deck[random_num];				// Replaces the card at i with a pseudorandomly selected card
		deck[random_num] = temp;				// Reinserts the stored card into the deck at the pseudorandom position
	}
	
	for (i = 0; i < 2; i++) {				// Deal initial cards to dealer
		dealer_hand[i] = deck[index];
		if (65 == dealer_hand[i]) {			// 65 is the ASCII code for 'A'
			dealer_num_aces++;
		}
		dealer_sum += card_to_sum(dealer_hand[i]);
		if (dealer_sum > 21) {				// Transforms an ace from an 11 to a 1.  This only occurs if 2 aces are initially drawn.
			dealer_sum -= 10;
			dealer_num_aces--;
		}
		dealer_num_cards++;
		index++;
	}
	
	for (i = 0; i < 2; i++) {				// Deal initial cards to player
		player_hand[i] = deck[index];
		if (65 == player_hand[i]) {
			player_num_aces++;
		}
		player_sum += card_to_sum(player_hand[i]);
		if (player_sum > 21) {				// Transforms an ace from an 11 to a 1
			player_sum -= 10;
			player_num_aces--;
		}
		player_num_cards++;
		index++;
	}
	
	/*/---------------------------------------------------------\
	 <                      Player Interaction                   >
	  \---------------------------------------------------------/*/
	
	printf("Dealer's hand: ??? + ");			// Prints out the dealer's hand with the hole card hidden
	card_to_print(dealer_hand[1]);
	printf("\n");
	
	printf("Your hand: ");						// Prints out the player's hand
	card_to_print(player_hand[0]);
	printf(" + ");
	card_to_print(player_hand[1]);
	printf(" = %d\n\n", player_sum);
	
	do {
		printf("Do you want to \"hit\" or \"stand\"?  ");
		
		fgets(response, 100, stdin);			// I wanted to use fgets instead of scanf as recommended from lecture
		str_len = strlen(response) - 1;					// Gets the length of the string before the 'Enter' key
		
		for (i = 0; i < str_len; i++) {					// Converts the user response to all uppercase letters
			response[i] = toupper(response[i]);
		}
		response[str_len] = 0;			// Replaces the carriage return with a null-terminator
		
		match_hit = strcmp(response, "HIT");
		match_stand = strcmp(response, "STAND");
		
		/*>>>>>>>>>>>>>>>> Null Terminator and strcmp <<<<<<<<<<<<<<<<
		  | I chose to replace the carriage return with a null in    |
		  | order to ensure that the strings match exactly.  When I  |
		  | used strncmp with the length of the user-entered         |
		  | response, phrases like "st" or "sta" would match since   |
		  | that combination only compares up to the length of the   |
		  | entered string.  When I used strncmp with a fixed value, |
		  | phrases like "stand blah blah" will also match since it  |
		  | only compares the first, in this case, 5 characters.  By |
		  | replacing the carriage return with a null character and  |
		  | strictly using strcmp, this ensures the responses match  |
		  | exactly (regardless of case).                            |
		  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
		
		if (match_hit != 0 && match_stand != 0) {		// Message if neither "hit" nor "stand" are entered
		printf("I didn't understand your response.  I'll ask you again.\n");
		}
	} while (match_hit != 0 && match_stand != 0);		// This section loops until the user enters a valid response
	
	if (0 == match_stand) {				// For console formatting purposes
		printf("\n");
	}
	
	/*>>>>>>>>>>>>>>>> Player Card Hit <<<<<<<<<<<<<<<<*/
	
	while (0 == match_hit) {			// Loops while the player response is "HIT"
		player_hand[player_num_cards] = deck[index];
		player_sum += card_to_sum(player_hand[player_num_cards]);
		
		if (65 == player_hand[player_num_cards]) {		// Conditional if the card drawn is an ace
			player_num_aces++;
		}
		
		player_num_cards++;
		index++;
		
		printf("\nYour hand: ");						// Prints the player's hand
		for (i = 0; i < player_num_cards - 1; i++) {
			card_to_print(player_hand[i]);
			printf(" + ");
		}
		card_to_print(player_hand[player_num_cards - 1]);
		
		if (player_sum > 21) {					// Conditional if the player's hand exceeds 21
			if (player_num_aces > 0) {			// Nested conditional if the player has any aces functioning as an 11
				for (i = 0; i < player_num_cards; i++) {		// Searches for the ace in the hand and changes it to a 1
					if (65 == player_hand[i]) {
						player_sum -= 10;
						player_num_aces--;
						break;
					}
				}
				printf(" = %d\n", player_sum);		// Prints the total of the player's hand
			}
			else {								// Without any aces to convert, the player loses
				printf(" = %d\n", player_sum);
				printf("BUSTED!  The dealer wins!\n\n");
				return 0;
			}
		}
		else {
			printf(" = %d\n", player_sum);			// Prints the total of the player's hand
		}
		
		do {
			printf("\nDo you want to \"hit\" or \"stand\"?  ");
			
			fgets(response, 100, stdin);
			str_len = strlen(response) - 1;
			
			for (i = 0; i < str_len; i++) {				// Converts the response to all uppercase
				response[i] = toupper(response[i]);
			}
			response[str_len] = 0;			// Replaces the carriage return with a null character
			
			match_hit = strcmp(response, "HIT");
			match_stand = strcmp(response, "STAND");
			
			if (match_hit != 0 && match_stand != 0) {		// Message if an invalid response is entered
			printf("I didn't understand your response.  I'll ask you again.");
			}
		} while (match_hit != 0 && match_stand != 0);		// Loops until a valid response is entered
		
		if (0 == match_stand) {
			printf("\n");				// Formatting purposes
		}
		
	}
	
	/*/---------------------------------------------------------\
	 <                      Dealer's Turn                        >
	  \---------------------------------------------------------/*/
	
	printf("The dealer reveals the hole card!\n");			// Prints the dealer's fully exposed hand
	printf("Dealer's hand: ");
	card_to_print(dealer_hand[0]);
	printf(" + ");
	card_to_print(dealer_hand[1]);
	printf(" = %d\n", dealer_sum);
	
	/*>>>>>>>>>>>>>>>> Dealer Card Hit <<<<<<<<<<<<<<<<*/
	
	while (dealer_sum < 17) {			// Loops while the dealer's hand is less than 17 points
		printf("\nThe dealer's hand must sum to at least 17 points!  The dealer hits.\n");
		
		dealer_hand[dealer_num_cards] = deck[index];
		dealer_sum += card_to_sum(dealer_hand[dealer_num_cards]);
		
		if (65 == dealer_hand[dealer_num_cards]) {		// Conditional if an ace is drawn
			dealer_num_aces++;
		}
		
		dealer_num_cards++;
		index++;
		
		printf("\nDealer's hand: ");			// Prints the dealer's hand
		for (i = 0; i < dealer_num_cards - 1; i++) {
			card_to_print(dealer_hand[i]);
			printf(" + ");
		}
		card_to_print(dealer_hand[dealer_num_cards - 1]);
		
		if (dealer_sum > 21) {					// Conditional if the dealer's hand exceeds 21
			if (dealer_num_aces > 0) {			// Nested conditional if the dealer has any aces functioning as an 11
				for (i = 0; i < dealer_num_cards; i++) {		// Searches for the ace in the hand and changes it to a 1
					if (65 == dealer_hand[i]) {
						dealer_sum -= 10;
						dealer_num_aces--;
						break;
					}
				}
				printf(" = %d\n", dealer_sum);		// Prints the total of the dealer's hand
			}
			else {					// Without any aces to convert, the dealer loses
				printf(" = %d\n", dealer_sum);
				printf("BUSTED!  You win!\n\n");
				return 0;
			}
		}
		else {
			printf(" = %d\n", dealer_sum);			// Prints the total of the dealer's hand
		}
	}
	printf("\nThe dealer's hand is at least 17 points.  The dealer stands.\n\n");
	
	/*/---------------------------------------------------------\
	 <                      Endgame Results                      >
	  \---------------------------------------------------------/*/
	
	printf("Comparing hands...\n");
	printf("\nYour hand: ");						// Prints the player's hand
	for (i = 0; i < player_num_cards - 1; i++) {
		card_to_print(player_hand[i]);
		printf(" + ");
	}
	card_to_print(player_hand[player_num_cards - 1]);
	printf(" = %d", player_sum);
	if (21 == player_sum && 2 == player_num_cards) {		// Remember, a blackjack consists of exactly an ace and a face card
		printf(" BLACKJACK!\n");
		player_blackjack = 1;
	}
	else {
		printf("\n");
	}
	
	printf("Dealer's hand: ");						// Prints the dealer's hand
	for (i = 0; i < dealer_num_cards - 1; i++) {
		card_to_print(dealer_hand[i]);
		printf(" + ");
	}
	card_to_print(dealer_hand[dealer_num_cards - 1]);
	printf(" = %d", dealer_sum);
	if (21 == dealer_sum && 2 == dealer_num_cards) {
		printf(" BLACKJACK!\n");
		dealer_blackjack = 1;
	}
	else {
		printf("\n");
	}
	
	/*>>>>>>>>>>>>>>>>>>>>>>>> Victory Conditions <<<<<<<<<<<<<<<<<<<<<<<<
	  | The following are all the different scenarios that can occur if  |
	  | neither the player nor the dealer bust.  Please see              |
	  | https://en.wikipedia.org/wiki/Blackjack#Rules_of_play_at_casinos |
	  | for more details on the rules and scenarios that I used.         |
	  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
	
	if (player_sum > dealer_sum) {			// Conditional if the player's hand is greater than the dealer's hand
		printf("Your hand has more points!  You win!\n\n");
	}
	else if (dealer_sum > player_sum) {		// Conditional if the dealer's hand is greater than the player's hand
		printf("The dealer's hand has more points.  The dealer wins!\n\n");
	}
	else if (player_blackjack && dealer_blackjack) {		// Specific conditional if both the player and the dealer have a blackjack
		printf("Both you and the dealer got blackjacks.  Looks like it's a draw...\n\n");
	}
	else if (player_blackjack && !dealer_blackjack) {		// Conditional if the player has a blackjack but the dealer does not.
		printf("Your blackjack trumps the dealer's hand!\n\n");		// E.g. the dealer has 21 points made of 8 + 10 + 3 but the player
	}																//   has a blackjack.  The player wins in this scenario.
	else if (dealer_blackjack && !player_blackjack) {		// Conditional if the dealer has a blackjack but the player does not.
		printf("The dealer's blackjack beats your hand!  The dealer wins!\n\n");
	}
	else {						// Else, the player and the dealer have the same number of points
		printf("You have the same number of points as the dealer.  Looks like it's a draw...\n\n");
	}
	
	return 0;
}

/*/---------------------------------------------------------\
 <                   Displaying the Cards                    >
  \---------------------------------------------------------/
   |  This function prints the cards to the console by     |
   |  using a switch statement and the ASCII value of the  |
   |  card stored in the array.  For each card, except 10, |
   |  printf is used to print the character representation | 
   |  of the card.  Because 10 is made of two digits, when |
   |  the ASCII code for zero is encountered, printf       |
   |  directly prints out '10' instead of a single         |
   |  character.                                           |
   /_______________________________________________________\*/

void card_to_print(char card) {
	switch (card) {
		case 65:
			printf("%c", card);
			break;
		case 50:
			printf("%c", card);
			break;
		case 51:
			printf("%c", card);
			break;
		case 52:
			printf("%c", card);
			break;
		case 53:
			printf("%c", card);
			break;
		case 54:
			printf("%c", card);
			break;
		case 55:
			printf("%c", card);
			break;
		case 56:
			printf("%c", card);
			break;
		case 57:
			printf("%c", card);
			break;
		case 48:
			printf("10");
			break;
		case 74:
			printf("%c", card);
			break;
		case 81:
			printf("%c", card);
			break;
		case 75:
			printf("%c", card);
			break;
		default:			// If something goes wrong, prints the card as "a bad card" instead
			printf("a bad card");
			break;
	}
}

/*/---------------------------------------------------------\
 <                        Card Values                        >
  \---------------------------------------------------------/
   |  This function returns the integer value of each card |
   |  using a switch statement.  Here, the ASCII values    |
   |  correspond to the actual integer values for each     |
   |  card.  E.g., 65 (an ace) returns the value 11.       |
   /_______________________________________________________\*/

int card_to_sum(char card) {
	switch (card) {
		case 65:
			return 11;
		case 50:
			return 2;
		case 51:
			return 3;
		case 52:
			return 4;
		case 53:
			return 5;
		case 54:
			return 6;
		case 55:
			return 7;
		case 56:
			return 8;
		case 57:
			return 9;
		case 48:
			return 10;
		case 74:
			return 10;
		case 81:
			return 10;
		case 75:
			return 10;
		default:			// If something goes wrong, returns 0 and allows the game to continue
			return 0;
	}
}