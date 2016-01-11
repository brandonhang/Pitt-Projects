# Brandon Hang
# bsh41@pitt.edu
# CS 0447
# Project 1 - The Auto Coder
# Version 1.00

# Please read the Auto Coder readme for detailed information on this program.  It contains
# a basic outline of how the algorithm works, known issues (just 1), a table of how each
# register is used, a tree map of the functions in the program, and some details of the
# program's algorithm.

.data
temp: .space 4
input: .space 80
registers: .asciiz "$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6", "$t7", "$t8", "$t9"
opcodes: .asciiz "\tadd ", "\taddi ", "\tor ", "\tori ", "\tlw ", "\tsw ", "\tj ", "\tbeq ", "\tbne "
newline: .asciiz "\n"
comma: .asciiz ", "
greeting: .asciiz "Welcome to the Auto Coder!\n\n"
oplist: .asciiz "Valid Opcodes: 1=add, 2=addi, 3=or, 4=ori, 5=lw, 6=sw, 7=j, 8=beq, 9=bne\n"
ordinal: .asciiz "1st", "2nd", "3rd", "4th", "5th"
msg_1: .asciiz "Please enter the "
msg_2: .asciiz " opcode:  "
display: .asciiz "\n\nThe completed code is:\n"
machine: .asciiz "\n\nThe machine code is:\n"

.text

# The Main function loads various addresses in memory in order to print out messages
# and read integers.
Main:
	la $a0, greeting
	addi, $v0, $zero, 4
	syscall			# Prints out the greeting message
	la $a0, oplist
	syscall			# Prints out the valid opcode commands
	la $t4, ordinal		# Loads ordinal numbers into $t4
	la $s0, input		# Loads the input address into $s0
	la $s1, temp		# Loads a temporary address into $s1
	addi $k0, $zero, 100	# Sets $k0 to 100; keeps track of the immediate value
	addi $k1, $zero, 1	# Sets $k1 to 1; keeps track of the last register used

# The Input_Loop function reads an integer from the user and determines which instruction
# it needs to build.  This is done via branch if equal instructions that build a word to
# be used for displaying both the instructions and machine code.  After branching and
# building a word for the instruction, the program will jump back to Input_Return, a
# common return function for all function branches.  The program will also quit if an
# invalid integer is entered.
Input_Loop:
	addi $v0, $zero, 4
	la $a0, msg_1
	syscall			# Prints the first part of the user prompt message
	addi $a0, $t4, 0
	syscall			# Prints out an ordinal number (1st, 2nd, 3rd, etc.)
	la $a0, msg_2
	syscall			# Prints out the last part of the user prompt message
	addi $v0, $zero, 5
	syscall			# Reads an integer from the user
	addi $t0, $v0, 0	# Moves the integer into $t0 for comparison
	beq $t0, 1, Build_Add	# Builds an Add instruction if the integer is 1
	beq $t0, 2, Build_Addi	# Builds an Addi instruction if the integer is 2
	beq $t0, 3, Build_Or	# Builds an Or instruction if the integer is 3
	beq $t0, 4, Build_Ori	# Builds an Ori instruction if the integer is 4
	beq $t0, 5, Build_Lw	# Builds a Lw instruction if the integer is 5
	beq $t0, 6, Build_Sw	# Builds a SW instruction if the integer is 6
	beq $t0, 7, Build_J	# Builds a J instruction if the integer is 7
	beq $t0, 8, Build_Beq	# Builds a Beq instruction if the integer is 8
	beq $t0, 9, Build_Bne	# Builds a Bne instruction if the integer is 9
	j Quit			# Quits if an invalid integer is entered
Input_Return:
	addi $t8, $t8, 1	# Increments an internal counter for building instructions
	addi $t4, $t4, 4	# Advances the ordinal number address to the next string
	bne $t8, 5, Input_Loop	# Loops Input_Loop until the internal counter reaches 5
	j Print_Prep		# Jumps to the next phase of the program, Print_Prep

Build_Add:
	sb $t0, 0($s1)		# Stores the Add integer as the first byte in $s1
	j R_Type		# Jumps to the R_Type function
	
Build_Addi:
	sb $t0, 0($s1)		# Stores the Addi integer as the first byte in $s1
	j I_Type		# Jumps to the I_Type function
	
Build_Or:
	sb $t0, 0($s1)		# Stores the Or integer as the first byte in $s1
	j R_Type		# Jumps to the R_Type function
	
Build_Ori:
	sb $t0, 0($s1)		# Stores the Ori integer as the first byte in $s1
	j I_Type		# Jumps to the I_Type function
  
Build_Lw:
	sb $t0, 0($s1)		# Stores the Lw integer as the first byte in temporary memory
	addi $t0, $k1, 1	
	sb $t0, 1($s1)		# Stores the last register used +1 as the second byte in temporary memory
	sb $k0, 2($s1)		# Stores the immediate as the third byte in temporary memory
	sb $k1, 3($s1)		# Stores the last register used as the fourth byte in temporary memory
	lw $s2, 0($s1)		# Loads the built word from temporary memory into $s2
	sw $s2, 0($s0)		# Stores word from $s2 into the next available space in the input memory
	addi $k1, $k1, 1	# Increments the last register used by 1
	addi $k0, $k0, 1	# Increments the immediate by 1
	addi $s0, $s0, 4	# Advances the input memory address by 4
	lui $s2, 0x0000
	ori $s2, $s2, 0x0000	# Resets $s2 to all zeros
	sw $s2, 0($s1)		# Resets the temporary memory
	j Input_Return		# Jumps to Input_Return
	
