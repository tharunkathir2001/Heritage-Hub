package com.example.heritagehub;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Homefragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Details> detailList;
    private RelativeLayout noInfo,searchInfo;
    private FirebaseAuth auth;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        noInfo = view.findViewById(R.id.no_info);
        searchInfo = view.findViewById(R.id.seach_info);
        recyclerView.setVisibility(View.INVISIBLE);
        noInfo.setVisibility(View.INVISIBLE);
        searchInfo.setVisibility(View.VISIBLE);
        EditText searchBar=view.findViewById(R.id.search_bar);
        ImageView searchBtn = view.findViewById(R.id.search_image);

        FloatingActionButton fab=view.findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.form, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = false; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.setFocusable(true);

                EditText title=(EditText) popupView.findViewById(R.id.tile_edt_text);
                EditText des=(EditText) popupView.findViewById(R.id.des_edt_text);
                EditText content=(EditText) popupView.findViewById(R.id.cont_edt_text);
                EditText url=(EditText) popupView.findViewById(R.id.imagelink_edt_text);
                Button post=(Button) popupView.findViewById(R.id.postbtn);
                TextView txt = (TextView) popupView.findViewById(R.id.txtclose);


                post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(title.getText().toString().toUpperCase()+"/"+String.valueOf(new Random().nextInt(100000)));
                        auth=FirebaseAuth.getInstance();
                        databaseReference.setValue(new Details(title.getText().toString().toUpperCase(),des.getText().toString().toUpperCase(),auth.getCurrentUser().getEmail().toString().toUpperCase(),content.getText().toString().toUpperCase(),url.getText().toString().toUpperCase()));
                        Toast.makeText(getContext(),"CONTENT POSTED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });
                txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchBar.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Search Not Available", Toast.LENGTH_SHORT).show();
                }
                searchForTheInfo(searchBar.getText().toString());
            }
        });
        return view;
    }

    private void searchForTheInfo(String str) {
        recyclerView = getView().findViewById(R.id.recyclerview);
        noInfo = getView().findViewById(R.id.no_info);
        searchInfo = getView().findViewById(R.id.seach_info);
        detailList = new ArrayList<>();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(str.toUpperCase());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for(DataSnapshot ds : snapshot.getChildren()) {
                        String title = ds.child("title").getValue(String.class);
                        String des = ds.child("subject").getValue(String.class);
                        String content = ds.child("description").getValue(String.class);
                        String username = ds.child("username").getValue(String.class);
                        String imageurl = ds.child("imageUrl").getValue(String.class);
                        Details temp = new Details(title,des,username,content,imageurl);
                        detailList.add(temp);
                        Log.d("TAG", title);
                    }

                    Adapter adapter = new Adapter(getContext(), detailList);

                    //setting adapter to recyclerview

                    recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                    searchInfo.setVisibility(View.INVISIBLE);
                    noInfo.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);

                }
                else{
                    recyclerView.setVisibility(View.INVISIBLE);
                    searchInfo.setVisibility(View.INVISIBLE);
                    noInfo.setVisibility(View.VISIBLE);

                    return;
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

