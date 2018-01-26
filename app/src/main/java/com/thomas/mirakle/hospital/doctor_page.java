package com.thomas.mirakle.hospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class doctor_page extends AppCompatActivity {
    TextView logged_user;
    Button submit;
    EditText user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_page);
        logged_user=findViewById(R.id.logged_doc);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("user");
        logged_user.append(" Dr."+message);
        submit=findViewById(R.id.submit_1);
        user_id=findViewById(R.id.doc_pat_id);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(doctor_page.this,doc_pat_update.class);
                intent.putExtra("id",user_id.getText().toString());
                startActivity(intent);
                //finish();
            }
        });
    }
}
