package com.example.oneshare.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.oneshare.Adapter.ActivityAdapter;
import com.example.oneshare.Adapter.FirebaseAdapter;
import com.example.oneshare.LoginActivity;
import com.example.oneshare.R;
import com.example.oneshare.model.SharedUsers;
import com.example.oneshare.model.activity_model;
import com.example.oneshare.utils.AppUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_main extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<activity_model> list;
    ActivityAdapter adapter;

    View view;
    ImageView btnlogout;

    public static user_main newInstance() {
        user_main fragment = new user_main();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_main, container, false);
        btnlogout = view.findViewById(R.id.usrlogout);

        if (AppUtils.isOnline(getActivity()))
        {
            Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_SHORT).show();
            recyclerView = view.findViewById(R.id.recyclerviewactivity);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            recyclerView.setHasFixedSize(true);
            firebaseDatabase = FirebaseDatabase.getInstance();
            reference = FirebaseDatabase.getInstance().getReference().child("activities");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list = new ArrayList<>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        activity_model ac = postSnapshot.getValue(activity_model.class);
                        list.add(ac);
                        adapter = new ActivityAdapter(getContext(), list);
                        recyclerView.setAdapter(adapter);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            Toast.makeText(getActivity(), "Check internet connection", Toast.LENGTH_SHORT).show();
        }

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Do you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(getContext(), LoginActivity.class));
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return view;
    }
}
