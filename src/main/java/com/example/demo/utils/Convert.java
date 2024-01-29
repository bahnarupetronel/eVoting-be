package com.example.demo.utils;

public class Convert {
    public static Long convertToLong(String number) {
        try{
            return  Long.parseLong(number);
        }catch (NumberFormatException e) {
            throw new NumberFormatException(new String("Evenimentul cu id-ul " + number + " nu exista! Id-ul nu are formatul corect."));
        }
    }
}
