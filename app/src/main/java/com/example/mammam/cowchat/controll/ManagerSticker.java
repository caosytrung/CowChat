package com.example.mammam.cowchat.controll;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dee on 16/02/2017.
 */

public class ManagerSticker  {
    private static ManagerSticker INSTANCE;
    private ArrayList<String> listMeep;
    public ArrayList<String> listDog;

    private ManagerSticker(){

    }

    public void addMeep(){


    }

    public List<String> getListDove(Context context){
        List<String> listDove = new ArrayList<>();
        try {
            String[] arrDove = context.getAssets().list("doves");
            listDove = Arrays.asList(arrDove);
            return fixPath("doves",listDove);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listDove;
    }

    public List<String> getListDog(Context context){
        List<String> listDofg = new ArrayList<>();
        try {
            String[] arrDog = context.getAssets().list("dog");
            listDofg = Arrays.asList(arrDog);
            return fixPath("dog",listDofg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listDofg;
    }

    public List<String> getListMeep(Context context){
        List<String> listMeep = new ArrayList<>();
        try {
            String[] arrDog = context.getAssets().list("meep");
            listMeep = Arrays.asList(arrDog);
            return fixPath("meep",listMeep);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMeep;
    }

    public List<String> getListCCC(Context context){
        List<String> listMeep = new ArrayList<>();
        try {
            String[] arrDog = context.getAssets().list("ccc");
            listMeep = Arrays.asList(arrDog);
            return fixPath("ccc",listMeep);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listMeep;
    }

    private List<String> fixPath(String path,List<String> list){
        for (int i = 0 ; i< list.size(); i++){
            String tmpStr = list.get(i);
            String resultStr = path + "/" + tmpStr;
            list.set(i,resultStr);
        }
        return list;
    }

    public void addDog(){
       // listDog.add("dg");
    }

    public static ManagerSticker getINSTANCE(){
        if (null == INSTANCE){
            INSTANCE = new ManagerSticker();
            return INSTANCE;
        }
        return INSTANCE;
    }
}
