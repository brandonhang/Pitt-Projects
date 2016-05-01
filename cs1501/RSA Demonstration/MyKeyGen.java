/**
 * @author Brandon S. Hang
 * @version 1.350
 * CS 1501
 * Assignment 5
 * April 24, 2016
 * 
 * This class generates 1024-bit RSA keypairs according to the specifications
 * found in lecture.  The prime numbers p and q are generated to be at most 512
 * bits each.  This ensures that n will never exceed 1024 bits.  Phi of n is
 * computed to be (p - 1)(q - 1).  Public key e is set to a starting value of 3
 * and is checked with phi of n to be relatively prime.  If this is not the
 * case, e is incremented by 2 in order to remain odd as an even number greater
 * than 2 is never relatively prime with any other number.  The number d is
 * then computed as d = e^-1 (mod phi(n)).
 */

import java.util.Random;
import java.math.BigInteger;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

public class MyKeyGen {
	
	public static final BigInteger TWO = new BigInteger("2");
	
	public static void main(String[] args) {
		
		BigInteger p, q, n, phiN, e, d;
		Random random = new Random();
		boolean validE;
		
		System.out.printf("Generating keypairs...\n");
		
		do {
			do {
				p = new BigInteger(512, 1024, random);
			} while (!p.isProbablePrime(2048));					// Loops if p is not probabilistically prime
			
			do {
				q = new BigInteger(512, 1024, random);
			} while (!q.isProbablePrime(2048) || q.equals(p));			// Loops if q is not probabilistically prime or if p equals q
			
			n = p.multiply(q);
		} while (n.bitLength() != 1024);						// Loops if n is not 1024 bits in length
		
		phiN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));		// phi(n) = (p - 1)(q - 1)
		
		e = new BigInteger("3");					// Initialize e to 3
		validE = false;
		
		do {
			if (e.compareTo(phiN) >= 0) {			// Breaks out of the loop if e equals phi(n)
				break;
			}
			else if (!e.gcd(phiN).equals(BigInteger.ONE)) {			// Adds 2 to e if the GCD of e and phi(n) is 1 (keeps e odd)
				e = e.add(TWO);
			}
			else {
				validE = true;
			}
		} while (!validE);					// Loop while e is not relatively prime with phi(n)
		
		if (!validE) {
			System.out.printf("Unforunately, there was a problem generating the keypairs.  Please try again\n");
			System.exit(1);
		}
		
		d = e.modInverse(phiN);				// d = e^-1 (mod phi(n))
		
		int status = writeKeys(e, d, n);
		System.exit(status);
	}
	
	
	/**
	 * Writes the public and private keys to pubkey.rsa and privkey.rsa respectively
	 * @param e Public key e
	 * @param d Private key d
	 * @param n Common key n
	 * @return An int representing success or failure
	 */
	private static int writeKeys(BigInteger e, BigInteger d, BigInteger n) {
		
		try {
			FileOutputStream fileOut = new FileOutputStream("pubkey.rsa");				// Writes to "pubkey.rsa"
			ObjectOutputStream writer = new ObjectOutputStream(fileOut);
			writer.writeObject(e);										// Writes e first
			writer.writeObject(n);										// Writes n second
			writer.close();
			System.out.printf("The public keys were successfully written to pubkey.rsa!\n");
		}
		catch (IOException ioe) {
			System.err.printf("Error writing keys to pubkey.rsa!\n");
			return 1;
		}
		
		try {
			FileOutputStream fileOut = new FileOutputStream("privkey.rsa");				// Writes to "privkey.rsa"
			ObjectOutputStream writer = new ObjectOutputStream(fileOut);
			writer.writeObject(d);										// Writes d first
			writer.writeObject(n);										// Writes n second
			writer.close();
			System.out.printf("The private keys were successfully written to privkey.rsa!\n");
		}
		catch (IOException ioe) {
			System.err.printf("Error writing keys to privkey.rsa!\n");
			return 1;
		}
		
		return 0;
	}
}
