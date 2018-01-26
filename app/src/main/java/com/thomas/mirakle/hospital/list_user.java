package com.thomas.mirakle.hospital;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import static com.thomas.mirakle.hospital.R.layout.activity_list_user;

public class list_user extends AppCompatActivity {
    ListView list_user;
    Spinner table_name;
    Button show_btn;
    DataBaseHelper db;
    String a[]={"Id","name","Password","Address"};
    ArrayAdapter<String> arrayadapter;
    ArrayList itemlist,data1,data2,data3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_list_user);
        list_user=findViewById(R.id.list_view);
        table_name=findViewById(R.id.spinner);
        show_btn=findViewById(R.id.show_list);
        try {
            db = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }





        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemlist=db.list_user(table_name.getSelectedItem().toString());
                //for (itemlist){
                 //  int a= Log.d("My array list content: ", String.valueOf(itemlist));
                //}

               //data1=db.list_user(table_name.getSelectedItem().toString(),"name");
               //data1=db.list_user(table_name.getSelectedItem().toString(),"Password");
               //data3=db.list_user(table_name.getSelectedItem().toString(),"Address");

                //arrayadapter.clear();
                //arrayadapter = new ArrayAdapter<String>(list_user.this, android.R.layout.simple_list_item_1,itemlist);
                //arrayadapter.add(String.valueOf(itemlist));
                list_user.setAdapter(new ArrayAdapter<String>(list_user.this, android.R.layout.simple_list_item_1,itemlist));

                list_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        showDialog(String.valueOf(list_user.getAdapter().getItem(i)),i);
                        //String cool=arrayadapter.getItem(i)+""+data1.get(i).toString();


                        //Toast.makeText(list_user.this,""+list_user.getAdapter().getItem(i),Toast.LENGTH_LONG).show();

                        //ListEntry entry= (ListEntry) parent.getAdapter().getItem(position);
                        //showDialog(list_user.getCount());    ,(String) data1.get(i),(String) data2.get(i),(String) data3.get(i)


                    }
                });
            }
        });

    }//,String pass,String address,,String name

    public void showDialog(String clicked, final int i) throws Resources.NotFoundException {
        new AlertDialog.Builder(this)
                .setTitle("Detail of the user")
                .setMessage(clicked)
                .setIcon(
                        getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        "EDIT",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent intent=new Intent(list_user.this,editpage.class);
                                db.close();
                                intent.putExtra("column_number",table_name.getSelectedItemId());
                                //Toast.makeText(list_user.this,""+table_name.getSelectedItemId(),Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                //finish();
                            }
                        })
                .setNegativeButton(
                        "CANCEL",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        })
                .show();
    }


}
