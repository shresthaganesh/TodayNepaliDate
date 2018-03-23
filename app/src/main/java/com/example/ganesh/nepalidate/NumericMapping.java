package com.example.ganesh.nepalidate;

/**
 * Created by ganesh on 3/22/2018.
 */

//This class holds the mapping of English Number to Nepali Number with its corresponding Resource value

public class NumericMapping {
    int _english, _resource;
    String  _nepali;

    NumericMapping(int english, String nepali, int resource){
        _english=english;
        _nepali=nepali;
        _resource=resource;
    }

    public int getEnglish(){
        return _english;
    }

    public String getNepali(){
        return _nepali;
    }

    public  int getResource(){
        return _resource;
    }

}
