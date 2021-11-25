package com.example.voter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class end_Activity extends AppCompatActivity {
    private Button back;
    private TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_activity);
          Bundle b=getIntent().getExtras();
        String id=b.getString("id");
        back=(Button)findViewById(R.id.back);
        display=(TextView)findViewById(R.id.ID);
        String msg="You voted to candidate ID :"+id+ " in-room ID :"+MainActivity.getRoom_identity()+" !";
        display.setText(msg);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
