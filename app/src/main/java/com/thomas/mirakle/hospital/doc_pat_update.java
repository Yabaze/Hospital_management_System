package com.thomas.mirakle.hospital;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class doc_pat_update extends AppCompatActivity {
    TextView patient_id;
    DataBaseHelper db;
    ListView desc;
    ArrayList itemlist;
    String message;
    Button add_desc;
    @SuppressLint({"WrongConstant", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            db = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_doc_pat_update);
        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("id");
        patient_id=findViewById(R.id.pat_id);
        desc=findViewById(R.id.list_view);
        add_desc=findViewById(R.id.add_new_desc);
        String pat_name=db.get_patient_name(message);
        add_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent new_desc=new Intent(doc_pat_update.this,desc_edit.class);
                new_desc.putExtra("message",message);
                startActivity(new_desc);
                //finish();
            }
        });
        if(!pat_name.isEmpty()){
            patient_id.setText("Name -"+pat_name);
            itemlist=db.get_pat_history("patient_desc",message);
            desc.setAdapter(new ArrayAdapter<String>(doc_pat_update.this, android.R.layout.simple_list_item_1,itemlist));
            desc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ArrayList itemlist1 = db.get_pat_desc_detil("patient_desc", message);
                    //desc.setAdapter(new ArrayAdapter<String>(doc_pat_update.this, android.R.layout.simple_list_item_1,itemlist1));
                    //showDialog(String.valueOf(desc.getAdapter().getItem(i)),i);
                    showDialog(String.valueOf(itemlist1),i);
                }
            });
        }
        else{
            Toast.makeText(this,"Please Register The Patient Please",Toast.LENGTH_SHORT).show();
            Intent back=new Intent(this,doctor_page.class);
            startActivity(back);
            //finish();
        }
    }

    public void showDialog(String clicked, final int i) throws Resources.NotFoundException {
        new AlertDialog.Builder(this)
                .setTitle("Detail of the user")
                .setMessage(clicked)
                .setIcon(
                        getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        "DELETE",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //Intent intent=new Intent(list_user.this,editpage.class);
                                db.drop_desc_details(message,i);
                                //intent.putExtra("column_number",table_name.getSelectedItemId());
                                //Toast.makeText(list_user.this,""+table_name.getSelectedItemId(),Toast.LENGTH_LONG).show();
                                //startActivity(intent);
                                //finish();
                            }
                        })
                .setNegativeButton(
                        "CLOSE",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        })
                .show();
    }
}
