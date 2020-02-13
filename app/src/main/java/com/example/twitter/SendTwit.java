package com.example.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendTwit extends AppCompatActivity  implements View.OnClickListener {
    private EditText editText;
    private ListView ListView;
    private Button OthersTweet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_twit);

        editText = findViewById(R.id.editText);
        ListView = findViewById(R.id.ListView);
        OthersTweet=findViewById(R.id.OthersTweet);

        OthersTweet.setOnClickListener(this);

    }

    public void btnTweet(View view){
        ParseObject parseObject = new ParseObject("MyTweet");
        parseObject.put("tweet", editText.getText().toString());
        parseObject.put("user", ParseUser.getCurrentUser().getUsername());
        final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Loading....");
                progressDialog.show();

                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){

                            FancyToast.makeText( SendTwit.this,ParseUser.getCurrentUser()
                                            .getUsername() +"'s tweet " + "("
                                    + editText.getText().toString() + ")" + " is saved!!!", Toast.LENGTH_LONG,
                                    FancyToast.SUCCESS,true).show();
                        } else {
                            FancyToast.makeText(SendTwit.this,e.getMessage(),
                                    Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();

                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View v) {

        final ArrayList<HashMap<String,String>> tweetList = new ArrayList<>();
        final SimpleAdapter adapter = new SimpleAdapter(SendTwit.this,tweetList,android.R.layout.simple_list_item_2,
                new String[]{"tweetUserName","tweetValue"},
                new int[]{android.R.id.text1,android.R.id.text2});
        try {
            final ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("MyTweet");
            parseQuery.whereContainedIn("user", ParseUser.getCurrentUser().getList("fanOf"));
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects.size() > 0 && e == null) {
                        for (ParseObject tweetobject : objects) {
                            HashMap<String, String> userTweet = new HashMap<>();
                            userTweet.put("tweetuserName", tweetobject.getString("user"));
                            userTweet.put("tweetValue", tweetobject.getString("tweet"));
                            tweetList.add(userTweet);

                        }
                       ListView.setAdapter(adapter);
                    }
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

        }

    }

