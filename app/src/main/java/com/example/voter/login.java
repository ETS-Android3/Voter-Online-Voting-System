package com.example.voter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    Button islogin, isSignup;
    EditText username_id, username_pass;

    private static String full_name,mob_num,email_id,user_name;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        setTitle("Login Page");
        islogin=findViewById(R.id.login_btn);
        isSignup=findViewById(R.id.signup_btn);

        username_id=(EditText) findViewById(R.id.username_id);
        username_pass=(EditText) findViewById(R.id.password_id);

        islogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_Id=username_id.getText().toString();
                String password_Id=username_pass.getText().toString();

                if(!username_Id.isEmpty())
                {
                    username_id.setError(null);

                    if(!password_Id.isEmpty())
                    {
                        username_pass.setError(null);


                         String username_Id_curr=username_id.getText().toString();
                         String password_Id_curr=username_pass.getText().toString();

                        FirebaseDatabase firebaseDatabase;
                        DatabaseReference reference;

                        firebaseDatabase=FirebaseDatabase.getInstance();
                        reference=firebaseDatabase.getReference("UserData");

                        Query check_user=reference.orderByChild("username").equalTo(username_Id_curr);

                        check_user.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists())
                                {
                                    username_id.setError(null);


                                    String password_check=dataSnapshot.child(username_Id_curr).child("password").getValue(String.class);
                                    if(password_check.equals(password_Id_curr))
                                    {
                                        username_pass.setError(null);
                                        user_name=username_Id_curr;
                                        full_name=dataSnapshot.child(username_Id_curr).child("fullname").getValue(String.class);
                                        mob_num=dataSnapshot.child(username_Id_curr).child("phonenum").getValue(String.class);
                                        email_id=dataSnapshot.child(username_Id_curr).child("email").getValue(String.class);

                                        Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_SHORT).show();

                                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }else
                                    {
                                        username_pass.setError("Wrong Password!");
                                    }
                                }
                                else{
                                    username_id.setError("Invalid Username2");
                                }
                            }



                            @Override
                            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError databaseError) {

                            }
                        });

                    }
                    else
                    {
                        username_pass.setError("Please Enter Valid Password");
                    }
                }
                else{
                    username_id.setError("Please Enter Valid Username");
                }
            }
        });
        isSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),signup.class);
                startActivity(intent);
            }
        });
    }

    public static String getFull_name()
    {
        return  full_name;
    }
    public static String getMob_num()
    {
        return  mob_num;
    }
    public static String getEmail_id()
    {
        return  email_id;
    }
    public static String getUser_name(){return user_name;}
}

