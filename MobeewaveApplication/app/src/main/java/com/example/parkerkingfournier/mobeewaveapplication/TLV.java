/*
 *    TLV class. This class implements a TLV object. The TLV is stored as a byte array
 *    and the tag, and length of the data field are stored as bytes. Again, precautions
 *    were taken to free up any unused variables by setting their values to null.
 *
 *    Parker King-Fournier
 */

package com.example.parkerkingfournier.mobeewaveapplication;

class TLV{

    // Class Fields
    byte    tag;
    byte    data_length;
    byte[]  tlv;

    // Constructor Method
    TLV(byte[] input){
        this.tag            = input[0];
        this.data_length    = input[1];
        this.tlv            = new byte[input.length];
        for (byte i = 0; i < input.length; i++){
            this.tlv[i] = input[i];
        }
        input = null;
    }
}
