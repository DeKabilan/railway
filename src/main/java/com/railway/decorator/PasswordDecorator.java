package com.railway.decorator;

public class PasswordDecorator {
    public int hash(String input){
        int hash = 7;
        for(char c : input.toCharArray()){
            hash = hash*31 + c;
        }
        return hash;
    }

}
