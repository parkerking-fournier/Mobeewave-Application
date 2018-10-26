/*
*   Main method. This runs the routine described in the
*   Mobeewave application.
*
*   When considering the use of this software in an embedded environment
*   memory and data format was considered. Unless absolutely necessary
*   all data was expressed as bytes. This excludes the use of one short
*   used to parse the TLV and Strings which were used solely for Android Interface.
*
*   When variables were no longer needed, their values were set to null. This
*   is a way for force the JVM to free up that memory and forces garbage collection,
*   which would be beneficial in an embedded system.
*
*   No external Java libraries were used, however it should be noted that
*   the hexadecimal form of elements of byte arrays was displayed using the
*   Integer.toHexString(int a) method. If this proves to be a problem a decimal
*   to hexadecimal method can be written.
*
*/

package com.example.parkerkingfournier.mobeewaveapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// Imports added for basic app function
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //_____________________________DATA PROCESSING (APDU and TLV handling________________
        // Byte stream
        byte[] input_stream = { (byte)0x80, (byte)0xE2, (byte)0x00, (byte)0x00, (byte)0x0A,
                                (byte)0xaf, (byte)0x82, (byte)0x11, (byte)0xdb, (byte)0xdb,
                                (byte)0xd9, (byte)0x08, (byte)0x12, (byte)0x9b, (byte)0xd8 };

        // Read in byte stream to a command APDU
        final CommandAPDU input_apdu = new CommandAPDU(input_stream);

        // Clear up memory
        input_stream = null;

        // Read in the data field of the APDU to a TLV
        final byte[] apdu_data;
        if(input_apdu.apdu_case == 0 || input_apdu.apdu_case == 1 || input_apdu.apdu_case == 2){
            apdu_data = null;
            System.out.println("No data field in the command apdu.");
            System.out.println();
        }
        else{
            apdu_data = new byte[input_apdu.apdu[4]];
            for(byte i = 0; i < apdu_data.length; i++){
                apdu_data[i] = input_apdu.apdu[i+5];
            }
        }

        /*  Copy the data field into a new byte array that will hold the
         *  decrypted version
         */
        final byte[] decrypted_apdu_data = new byte[apdu_data.length];
        for(byte i=0; i<apdu_data.length; i++){
            decrypted_apdu_data[i] = apdu_data[i];
        }

        // Decrypt the APDU data field
        Encryption.decrypt(apdu_data);

        // Store this in a TLV object
        final TLV input_tlv = new TLV(decrypted_apdu_data);


        //_____________________________USER INTERFACE__________________________
        /* Initialize Button 1 Listener
         *       This shows the command APDU when the button is pressed.
         */
        final TextView text_a = findViewById(R.id.text_a);
        final Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
                for (byte i=0; i < input_apdu.apdu.length; i++){
                    message += Integer.toHexString(input_apdu.apdu[i]) + " ";
                }
                text_a.setText(message);
            }

        });

        /* Initialize Button 2 Listener
         *       This shows the command APDU when the button is pressed.
         */
        final TextView text_c = findViewById(R.id.text_c);
        final Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
                for (byte i=0; i < input_tlv.tlv.length; i++){
                    message += Integer.toHexString(input_tlv.tlv[i]) + " ";
                }
                text_c.setText(message);
            }

        });

        /* Initialize Button 3 Listener
         *       This shows the command APDU when the button is pressed.
         */
        final TextView text_b = findViewById(R.id.text_b);
        final Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
                for (byte i=0; i < apdu_data.length; i++){
                    message += Integer.toHexString(apdu_data[i]) + " ";
                }
                text_b.setText(message);
            }

        });
    }
}
