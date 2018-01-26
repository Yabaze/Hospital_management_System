package com.thomas.mirakle.hospital;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class admin_page extends AppCompatActivity implements View.OnClickListener {
    ListView list;
    int count=0;
    private DataBaseHelper db;
    TextView logged_user;
    Button create,list_user;
    FloatingActionButton fab,fab1,fab2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        create=findViewById(R.id.create_ac);
        create.setOnClickListener(this);
        logged_user=findViewById(R.id.logged_user);
        list_user=findViewById(R.id.list_of_user);
        list_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(admin_page.this,list_user.class);
                db.close();
                startActivity(intent);
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("user");
        logged_user.append(" "+message);

        fab = findViewById(R.id.fab);
        fab1=findViewById(R.id.fab1);
        fab2=findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count+=1;
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                if(count%2==1){
                    fab1.setVisibility(View.VISIBLE);
                    fab1.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            Intent intent=new Intent(admin_page.this,admin_new_account_creation.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    fab2.setVisibility(View.VISIBLE);

                    fab2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDialog();
                        }
                    });
                }
                else{
                    fab1.setVisibility(View.INVISIBLE);
                    fab2.setVisibility(View.INVISIBLE);
                }
            }
        });

        try {
            db = new DataBaseHelper(this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void showDialog() throws Resources.NotFoundException {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do you Want to logout..?")
                .setIcon(
                        getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        "YES",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //Do Something Here
                                Intent intent=new Intent(admin_page.this,MainActivity.class);
                                db.close();
                                startActivity(intent);
                                finish();
                                //Toast.makeText(admin_page.this,"I'm always Fine",Toast.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton(
                        "NO",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //Do Something Here
                            }
                        }).show();
    }

    @Override
    public void onClick(View view) {
        if(view==create){
            Intent intent=new Intent(this,admin_new_account_creation.class);
            startActivity(intent);
            finish();
        }
    }
}
