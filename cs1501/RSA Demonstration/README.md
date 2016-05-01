This project consists of a duo of programs that either generate a 1024-bit RSA keypair or digitally sign and verify
text files.  To generate the 1024-bit RSA keypair, the program MyKeyGen.java is executed as "java MyKeyGen" without
parameters.  The public keys are saved as "pubkey.rsa" while the private key is saved as "privkey.rsa".  In order to
digitally sign text files, the program MySign.java is executed as "java MySign s TEXTFILE" where TEXTFILE is the name
or path to the text file to be signed.  This saves a digitally signed text file as the original filename appended
with ".signed".  To verify signed files, MySign.java is executed as "java MySign v SIGNED" where SIGNED is the name
or path to the signed file ending in ".signed".  The program will then notify if the signed file has a valid digital
signature.  This project was originally submitted on 04/24/2016.
