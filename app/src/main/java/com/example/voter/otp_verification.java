package com.example.voter;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

public class otp_verification extends AppCompatActivity {
    private EditText dig1, dig2, dig3, dig4, dig5, dig6;
    private TextView resend;
    Button btn_auth;
    private String getotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpvarification);
        dig1 = (EditText) findViewById(R.id.dig1);
        dig2 = (EditText) findViewById(R.id.dig2);
        dig3 = (EditText) findViewById(R.id.dig3);
        dig4 = (EditText) findViewById(R.id.dig4);
        dig5 = (EditText) findViewById(R.id.dig5);
        dig6 = (EditText) findViewById(R.id.dig6);
        btn_auth = (Button) findViewById(R.id.verify);
        resend=(TextView)findViewById(R.id.resend);

        getotp = getIntent().getStringExtra("otp");
        btn_auth = (Button) findViewById(R.id.verify);
        ProgressBar progressBar=(ProgressBar)findViewById(R.id.pb_otp);


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),send_otp.class);
                startActivity(intent);
                finish();
            }
        });

        btn_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!dig1.getText().toString().trim().isEmpty() && !dig2.getText().toString().trim().isEmpty() && !dig3.getText().toString().trim().isEmpty() && !dig4.getText().toString().trim().isEmpty() && !dig5.getText().toString().trim().isEmpty() && !dig6.getText().toString().trim().isEmpty()) {
                    String user_otp = dig1.getText().toString() + dig2.getText().toString() + dig3.getText().toString() + dig4.getText().toString() + dig5.getText().toString() + dig6.getText().toString();
                    if (getotp != null) {

                        progressBar.setVisibility(View.VISIBLE);
                        btn_auth.setVisibility(View.GONE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getotp, user_otp);

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    progressBar.setVisibility(View.GONE);
                                    btn_auth.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), "OTP Verified!!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), face_detection.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    btn_auth.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), "OTP Verification Failed,Please Enter Correct OTP", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Check Internate Connection!!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Enter All Number", Toast.LENGTH_LONG).show();
                }
            }
        });

        numberOnmove();
    }

    private void numberOnmove() {

        dig1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    dig2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dig2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    dig3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dig3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    dig4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dig4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    dig5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dig5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    dig6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