Build_Sw:
	sb $t0, 0($s1)		# Stores the Sw integer as the first byte in temporary memory
	sb $k1, 1($s1)		# Stores the last register used as the second byte in temporary memory
	sb $k0, 2($s1)		# Stores the immediate as the third byte in temporary memory
	addi $t0, $k1, 1
	sb $t0, 3($s1)		# Stores the last register used +1 as the fourth byte in temporary memory
	lw $s2, 0($s1)		# Loads the built word from temporary memory into $s2
	sw $s2, 0($s0)		# Stores word from $s2 into the next available space in the input memory
	addi $k1, $k1, 1	# Increments the last register used by 1
	addi $k0, $k0, 1	# Increments the immediate by 1
	addi $s0, $s0, 4	# Advances the input memory address by 4
	lui $s2, 0x0000
	ori $s2, $s2, 0x0000	# Resets $s2 to all zeros
	sw $s2, 0($s1)		# Resets the temporary memory
	j Input_Return		# Jumps to Input_Return
	
Build_J:
	jal Insert_Label	# Jumps to Insert_Label to move words in the input memory
	addi $t0, $zero, 7
	sb $t0, 0($s1)		# Stores the J integer as the first byte in temporary memory
	sb $k0, 1($s1)		# Stores the immediate as the second byte in temporary memory
	lw $s2, 0($s1)		# Loads the built word from temporary memory into $s2
	sw $s2, 0($s0)		# Stores word from $s2 into the next available space in the input memory
	addi $k0, $k0, 1	# Increments the last register used by 1
	addi $k1, $k1, 1	# Increments the immediate by 1
	addi $s0, $s0, 4	# Advances the input memory address by 4
	lui $s2, 0x0000
	ori $s2, $s2, 0x0000	# Resets $s2 to all zeros
	sw $s2, 0($s1)		# Resets the temporary memory
	j Input_Return		# Jumps to Input_Return
	
Build_Beq:
	jal Insert_Label	# Jumps to Insert_Label to move words in the input memory
	addi $t0, $zero, 8
	sb $t0, 0($s1)		# Stores the Beq integer as the first byte in temporary memory
	j I_Type		# Jumps to I_Type
	
Build_Bne:
	jal Insert_Label	# Jumps to Insert_Label to move words in the input memory
	addi $t0, $zero, 9
	sb $t0, 0($s1)		# Stores the Bne integer as the first byte in temporary memory
	j I_Type		# Jumps to I_Type

# R_Type builds words for both Add and Or instructions since they share common syntax for source
# and destination registers.
R_Type:	
	addi $t0, $k1, 2
	sb $t0, 1($s1)		# Stores the last register used +2 as the second byte in temporary memory
	sb $k1, 2($s1)		# Stores the last register used as the third byte in temporary memory
	addi $t0, $k1, 1
	sb $t0, 3($s1)		# Stores the last register used +1 as the third byte in temporary memory
	lw $s2, 0($s1)		# Loads the built word from temporary memory into $s2
	sw $s2, 0($s0)		# Stores word from $s2 into the next available space in the input memory
	addi $k1, $k1, 2	# Increments the last register used by 2
	addi $s0, $s0, 4	# Advances the input memory address by 4
	lui $s2, 0x0000
	ori $s2, $s2, 0x0000	# Resets $s2 to all zeros
	sw $s2, 0($s1)		# Resets the temporary memory
	j Input_Return		# Jumps to Input_Return
	
# I_Type builds words for Addi, Ori, Beq, and Bne instructions since they share common syntax
# for source and destination registers and the immediate.
I_Type:	
	addi $t0, $k1, 1
	sb $t0, 1($s1)		# Stores the last register used +1 as the second byte in temporary memory
	sb $k1, 2($s1)		# Stores the last register used as the third byte in temporary memory
	sb $k0, 3($s1)		# Stores the immediate as the fourth byte in temporary memory
	lw $s2, 0($s1)		# Loads the built word from temporary memory into $s2
	sw $s2, 0($s0)		# Stores word from $s2 into the next available space in the input memory
	addi $k1, $k1, 1	# Increments the last register used by 1
	addi $k0, $k0, 1	# Increments the immediate by 1
	addi $s0, $s0, 4	# Advances the input memory address by 4
	lui $s2, 0x0000
	ori $s2, $s2, 0x0000	# Resets $s2 to all zeros
	sw $s2, 0($s1)		# Resets the temporary memory
	j Input_Return		# Jumps to Input_Return
	
# Insert_Label is called before any jump or branch instruction word is created.  It does this by
# taking and moving the last instruction word in input memory by 4 bytes.  This leaves a gap
# between words in input memory.  Another word containing information on displaying a label is
# then inserted in the gap.  The function then returns to the proper instruction word builder
# (J, Beq, or Bne).  This function also handles the scenario in which a jump or branch function
# is the first instruction word built.  In this case, the program will simply create a label
# without moving the previous word (since it doesn't exist) before returning to the proper
# input memory address.
Insert_Label:
	beq $t8, 0, Skip_1	# Branches to Skip_1 if the internal build counter is at 0 (first opcode to build)
	addi $s0, $s0, -4	# Decreases the input memory address by 4
	lw $s3, 0($s0)		# Loads the last created word from memory to be restored later
	sw $s2, 0($s0)		# Resets the word at the same location in memory to all zeros
