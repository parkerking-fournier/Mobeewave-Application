/*
*   This class implements a 2 bit circular shift to the right
*   on a byte of data. Decryption is then a 2 bit circular shift
*   to the left.
*
*   For encryption these shifts are accomplished by shifting all but the two
*   rightmost bits to the right and the remaining unshifted bits (8-2) = 6
*   bits to the left. Decryption is the opposite.
*
*   To save memory, all data structures are composed of bytes, and unused variables
*   are set to 'null' as a way to force the JVM to free up the memory.
*
*   Parker King-Fournier
*/
package com.example.parkerkingfournier.mobeewaveapplication;

class Encryption{

    static void encrypt(byte[] input){
        for (byte i=0; i<input.length; i++){
            input[i] = (byte)(((input[i] & 0xff) >>> 2) | ((input[i] & 0xff) << 6));
        }
        input = null;
    }

    static void decrypt(byte[] input){
        for (byte i=0; i<input.length; i++){
            input[i] = (byte)(((input[i] & 0xff) << 2) | ((input[i] & 0xff) >>> 6));
        }
        input = null;
    }
}