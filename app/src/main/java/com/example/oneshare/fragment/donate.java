package com.example.oneshare.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oneshare.model.SharedUsers;
import com.example.oneshare.utils.AppUtils;

import com.example.oneshare.R;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

import  com.example.oneshare.Firebase.CRUD;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class donate extends Fragment {

    View view;
    Button pay;
    EditText reg_uname,reg_email,reg_amount,reg_note;
    Activity activity;

    String upiId;
    final int UPI_PAYMENT = 0;
    String name,email,note,amt;

    public static donate newInstance() {
        donate fragment = new donate();
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
        view = inflater.inflate(R.layout.fragment_donate, container, false);

        pay = view.findViewById(R.id.btn_pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg_uname = view.findViewById(R.id.reg_fullname);
                reg_email = view.findViewById(R.id.reg_email);
                reg_amount = view.findViewById(R.id.reg_amount);
                reg_note = view.findViewById(R.id.reg_note);

                getValue();

                if (validate(name,email,amt))
                {
                    if(AppUtils.isOnline(getActivity())) {
                        FirebaseDatabase.getInstance().getReference().child("upi_id").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                upiId = dataSnapshot.getValue(String.class);
                                payUsingUpi(amt, upiId, name, note);
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
            }
        });

        return view;
    }

    public void getValue()
    {
        name = reg_uname.getText().toString().trim();
        email = reg_email.getText().toString().trim();
        amt = reg_amount.getText().toString().trim();
        note = reg_note.getText().toString().trim();
    }


    void payUsingUpi(String amount, String upiId, String name, String note) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(activity.getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(getContext(),"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                Toast.makeText(getContext(), "Transaction successful.", Toast.LENGTH_SHORT).show();
                final SharedUsers su = new SharedUsers(name,email,note,amt);
                su.setCureentdatetime();
                final CRUD save = new CRUD();
                save.save(su);
                FirebaseDatabase.getInstance().getReference().child("available_balance").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String balance = dataSnapshot.getValue(String.class);
                        double bal = Double.parseDouble(balance) + Double.parseDouble(su.getAmout());
                        save.balance(Double.toString(bal));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                Log.d("UPI", "responseStr: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(getContext(), "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getContext(), "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        }

    public boolean validate(String name, String mail, String amt)
    {
        if(name.isEmpty())
        {
            reg_uname.setError("please fill the field");
            return false;
        }
        else if(name.length() < 3)
        {
            reg_uname.setError("your name very short");
            return false;
        }
        else if(mail.isEmpty())
        {
            reg_email.setError("please fill the field");
            return false;
        }
        else if(amt.isEmpty())
        {
            reg_amount.setError("please fill the field");
            return false;
        }
        else if(Double.parseDouble(amt) < 1.0)
        {
            reg_amount.setError("please enter valid amount");
            return false;
        }

        return true;
    }
}
