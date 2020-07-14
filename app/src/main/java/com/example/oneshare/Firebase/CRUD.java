package com.example.oneshare.Firebase;

import com.example.oneshare.R;
import com.example.oneshare.model.SharedUsers;
import com.example.oneshare.model.activity_model;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CRUD {

    DatabaseReference ref;

    public void save(final SharedUsers su)
    {
        ref = FirebaseDatabase.getInstance().getReference().child("sharingpersons");
        String id = ref.push().getKey();
        //ref.child(su.getEmail()).setValue(su);
        //ref.child(su.getCureentdatetime()+su.getEmail().substring(0,su.getEmail().indexOf("."))).setValue(su);
        ref.child(id).setValue(su);
    }

    public void upi_update(final String id)
    {
        ref = FirebaseDatabase.getInstance().getReference().child("upi_id");
        ref.setValue(id);
    }

    public void balance(final String bal)
    {
        ref = FirebaseDatabase.getInstance().getReference().child("available_balance");
        ref.setValue(bal);
    }

    public void reguser(final SharedUsers su,final String email)
    {
        ref = FirebaseDatabase.getInstance().getReference().child("users");
        //String id = ref.push().getKey();
        //ref.child(su.getEmail()).setValue(su);
        //ref.child(su.getCureentdatetime()+su.getEmail().substring(0,su.getEmail().indexOf("."))).setValue(su);
        ref.child(email.substring(0,email.indexOf("."))).setValue(su);
    }


    public void addactivity(final activity_model am)
    {
        ref = FirebaseDatabase.getInstance().getReference().child("activities");
        String id = ref.push().getKey();
        //ref.child(su.getEmail()).setValue(su);
        //ref.child(su.getCureentdatetime()+su.getEmail().substring(0,su.getEmail().indexOf("."))).setValue(su);
        ref.child(id).setValue(am);
    }

}