Skip_1:
	addi $t0, $zero, 88
	sb $t0, 0($s1)		# Stores the integer 88 as the first byte in temporary memory
	sb $k0, 1($s1)		# Stores the immediate as the second byte in temporary memory
	lw $s2, 0($s1)		# Loads the built word from temporary memory into $s2
	sw $s2, 0($s0)		# Stores word from $s2 into the next available space in the input memory
	lui $s2, 0x0000
	ori $s2, $s2, 0x0000	# Resets the input memory address by 1
	sw $s2, 0($s1)		# Resets the temporary memory
	beq $t8, 0, Skip_2	# Branches to Skip_2 if the internal build counter is at 0
	sw $s3, 4($s0)		# Restores the stored word into the next available space in the input memory
	addi $s0, $s0, 8	# Advances the input memory address by 8 (after the restored word)
	jr $ra			# Jumps to the return address
Skip_2:
	addi $s0, $s0, 4	# Advances the input memory address by 4 (since no word was restored)
	jr $ra			# Jumps to the return address

# Print_Prep loads the associated addresses into registers in order to print the instructions
# in the proper format.
Print_Prep:
	la $s0, input		# Loads the input memory address into $s0 (resets it to the beginning)
	la $s4, registers	# Loads the register address to print register names
	la $s5, opcodes		# Loads the opcodes address to print function names
	la $s6, newline		# Loads the newline address to print a newline
	la $s7, comma		# Loads the comma address to print ", "
	addi $v0, $zero, 4
	la $a0, display
	syscall			# Prints the instruction display message

# Print loads the first byte of the input address word at the specified address.  The program
# then compares this with integers ranging from 1-9 and with integer 88.  It then branches to
# the associated function in order to print a formatted instruction.  Integers 1-9 correspond
# with operations while 88 tells the program to print a label.  All branch functions, except
# for Print_Label, return to Output to go through another iteration of the Print loop.
# Print_Label instead returns to Print directly as to not trigger a completed iteration
# (since all it does is print a label).  Print_Register is a common function found in all
# branch functions except Print_J.  Print_Register prints out the name of the register
# associated with the integer in the instruction word.
Print:	
	lb $t0, 0($s0)			# Loads the first byte of the stored instruction word into $t0
	beq $t0, 1, Print_Add		# Prints an Add instruction if the integer is 1
	beq $t0, 2, Print_Addi		# Prints an Addi instruction if the integer is 2
	beq $t0, 3, Print_Or		# Prints an Or instruction if the integer is 3
	beq $t0, 4, Print_Ori		# Prints an Ori instruction if the integer is 4
	beq $t0, 5, Print_Lw		# Prints a Lw instruction if the integer is 5
	beq $t0, 6, Print_Sw		# Prints a Sw instruction if the integer is 6
	beq $t0, 7, Print_J		# Prints a J instruction if the integer is 7
	beq $t0, 8, Print_Beq		# Prints a Beq instruction if the integer is 8
	beq $t0, 9, Print_Bne		# Prints a Bne instruction if the integer is 9
	beq $t0, 88, Print_Label	# Prints a label if the integer is 88
Output:
	addi $t9, $t9, 1		# Increments the print instruction counter by 1
	addi $s0, $s0, 4		# Advances to the next instruction word in the input memory
	addi $v0, $zero, 4
	addi $a0, $s6, 0
	syscall				# Prints a newline
	bne $t9, 5, Print		# Branches back to Print if the counter is not at 5
	la $s0, input			# Resets the input address back to the beginning
	j Machine_Prep			# Jumps to the next phase of the program, Machine_Prep
	
Print_Add:
	addi $v0, $zero, 4
	addi $a0, $s5, 0
	syscall				# Prints the word "add"
	jal Print_R_Type		# Jumps to Print_R_Type
	j Output			# Jumps to Output
	
Print_Addi:
	addi $v0, $zero, 4
	addi $a0, $s5, 6
	syscall				# Prints the word "addi"
	jal Print_I_Type		# Jumps to Print_I_Type
	j Output			# Jumps to Output
	
Print_Or:
	addi $v0, $zero, 4
	addi $a0, $s5, 13
	syscall				# Prints the word "or"
	jal Print_R_Type		# Jumps to Print_R_Type
	j Output			# Jumps to Output
	
Print_Ori:
	addi $v0, $zero, 4
	addi $a0, $s5, 18
	syscall				# Prints the word "ori"
	jal Print_I_Type		# Jumps to Print_I_Type
	j Output			# Jumps to Output
	
Print_Lw:
	addi $v0, $zero, 4
	addi $a0, $s5, 24
	syscall				# Prints the word "lw"
	jal Print_W			# Jumps to Print_W
	j Output			# Jumps to Output
	
Print_Sw:
	addi $v0, $zero, 4
	addi $a0, $s5, 29
	syscall				# Prints the word "sw"
	jal Print_W			# Jumps to Print_W
	j Output			# Jumps to Output
	
Print_J:
	addi $v0, $zero, 4
	addi $a0, $s5, 34
	syscall				# Prints the letter 'j'
	addi $v0, $zero, 11
	addi $a0, $zero, 0x4c
	syscall				# Prints the letter 'L'
	addi $v0, $zero, 1
	lb $a0, 1($s0)
	syscall				# Prints the immediate value
	j Output			# Jumps to Output
	
