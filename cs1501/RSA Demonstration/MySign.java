/**
 * @author Brandon S. Hang
 * @version 1.500
 * CS 1501
 * Assignment 5
 * April 24, 2016
 * 
 * This class digitally signs and verifies text files.  It takes two command
 * line arguments: The first argument specifies the mode ('s' for signing or
 * 'v' for verifying' while the second is the name of the file.  When
 * digitally signing a file, the program first reads the contents of the file
 * and hashes it using Secure Hashing Algorithm 256 (SHA-256).  It then
 * "decrypts" the contents using the public keys generated using MyKeyGen.java.
 * The result is stored as the original file appended with ".signed".
 * Upon verifying a signed file, its contents are read in 2 parts: the original
 * data and the decrypted data.  The original data is hashed again using
 * SHA-256 and the decrypted data is "encrypted" using the private keys created
 * from MyKeyGen.java.  If the resulting hash values are equal, the program
 * notifies the user that the signed file is valid. 
 */

import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileSystemNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MySign {
	
	public static void main(String[] args) {
		
		char mode;			// Specifies either signing or verifying
		int status;			// System exit status
		
		if (args.length == 0) {
			throw new IllegalArgumentException("No parameters were entered!\n"		// Exception if no parameters are entered
					+ "A mode ('s' or 'v') and a filename must be entered!\n");
		}
		if (args.length == 1) {
			throw new IllegalArgumentException("A parameter is missing!\n"					// Exception if only 1 parameter is entered
					+ "Both a mode ('s' or 'v') and a filename must be entered!\n");
		}
		if (args.length > 2) {
			throw new IllegalArgumentException("Too many parameters were entered!\n"		// Exception if more than 2 parameters are entered
					+ "Please only enter a mode ('s' or 'v') and a filename!\n");
		}
		if (args[0].length() > 1) {
			throw new IllegalArgumentException("An invalid mode parameter was entered!\n"		// Exception if the mode parameter is not a single character
					+ "Please enter either 's' or 'v' only!\n");
		}
		
		mode = args[0].charAt(0);
		
		switch (mode) {					// Modes need to be the correct letter but are case insensitive
		case 'S':
		case 's':
			status = signFile(args[1]);			// Digitally signs the file
			break;
		case 'V':
		case 'v':
			status = verifySignature(args[1]);			// Verifies the digital signature of a file
			break;
		default:
			throw new IllegalArgumentException("An invalid mode parameter was entered!\n"		// Exception if the mode parameter is neither 's' nor 'v'
					+ "Please enter either 's' or 'v' only!\n");
		}
		
		System.exit(status);			// Successful operations return a status of 0; otherwise, a 1 is returned
	}
	
	
	/**
	 * Digitally signs the file using the keys found in privkey.rsa (d and n).
	 * @param filename The name of the file to sign
	 * @return Returns 0 if successful, 1 if unsuccessful
	 */
	private static int signFile(String filename) {
		
		System.out.printf("Creating a digital signature for %s...\n", filename);
		
		try {
			BigInteger decrypted, d, n;
			byte[] originalData, originalHashed;
			
			Path filepath = Paths.get(filename);				// Gets the URI path of the file
			originalData = Files.readAllBytes(filepath);			// Reads the entire contents at once as a byte array
			
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");			// Hashes the contents using Secure Hash Algorithm 256
			sha256.update(originalData);
			originalHashed = sha256.digest();					// Stores the hashed data into a different byte array
			
			FileInputStream keyFile = new FileInputStream("privkey.rsa");			// Opens privkey.rsa
			ObjectInputStream keyReader = new ObjectInputStream(keyFile);
			
			d = (BigInteger)keyReader.readObject();				// Reads the first object as d
			n = (BigInteger)keyReader.readObject();				// Reads the second object as n
			keyReader.close();
			decrypted = new BigInteger(1, originalHashed).modPow(d, n);			// decrypted = hash^d (mod n)
			
			return writeSignedFile(filename, originalData, decrypted);			// Calls the function to write the signed file
		}
		catch (FileSystemNotFoundException fsnfe) {
			System.err.printf("Error: The requested file \"%s\" was not found.\n", filename);		// Exception if the URI could not be found
			return 1;
		}
		catch (NoSuchAlgorithmException nsae) {
			System.err.printf("Error: The requested algorithm does not exist.\n");			// Exception if the hashing algorithm does not exist
			return 1;
		}
		catch (ClassNotFoundException cnfe) {
			System.err.printf("Error: The object that was read could not be associated with a class.\n");		// Exception if the ObjectInputStream could not recognize the class
			return 1;
		}
		catch (FileNotFoundException fnfe) {
			System.err.printf("Error: privkey.rsa was not found in this directory.\n");			// Exception if privkey.rsa could not be found
			return 1;
		}
		catch (IOException ioe) {
			System.err.printf("Error: There was an problem reading the file.\n");			// General exception if a file could not be read
			return 1;
		}
	}
	
	
	/**
	 * Writes a new signed file with a .signed extension
	 * @param filename The filename of the original file
	 * @param fileData The original, unhashed data of the file
	 * @param decrypted The hashed "decrypted" data
	 * @return 0 if successful, 1 if unsuccessful.
	 */
	private static int writeSignedFile(String filename, byte[] fileData, BigInteger decrypted) {
		
		try {
			String signedFilename = filename.concat(".signed");						// Appends the filename with .signed
			FileOutputStream decryptedFile = new FileOutputStream(signedFilename);			// Creates new file and object output streams
			ObjectOutputStream decryptedWriter = new ObjectOutputStream(decryptedFile);
			
			decryptedWriter.writeObject(fileData);				// Writes the original, unhashed data first
			decryptedWriter.writeObject(decrypted);				// Writes the decrypted data second
			decryptedWriter.close();
			
			System.out.printf("Successfully signed %s!  The signed file has been saved as %s.\n", filename, signedFilename);
			return 0;
		}
		catch (IOException ioe) {
			System.err.printf("Error: There was an error writing the signed file.\n");		// Exception if an error occurred while writing the file
			return 1;
		}
	}
	
	
	/**
	 * Verifies an already digitally signed file
	 * @param filename The name of the signed file
	 * @return 0 if successful, 1 if unsuccessful
	 */
	private static int verifySignature(String filename) {
		
		System.out.printf("Verifying the digital signature for %s...\n", filename);
		
		try {
			byte[] original;				// Holds the original, unhashed data
			BigInteger decrypted;				// Holds the decrypted data
			
			FileInputStream signedFile = new FileInputStream(filename);				// Opens the signed file
			ObjectInputStream signedReader = new ObjectInputStream(signedFile);
			
			original = (byte[])signedReader.readObject();				// Reads the original data first
			decrypted = (BigInteger)signedReader.readObject();				// Reads the decrypted data second
			signedReader.close();
			
			return readPublicKey(original, decrypted, filename);			// Calls the function that checks the signature against public keys
		}
		catch (FileNotFoundException fnfe) {
			System.err.printf("Error: pubkey.rsa was not found in this directory.\n");			// Exception if pubkey.rsa could not be found
			return 1;
		}
		catch (ClassNotFoundException cnfe) {
			System.err.printf("Error: The object that was read could not be associated with a class.\n");		// Exception if the ObjectInputStream could not recognize the class 
			return 1;
		}
		catch (IOException ioe) {
			System.err.printf("Error: There was an problem reading the file.\n");			// General exception if a file could not be read
			return 1;
		}
		
	}
	
	
	/**
	 * Encrypts the decrypted data to compare hash values
	 * @param original The original, unhashed data
	 * @param decryptedHash The decrypted data from the digital signature
	 * @param filename The name of the file
	 * @return 0 if successful, 1 if unsuccessful
	 */
	private static int readPublicKey(byte[] original, BigInteger decryptedHash, String filename) {
		
		try {
			BigInteger e, n, encrypted, originalHash;		// Holds public keys and file data
			byte[] hashedData;				// Holds the hashed original data
			
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");		// Hashes the original data using SHA-256
			sha256.update(original);
			hashedData = sha256.digest();
			originalHash = new BigInteger(1, hashedData);			// Creates a new, positive BigInteger object 
			
			FileInputStream keyFile = new FileInputStream("pubkey.rsa");		// Opens pubkey.rsa
			ObjectInputStream keyReader = new ObjectInputStream(keyFile);
			
			e = (BigInteger)keyReader.readObject();				// Reads e first
			n = (BigInteger)keyReader.readObject();				// Reads n second
			keyReader.close();
			encrypted = decryptedHash.modPow(e, n);				// encrypted = decrypted^e (mod n)
			
			System.out.printf("The signature of %s is %svalid!\n", filename, (originalHash.equals(encrypted) ? "" : "in"));
									// Compares the hash values.  If they are unequal, the ternary operator prints "in" before "valid" to form "invalid".
			return 0;
		}
		catch (NoSuchAlgorithmException nsae) {
			System.err.printf("Error: The requested algorithm does not exist.\n");			// Exception if the hashing algorithm does not exist
			return 1;
		}
		catch (FileNotFoundException fnfe) {
			System.err.printf("Error: pubkey.rsa was not found in this directory.\n");		// Exception if pubkey.rsa could not be found
			return 1;
		}
		catch (IOException ioe) {
			System.err.printf("Error: There was an problem reading the file.\n");			// General exception if a file could not be read
			return 1;
		}
		catch (ClassNotFoundException cnfe) {
			System.err.printf("Error: The object that was read could not be associated with a class.\n");		// Exception if the ObjectInputStream could not recognize the class
			return 1;
		}
	}
}
