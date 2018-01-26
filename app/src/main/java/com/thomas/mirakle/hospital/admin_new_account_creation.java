package com.thomas.mirakle.hospital;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

public class admin_new_account_creation extends AppCompatActivity implements View.OnClickListener {
    Button register_btn,close;
    DataBaseHelper db;
    String u_id,u_name,u_pwd,u_address,table_name;
    EditText uname,name,pwd,address;
    Spinner t_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_account_creation);
        register_btn=findViewById(R.id.register_btn);
        close=(Button)findViewById(R.id.close_btn);
        register_btn.setOnClickListener(this);
        try {
            db = new DataBaseHelper(this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        uname=findViewById(R.id.username);
        name=findViewById(R.id.name);
        pwd=findViewById(R.id.password);
        address=findViewById(R.id.address);
        t_name=findViewById(R.id.member);

        u_id=uname.getText().toString();
        u_name=name.getText().toString();
        u_pwd=pwd.getText().toString();
        u_address=address.getText().toString();
        table_name=t_name.getSelectedItem().toString();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(admin_new_account_creation.this,admin_page.class);
                db.close();
                startActivity(back);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view==register_btn){
            try{
            u_id=uname.getText().toString();
            u_name=name.getText().toString();
            u_pwd=pwd.getText().toString();
            u_address=address.getText().toString();
            table_name=t_name.getSelectedItem().toString();

                if(Objects.equals(u_id, "") || Objects.equals(u_name ,"")||Objects.equals(u_pwd,"")||Objects.equals(u_address,"")){
                    Snackbar.make(view,"Please Enter the Details",Snackbar.LENGTH_LONG).setDuration(10000).show();
                }

                else if(Objects.equals(u_id, db.db_check_username(u_id, table_name))){
                    Snackbar.make(view,"The Username is Already used. Try Another name",Snackbar.LENGTH_LONG).setDuration(10000).show();
                }
                else{

                        long id=db.add_by_admin(table_name,u_id,u_name,u_pwd,u_address);
                    if(id>0) {
                        Toast.makeText(getApplicationContext(),"Account Created to " + u_name, Toast.LENGTH_LONG).show();
                        Snackbar.make(view, "Account Created to " + u_name, Snackbar.LENGTH_LONG)
                                .setAction("CLOSE REGISTRATION WINDOW", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(admin_new_account_creation.this, admin_page.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .setDuration(50000).show();

                    }
                }

            }
            catch(NullPointerException e){
                e.printStackTrace();
            }
        }
    }
}
