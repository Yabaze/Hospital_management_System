package com.thomas.mirakle.hospital;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class editpage extends AppCompatActivity {
    Spinner user_id,members;
    ArrayAdapter<String> arrayadapter;
    ImageButton reload,refresh;
    Button update_db_btn;
    EditText name_update,password_update,address_update;
    ArrayList itemlist;
    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            db = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_editpage);
        Bundle bundle = getIntent().getExtras();
        String a=bundle.get("column_number").toString();
        user_id=findViewById(R.id.user_list_spinner);
        reload=findViewById(R.id.reload);
        members=findViewById(R.id.tablename);
        name_update=findViewById(R.id.name_update);
        password_update=findViewById(R.id.password_update);
        address_update=findViewById(R.id.address_update);
        refresh=findViewById(R.id.refresh);
        update_db_btn=findViewById(R.id.update_db_btn);
        members.setSelection(Integer.parseInt(a));
        members.callOnClick();
        itemlist=db.list_user(members.getSelectedItem().toString(),"id");
        user_id.setAdapter(new ArrayAdapter<String>(editpage.this, android.R.layout.simple_list_item_1,itemlist));
        String tb_name,userid;
        tb_name=members.getSelectedItem().toString();
        userid=user_id.getSelectedItem().toString();
        //refresh.setVisibility(View.INVISIBLE);
        //arrayadapter.add(String.valueOf(itemlist));
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemlist=db.list_user(members.getSelectedItem().toString(),"id");
                user_id.setAdapter(new ArrayAdapter<String>(editpage.this, android.R.layout.simple_list_item_1,itemlist));
                //Toast.makeText(editpage.this,user_id.getCount()+"",Toast.LENGTH_LONG).show();
                if (user_id.getCount()==0){
                    refresh.setEnabled(false);
                    //refresh.setVisibility(View.INVISIBLE);
                    name_update.setText("");
                    password_update.setText("");
                    address_update.setText("");
                    update_db_btn.setEnabled(false);
                }
                else{
                    refresh.setEnabled(true);
                    //refresh.setVisibility(View.VISIBLE);
                    update_db_btn.setEnabled(true);
                }
            }
        });
        String[] allTableNames;
        allTableNames=db.get_data(tb_name,userid);
        name_update.setText(allTableNames[0]);
        password_update.setText(allTableNames[1]);
        address_update.setText(allTableNames[2]);

        if(refresh.isClickable()) {
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] allTableNames1 = db.get_data(members.getSelectedItem().toString(), user_id.getSelectedItem().toString());
                    name_update.setText(allTableNames1[0]);
                    password_update.setText(allTableNames1[1]);
                    address_update.setText(allTableNames1[2]);
                }
            });
        }
        if(update_db_btn.isClickable()){
            update_db_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String table,primary_key,name,password,address;
                    table=members.getSelectedItem().toString();
                    primary_key=user_id.getSelectedItem().toString();
                    name=name_update.getText().toString();
                    password=password_update.getText().toString();
                    address=address_update.getText().toString();

                    //Toast.makeText(editpage.this, table+"hai"+primary_key+"cool"+name+"pass"+password+"add"+address, Toast.LENGTH_LONG).show();

                    db.update(table,primary_key,name,password,address);
                    Snackbar.make(view,"Successfully updated",3000).setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                }
            });
        }


    }
}
