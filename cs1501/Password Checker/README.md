This project generates a list of passwords of length 5 by comparing them to a list of (500, I believe) common English words.  It then
checks a user-entered password to check its validity.  Passwords containing any word, or enumeration thereof, from that list
(enumerations of the word "that" include "th@t", "7h@t", etc.).  Valid characters for passwords include alphanumeric characters and
the symbols '!', '@', '$', '^', '*', and '_'.  Passwords also must contain 1-3 lowercase letters (no uppercase!), 1-2 numbers, and
1-2 symbols.

The program is first run using "java PasswordChecker -g".  The -g parameter allows the program to first generate and save a list of
valid passwords.  The program is then run again without the parameter in order to take user input and check it against the saved
file of valid passwords.  The purpose of this project was to utilize a de la Briandais trie as the feature data structure.  This
project was originally submitted on 02/12/2016.