Print_Beq:
	addi $v0, $zero, 4
	addi $a0, $s5, 38
	syscall				# Prints the word "beq"
	jal Print_Branch		# Jumps to Print_Branch
	j Output			# Jumps to Output
	
Print_Bne:
	addi $v0, $zero, 4
	addi $a0, $s5, 44
	syscall				# Prints the word "bne"
	jal Print_Branch		# Jumps to Print_Branch
	j Output			# Jumps to Output
	
# R_Type prints the rest of the instructions for the Add and Or operations because they
# share a common syntax.
Print_R_Type:
	sw $ra, 0($sp)		# Stores current return address in stack
	lb $t0, 1($s0)		# Loads the second byte from the instruction word (destination)
	jal Print_Register	# Prints the associated register
	addi $a0, $s7, 0
	syscall			# Prints out ", "
	lb $t0, 2($s0)		# Loads the third byte from the instruction word (source 1)
	jal Print_Register	# Prints the associated register
	addi $a0, $s7, 0
	syscall			# Prints out ", "
	lb $t0, 3($s0)		# Loads the fourth byte from the instruction word (source 2)
	jal Print_Register	# Prints the associated register
	lw $ra, 0($sp)		# Loads the stored return address from the stack
	jr $ra			# Jumps to the return address

# I_Type prints the rest of the instructions for the Addi and Ori operations because they
# share a common syntax.
Print_I_Type:
	sw $ra, 0($sp)		# Stores current return address in stack
	lb $t0, 1($s0)		# Loads the second byte from the instruction word (destination)
	jal Print_Register	# Prints the associated register
	addi $a0, $s7, 0
	syscall			# Prints out ", "
	lb $t0, 2($s0)		# Loads the third byte from the instruction word (source)
	jal Print_Register	# Prints the associated register
	addi $a0, $s7, 0
	syscall			# Prints out ", "
	lb $a0, 3($s0)		# Loads the fourth byte from the instruction word (immediate)
	addi $v0, $zero, 1
	syscall			# Prints the immediate value
	lw $ra, 0($sp)		# Loads the stored return address from the stack
	jr $ra			# Jumps to the return address

# W_Type prints the rest of the instructions for the Lw and Sw operations because they
# share a common syntax.
Print_W:
	sw $ra, 0($sp)		# Stores current return address in stack
	lb $t0, 1($s0)		# Loads the second byte from the instruction word (register 1)
	jal Print_Register	# Prints the associated register
	addi $a0, $s7, 0
	syscall			# Prints out ", "
	lb $a0, 2($s0)		# Loads the third byte from the instruction word (immediate)
	addi $v0, $zero, 1
	syscall			# Prints the immediate value
	addi $v0, $zero, 11
	addi $a0, $zero, 0x28
	syscall			# Prints a left parenthesis
	addi $v0, $zero, 4
	lb $t0, 3($s0)		# Loads the fourth byte from the instruction word (register 2)
	jal Print_Register	# Prints the associated register
	addi $v0, $zero, 11
	addi $a0, $zero, 0x29
	syscall			# Prints a right parenthesis
	lw $ra, 0($sp)		# Loads the stored return address from the stack
	jr $ra			# Jumps to the return address

# Print_Branch prints the rest of the instructions for the Beq and Bne operations because they
# share a common syntax.
Print_Branch:
	sw $ra, 0($sp)		# Stores current return address in stack
	lb $t0, 1($s0)		# Loads the second byte from the instruction word (source 2)
	jal Print_Register	# Prints the associated register
	addi $a0, $s7, 0
	syscall			# Prints out ", "
	lb $t0, 2($s0)		# Loads the third byte from the instruction word (source 1) 
	jal Print_Register	# Prints the associated register
	addi $a0, $s7, 0
	syscall			# Prints out ", "
	addi $v0, $zero, 11
	addi $a0, $zero, 0x4c
	syscall			# Prints the letter 'L'
	lb $a0, 3($s0)		# Loads the fourth byte from the instruction word (immediate)
	addi $v0, $zero, 1
	syscall			# Prints the immediate value
	lw $ra, 0($sp)		# Loads the stored return address from the stack
	jr $ra			# Jumps to the return address

Print_Label:
	addi $v0, $zero, 11
	addi $a0, $zero, 0x4c
	syscall			# Prints the letter 'L'
	lb $a0, 1($s0)
	addi $v0, $zero, 1
	syscall			# Prints the immediate value
	addi $v0, $zero, 11
	addi $a0, $zero, 0x3a
	syscall			# Prints out a colon
	addi $s0, $s0, 4	# Advances the input memory address to the next instruction word
	j Print			# Jumps to Print
	
Print_Register:
	beq $t0, 1, Reg_t0	# Prints "$t0" if the integer is 1
	beq $t0, 2, Reg_t1	# Prints "$t1" if the integer is 2
	beq $t0, 3, Reg_t2	# Prints "$t2" if the integer is 3
	beq $t0, 4, Reg_t3	# Prints "$t3" if the integer is 4
	beq $t0, 5, Reg_t4	# Prints "$t4" if the integer is 5
	beq $t0, 6, Reg_t5	# Prints "$t5" if the integer is 6
	beq $t0, 7, Reg_t6	# Prints "$t6" if the integer is 7
	beq $t0, 8, Reg_t7	# Prints "$t7" if the integer is 8
	beq $t0, 9, Reg_t8	# Prints "$t8" if the integer is 9
	beq $t0, 0x0a, Reg_t9	# Prints "$t9" if the integer is 10
	beq $t0, 0x0b, Reg_t0	# Prints "$t0" if the integer is 11; only occurs if 5 R-Type instructions are generated
	
