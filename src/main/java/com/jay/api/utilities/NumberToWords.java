package com.jay.api.utilities;

public class NumberToWords {

    // Strings at index 0 is not used, it is to make array
    // indexing simple
    static String one[] = { "", "One ", "Two ", "Three ", "Four ",
            "Five ", "Six ", "Seven ", "Eight ",
            "Nine ", "Ten ", "Eleven ", "Twelve ",
            "Thirteen ", "Fourteen ", "Fifteen ",
            "Sixteen ", "Seventeen ", "Eighteen ",
            "Nineteen " };

    // Strings at index 0 and 1 are not used, they are to
    // make array indexing simple
    static String ten[] = { "", "", "Twenty ", "Thirty ", "Forty ",
            "Fifty ", "Sixty ", "Seventy ", "Eighty ",
            "Ninety " };

    // n is 1- or 2-digit number
    static String numToWords(int n, String s)
    {
        String str = "";
        // if n is more than 19, divide it
        if (n > 19) {
            str += ten[n / 10] + one[n % 10];
        }
        else {
            str += one[n];
        }
        // if n is non-zero
        if (n != 0) {
            str += s;
        }
        return str;
    }
    // Function to print a given number in words
    static String convertToWords(long n)
    {
        // stores word representation of given number n
        String out = "";
        // handles digits at ten millions and hundred
        // millions places (if any)
        out += numToWords((int)(n / 10000000), "Crore ");
        // handles digits at hundred thousands and one
        // millions places (if any)
        out += numToWords((int)((n / 100000) % 100), "Lakh ");
        // handles digits at thousands and tens thousands
        // places (if any)
        out += numToWords((int)((n / 1000) % 100), "Thousand ");
        // handles digit at hundreds places (if any)
        out += numToWords((int)((n / 100) % 10), "Hundred ");
        if (n > 100 && n % 100 > 0) {
            out += "And ";
        }
        // handles digits at ones and tens places (if any)
        out += numToWords((int)(n % 100), "");
        return out;
    }

    // Driver code
    public static String convertNumberToWords(String amountToConvert)
    {
        // long handles upto 9 digit no
        // change to unsigned long long int to
        // handle more digit number
        String[] amount = amountToConvert.split("\\.");
        long rupees = Long.parseLong(amount[0]);
        long paise = Long.parseLong(amount[1]);
        String amountInWords="";
        if(paise==0L){
            amountInWords = convertToWords(rupees) + "Rupees Only";
        }
        else {
            amountInWords = convertToWords(rupees) + "Rupees And " + convertToWords(paise) + "Paise Only";
        }
        return amountInWords;
    }

}
