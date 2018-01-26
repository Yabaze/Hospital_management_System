package com.thomas.mirakle.hospital;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

public class Registration_module extends AppCompatActivity implements View.OnClickListener {
    DataBaseHelper db;
    EditText uid,uname,upwd,udob,uaddress;
    Button reg_btn;
    Spinner ubg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_module);
        try {
            db = new DataBaseHelper(this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        get_id();
        clear_editText();
        reg_btn.setOnClickListener(this);
    }
    public void clear_editText(){
        uid.setText("");
        uname.setText("");
        upwd.setText("");
        udob.setText("");
        uaddress.setText("");
    }
    public void get_id(){
        uid = findViewById(R.id.uid);
        uname = findViewById(R.id.uname);
        upwd = findViewById(R.id.upwd);
        udob = findViewById(R.id.udob);
        ubg = findViewById(R.id.blood_spinner);
        uaddress = findViewById(R.id.uaddress);
        reg_btn = findViewById(R.id.registration_btn);
    }
    @Override
    public void onClick(View view) {
        try{
            String id=uid.getText().toString();
            String name = uname.getText().toString();
            String pwd=upwd.getText().toString();
            String dob=udob.getText().toString();
            String bg=ubg.getSelectedItem().toString();
            String address = uaddress.getText().toString();
            if(id.isEmpty()|| name.isEmpty()||pwd.isEmpty()||dob.isEmpty()||bg.isEmpty()||address.isEmpty()){
                requestFocus(uid);
                Snackbar.make(view,"Please Enter All Details.",Snackbar.LENGTH_LONG).setDuration(10000).show();
            }
            else if(Objects.equals(id, db.db_check_username(id, "patient"))){
                Snackbar.make(view,"The Username is Already used. Try Another name",Snackbar.LENGTH_LONG).setDuration(10000).show();
            }
            else{
                Long insID = db.AddUser(id,name,pwd,dob,bg,address);
                if (insID>0) {
                    Toast.makeText(getApplicationContext(), "Account Created to "+name, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Registration_module.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