Reg_t0:	addi $a0, $s4, 0
	syscall			# Prints out "$t0"
	jr $ra			# Jumps to the return address
	
Reg_t1:	addi $a0, $s4, 4
	syscall			# Prints out "$t1"
	jr $ra			# Jumps to the return address
	
Reg_t2:	addi $a0, $s4, 8
	syscall			# Prints out "$t2"
	jr $ra			# Jumps to the return address
	
Reg_t3:	addi $a0, $s4, 12
	syscall			# Prints out "$t3"
	jr $ra			# Jumps to the return address
	
Reg_t4:	addi $a0, $s4, 16
	syscall			# Prints out "$t4"
	jr $ra			# Jumps to the return address
	
Reg_t5:	addi $a0, $s4, 20
	syscall			# Prints out "$t5"
	jr $ra			# Jumps to the return address
	
Reg_t6:	addi $a0, $s4, 24
	syscall			# Prints out "$t6"
	jr $ra			# Jumps to the return address
	
Reg_t7:	addi $a0, $s4, 28
	syscall			# Prints out "$t7"
	jr $ra			# Jumps to the return address
	
Reg_t8:	addi $a0, $s4, 32
	syscall			# Prints out "$t8"
	jr $ra			# Jumps to the return address
	
Reg_t9:	addi $a0, $s4, 36
	syscall			# Prints out "$t9"
	jr $ra			# Jumps to the return address

Machine_Prep:
	la $a0, machine
	addi $v0, $zero, 4
	syscall			# Prints the machine code message

# Prefixes the machine code by printing out a horizontal tab followed by "0x".
Machine_Print:
	li $v0, 11
	addi $a0, $zero, 0x09
	syscall			# Prints a horizontal tab
	addi $a0, $zero, 0x30
	syscall			# Prints the number 0
	addi $a0, $zero, 0x78
	syscall			# Prints the letter 'x'
	
# Machine_Loop builds the machine code for each instruction using the built word from past
# functions in the program.  It does this by first loading the first byte from the
# instruction word and comparing it to integers ranging from 1-9.  If the integer compared
# is not within 1-9 (such as 88, a label), it advances to the next word in the input
# memory.  From there, it builds the entire 32-bit machine code with the proper opcode,
# registers, immediates, and/or target address.  After the machine code is generated, it
# is immediately printed out WITHOUT using syscall 34.  The program then returns to
# Machine_Return to restart the loop.  The program then quits after 5 iterations have
# completed.
Machine_Loop:
	lb $t1, 0($s0)			# Loads the first byte from the instruction word in memory
	beq $t1, 1, Machine_R_Type	# Builds an R-type machine code if the integer is 1
	beq $t1, 2, Machine_Addi	# Builds machine code for Addi if the integer is 2
	beq $t1, 3, Machine_R_Type	# Builds an R-type machine code if the integer is 3
	beq $t1, 4, Machine_Ori		# Builds machine code for Ori if the integer is 4
	beq $t1, 5, Machine_Lw		# Builds machine code for Lw if the integer is 5
	beq $t1, 6, Machine_Sw		# Builds machine code for Sw if the integer is 6
	beq $t1, 7, Machine_J		# Builds machine code for J if the integer is 7
	beq $t1, 8, Machine_Beq		# Builds machine code for Beq if the integer is 8
	beq $t1, 9, Machine_Bne		# Builds machine code for Bne if the integer is 9
	add $s0, $s0, 4			# Otherwise advances the input memory address to the next word
	j Machine_Loop			# Restarts Machine_Loop
Machine_Return:
	addi $t5, $t5, 1		# Increments the machine code counter
	addi $s0, $s0, 4		# Advances the input memory address to the next word
	bne $t5, 5, Machine_Print	# Jumps back to Machine_Print until 5 iterations have completed
	j Quit				# Jumps to Quit
	
# Machine_R_Type builds machine code for the Add and Or operations as they share common syntax.
Machine_R_Type:
	lb $t0, 2($s0)		# Loads the third byte from the instruction word (rs)
	jal Find_Reg		# Adds the associated register binary code to the sequence
	sll $t2, $t2, 5		# Logically shifts all bits to the left by 5
	lb $t0, 3($s0)		# Loads the fourth byte from the instruction word (rt)
	jal Find_Reg		# Adds the associated register binary code to the sequence
	sll $t2, $t2, 5		# Logically shifts all bits to the left by 5
	lb $t0, 1($s0)		# Loads the second byte from the instruction word (rd)
	jal Find_Reg		# Adds the associated register binary code to the sequence
	sll $t2, $t2, 8		# Logically shifts all bits to the left by 8
	beq $t1, 3, Mach_Or	# Branches to Mach_Or if the first byte of the word is 3
	ori $t2, $t2, 0x20	# Otherwise adds the Add funct code to the sequence
	sw $t2, 0($s1)		# Stores the completed machine code to print
	j Hex_Prep		# Jumps to Hex_Prep
