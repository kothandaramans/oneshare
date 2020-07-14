package com.example.oneshare.admin;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oneshare.R;


public class admin extends Fragment {

    View view;
    Button login;
    EditText uname,pwd;
    Activity activity;

    public static admin newInstance() {
        admin fragment = new admin();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_admin, container, false);

        login = view.findViewById(R.id.btnlogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = view.findViewById(R.id.uname);
                pwd = view.findViewById(R.id.pwd);
                String name = uname.getText().toString();
                String pass = pwd.getText().toString();
                if (validate(name,pass))
                {
                    if (name.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("admin"))
                    {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, new admin_main());
                        transaction.commit();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "wrong credientials", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }

    public boolean validate(String name, String pass)
    {
        if(name.isEmpty())
        {
            uname.setError("please fill the field");
            return false;
        }
        else if(pass.isEmpty())
        {
            pwd.setError("please fill the field");
            return false;
        }
        return true;
    }
}
