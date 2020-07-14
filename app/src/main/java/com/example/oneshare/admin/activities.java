package com.example.oneshare.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oneshare.Firebase.CRUD;
import com.example.oneshare.R;
import com.example.oneshare.RegisterActivity;
import com.example.oneshare.fragment.donate;
import com.example.oneshare.model.activity_model;
import com.example.oneshare.utils.AppUtils;

public class activities extends Fragment {

    View view;
    EditText subject, description, amount;
    Button act_btn;
    String sub, des, amt;

    public static activities newInstance() {
        activities fragment = new activities();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_activities, container, false);

        subject = view.findViewById(R.id.act_sub);
        description = view.findViewById(R.id.act_des);
        amount = view.findViewById(R.id.act_amt);

        act_btn = view.findViewById(R.id.act_add);

        act_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetch_values();
                if(validate(sub,des,amt))
                {
                    if(AppUtils.isOnline(getContext()))
                    {
                        activity_model am = new activity_model(sub,des,Double.parseDouble(amt));
                        CRUD c = new CRUD();
                        c.addactivity(am);
                        Toast.makeText(getContext(), "activity added", Toast.LENGTH_SHORT).show();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, admin_main.newInstance());
                        transaction.commit();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        return view;
    }

    void fetch_values()
    {
        sub = subject.getText().toString().trim();
        des = description.getText().toString().trim();
        amt = amount.getText().toString().trim();
    }


    public boolean validate(String sub, String des, String amt)
    {
        if(sub.isEmpty())
        {
            subject.setError("please fill the field");
            return false;
        }
        else if(des.isEmpty())
        {
            description.setError("please fill the field");
            return false;
        }
        else if(amt.isEmpty())
        {
            amount.setError("please fill the field");
            return false;
        }
        else if(Double.parseDouble(amt) < 0)
        {
            amount.setError("please enter valid amount");
            return false;
        }
        return true;
    }
}
