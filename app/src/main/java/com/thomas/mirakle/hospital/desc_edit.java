package com.thomas.mirakle.hospital;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

public class desc_edit extends AppCompatActivity {
    Button add_medi,add_desc;
    DataBaseHelper db;
    LinearLayout medi2_layout,medi3_layout,medi4_layout,medi5_layout,medi6_layout,medi7_layout,medi8_layout,medi9_layout,medi10_layout;
    int count=1;
    EditText date,id,age,disease,description,medi1,medi2,medi3,medi4,medi5,medi6,medi7,medi8,medi9,medi10;
    CheckBox m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,n1,n2,n3,n4,n5,n6,n7,n8,n9,n10;
    String message,get_name;
    TextView new_admission;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_edit);
        try {
            db = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("message");
        get_name=db.get_patient_name(message);
        new_admission=findViewById(R.id.new_appoinment);
        add_medi=findViewById(R.id.add_medi);
        add_desc=findViewById(R.id.add_medicine);
        medi2_layout=findViewById(R.id.medi2_layout);
        medi3_layout=findViewById(R.id.medi3_layout);
        medi4_layout=findViewById(R.id.medi4_layout);
        medi5_layout=findViewById(R.id.medi5_layout);
        medi6_layout=findViewById(R.id.medi6_layout);
        medi7_layout=findViewById(R.id.medi7_layout);
        medi8_layout=findViewById(R.id.medi8_layout);
        medi9_layout=findViewById(R.id.medi9_layout);
        medi10_layout=findViewById(R.id.medi10_layout);
        date=findViewById(R.id.date);
        id=findViewById(R.id.pat_id);
        age=findViewById(R.id.age);
        disease=findViewById(R.id.disease);
        description=findViewById(R.id.desc);
        medi1=findViewById(R.id.medi1);
        medi2=findViewById(R.id.medi2);
        medi3=findViewById(R.id.medi3);
        medi4=findViewById(R.id.medi4);
        medi5=findViewById(R.id.medi5);
        medi6=findViewById(R.id.medi6);
        medi7=findViewById(R.id.medi7);
        medi8=findViewById(R.id.medi8);
        medi9=findViewById(R.id.medi9);
        medi10=findViewById(R.id.medi10);
        m1=findViewById(R.id.morning1);
        m2=findViewById(R.id.morning2);
        m3=findViewById(R.id.morning3);
        m4=findViewById(R.id.morning4);
        m5=findViewById(R.id.morning5);
        m6=findViewById(R.id.morning6);
        m7=findViewById(R.id.morning7);
        m8=findViewById(R.id.morning8);
        m9=findViewById(R.id.morning9);
        m10=findViewById(R.id.morning10);
        a1=findViewById(R.id.afternoon1);
        a2=findViewById(R.id.afternoon2);
        a3=findViewById(R.id.afternoon3);
        a4=findViewById(R.id.afternoon4);
        a5=findViewById(R.id.afternoon5);
        a6=findViewById(R.id.afternoon6);
        a7=findViewById(R.id.afternoon7);
        a8=findViewById(R.id.afternoon8);
        a9=findViewById(R.id.afternoon9);
        a10=findViewById(R.id.afternoon10);
        n1=findViewById(R.id.night1);
        n2=findViewById(R.id.night2);
        n3=findViewById(R.id.night3);
        n4=findViewById(R.id.night4);
        n5=findViewById(R.id.night5);
        n6=findViewById(R.id.night6);
        n7=findViewById(R.id.night7);
        n8=findViewById(R.id.night8);
        n9=findViewById(R.id.night9);
        n10=findViewById(R.id.night10);

        new_admission.setText("Prescription for ( "+get_name+" )");
        id.append(message);

        //id.setEnabled(false);
        add_medi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count+=1;
                if(count<10){
                    add_medi.setClickable(true);
                }
                else if(count>=10)
                {
                    add_medi.setClickable(false);
                }
                //Toast.makeText(desc_edit.this,"count ="+count,Toast.LENGTH_LONG).show();
                if(count>=2){
                    medi2_layout.setVisibility(View.VISIBLE);
                }
                if(count>=3){
                    medi3_layout.setVisibility(View.VISIBLE);
                }
                if(count>=4){
                    medi4_layout.setVisibility(View.VISIBLE);
                }
                if(count>=5){
                    medi5_layout.setVisibility(View.VISIBLE);
                }
                if(count>=6){
                    medi6_layout.setVisibility(View.VISIBLE);
                }
                if(count>=7){
                    medi7_layout.setVisibility(View.VISIBLE);
                }
                if(count>=8){
                    medi8_layout.setVisibility(View.VISIBLE);
                }
                if(count>=9){
                    medi9_layout.setVisibility(View.VISIBLE);
                }
                if(count>=10){
                    medi10_layout.setVisibility(View.VISIBLE);
                    //add_medicine.setClickable(false);
                }

            }
        });



        add_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time1="",time2="",time3="",time4="",time5="",time6="",time7="",time8="",time9="",time10="";
                if(m1.isChecked()){
                    time1.concat("Morning  ");
                }
                if(m2.isChecked()){
                    time2.concat("Morning  ");
                }
                if(m3.isChecked()){
                    time3.concat("Morning  ");
                }
                if(m4.isChecked()){
                    time4.concat("Morning  ");
                }
                if(m5.isChecked()){
                    time5.concat("Morning  ");
                }
                if(m6.isChecked()){
                    time6.concat("Morning  ");
                }
                if(m7.isChecked()){
                    time7.concat("Morning  ");
                }
                if(m8.isChecked()){
                    time8.concat("Morning  ");
                }
                if(m9.isChecked()){
                    time9.concat("Morning  ");
                }
                if(m10.isChecked()){
                    time10.concat("Morning  ");
                }


                if(a1.isChecked()){
                    time1.concat("Afternoon  ");
                }
                if(a2.isChecked()){
                    time2.concat("Afternoon  ");
                }
                if(a3.isChecked()){
                    time3.concat("Afternoon  ");
                }
                if(a4.isChecked()){
                    time4.concat("Afternoon  ");
                }
                if(a5.isChecked()){
                    time5.concat("Afternoon  ");
                }
                if(a6.isChecked()){
                    time6.concat("Afternoon  ");
                }
                if(a7.isChecked()){
                    time7.concat("Afternoon  ");
                }
                if(a8.isChecked()){
                    time8.concat("Afternoon  ");
                }
                if(a9.isChecked()){
                    time9.concat("Afternoon  ");
                }
                if(a10.isChecked()){
                    time10.concat("Afternoon  ");
                }


                if(n1.isChecked()){
                    time1.concat("Night  ");
                }
                if(n2.isChecked()){
                    time2.concat("Night  ");
                }
                if(n3.isChecked()){
                    time3.concat("Night  ");
                }
                if(n4.isChecked()){
                    time4.concat("Night  ");
                }
                if(n5.isChecked()){
                    time5.concat("Night  ");
                }
                if(n6.isChecked()){
                    time6.concat("Night  ");
                }
                if(n7.isChecked()){
                    time7.concat("Night  ");
                }
                if(n8.isChecked()){
                    time8.concat("Night  ");
                }
                if(n9.isChecked()){
                    time9.concat("Night  ");
                }
                if(n10.isChecked()){
                    time10.concat("Night  ");
                }

                db.Add_desc(date.getText().toString(),id.getText().toString(),Integer.parseInt(age.getText().toString()),disease.getText().toString(),description.getText().toString(),medi1.getText().toString(),time1,medi2.getText().toString(),time2,medi3.getText().toString(),time3,medi4.getText().toString(),time4,medi5.getText().toString(),time5,medi6.getText().toString(),time6,medi7.getText().toString(),time7,medi8.getText().toString(),time8,medi9.getText().toString(),time9,medi10.getText().toString(),time10);
                Snackbar.make(view,"Sucessfully Updated",Snackbar.LENGTH_LONG)
                        .show();
                //Intent back=new Intent(desc_edit.this,doc_pat_update.class);
                //startActivity(back);
                //finish();
            }
        });
    }
}
