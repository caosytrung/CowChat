package com.example.mammam.cowchat.ui.fragm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerDbLocal;
import com.example.mammam.cowchat.controll.ManagerUser;
import com.example.mammam.cowchat.models.UserLocal;
import com.example.mammam.cowchat.ui.activity.LoginActivity;
import com.example.mammam.cowchat.ui.interf.IDeleteTmpAccount;
import com.example.mammam.cowchat.ui.interf.INextFragment;
import com.example.mammam.cowchat.ui.interf.ISignup;


/**
 * Created by Mam  Mam on 12/12/2016.
 */

public class RegisterAcountFragment extends  BaseFragment
        implements View.OnClickListener,ISignup,IDeleteTmpAccount {

    public static final String  TAG = "RegisterAcountFragment";

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnNextStep;
    private TextView tvToLogin;
    private String mEmail;
    private String mPassword;
    private ManagerUser mManagerUser;
    private INextFragment iNextFragment;
    private ProgressDialog dialog;

    public void setiNextFragment(INextFragment iNextFragment) {
        this.iNextFragment = iNextFragment;
    }

    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.fragment_register_email,viewGroup,false);
    }

    @Override
    public void initViews(View view) {
        edtEmail = (EditText) view.findViewById(R.id.edtEmailSignup);
        edtPassword = (EditText) view.findViewById(R.id.edtPasswordSignup);
        btnNextStep = (Button) view.findViewById(R.id.btnNextStep);
        tvToLogin = (TextView) view.findViewById(R.id.tvNextLogin);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        dialog.setMessage("Registering  ...");

    }

    @Override
    public void initComponents() {
     //   Log.d(TAG,local.getEmail());
        mManagerUser = new ManagerUser(getContext());
        mManagerUser.setiSignup(this);
        mManagerUser.setiDeleteTmpAccount(this);
    }

    @Override
    public void setEventViews() {
        btnNextStep.setOnClickListener(this);
        tvToLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnNextStep:
                Log.d(TAG,"Da vao next");
                 mEmail = edtEmail.getText().toString().trim();
                 mPassword = edtPassword.getText().toString().trim();

                if (mEmail.isEmpty() || mPassword.isEmpty()){
                    Toast.makeText(getContext(),
                            "Email or password are empty .Please try again .. !",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else if (mPassword.length() < 6 ){
                    Toast.makeText(getContext(),
                            "Please password with length more than 6",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
                    Toast.makeText(getContext(),
                            "Please input valid email !",Toast.LENGTH_LONG).show();
                    return;
                }
                dialog.show();
                dialog.setCancelable(false);
                mManagerUser.signUpWithEmailAndPassword(mEmail,mPassword);


                break;
            case R.id.tvNextLogin:

                startActivity(new Intent(getContext(),
                        LoginActivity.class));
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void signUp(boolean result,String id) {

        if (result){
            mManagerUser.deleteTmpAccount(mEmail,mPassword);
        }
        else {
            dialog.dismiss();
            Log.d(TAG,"Vao Delete");
            Toast.makeText(getContext(),
                    "Could not register account,Please try again !",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void deleteTmpAccount(boolean result) {
        dialog.dismiss();
        if (result){
            iNextFragment.nextFragmentInfor(mEmail,mPassword);
        }
        else {
            Log.d(TAG,"Vao Delete");
            Toast.makeText(getContext(),
                    "Could not register account,Please try again !",
                    Toast.LENGTH_LONG).show();
        }
    }
}