Mach_Or:
	ori $t2, $t2, 0x25	# Adds the Or funct code to the sequence
	sw $t2, 0($s1)		# Stores the completed machine code to print
	j Hex_Prep		# Jumps to Hex_Prep
	
Machine_Addi:
	ori $t2, $t2, 0x20	# Adds the Addi opcode to the sequence
	sll $t2, $t2, 6		# Logically shifts all bits to the left by 6
	j Machine_I_Type	# Jumps to Machine_I_Type
	
Machine_Ori:
	ori $t2, $t2, 0x34	# Adds the Ori opcode to the sequence
	sll $t2, $t2, 6		# Logically shifts all bits to the left by 6
	j Machine_I_Type	# Jumps to Machine_T_Type
	
# Machine_I_Type finishes the machine code sequence for the Addi and Ori operations as they
# share common syntax.
Machine_I_Type:
	lb $t0, 2($s0)		# Loads the third byte from the instruction word (rs)
	jal Find_Reg		# Adds the associated register binary code to the sequence
	sll $t2, $t2, 5		# Logically shifts all bits to the left by 5
	lb $t0, 1($s0)		# Loads the second byte from the instruction word (rt)
	jal Find_Reg		# Adds the associated register binary code to the sequence
	sll $t2, $t2, 13	# Logically shifts all bits to the left by 13
	lb $t0, 3($s0)		# Loads the fourth byte from the instruction word (immediate)
	or $t2, $t2, $t0	# Adds the immediate to the sequence
	sw $t2, 0($s1)		# Stores the completed machine code to print
	j Hex_Prep		# Jumps to Hex_Prep
	
Machine_Lw:
	ori $t2, $t2, 0x8c	# Adds the Lw opcode to the sequence
	sll $t2, $t2, 6		# Logically shifts all bits to the left by 6
	j Mach_W		# Jumps to Mach_W
	
Machine_Sw:
	ori $t2, $t2, 0xac	# Adds the Sw opcode to the sequence
	sll $t2, $t2, 6		# Logically shifts all bits to the left by 6
	j Mach_W		# Jumps to Mach_W
	
# Mach_W finishes the machine code sequence for the Lw and Sw operations as they share
# common syntax.
Mach_W:
	lb $t0, 3($s0)		# Loads the fourth byte from the instruction word (rs)
	jal Find_Reg		# Adds the associated register binary code to the sequence
	sll $t2, $t2, 5		# Logically shifts all bits to the left by 5
	lb $t0, 1($s0)		# Loads the second byte from the instruction word (rt)
	jal Find_Reg		# Adds the associated register binary code to the sequence
	sll $t2, $t2, 13	# Logically shifts all bits to the left by 13
	lb $t0, 2($s0)		# Loads the third byte from the instruction word (immediate)
	or $t2, $t2, $t0	# Adds the immediate to the sequence
	sw $t2, 0($s1)		# Stores the completed machine code to print
	j Hex_Prep		# Jumps to Hex_Prep
	
# Machine_J generates the machine code for a jump operation.  It uses the machine code
# counter to determine what address the label is located on.  The label address is then
# logically shifted right 2 bits and added with the opcode to complete the target address.
Machine_J:
	ori $t2, $t2, 0x08	# Adds the J opcode to the sequence
	sll $t2, $t2, 24	# Logically shifts all bits to the left by 24
	beq $t5, 0, Load_0	# Uses 0x00400000 as the label address if the counter is 0
	beq $t5, 1, Load_0	# Uses 0x00400000 as the label address if the counter is 1
	beq $t5, 2, Load_1	# Uses 0x00400004 as the label address if the counter is 2
	beq $t5, 3, Load_2	# Uses 0x00400008 as the label address if the counter is 3
	beq $t5, 4, Load_3	# Uses 0x0040000c as the label address if the counter is 4
	
Load_0:
	lui $t3, 0x0040		# Loads 0x00400000 into $t3
	srl $t3, $t3, 2		# Logically shifts all bits to the right by 2
	or $t2, $t2, $t3	# Adds the target address to the sequence
	sw $t2, 0($s1)		# Stores the completed machine code to print
	j Hex_Prep		# Jumps to Hex_Prep
	
Load_1:
	lui $t3, 0x0040
	ori $t3, 0x0004		# Loads 0x00400004 into $t3
	srl $t3, $t3, 2		# Logically shifts all bits to the right by 2
	or $t2, $t2, $t3	# Adds the target address to the sequence
	sw $t2, 0($s1)		# Stores the completed machine code to print
	j Hex_Prep		# Jumps to Hex_Prep
	
Load_2:
	lui $t3, 0x0040
	ori $t3, 0x0008		# Loads 0x00400008 into $t3
	srl $t3, $t3, 2		# Logically shifts all bits to the right by 2
	or $t2, $t2, $t3	# Adds the target address to the sequence
	sw $t2, 0($s1)		# Stores the completed machine code to print
	j Hex_Prep		# Jumps to Hex_Prep
	
Load_3:
	lui $t3, 0x0040
	ori $t3, 0x000c		# Loads 0x0040000c into $t3
	srl $t3, $t3, 2		# Logically shifts all bits to the right by 2
	or $t2, $t2, $t3	# Adds the target address to the sequence
	sw $t2, 0($s1)		# Stores the completed machine code to print
	j Hex_Prep		# Jumps to Hex_Prep
	
