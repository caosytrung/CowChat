package com.example.mammam.cowchat.ui.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by Mam  Mam on 12/28/2016.
 */

public class ZoomView extends View {
    private Drawable image;
    private int zoomControler=20;

    public ZoomView(Context context,Drawable drawable) {
        super(context);
        image = drawable;
    }
    public void setBitmap(Bitmap bitmap){

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        image.setBounds((getWidth()/2)-zoomControler, (getHeight()/2)-zoomControler, (getWidth()/2)+zoomControler, (getHeight()/2)+zoomControler);
        image.draw(canvas);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_DPAD_UP){
            // zoom in
            zoomControler+=10;
        }
        if(keyCode==KeyEvent.KEYCODE_DPAD_DOWN){
            // zoom out
            zoomControler-=10;
        }
        if(zoomControler<10){
            zoomControler=10;
        }

        invalidate();
        return true;
    }
}
