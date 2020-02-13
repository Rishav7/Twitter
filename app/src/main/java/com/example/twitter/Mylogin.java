package com.example.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Mylogin extends AppCompatActivity implements View.OnClickListener {
    private EditText LoginEmail,LoginPassword;
    private Button btnLogIn2,btnSignUp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylogin);

        setTitle("Log In");
        LoginEmail=findViewById(R.id.LoginEmail);
        LoginPassword=findViewById(R.id.LoginPassword);

        LoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER &&  event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    onClick(btnLogIn2);
                }
                return false;
            }
        });

        btnLogIn2=findViewById(R.id.btnLogIn2);
        btnSignUp2=findViewById(R.id.btnSignUp2);


        btnLogIn2.setOnClickListener(this);
        btnSignUp2.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null)
        {
            ParseUser.getCurrentUser().logOut();
        }




    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnLogIn2:
                ParseUser.logInInBackground(LoginEmail.getText().toString(), LoginPassword.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if(user != null && e == null)
                                {
                                    FancyToast.makeText(Mylogin.this, user.get("username") +
                                                    "Logged in Successfully"
                                            , FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                                        Transition();

                                }
                                else {
                                    FancyToast.makeText(Mylogin.this, "login error",
                                            FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                                }}
                        });



                break;
            case R.id.btnSignUp2:
                Intent intent = new Intent(Mylogin.this, MainActivity.class);
                startActivity(intent);
                break;
        }

    }


//    public void rootlayouttapped (View view)
//    {
//        try {
//
//            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }

    public void Transition ()
    {
        Intent intent = new Intent(Mylogin.this,Users.class);
        startActivity(intent);

    }


    }

