package com.example.mammam.cowchat.controll;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.models.Cond;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.UserLocal;
import com.example.mammam.cowchat.ui.activity.GlMapActivity;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by dee on 20/02/2017.
 */

public class ManagerMap implements
    GoogleMap.InfoWindowAdapter,LocationListener,IConstand, ChildEventListener {

    private Context mContext;
    private GoogleMap mGoogleMap;
    private Marker mMarker;
    private Marker fMaker;
    private LocationManager mLocationManager;
    private Geocoder mGeocoder;
    private PolygonOptions mPolygonOptions;
    private GlMapActivity activity;
    private String roomId;
    private DatabaseReference childF;
    private DatabaseReference childC;
    private String currentID;
    private String friendId;
    private UserLocal userLocal;


    private static double a;
    public ManagerMap(GoogleMap googleMap,Context context,GlMapActivity glMapActivity){
        mGoogleMap = googleMap;
        mContext =context;
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        mGeocoder = new Geocoder(mContext, Locale.getDefault());
        activity = glMapActivity;
        ManagerDbLocal managerDbLocal = ManagerDbLocal.getINSTANCE();
        managerDbLocal.setContext(mContext);
        managerDbLocal.openDatatbase();
         userLocal = managerDbLocal.getUser();
        currentID = userLocal.getId();
        managerDbLocal.closeDatabase();
        initMap(googleMap);





    }

    public void setRoomId(String roomId,String friendId) {
        this.roomId = roomId;
        childF = FirebaseDatabase.getInstance().
                getReference().child(LIST_ROOM).
                child(roomId).child("LOCATION").
                child(friendId);
        childC = FirebaseDatabase.getInstance().
                getReference().child(LIST_ROOM).
                child(roomId).child("LOCATION");
        this.friendId = friendId;

    }

    private void initMap(GoogleMap googleMap){
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setInfoWindowAdapter(this);

        Criteria criteria
                 = new Criteria();
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(true);

        String provide = mLocationManager.getBestProvider(criteria,true);

        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
            Log.d("aaaaasda","mnbbbaasd");


        } else {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivityForResult(intent,1);
        }


    }

    private Marker drawMarker(Location location){
        LatLng latLng = new LatLng(location.getLatitude(),
                location.getLongitude());

        CameraPosition position = new CameraPosition(latLng,15,0,0);
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));

        Bitmap tmp = getCroppedBitmap(getResizedBitmap(userLocal.
                getBitMapAvatar(),80,80));

        BitmapDescriptor icin = BitmapDescriptorFactory.fromBitmap(tmp);
        MarkerOptions options =     new MarkerOptions();
        options.position(latLng);
        options.title("MyLocation");
        options.snippet(getStringLocation(latLng));

        options.icon(icin);
        return mGoogleMap.addMarker(options);
    }
    private Marker drawFMarker(Cond location){
        if (null == location){

        }
        LatLng latLng = new LatLng(location.getLat(),
                location.getLon());


        Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.coww);

        BitmapDescriptor icin = BitmapDescriptorFactory.
                fromBitmap(getCroppedBitmap(getResizedBitmap(largeIcon,80,80)));

        MarkerOptions options =     new MarkerOptions();
        options.position(latLng);
        options.title("MyLocation");
        options.snippet(getStringLocation(latLng));

        options.icon(icin);
        return mGoogleMap.addMarker(options);
    }



    public String getStringLocation(LatLng latLng) {
        try {
            List<android.location.Address> addresses =
                    mGeocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            String ad = "";
            if (addresses.size() == 1) {
                ad = addresses.get(0).getAddressLine(0);
                ad = ad + ", " + addresses.get(0).getAddressLine(1);
                ad = ad + ", " + addresses.get(0).getAddressLine(2);
            }
            //   Toast.makeText(mContext,"zzzStr",Toast.LENGTH_LONG).show();
            return ad;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }



    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onLocationChanged(final Location location) {
        Cond coordinate = new Cond(location.getLatitude(),location.getLongitude());
        DatabaseReference cc = FirebaseDatabase.getInstance().
                getReference().child(LIST_ROOM).
                child(roomId).child("LOCATION").child(currentID);
        cc.setValue(coordinate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("cmddddddd","ddd");
                }
            }
        });


        Log.d("zzzzzz","toadoooooooooooooo");
        LatLng latLng = new LatLng(location.getLatitude(),
                location.getLongitude());
        if (mMarker == null) {
            Log.d("ccczzzz","zzzzzzzzzz");
            mMarker = drawMarker(location);

            DatabaseReference zz = FirebaseDatabase.getInstance().
                    getReference().child(LIST_ROOM).
                    child(roomId).child("LOCATION").child(friendId);
            zz.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Cond cond = dataSnapshot.getValue(Cond.class);
                    Log.d("zzzzzccckey",dataSnapshot.getKey());
                    if (cond == null){
                        return;
                    }
                    fMaker = drawFMarker(cond);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else {
            //  Toast.makeText(mContext,"zzz",Toast.LENGTH_LONG).show();
            mMarker.setPosition(latLng);
            mMarker.setSnippet(getStringLocation(latLng));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        }
        if (fMaker == null) {
            Log.d("ccczzzz","zzzzzzzzzz");
            fMaker = drawFMarker(new Cond(location.getLatitude(),location.getLongitude()));


        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.d("aaaaaaaaaaaaaqqqqq",dataSnapshot.getKey());
        Toast.makeText(mContext,"aaaaa",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Toast.makeText(mContext,"changeee",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
