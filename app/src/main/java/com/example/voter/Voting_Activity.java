package com.example.voter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Voting_Activity extends AppCompatActivity {

    private TextView bb_pn, bb_n, bb_id;
    private ImageView bb_pi;
    private Button bb_btn;
    private String room_id;
    private List<candidate_item> candidates = new ArrayList<>();
    private ListView lv;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private String phoneNo, msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.votingactivity);

        room_id = MainActivity.getRoom_identity();

        FirebaseDatabase firebaseDatabase;
        DatabaseReference reference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Voting_Room").child(room_id).child("candidate");
        lv = (ListView) findViewById(R.id.li);

        Log.v("------------------>", reference.toString());
        Log.v("------------------>", reference.getKey());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Log.v("------------------>", snapshot.getKey());
                for (DataSnapshot ds : snapshot.getChildren()) {
                    candidate_item ci = new candidate_item();
                    int it = 0;
                    for (DataSnapshot ds2 : ds.getChildren()) {
                        if (it == 0)
                            ci.setId(ds2.getValue(String.class));
                        else if (it == 1)
                            ci.setName(ds2.getValue(String.class));
                        else if (it == 2)
                            ci.setParty(ds2.getValue(String.class));
                        else
                            ci.setImg(ds2.getValue(String.class));
                        it++;
                        Log.v("------------------>", ds2.getValue(String.class));
                    }
                    candidates.add(ci);
                }
                myAdapter adapter = new myAdapter(getApplicationContext(), R.layout.ballot_box, candidates);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast t = Toast.makeText(getApplicationContext(), "Clicked at " + position + "!", Toast.LENGTH_LONG);
                t.show();
                Log.v("-------------> ", "Cliked!");
                new AlertDialog.Builder(Voting_Activity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("You Want to Submit Your Vote?")
                        .setMessage("Your are Voting To " + candidates.get(position).getName() + " !")
                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Whatever...
                                String user = login.getUser_name();
                                FirebaseDatabase firebaseDatabase;
                                Task<Void> reference;

                                firebaseDatabase = FirebaseDatabase.getInstance();
                                reference = firebaseDatabase.getReference("Voting_Room").child(room_id).child("voter").child(user).child("voted").setValue(position);
                                Intent intent = new Intent(getApplicationContext(), end_Activity.class);
                                intent.putExtra("id",candidates.get(position).getId() );
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();
            }
        });
    }


}