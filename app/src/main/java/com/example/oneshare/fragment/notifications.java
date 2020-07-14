package com.example.oneshare.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.oneshare.Adapter.FirebaseAdapter;
import com.example.oneshare.R;
import com.example.oneshare.model.SharedUsers;
import com.example.oneshare.utils.AppUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class notifications extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<SharedUsers> list;
    FirebaseAdapter adapter;
    View view;

    public static notifications newInstance() {
        notifications fragment = new notifications();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notifications, container, false);

        if (AppUtils.isOnline(getActivity()))
        {
            Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_SHORT).show();
            recyclerView = view.findViewById(R.id.recyclerviewarchivement);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setHasFixedSize(true);
            firebaseDatabase = FirebaseDatabase.getInstance();
            reference = FirebaseDatabase.getInstance().getReference().child("sharingpersons");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list = new ArrayList<>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        SharedUsers archievement = postSnapshot.getValue(SharedUsers.class);
                        list.add(archievement);
                        adapter = new FirebaseAdapter(getContext(), list);
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
        return view;
    }
}