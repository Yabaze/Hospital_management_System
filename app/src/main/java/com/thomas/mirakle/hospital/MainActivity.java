package com.thomas.mirakle.hospital;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AutoCompleteTextView u_name;
    EditText pass;
    Button login, register_btn,try_button;
    DataBaseHelper db;
    Animation slideOutBottom,shake;
    TextInputLayout username_layout, password_layout;
    Drawable Right,left,Right1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slideOutBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.out_bottom);
        shake=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        //Exception for database
        try {
            db = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        username_layout = findViewById(R.id.username_layout);
        password_layout = findViewById(R.id.password_layout);
        u_name = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);

        u_name.addTextChangedListener(new MyTextWatcher(u_name));
        pass.addTextChangedListener(new MyTextWatcher(pass));
        login.setOnClickListener(this);
        register_btn.setOnClickListener(this);

        Right = getResources().getDrawable( R.mipmap.usericon);
        left=getResources().getDrawable(R.mipmap.admin1);
        Right1=getResources().getDrawable(R.mipmap.passwordicon);
        Right.setBounds( 0, 0, 130, 130 );
        Right1.setBounds( 0, 0, 130, 130 );
        left.setBounds( 0, 0, 270, 130 );
        u_name.setCompoundDrawables( Right, null, null, null );
        pass.setCompoundDrawables( Right1, null, null, null );


        //
        try_button=findViewById(R.id.buttontry);
        try_button.setOnClickListener(this);
    }

    public void showDialog() throws Resources.NotFoundException {
        new AlertDialog.Builder(this)
                .setTitle("Hello")
                .setMessage("Hello")
                .setIcon(
                        getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        "how are you?",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //Do Something Here
                                //Intent intent=new Intent(MainActivity.this,admin_page.class);
                                Toast.makeText(MainActivity.this,"I'm always Fine",Toast.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton(
                        "i'm fine",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //Do Something Here
                            }
                        }).show();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        if (view == login) {
            String id = u_name.getText().toString();
            String pwd = pass.getText().toString();
            String pwd_admin, pwd_nurse, pwd_patient, pwd_doctor,logged_user;
            pwd_admin = db.get_admin_Password(id);
            pwd_nurse = db.get_nurse_Password(id);
            pwd_doctor = db.get_doctor_Password(id);
            pwd_patient = db.get_patient_Password(id);
            btn_click_animation();
            login.startAnimation(slideOutBottom);
            //below code for Checking the username and password by some Condition
            if (id.isEmpty() && pwd.isEmpty()) {
                Toast.makeText(this, "Enter the username and password", Toast.LENGTH_SHORT).show();
                u_name.startAnimation(shake);
                pass.startAnimation(shake);
                requestFocus(u_name);
            }
            else if(id.isEmpty()){
                Toast.makeText(this, "Enter the username", Toast.LENGTH_SHORT).show();
                u_name.startAnimation(shake);
                requestFocus(u_name);
            }
            else if(pwd.isEmpty()){
                Toast.makeText(this, "Enter the password", Toast.LENGTH_SHORT).show();
                pass.startAnimation(shake);
                requestFocus(pass);
            }
            else {
                if (pwd_admin.equals(pwd)) {
                    logged_user=db.logged_user("admin",id);
                    Intent intent = new Intent(this,admin_page.class);
                    intent.putExtra("user",logged_user);
                    startActivity(intent);
                    finish();
                } else if (pwd_nurse.equals(pwd)) {
                    logged_user=db.logged_user("nurse",id);
                    Intent intent = new Intent(this, nurse_page.class);
                    intent.putExtra("user",logged_user);
                    startActivity(intent);
                    finish();
                } else if (pwd_doctor.equals(pwd)) {
                    Intent intent = new Intent(this, doctor_page.class);
                    logged_user=db.logged_user("doctor",id);
                    intent.putExtra("user",logged_user);
                    startActivity(intent);
                    finish();
                } else if (pwd_patient.equals(pwd)) {
                    Intent intent = new Intent(this, patient_page.class);
                    logged_user=db.logged_user("patient",id);
                    intent.putExtra("user",logged_user);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "You Entered Wrong Username and Password", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (view == register_btn) {
            Intent intent = new Intent(this, Registration_module.class);
            startActivity(intent);
            finish();
        }


        //try path
        if(view==try_button){
            showDialog();
        }
    }




    //below code for animation
    private void btn_click_animation() {
        slideOutBottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
// Update the text here//in_top.xml
                @SuppressLint("ResourceType") Animation slideInTop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.in_top);
                login.startAnimation(slideInTop);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //below code for text alerting
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.username:
                        validateusername();
                    break;
                case  R.id.password:
                        validatePassword();
                    break;
            }
        }

    }
    private boolean validateusername() {
        String username = u_name.getText().toString().trim();

        if (username.isEmpty()) {
            username_layout.setError("Enter the correct username");
            requestFocus(u_name);
            return false;
        }
        else
         {
             username_layout.setErrorEnabled(false);
        }
        if(username.toLowerCase().startsWith("admin")) {
            u_name.setCompoundDrawables( Right, null, left, null );
        }
        else{
            u_name.setCompoundDrawables( Right, null, null, null );
        }

        return true;
    }
    private boolean validatePassword() {
        String password=pass.getText().toString().trim();
        if (password.isEmpty()) {
            pass.setError("Enter the password");
            requestFocus(pass);
            return false;
        } else {
            password_layout.setErrorEnabled(false);
        }

        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}

