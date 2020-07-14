package com.example.oneshare.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.oneshare.Firebase.CRUD;
import com.example.oneshare.LoginActivity;
import com.example.oneshare.MainActivity;
import com.example.oneshare.R;
import com.example.oneshare.utils.AppUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_main extends Fragment {

    View view;
    Button btn_update, btn_bal, btn_act;
    ImageView btn_logout;
    EditText upi_id;

    public static admin_main newInstance() {
        admin_main fragment = new admin_main();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_main, container, false);

        btn_update = view.findViewById(R.id.btnupdate);
        btn_bal = view.findViewById(R.id.btnbal);
        btn_logout = view.findViewById(R.id.btnlogout);
        btn_act = view.findViewById(R.id.btnact);

        btn_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, activities.newInstance());
                transaction.commit();
            }
        });

        btn_bal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(AppUtils.isOnline(getActivity()))
                {
                    FirebaseDatabase.getInstance().getReference().child("available_balance").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String balance = dataSnapshot.getValue(String.class);
                            btn_bal.setText("\u20B9 "+balance);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                else
                {
                    Toast.makeText(getActivity(), "Check internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
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

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upi_id = view.findViewById(R.id.upi_id);
                String upi = upi_id.getText().toString();

                if(upi.isEmpty())
                {
                    upi_id.setError("Please fill the field");
                }
                else
                {
                    if(AppUtils.isOnline(getActivity()))
                    {
                        CRUD fb = new CRUD();
                        fb.upi_update(upi);
                        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                        upi_id.setText("");
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }
}
