package com.hackdav.recipierv1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends Activity {
    EditText UserName, Email, Phone, Pass1, Pass2;
    String strName, strEmail, strPhone,strPass, strPass2;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button Register =(Button) findViewById(R.id.btnRegister);
        Register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                strName = UserName.getText().toString();
                strEmail = Email.getText().toString();
                strPhone = Phone.getText().toString();
                strPass = Pass1.getText().toString();
                strPass2 = Pass2.getText().toString();
                if (strName.matches("")) {
                    alertDialog("Username is required");
                }else if(strEmail.matches("")) {
                    alertDialog("Email is required");
                }else if(strPhone.matches("")) {
                    alertDialog("Phone is required");
                }else if(strPass.matches("")) {
                    alertDialog("Password is required");
                }
                else if(!isValidEmailAddress(strEmail)) {
                    alertDialog("Introduce a valid email format");
                }
                else if(!strPass.equals(strPass2)) {
                    alertDialog("Password not match");
                }
                else{
                    Register(strName, strEmail, strPhone, strPass);
                }
            }
        });
        mAuth = FirebaseAuth.getInstance();
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
        UserName = (EditText)findViewById(R.id.RegUsername);
        Email = (EditText)findViewById(R.id.RegEmail);
        Phone = (EditText)findViewById(R.id.RegPhone);
        Pass1 = (EditText)findViewById(R.id.RegPass);
        Pass2 = (EditText)findViewById(R.id.RegPass2);
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

    public void alertDialog(String Message){
        new AlertDialog.Builder(this)
                .setTitle("Recipier Message")
                .setMessage(Message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    protected void Register(final String Name, final String Email,final String Phone,final String Password){
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(RegisterActivity.this, "Verify your email id with the link we sent.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}