package com.example.voter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    private EditText room_id,room_pass;
    private ProgressBar pb;
    public static String room_identity ,room_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout ll=(LinearLayout)findViewById(R.id.lay_1);
        TextView tv=(TextView) findViewById(R.id.text) ;
        Button btn=(Button) findViewById(R.id.but1);
        room_id=(EditText)findViewById(R.id.username_room_id);
        room_pass=(EditText)findViewById(R.id.password_room_id);

        String fn=login.getFull_name();
        tv.setText("Hello "+fn);
        String user_name=login.getUser_name();



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username_Id=room_id.getText().toString();
                String password_Id=room_pass.getText().toString();

                if(!username_Id.isEmpty())
                {
                    room_id.setError(null);

                    if(!password_Id.isEmpty())
                    {
                        room_pass.setError(null);
                        String username_Id_curr=room_id.getText().toString();
                        String password_Id_curr=room_pass.getText().toString();

                        FirebaseDatabase firebaseDatabase;
                        DatabaseReference reference;

                        firebaseDatabase=FirebaseDatabase.getInstance();
                        reference=firebaseDatabase.getReference("Voting_Room");

                        Query check_user=reference.orderByChild("room_id").equalTo(username_Id_curr);

                        check_user.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists())
                                {
                                    room_id.setError(null);
                                    String password_check=dataSnapshot.child(username_Id_curr).child("room_pass").getValue(String.class);
                                    if(password_check.equals(password_Id_curr))
                                    {
                                        room_pass.setError(null);
                                        boolean started_check=dataSnapshot.child(username_Id_curr).child("started").getValue(Boolean.class);
                                        if(started_check) {

                                            DataSnapshot ds=dataSnapshot.child(username_Id_curr).child("voter");
                                            boolean find=true;
                                            int voted = 0;
                                            Log.v("MyTag-----",ds.toString());
                                            for(DataSnapshot cs:ds.getChildren())
                                            {
                                                Log.v("MyTag-----",cs.getKey());
                                                if(cs.getKey().equals(user_name))
                                                {
                                                    voted=cs.child("voted").getValue(Integer.class);
                                                    find=false;
                                                    break;
                                                }
                                            }
                                            if(find){
                                                Toast.makeText(getApplicationContext(), "You are not registered for this room!", Toast.LENGTH_SHORT).show();
                                            }
                                            else if(voted==-1) {

                                                room_identity = username_Id_curr;
                                                room_img=dataSnapshot.child(username_Id_curr).child("voter").child(user_name).child("image_url").getValue(String.class);
                                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), send_otp.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "You already voted from this room!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "This Room is currently not Running!", Toast.LENGTH_SHORT).show();
                                        }
                                    }else
                                    {
                                        room_pass.setError("Wrong Room Password!");
                                    }
                                }
                                else{
                                    room_id.setError("Invalid room ID");
                                }
                            }



                            @Override
                            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError databaseError) {

                            }
                        });

                    }
                    else
                    {
                        room_pass.setError("Please Enter Valid Room Password");
                    }
                }
                else{
                    room_id.setError("Please Enter Valid Room ID");
                }
            }

        });
    }


    public static String getRoom_identity()
    {
        return room_identity;
    }

    public static String getRoom_img()
    {
        return room_img;
    }


}