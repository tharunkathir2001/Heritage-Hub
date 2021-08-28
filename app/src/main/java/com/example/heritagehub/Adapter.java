package com.example.heritagehub;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.EventLogTags;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Adapter extends RecyclerView.Adapter<Adapter.ProductViewHolder>{
    private Context mCtx;

    //we are storing all the products in a list
    private List<Details> details;


    //getting the context and product list with constructor
    public Adapter(Context mCtx, List<Details> details) {
        this.mCtx = mCtx;
        this.details = details;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycler_details, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Details info = details.get(position);



        //binding the data with the viewholder views
        holder.userName.setText(info.getUsername());
        holder.description.setText(info.getSubject());
        holder.title.setText(info.getTitle());
        Glide.with(mCtx)
                .load(info.getImageUrl())
                .into(holder.image);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        mCtx.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.detailed_text, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = false; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                TextView title=(TextView) popupView.findViewById(R.id.title_txt_view);
                TextView des=(TextView) popupView.findViewById(R.id.content_txt_view);
                TextView cross=(TextView) popupView.findViewById(R.id.txtclose_detail);

                title.setText(info.getTitle());
                des.setText(info.getDescription());

                cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });


                popupWindow.setFocusable(true);

                popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);
            }
        });


    }

    @Override
    public int getItemCount() {
        return details.size();
    }



    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView userName,title,description;
        ImageView image;
        RelativeLayout rel;
        CardView card;


        public ProductViewHolder(View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            userName = itemView.findViewById(R.id.article_by);
            image=itemView.findViewById(R.id.recycler_img_home);
            card=itemView.findViewById(R.id.home_card_view);
        }
    }
}