Machine_Beq:
	ori $t2, $t2, 0x10	# Adds the Beq opcode to the sequence
	sll $t2, $t2, 6		# Logically shifts all bits to the left by 6
	j Mach_I_Branch		# Jumps to Mach_I_Branch
	
Machine_Bne:
	ori $t2, $t2, 0x14	# Adds the Bne opcode to the sequence
	sll $t2, $t2, 6		# Logically shifts all bits to the left by 6
	j Mach_I_Branch		# Jumps to Mach_I_Branch
	
# Mach_I_Branch finishes the machine code sequence for the operations Beq and Bne as they
# share common syntax.  Since the label that the branch jumps to is always the preceding
# line, the immediate is always 0xfffe.  However, there is a special case should the
# branch operation be the first operation created.
Mach_I_Branch:
	lb $t0, 1($s0)			# Loads the second byte from the instruction word (rs)
	jal Find_Reg			# Adds the associated register binary code to the sequence
	sll $t2, $t2, 5			# Logically shifts all bits to the left by 5
	lb $t0, 2($s0)			# Loads the third byte from the instruction word (rt)
	jal Find_Reg			# Adds the associated register binary code to the sequence
	sll $t2, $t2, 13		# Logically shifts all bits to the left by 13
	beq $t5, 0, Special_B_Case	# Branches to Special_B_Case should the counter be at 0 (first operation)
	ori $t2, $t2, 0xfffe		# Adds the immediate to the sequence
	sw $t2, 0($s1)			# Stores the completed machine code to print
	j Hex_Prep			# Jumps to Hex_Prep
	
# Special_B_Case is called if and only if Beq or Bne is the very first operation that is
# built and displayed.  In this case, the branch operation jumps to itself.  As such, the
# immediate can only be 0xffff.
Special_B_Case:
	ori $t2, $t2, 0xffff	# Adds the immediate to the sequence
	sw $t2, 0($s1)		# Stores the completed machine code to print
	j Hex_Prep		# Jumps to Hex_Prep

# Find_Reg compares the loaded byte from the instruction word with integers ranging from
# 1-10.  Each integer corresponds to registers from $t0-$t9.  The program then branches
# to the proper function and adds the corresponding register binary code to the sequence.
Find_Reg:
	beq $t0, 1, Hex_t0	# Adds $t0 to the sequence if the integer is 1
	beq $t0, 2, Hex_t1	# Adds $t1 to the sequence if the integer is 2
	beq $t0, 3, Hex_t2	# Adds $t2 to the sequence if the integer is 3
	beq $t0, 4, Hex_t3	# Adds $t3 to the sequence if the integer is 4
	beq $t0, 5, Hex_t4	# Adds $t4 to the sequence if the integer is 5
	beq $t0, 6, Hex_t5	# Adds $t5 to the sequence if the integer is 6
	beq $t0, 7, Hex_t6	# Adds $t6 to the sequence if the integer is 7
	beq $t0, 8, Hex_t7	# Adds $t7 to the sequence if the integer is 8
	beq $t0, 9, Hex_t8	# Adds $t8 to the sequence if the integer is 9
	beq $t0, 0x0a, Hex_t9	# Adds $t9 to the sequence if the integer is 10
	
Hex_t0:
	ori $t2, $t2, 0x40	# Adds $t0 to the sequence
	jr $ra			# Jumps to the return address
	
Hex_t1:
	ori $t2, $t2, 0x48	# Adds $t1 to the sequence
	jr $ra			# Jumps to the return address
	
Hex_t2:
	ori $t2, $t2, 0x50	# Adds $t2 to the sequence
	jr $ra			# Jumps to the return address
	
Hex_t3:
	ori $t2, $t2, 0x58	# Adds $t3 to the sequence
	jr $ra			# Jumps to the return address
	
Hex_t4:
	ori $t2, $t2, 0x60	# Adds $t4 to the sequence
	jr $ra			# Jumps to the return address
	
Hex_t5:
	ori $t2, $t2, 0x68	# Adds $t5 to the sequence
	jr $ra			# Jumps to the return address
	
Hex_t6:
	ori $t2, $t2, 0x70	# Adds $t6 to the sequence
	jr $ra			# Jumps to the return address
	
Hex_t7:
	ori $t2, $t2, 0x78	# Adds $t7 to the sequence
	jr $ra			# Jumps to the return address
	
Hex_t8:
	ori $t2, $t2, 0xc0	# Adds $t8 to the sequence
	jr $ra			# Jumps to the return address
	
Hex_t9:
	ori $t2, $t2, 0xc8	# Adds $t9 to the sequence
	jr $ra			# Jumps to the return address
	
