package com.e.digitaltechnologies;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

public class BaseClass {

    private static final BaseClass ourInstance = new BaseClass();
    public   Cart cart=new Cart();
    public   Selected selected=new Selected();
    public  CakeMenu cakeMenu=new CakeMenu();

    public BaseClass() {


    }

    public static BaseClass getOurInstance() {
        return ourInstance;
    }
}
