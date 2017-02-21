package com.example.mammam.cowchat.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerDbLocal;
import com.example.mammam.cowchat.controll.ManagerUser;
import com.example.mammam.cowchat.models.IConstand;
import com.example.mammam.cowchat.models.UserChat;
import com.example.mammam.cowchat.models.UserLocal;
import com.example.mammam.cowchat.service.CallService;
import com.example.mammam.cowchat.ui.interf.ILogin;

/**
 * Created by Mam  Mam on 12/11/2016.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,IConstand,ILogin {
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView tvNextSignup;
    private ManagerUser mManagerUser;
    String email;
    String passWord;
    private ProgressDialog dialog;

    @Override
    protected void initComponents() {
        mManagerUser = new ManagerUser(this);
        mManagerUser.setiLogin(this);
    }

    @Override
    protected void initViews() {
        edtPassword = (EditText) findViewById(R.id.edtPasswordLogin);
        edtEmail = (EditText) findViewById(R.id.edtEmailLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvNextSignup = (TextView) findViewById(R.id.tvNextSignup);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading ...");

    }

    @Override
    protected void setEventViews() {
        tvNextSignup.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    protected void setViewRoot() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                 email = edtEmail.getText().toString().trim();
                 passWord = edtPassword.getText().toString().trim();
                if (email.isEmpty() || passWord.isEmpty()){
                    Toast.makeText(this,"Email or Passwork are Empty, Please Try Again !!",Toast.LENGTH_LONG).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(this,"Please Input invalid Email ",Toast.LENGTH_LONG).show();
                }
                else {

                    dialog.show();
                   mManagerUser.login(email,passWord,false);
                }



//                SharedPreferences sharedPreferences = getSharedPreferences(LOGIN,MODE_PRIVATE);
//                if (null != sharedPreferences){
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString(SAVE_USER,edtEmail.getText().toString());
//                    editor.putString(SAVE_PASSWORD,edtPassword.getText().toString());
//                    editor.commit();
//                }
//                else {
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString(SAVE_USER,edtEmail.getText().toString());
//                    editor.putString(SAVE_PASSWORD,edtPassword.getText().toString());
//                    editor.commit();
//                }
                break;
            case R.id.tvNextSignup:
                startActivity(new Intent(this,SignupActivity.class));
                break;
            default:
                break;
        }
    }
    public static void saveSharedPrefrence(Context context,String email,String passWord){
        SharedPreferences sharedPreferences = context.
                getSharedPreferences(LOGIN,MODE_PRIVATE);
        SharedPreferences.Editor editor =
                sharedPreferences.edit();

        editor.putString(SAVE_USER,email);
        editor.putString(SAVE_PASSWORD,passWord);
        editor.commit();
    }

    @Override
    public void login(boolean isSuc, String notif) {
        dialog.dismiss();
        if (isSuc){
            saveSharedPrefrence(this,email,passWord);
            mManagerUser.changeStatus(1);
            Intent intent = new Intent(this,MainActivity.class);
//            intent.setAction(WELCOME);
//            intent.putExtra(SAVE_USER,email);
//            intent.putExtra(SAVE_PASSWORD,passWord);
            finish();
            startService(new Intent(this, CallService.class));
            startActivity(intent);


        }
        else {
            Toast.makeText(this,"Invalid Login or password.",Toast.LENGTH_LONG).show();
        }
    }
}
