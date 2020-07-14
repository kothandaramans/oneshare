package com.example.oneshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oneshare.Firebase.CRUD;
import com.example.oneshare.model.SharedUsers;
import com.example.oneshare.utils.AppUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    EditText fname,email,pin;
    String rname,remail,rpin;
    Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname = findViewById(R.id.reg_fullname);
        email = findViewById(R.id.reg_email);
        pin = findViewById(R.id.reg_pin);

        reg = findViewById(R.id.btn_reg);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedUsers s=null;
                getValues();
                if(validate(rname,remail,rpin))
                {
                    if(AppUtils.isOnline(getApplicationContext()))
                    {
                        FirebaseDatabase.getInstance().getReference().child("users").child(remail.substring(0,remail.indexOf("."))).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                SharedUsers s = dataSnapshot.getValue(SharedUsers.class);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        try
                        {
                            if(s.getEmail().equals(remail))
                            {
                                Toast.makeText(RegisterActivity.this, "User exists", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception ex)
                        {
                            SharedUsers su = new SharedUsers(rname,remail,rpin);
                            CRUD c = new CRUD();
                            c.reguser(su,su.getEmail());
                            Toast.makeText(RegisterActivity.this, "registered...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            finish();
                        }
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    void getValues()
    {
        rname = fname.getText().toString().trim();
        remail = email.getText().toString().trim();
        rpin = pin.getText().toString().trim();
    }

    public boolean validate(String name, String mail, String amt)
    {
        if(rname.isEmpty())
        {
            fname.setError("please fill the field");
            return false;
        }
        else if(rname.length() < 3)
        {
            fname.setError("your name very short");
            return false;
        }
        else if(remail.isEmpty())
        {
            email.setError("please fill the field");
            return false;
        }
        else if(rpin.isEmpty())
        {
            pin.setError("please fill the field");
            return false;
        }
        else if(rpin.length() < 6)
        {
            pin.setError("pin length must be 6 digits");
            return false;
        }

        return true;
    }
}
