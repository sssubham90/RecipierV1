package com.hackdav.recipierv1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText EtLogEmail, EtLogPass;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button btnSignin, btnRegister;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        if(intent.hasExtra("Email")) {
            String Email_User_From_register = intent.getStringExtra("Email");
            String PassUser_From_register = intent.getStringExtra("Pass");
            EtLogEmail.setText(Email_User_From_register);
            EtLogPass.setText(PassUser_From_register);
        }
        EtLogEmail = (EditText) findViewById(R.id.LogEmail);
        EtLogPass = (EditText) findViewById(R.id.LogPass);
        btnSignin = (Button) findViewById(R.id.btnSignin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnSignin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        EtLogPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View view) {
        String strEmail="", strPass;
        switch (view.getId()) {
            case R.id.btnSignin:
                strEmail = EtLogEmail.getText().toString();
                strPass = EtLogPass.getText().toString();
                if (strEmail.matches("")) {
                    alertDialog("Email is required");
                    break;
                }
                else if (strPass.matches("")) {
                    alertDialog("Password is required");
                    break;
                }
                else if (!isValidEmailAddress(strEmail)) {
                    alertDialog("Invalid Email Id");
                    break;
                }
                else {
                        Login(strEmail, strPass);
                }
                break;
            case R.id.btnRegister:
                Intent register = new Intent(this, RegisterActivity.class);
                startActivity(register);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.btnForgot:
                if (strEmail.matches("")) {
                    alertDialog("Email is required");
                    break;
                }
                else{
                    forgotPassword(strEmail);
                    break;}
        }
    }

    public void alertDialog(String Message) {

        new AlertDialog.Builder(this).setTitle("Recipier Alert").setMessage(Message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    protected void Login(final String Email,final String Password){
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            SessionManager session = new SessionManager(getApplicationContext());
                            session.createLoginSession(Email);
                            Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mainActivity);
                            overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
                        }
                    }
                });
    }

    protected void forgotPassword(final String Email){
        FirebaseAuth.getInstance().sendPasswordResetEmail(Email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Password Reset Email sent to your ID.");
                        }
                        else {
                            Log.d(TAG, "Failed to send reset email!");
                        }
                    }
                });
    }

}