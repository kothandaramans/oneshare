package com.example.oneshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oneshare.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText uname,pwd;
    TextView reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.btnlogin);
        reg = findViewById(R.id.reg_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = findViewById(R.id.uname);
                pwd = findViewById(R.id.pwd);
                final String name = uname.getText().toString();
                final String pass = pwd.getText().toString();
                if (validate(name,pass))
                {
                    User su = null;
                    String mail = "",pin = "";
                    if (name.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("1234"))
                    {
                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                        i.putExtra("admin",true);
                        startActivity(i);
                        finish();
                    }
                    else if(name.equalsIgnoreCase("raman") && pass.equalsIgnoreCase("123456"))
                    {
                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                        i.putExtra("admin",false);
                        startActivity(i);
                        finish();
                    }
                    else {
                        try
                        {
                            FirebaseDatabase.getInstance().getReference().child("users/"+name.substring(0, name.indexOf("."))).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User  su = dataSnapshot.getValue(User.class);
                                    String mail = su.getEmail();
                                    String pin = su.getPin();

                                    Log.d("name",name);
                                    Log.d("pass",pass);
                                    if(name.equalsIgnoreCase(mail) && pass.equals(pin))
                                    {
                                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                        i.putExtra("admin",false);
                                        startActivity(i);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this, "wrong credentials", Toast.LENGTH_LONG).show();
                                    }
                                    Log.d("mail",su.getEmail());
                                    Log.d("name",su.getFullname());
                                    Log.d("pin",su.getPin());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
//                            addChildEventListener(new ChildEventListener() {
//                                @Override
//                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                                    String  su = dataSnapshot.getValue(String.class);
//                                    Log.d("mail",su.getEmail());
//                                    Log.d("name",su.getFullname());
//                                    Log.d("pin",su.getPin());
//
//                                }
//
//                                @Override
//                                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                                }
//
//                                @Override
//                                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                                }
//
//                                @Override
//                                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
                        }
                        catch (Exception ex)
                        {
                            Toast.makeText(LoginActivity.this, "wrong credentials", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
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

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if(doubleBackToExitPressedOnce)
        {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}

