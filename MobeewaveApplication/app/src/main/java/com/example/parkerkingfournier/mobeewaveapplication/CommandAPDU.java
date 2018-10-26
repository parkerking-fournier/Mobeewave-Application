/*
*   Command APDU class. This class implements a Command APDU data
*   object. Again, precautions were taken to free up any unused
*   variables by setting their values to null. Any place this couldn't
*   be done is outlined below. Data is stored exclusively in bytes to
*   minimize memory. Lastly, the creation of new variables was limited.
*   This resulted in a slightly less readable code but is easier on memory
*   constraints.
*
*   Parker King-Fournier
*/

package com.example.parkerkingfournier.mobeewaveapplication;

class CommandAPDU{

    // Class Fields
    byte[]  apdu;
    byte    apdu_case;

    // Constructor Methods
    CommandAPDU(byte[] apdu){
        this.apdu_case = checkValidity(apdu);
        if(this.apdu_case != 0){
            this.apdu = new byte[apdu.length];
            for (byte i = 0; i < apdu.length; i++){
                this.apdu[i] = apdu[i];
            }
        }
        apdu = null;
    }

    byte[] caseOne(){
        if(this.apdu_case != 0){
            return new byte[] {this.apdu[0], this.apdu[1], this.apdu[2], this.apdu[3]};
        }
        else{
            return null;
        }
    }

    byte[] caseTwo(){
        if(this.apdu_case == 2 || this.apdu_case == 4){
            return new byte[]{this.apdu[0], this.apdu[1], this.apdu[2], this.apdu[3], this.apdu[this.apdu.length - 1]};
        }
        else
            return null;
    }
    /*  Methods caseThree() and caseFour() required that an array of bytes
        was created. Unfortunately since it is returned, we will have to let the JVM
        clean up these arrays.
     */
    byte[] caseThree(){
        if(this.apdu_case == 3 || this.apdu_case == 4){
            byte[] case_three_apdu = new byte[this.apdu[4] + 5];
            for (byte i = 0; i < case_three_apdu.length; i++){
                case_three_apdu[i] = this.apdu[i];
            }
            return case_three_apdu;
        }
        else{
            return null;
        }
    }

    byte[] caseFour(){
        if(this.apdu_case == 4) {
            byte[] case_four_apdu = new byte[this.apdu.length];
            for (byte i = 0; i < case_four_apdu.length; i++){
                case_four_apdu[i] = this.apdu[i];
            }
            return case_four_apdu;
        }
        else{
            return null;
        }
    }


    // Helper Methods
    /*  This returns the Case that the form of the APDU is in. A return of 0 indicates
        an invalid APDU.
     */
    byte checkValidity(byte[] input){
        if      (input.length > 250)            {input = null; return 0;}
        else if (input.length == 4)             {input = null; return 1;}
        else if (input.length == 5)             {input = null; return 2;}
        else if (input.length == input[4] + 5)  {input = null; return 3;}
        else if (input.length == input[4] + 6)  {input = null; return 4;}
        else                                    {input = null; return 0;}
    }
}
