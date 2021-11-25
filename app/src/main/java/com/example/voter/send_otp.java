package com.example.voter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class send_otp extends AppCompatActivity
{
    Button btn_sendotp;
    TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendotp);

        btn_sendotp = (Button) findViewById(R.id.send_otp);
        text2 = (TextView) findViewById(R.id.text2);

        String phone_num=login.getMob_num();
        String lastdig=phone_num.substring(8,10);

        text2.setText("We will send otp to mobile number ends with "+lastdig);

        ProgressBar progressBar=(ProgressBar)findViewById(R.id.pb_sendotp);

        btn_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                btn_sendotp.setVisibility(View.GONE);


                if(phone_num.length()==10)
                {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + phone_num, 60, TimeUnit.SECONDS, send_otp.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {

                                    progressBar.setVisibility(View.GONE);
                                    btn_sendotp.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {

                                    progressBar.setVisibility(View.GONE);
                                    btn_sendotp.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCodeSent(@NotNull String real_otp, @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                                {
                                    super.onCodeSent(real_otp, forceResendingToken);

                                    progressBar.setVisibility(View.GONE);
                                    btn_sendotp.setVisibility(View.VISIBLE);
                                    Intent intent=new Intent(getApplicationContext(),otp_verification.class);
                                    intent.putExtra("otp",real_otp);
                                    startActivity(intent);
                                    finish();
                                }


                            }
                    );
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Phone Number",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
