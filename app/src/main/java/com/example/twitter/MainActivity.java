package com.example.twitter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name,email,password;
    private Button btnLogIn,btnSignUp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnSignUp);

                }
                return false;
            }
        });
        email=findViewById(R.id.email);
        btnLogIn=findViewById(R.id.btnLogIn);
        btnSignUp=findViewById(R.id.btnSignUp);
       // id = findViewById(R.id.id);


        btnLogIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSignUp:
                if (email.getText().toString().equals("") ||
                        password.getText().toString().equals("") ||
                        name.getText().toString().equals("")) {
                    FancyToast.makeText(MainActivity.this, "Please enter All Credentials",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();


                } else {


                    final ParseUser appUser = new ParseUser();
                    appUser.setUsername(name.getText().toString());
                    appUser.setEmail(email.getText().toString());
                    appUser.setPassword(password.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing Up" + name.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(MainActivity.this, appUser.get("username") +
                                                "Signed Up Successfully"
                                        , FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                                Transition();


                            } else {
                                FancyToast.makeText(MainActivity.this, "There is an error" + e.getMessage(),
                                        FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;
            case R.id.btnLogIn:


                Intent intent = new Intent(MainActivity.this, Mylogin.class);
                startActivity(intent);

               break;
        }
//        public void onClick(View view)
//        {
//            try {
//                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    public void Transition ()
    {
        Intent intent = new Intent(MainActivity.this,Users.class);
        startActivity(intent);

    }

    }




