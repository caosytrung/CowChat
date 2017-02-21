package com.example.mammam.cowchat.ui.asset;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Mam  Mam on 12/21/2016.
 */

public class MrgTypeFace {
    public static void setFontAnswesSome(TextView tv, Context context){
        Typeface tp = Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
        tv.setTypeface(tp);
    }
    public static void setFontAnswesSomeBt(Button tv, Context context){
        Typeface tp = Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
        tv.setTypeface(tp);
    }
}
