package com.example.twitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class Users extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayList<String> tusers;
    private ArrayAdapter adapter;
   // private String followedUser = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        FancyToast.makeText(Users.this, "Logged in Successfully" + ParseUser.getCurrentUser()
                .getUsername()
                , FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
        
        listView =findViewById(R.id.listview);
        tusers = new ArrayList<>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_checked,tusers);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(this);

    try {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    for (ParseUser twitterUser : objects) {
                        tusers.add(twitterUser.getUsername());
                    }
                    listView.setAdapter(adapter);
                    for (String twitterUser : tusers){
                       // if (ParseUser.getCurrentUser().getList("fanof") != null) {
                            // followedUser = followedUser + twitterUser + "\n";

                            if (ParseUser.getCurrentUser().getList("fanOf").contains(twitterUser)) {
                                listView.setItemChecked(tusers.indexOf(twitterUser), true);
//                            FancyToast.makeText(Users.this,ParseUser.getCurrentUser().getUsername()
//                                    + " Is Following " + followedUser
//                                    , Toast.LENGTH_LONG,FancyToast.INFO,true).show();
                            //}
                        }
                    }
                }
            }
        });
    } catch (Exception e) {
        e.printStackTrace();
    }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.logout_item:
                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        Intent intent = new Intent(Users.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                break;

            case R.id.sendTweet:
                Intent intent = new Intent(Users.this,SendTwit.class);
                startActivity(intent);

                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckedTextView checkedTextView = (CheckedTextView) view;
        if (checkedTextView.isChecked())
        {
            FancyToast.makeText( Users.this,tusers.get(position) + " is Followed"
                    , Toast.LENGTH_SHORT, FancyToast.INFO,true).show();
            ParseUser.getCurrentUser().add("fanOf",tusers.get(position));
        }
        else{
            FancyToast.makeText( Users.this,tusers.get(position) +"  Un Followed", Toast.LENGTH_SHORT,
                    FancyToast.INFO,true).show();

            ParseUser.getCurrentUser().getList("fanOf").remove(tusers.get(position));

            List currentUserFanOFList = ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().remove("fanOf");
            ParseUser.getCurrentUser().put("fanOf",currentUserFanOFList);
        }



            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e== null){
                        FancyToast.makeText(Users.this,"saved",
                                Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                    }


                }
            });
    }
}
