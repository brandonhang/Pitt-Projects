# Brandon S. Hang
# CS 0449
# Project 4
# April 10, 2016
#
# This is the Makefile for building the /dev/dice Linux driver.

obj-m := dice_driver.o 

KDIR  := /u/SysLab/shared/linux-2.6.23.1
PWD   := $(shell pwd)

default:
	$(MAKE) -C $(KDIR) M=$(PWD) modules