# Hex_Prep is the destination for every operation once the machine code is assembled.
# Because syscall 34 is not allowed to be used, the program will instead load each nybble
# of the machine code sequence and print out the character it represents using branch
# functions and syscall 11.  Since MIPS cannot manipulate nybbles directly, it must first
# load a byte and use a combination of shift and logical and operators to isolate the
# nybble.  Starting from the eighth nybble and ending with the first, Hex_Prep works
# through the sequence backwards using a loop (4 iterations).  After printing the machine
# code sequence, the program will jump back to Machine_Return.
Hex_Prep:	
	addi $v0, $zero, 11
	lb $t0, 3($s1)		# Loads a byte from the machine code
	srl $t0, $t0, 4		# Logically shifts all bits to the right by 4
	andi $t0, $t0, 0x0f	# Isolates the (2n)th nybble of the machine code
	jal Print_Hex		# Prints the associated hexadecimal value as a character
	lb $t0, 3($s1)		# Loads a byte from the machine code
	andi $t0, $t0, 0x0f	# Isolates the (2n-1)th nybble of the machine code
	jal Print_Hex		# Prints the associated hexadecimal value as a character
	addi $t6, $t6, 1	# Increments the hexadecimal printing counter by 1
	addi $s1, $s1, -1	# Decrements the machine code memory address by 1
	bne $t6, 4, Hex_Prep	# Jumps back to Hex_Prep until the counter reaches 4
	addi $s1, $s1, 4	# Resets the machine code memory address to its initial value
	addi $t2, $zero, 0	# Resets $t2 to all zeros
	addi $t6, $zero, 0	# Resets $t6 to all zeros
	addi $a0, $zero, 0x0a
	syscall			# Prints a newline
	j Machine_Return	# Jumps to Machine_Return

# Prints a hexadecimal by comparing the isolated nybble to integers ranging from 0-9 and
# letters a-f.  It then uses syscall 11 to print out the equivalent character.
Print_Hex:
	beq $t0, 0x00, Print_0x0	# Prints the integer 0 if the nybble is 0x0
	beq $t0, 0x01, Print_0x1	# Prints the integer 1 if the nybble is 0x1
	beq $t0, 0x02, Print_0x2	# Prints the integer 2 if the nybble is 0x2
	beq $t0, 0x03, Print_0x3	# Prints the integer 3 if the nybble is 0x3
	beq $t0, 0x04, Print_0x4	# Prints the integer 4 if the nybble is 0x4
	beq $t0, 0x05, Print_0x5	# Prints the integer 5 if the nybble is 0x5
	beq $t0, 0x06, Print_0x6	# Prints the integer 6 if the nybble is 0x6
	beq $t0, 0x07, Print_0x7	# Prints the integer 7 if the nybble is 0x7
	beq $t0, 0x08, Print_0x8	# Prints the integer 8 if the nybble is 0x8
	beq $t0, 0x09, Print_0x9	# Prints the integer 9 if the nybble is 0x9
	beq $t0, 0x0a, Print_0xa	# Prints the letter 'a' if the nybble is 0xa
	beq $t0, 0x0b, Print_0xb	# Prints the letter 'b' if the nybble is 0xb
	beq $t0, 0x0c, Print_0xc	# Prints the letter 'c' if the nybble is 0xc
	beq $t0, 0x0d, Print_0xd	# Prints the letter 'd' if the nybble is 0xd
	beq $t0, 0x0e, Print_0xe	# Prints the letter 'e' if the nybble is 0xe
	beq $t0, 0x0f, Print_0xf	# Prints the letter 'f' if the nybble is 0xf
	
Print_0x0:
	addi $a0, $zero, 0x30
	syscall			# Prints the integer 0
	jr $ra			# Jumps to the return address
	
Print_0x1:
	addi $a0, $zero, 0x31
	syscall			# Prints the integer 1
	jr $ra			# Jumps to the return address
	
Print_0x2:
	addi $a0, $zero, 0x32
	syscall			# Prints the integer 2
	jr $ra			# Jumps to the return address
	
Print_0x3:
	addi $a0, $zero, 0x33
	syscall			# Prints the integer 3
	jr $ra			# Jumps to the return address
	
Print_0x4:
	addi $a0, $zero, 0x34
	syscall			# Prints the integer 4
	jr $ra			# Jumps to the return address
	
Print_0x5:
	addi $a0, $zero, 0x35
	syscall			# Prints the integer 5
	jr $ra			# Jumps to the return address
	
Print_0x6:
	addi $a0, $zero, 0x36
	syscall			# Prints the integer 6
	jr $ra			# Jumps to the return address
	
Print_0x7:
	addi $a0, $zero, 0x37
	syscall			# Prints the integer 7
	jr $ra			# Jumps to the return address
	
Print_0x8:
	addi $a0, $zero, 0x38
	syscall			# Prints the integer 8
	jr $ra			# Jumps to the return address
	
Print_0x9:
	addi $a0, $zero, 0x39
	syscall			# Prints the integer 9
	jr $ra			# Jumps to the return address
	
Print_0xa:
	addi $a0, $zero, 0x61
	syscall			# Prints the letter 'a'
	jr $ra			# Jumps to the return address
	
Print_0xb:
	addi $a0, $zero, 0x62
	syscall			# Prints the letter 'b'
	jr $ra			# Jumps to the return address
	
Print_0xc:
	addi $a0, $zero, 0x63
	syscall			# Prints the letter 'c'
	jr $ra			# Jumps to the return address
	
Print_0xd:
	addi $a0, $zero, 0x64
	syscall			# Prints the letter 'd'
	jr $ra			# Jumps to the return address
	
Print_0xe:
	addi $a0, $zero, 0x65
	syscall			# Prints the letter 'e'
	jr $ra			# Jumps to the return address
	
Print_0xf:
	addi $a0, $zero, 0x66
	syscall			# Prints the letter 'f'
	jr $ra			# Jumps to the return address
	
Quit:	addi $v0, $zero, 10
	syscall			# Exits the program
