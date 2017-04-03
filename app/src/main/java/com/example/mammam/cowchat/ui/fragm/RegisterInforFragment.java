package com.example.mammam.cowchat.ui.fragm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerDbLocal;
import com.example.mammam.cowchat.controll.ManagerStorage;
import com.example.mammam.cowchat.controll.ManagerUser;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.UserChat;
import com.example.mammam.cowchat.ui.activity.LoginActivity;
import com.example.mammam.cowchat.ui.activity.MainActivity;
import com.example.mammam.cowchat.ui.activity.WelcomActivity;
import com.example.mammam.cowchat.ui.interf.ILogin;
import com.example.mammam.cowchat.ui.interf.ISaveInformation;
import com.example.mammam.cowchat.ui.interf.ISavePhoto;
import com.example.mammam.cowchat.ui.interf.ISignup;
import com.squareup.picasso.Picasso;
import java.util.Date;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Mam  Mam on 12/12/2016.
 */

public class RegisterInforFragment extends BaseFragment
        implements View.OnClickListener,IConstand,ISignup,ISaveInformation, ISavePhoto {

    private static final String TAG = "RegisterInforFragment" ;
    private static final int PIC_CROP = 96;
    private ManagerStorage mManagerStorage = ManagerStorage.getInstance();

    private Uri uriImage;
    private String mEmail;
    private String mPassword;
    private EditText edtFirstName;
    private EditText edtLastname;
    private RadioGroup radioGroup;
    private ProgressDialog mDialog;
    private Button btnSignup;
    private ManagerUser mManagerUser;
    private ImageView ivAvatar;
    private TextView tvSelectAvatar;
    private String firstName;
    private String lastName;
    private int gender = 0;
    private ProgressDialog dialog;
    private String id;
    private ManagerDbLocal managerDbLocal = ManagerDbLocal.getINSTANCE();
    private String link;

    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.fragment_register_infor,viewGroup,false);
    }

    @Override
    public void initViews(View view) {
        radioGroup = (RadioGroup) view.findViewById(R.id.rg_parent);
        edtFirstName = (EditText) view.findViewById(R.id.edtFirstNameSU);
        edtFirstName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        edtLastname = (EditText) view.findViewById(R.id.edtLastNameSU);
        edtLastname.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        btnSignup = (Button) view.findViewById(R.id.btnSignup);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAvatarSU);
        tvSelectAvatar = (TextView) view.findViewById(R.id.tvSelectAvatar);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        dialog.setMessage("Registering ... ");


    }

    @Override
    public void initComponents() {
        managerDbLocal.setContext(getContext());
        managerDbLocal.openDatatbase();
        mManagerUser = new ManagerUser(getContext());
        mManagerUser.setiSignup(this);
        mManagerUser.setiSaveInformation(this);
        mEmail = getArguments().getString(EMAIL);
        mPassword = getArguments().getString(PASSWORD);
    }

    @Override
    public void setEventViews() {

        btnSignup.setOnClickListener(this);
        tvSelectAvatar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignup:
                 firstName = edtFirstName.getText().toString().trim();
                 lastName = edtLastname.getText().toString().trim();
                if (firstName.isEmpty() || lastName.isEmpty()){
                    Toast.makeText(getContext(),"First name or last Name is empty !!",Toast.LENGTH_LONG).show();
                    return;
                }
                int idCheck = radioGroup.getCheckedRadioButtonId();
                switch (idCheck){
                    case R.id.rbMale:
                        gender =1;
                        break;
                    case R.id.rbFemale:
                        gender = 0;
                        break;
                }
                String password = getArguments().getString(PASSWORD);
                String email = getArguments().getString(EMAIL);
                mManagerUser.signUpWithEmailAndPassword(email,password);
                dialog.show();

                break;
            case R.id.tvSelectAvatar:
                Log.i(TAG,"Lan1: da select dc anh");
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, RQ_GALL);

                break;
            default:
                break;
        }
    }
    private void performCrop(Uri picUri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setDataAndType(picUri, "image/*");

        cropIntent.putExtra("crop", true);

        //cropIntent.putExtra("aspectX", 9);
        //cropIntent.putExtra("aspectY", 16);

        //cropIntent.putExtra("outputX", 108);
        //cropIntent.putExtra("outputY", 128);

        cropIntent.putExtra("return-data", true);

        startActivityForResult(cropIntent, PIC_CROP);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RQ_GALL == requestCode && resultCode == getActivity().RESULT_OK){
            uriImage = data.getData();
            performCrop(uriImage);
            //Picasso.with(getContext()).load(uriImage).into(ivAvatar);
        } else if (requestCode == PIC_CROP){
            if (data != null) {
                Bundle extras = data.getExtras();
                Bitmap bitmap= extras.getParcelable("data");
                ivAvatar.setImageBitmap(bitmap);
            }
        }
    }



    @Override
    public void signUp(boolean result, String id) {
        Log.d(TAG,"lan2: da sign up");
        mManagerStorage.setiSavePhoto(this);
        mManagerStorage.saveAvater(getBitmap(),uriImage );
        this.id = id;
//        byte[] b = convertImageToByteArray();
//        String avatar = Base64.encodeToString(b,Base64.DEFAULT);
//        UserChat userChat = new UserChat(id,mEmail,
//                mPassword,firstName,lastName,gender,"Nothing",avatar);
//        mManagerUser.saveUserInfor(userChat);
//


//        byte[] bb = Base64.decode(avatar,Base64.DEFAULT);
//        Bitmap bm = BitmapFactory.decodeByteArray(bb,0,bb.length);
//        ivAvatar.setImageBitmap(bm);

    }
    private Bitmap getBitmap(){
        BitmapDrawable drawable = (BitmapDrawable) ivAvatar.getDrawable();
        return  drawable.getBitmap();
    }

    @Override
    public void saveInformation(boolean result) {
        dialog.dismiss();
        if (result){
            // do somthing
            managerDbLocal.saveUser( new UserChat(id,mEmail,mPassword,
                    firstName,lastName,gender,"Nothing",
                    link,currentDate()),
                    ManagerStorage.scaleDown(getBitmap(),(128*4),true)
                    );
            Log.d("luc nao ms vao dc day","sdsadsdasdas");
            LoginActivity.saveSharedPrefrence(getContext(),mEmail,mPassword);
            mManagerUser.changeStatus(1);
            mManagerUser.setiLogin(new ILogin() {
                @Override
                public void login(boolean isSuc, String notif) {
                    moveMainActivity();
                }
            });
            mManagerUser.login(mEmail,mPassword,false);
//            Intent intent = new Intent(getContext(),WelcomActivity.class);
//            intent.putExtra(SAVE_USER,mEmail);
//            intent.putExtra(SAVE_PASSWORD,mPassword);
//            intent.setAction(WELCOME);
//            startActivity(intent);


        }
        else {
            Toast.makeText(getContext(),"Error register ! Please try again",Toast.LENGTH_LONG).show();
        }
    }
    public void moveMainActivity(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        getContext().startActivity(intent);
    }

    private String currentDate(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);
        return  dateFormat.format(date);
    }

    @Override
    public void savePhoto(Uri uri) {
        Log.d(TAG,"lan3: da save");
        link = String.valueOf(uri);
        UserChat userChat = new UserChat(id,mEmail,mPassword,
                firstName,lastName,gender,"Nothing",link,currentDate());

        mManagerUser.saveUserInfor(userChat);
    }
}
