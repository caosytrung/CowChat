package com.example.mammam.cowchat.ui.fragm;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerDrawData;
import com.example.mammam.cowchat.models.ItemPenColor;
import com.example.mammam.cowchat.models.ItemPenWitdth;
import com.example.mammam.cowchat.ui.activity.ConsersationActivity;
import com.example.mammam.cowchat.ui.adapter.BGColorAdapter;
import com.example.mammam.cowchat.ui.adapter.PenColorAdapter;
import com.example.mammam.cowchat.ui.adapter.PenWidthAdapter;
import com.example.mammam.cowchat.ui.asset.MrgTypeFace;
import com.example.mammam.cowchat.ui.custom.DrawingView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by dee on 10/03/2017.
 */

public class DrawFragment extends BaseFragment implements View.OnClickListener {
    private TextView tvBack;
    private Spinner spPenWidth;
    private Spinner spPenColor;
    private Spinner spBackground;
    private List<ItemPenWitdth> mItemPenWitdths;
    private List<ItemPenColor> mItemBGColors;
    private List<ItemPenColor> mItemPenColors;

    private PenWidthAdapter mPenWidthAdapter;
    private BGColorAdapter mBGColorAdapter;
    private PenColorAdapter mPenColorAdapter;

    private TextView tvbackDraw;
    private DrawingView dvDraw;
    private TextView tvUndo;
    private TextView tvSend;
    private ImageView ivDelete;



    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.fragment_draw,viewGroup,false);
    }

    @Override
    public void initViews(View view) {
        tvBack = (TextView) view.findViewById(R.id.tvBackDraw);
        spBackground = (Spinner) view.findViewById(R.id.spPenColorBackGround);
        spPenWidth = (Spinner) view.findViewById(R.id.spPenWidth);
        spPenColor = (Spinner) view.findViewById(R.id.ivPenColor) ;

        tvbackDraw = (TextView) view.findViewById(R.id.tvBackDraw);
        MrgTypeFace.setFontAnswesSome(tvbackDraw,getContext());
        tvbackDraw.setText("\uf053");
        dvDraw = (DrawingView) view.findViewById(R.id.dvDraw);
        tvUndo = (TextView) view.findViewById(R.id.tvUndoDraw);
        tvSend = (TextView) view.findViewById(R.id.tvSendDraw);
        ivDelete = (ImageView) view.findViewById(R.id.ivDeleteDraw);

        spPenColor.setAdapter(mPenColorAdapter);
        spPenWidth.setAdapter(mPenWidthAdapter);
        spBackground.setAdapter(mBGColorAdapter);


    }

    @Override
    public void initComponents() {
        mItemBGColors = ManagerDrawData.getBGColors();
        mItemPenWitdths = ManagerDrawData.getItemPenWidth();
        mItemPenColors = ManagerDrawData.getItemPenColors();

        mPenWidthAdapter = new PenWidthAdapter(getContext());
        mPenWidthAdapter.setItemPenWitdths(mItemPenWitdths);
        mPenWidthAdapter.notifyDataSetChanged();
        mBGColorAdapter = new BGColorAdapter(getContext());
        mBGColorAdapter.setItemPenWitdths(mItemBGColors);
        mBGColorAdapter.notifyDataSetChanged();
        mPenColorAdapter = new PenColorAdapter(getContext(),mItemPenColors);
        mPenColorAdapter.notifyDataSetChanged();


    }

    @Override
    public void setEventViews() {
        tvUndo.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        tvSend.setOnClickListener(this);
        spPenWidth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ItemPenWitdth itemPenWitdth = mItemPenWitdths.get(position);
                dvDraw.setUpWidthPaint(itemPenWitdth.getWidth());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spBackground.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dvDraw.setBackgroundColor(Color.
                        parseColor(mItemBGColors.
                                get(position).getColor()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spPenColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dvDraw.setUpColorPain(mItemPenColors.get(position).getColor());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public  Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap( dvDraw.w,
                dvDraw
                .h, Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }
    Bitmap bitmap  =null;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvUndoDraw:
                dvDraw.undo();
                break;
            case R.id.tvSendDraw:

                dvDraw.post(new Runnable() {
                    @Override
                    public void run() {
                        bitmap = loadBitmapFromView(dvDraw);
//                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                        byte[] data = baos.toByteArray();
                        ConsersationActivity consersationActivity = (ConsersationActivity)getActivity();
                        consersationActivity.remoteFragmentConv(bitmap);
                    }
                });
                dvDraw.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
                    @Override
                    public void onGlobalFocusChanged(View oldFocus, View newFocus) {

                    }
                });


                break;
            case R.id.ivDeleteDraw:
                dvDraw.delete();
                break;
        }
    }
}
