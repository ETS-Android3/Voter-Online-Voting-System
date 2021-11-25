package com.example.voter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class myAdapter extends ArrayAdapter {
    private Context context;
    private List<candidate_item> arrayList;

    public myAdapter(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        arrayList = objects;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.ballot_box, null);
        viewHolder viewholder = new viewHolder();
        viewholder.name = v.findViewById(R.id.bb_name);
        viewholder.id = v.findViewById(R.id.bb_id);
        viewholder.party = v.findViewById(R.id.bb_party_name);
        viewholder.img = v.findViewById(R.id.bb_party_img);
        viewholder.btn = (Button) v.findViewById(R.id.bb_btn);

        viewholder.name.setText(arrayList.get(position).getName());
        viewholder.id.setText(arrayList.get(position).getId());
        viewholder.party.setText(arrayList.get(position).getParty());
        String url = arrayList.get(position).getImg();

        Log.v("---------->>>>>>>>>", arrayList.get(position).getName());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("candidates_party").child(url);
        try {
            File file = File.createTempFile("temp" + String.valueOf(position), "jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    viewholder.img.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    //Toast.makeText(getContext(),"Some Error Occured!",Toast.LENGTH_LONG).show();
                }
            });

        } catch (IOException e) {
            //Toast.makeText(getContext(),"Some Error Occured!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


        return v;
    }

    public class viewHolder {
        ImageView img;
        TextView name, id, party;
        Button btn;
    }
}

