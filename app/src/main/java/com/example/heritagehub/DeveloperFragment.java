package com.example.heritagehub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class DeveloperFragment extends Fragment {

    private FirebaseAuth auth;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.developer_fragment, container, false);
        Button userLogoutButton = (Button) v.findViewById(R.id.userLogout);
        auth = FirebaseAuth.getInstance();

        ImageView tharunInsta=(ImageView) v.findViewById(R.id.tharunInsta);
        tharunInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeb("https://www.instagram.com/tharun_.k._/");
            }
        });
        ImageView tharunLinkedin=(ImageView) v.findViewById(R.id.tharunLikedin);
        tharunLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeb("https://www.linkedin.com/in/tharun-kumar-a406621ab/");
            }
        });

        //Karthik B
        ImageView karthikInsta=(ImageView) v.findViewById(R.id.karthikInsta);
        karthikInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeb("https://www.instagram.com/karthik__.b.__");
            }
        });
        ImageView karthikLinkedin=(ImageView) v.findViewById(R.id.karthikLikedin);
        karthikLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeb("https://www.linkedin.com/in/karthik-b-0212");
            }
        });

        userLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
                Toast.makeText(getContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
    private void showWeb(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}