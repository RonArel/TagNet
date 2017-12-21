package com.example.ortel.tagnet;

import java.io.IOException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import android.support.v4.view.GestureDetectorCompat;
import android.net.ParseException;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


///https://www.instagram.com/NetsGets/media////
import com.example.ortel.tagnet.BackgroundWorker;
import com.example.ortel.tagnet.InstagramApp;
import com.example.ortel.tagnet.InstagramApp.OAuthAuthenticationListener;
import lazyload.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


//import static com.tag.instagramdemo.R.id.bottomBar;
//import com.roughike.bottombar.OnMenuItemClickListner;

public class MainActivityOld extends FragmentActivity implements View.OnClickListener{
    InputStream is = null;
    String result = null;
    String x;
    String[] data;
    private ListView lvRelationShipAllUser;

    //BottomBar test;
    private GestureDetectorCompat getGesture;
    private static InstagramApp mApp;
    String line=null;
    String address = "http://tagnetserver.club/getRemoved.php";
    private Button btnConnect, btnViewInfo, btnGetAllImages, btnFollowers,
            btnFollwing,btnRemove,btnTogether;
    private LinearLayout llAfterLoginView;
    public static HashMap<String, String> userInfoHashmap = new HashMap<String, String>();
    private Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == InstagramApp.WHAT_FINALIZE) {
                userInfoHashmap = mApp.getUserInfo();
                String username =(userInfoHashmap.get(InstagramApp.TAG_USERNAME));
                Log.d("HashMapOne", String.valueOf(userInfoHashmap.get(InstagramApp.TAG_USERNAME)));
                Log.d("HashMapTwo", String.valueOf(userInfoHashmap));
                String password = "hey";
                String type = "register";
                //String username = params[1];
                Log.d("TheRealapp", "I am hereOne");
                Log.d("TheRealappUsername", username);
                //String password = params[2];
                Log.d("TheRealapp", "I am hereOne");
                Log.d("TheRealappPassword", password);
                btnConnect.setText("Disconnect");
                BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
                backgroundWorker.execute(type, username, password);
            } else if (msg.what == InstagramApp.WHAT_FINALIZE) {
                //Toast.makeText(MainActivity.this, "Check your network.",
                //		Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });
    EditText UsernameEt, PasswordEt, username, password;



    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /**BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottomBar);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        /**	String url;

                         switch (item.getItemId()) {

                         case R.id.Followers:
                         url = "https://api.instagram.com/v1/users/"
                         + userInfoHashmap.get(InstagramApp.TAG_ID)
                         + "/follows?access_token=" + mApp.getTOken();
                         startActivity(new Intent(MainActivity.this, Relationship.class)
                         .putExtra("userInfo", url));
                         break;
                         case R.id.Following:
                         url = "https://api.instagram.com/v1/users/"
                         + userInfoHashmap.get(InstagramApp.TAG_ID)
                         + "/followed-by?access_token=" + mApp.getTOken();
                         startActivity(new Intent(MainActivity.this, Relationship.class)
                         .putExtra("userInfo", url));
                         break;
                         case R.id.Matched:
                         startActivity(new Intent(MainActivity.this, Relationship.class)
                         );
                         break;
                         case R.id.Main:
                         startActivity(new Intent(MainActivity.this, Relationship.class)
                         );
                         break;
                         case R.id.Tagged:
                         startActivity(new Intent(MainActivity.this, Relationship.class)
                         );
                         break;

                         }**//**
                        return true;
                    }
                });**/
        //getGesture = new GestureDetectorCompat(this, new LearnGesture());
        //test = BottomBar.attach(this,savedInstanceState);
        /**findViewById(R.id.followers);
         findViewById(R.id.followings).setOnClickListener(this);
         findViewById(R.id.matched).setOnClickListener(this);
         findViewById(R.id.tagged).setOnClickListener(this);
         //	userInfoHashmap = mApp.getUserInfo();**/
        //String.valueOf(getdata());
        mApp = new InstagramApp(this, ApplicationData.CLIENT_ID,
                ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);
        mApp.setListener(new OAuthAuthenticationListener() {

            @Override
            public void onSuccess() {

                // tvSummary.setText("Connected as " + mApp.getUserName());
                //getdata();
                // USERNAME IS THE ERROR


                llAfterLoginView.setVisibility(View.VISIBLE);
                // userInfoHashmap = mApp.
                mApp.fetchUserName(handler);
                startActivity(new Intent(MainActivityOld.this, MainActivity.class));

            }
			/*public void OnLogin() {


			}*/

            @Override
            public void onFail(String error) {
                Toast.makeText(MainActivityOld.this, error, Toast.LENGTH_SHORT)
                        .show();
            }

        });
        setWidgetReference();
        bindEventHandlers();

        if (mApp.hasAccessToken()) {
            // tvSummary.setText("Connected as " + mApp.getUserName());
            btnConnect.setText("Disconnect");
            llAfterLoginView.setVisibility(View.VISIBLE);
            mApp.fetchUserName(handler);

        }

    }
    //@Override
    /**public boolean onTouchEvent(MotionEvent event){
     this.getGesture.onTouchEvent(event);
     return super.onTouchEvent(event);
     }
     class LearnGesture extends GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY){
    if (event2.getX() > event1.getX()){
    Intent intent = (new Intent(MainActivity.this, RelationshipRemoved.class));
    finish();
    startActivity(intent);

    }else if(event2.getX() < event1.getX()){
    Intent intent = (new Intent(MainActivity.this, RelationshipTogether.class));
    finish();
    startActivity(intent);

    }
    return true;

    }
    }**/
    public interface GlobalConstants {
        String globalInt = userInfoHashmap.get(InstagramApp.TAG_USERNAME);
        String globalIntTwo = null;
    }




    private void bindEventHandlers() {
        btnConnect.setOnClickListener(this);

    }

    private void setWidgetReference() {
        llAfterLoginView = (LinearLayout) findViewById(R.id.llAfterLoginView);
        btnConnect = (Button) findViewById(R.id.btnConnect);

    }



    // OAuthAuthenticationListener listener ;

    @Override
    public void onClick(View v) {
        if (v == btnConnect) {

            //DasGood();
            connectOrDisconnectUser();
            //getdata();
        }
    }



    private void DasGood(){

    }
    public interface GlobalConstantsTwo {
        String urlReal = "https://api.instagram.com/v1/users/"
                + userInfoHashmap.get(InstagramApp.TAG_ID)
                + "/followed-by?access_token=" + mApp.getTOken();
    }

    private void connectOrDisconnectUser() {
        //startActivity(new Intent(MainActivityOld.this, MainActivity.class));
        if (mApp.hasAccessToken()) {
            //CookieManager.getInstance().setAcceptCookie(true);

            final AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivityOld.this);
            builder.setMessage("Disconnect from Instagram?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    mApp.resetAccessToken();
                                    // btnConnect.setVisibility(View.VISIBLE);
                                    llAfterLoginView.setVisibility(View.GONE);
                                    btnConnect.setText("Connect");
                                    // tvSummary.setText("Not connected");
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });
            final AlertDialog alert = builder.create();
            alert.show();
            startActivity(new Intent(MainActivityOld.this, MainActivity.class));

        } else {
            mApp.authorize();
            //startActivity(new Intent(MainActivityOld.this, MainActivity.class));

        }
    }

    public String Test = userInfoHashmap.get(InstagramApp.TAG_USERNAME);
    private String[] getdata(){
        try {
            URL url = new URL(address);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            is=new BufferedInputStream(con.getInputStream());
        }catch(Exception e){
            e.printStackTrace();
        }
        try {

            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();

            while((line=br.readLine())!=null){
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();
            //Log.d("TheRealapp", result);

        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            JSONArray js=new JSONArray(result);
            JSONObject jo = null;
            Log.d("TheRealApp", String.valueOf(js));
            data = new String[js.length()];
            for(int i=0;i<js.length();i++){
                jo=js.getJSONObject(i);
                data[i]= jo.getString("username");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;


    }
    private void displayInfoDialogView() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivityOld.this);
        alertDialog.setTitle("Profile Info");

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.profile_view, null);
        alertDialog.setView(view);
        ImageView ivProfile = (ImageView) view
                .findViewById(R.id.ivProfileImage);
        TextView tvName = (TextView) view.findViewById(R.id.tvUserName);
        TextView tvNoOfFollwers = (TextView) view
                .findViewById(R.id.tvNoOfFollowers);
        TextView tvNoOfFollowing = (TextView) view
                .findViewById(R.id.tvNoOfFollowing);
        new ImageLoader(MainActivityOld.this).DisplayImage(
                userInfoHashmap.get(InstagramApp.TAG_PROFILE_PICTURE),
                ivProfile);
        tvName.setText(userInfoHashmap.get(InstagramApp.TAG_USERNAME));
        tvNoOfFollowing.setText(userInfoHashmap.get(InstagramApp.TAG_FOLLOWS));
        tvNoOfFollwers.setText(userInfoHashmap
                .get(InstagramApp.TAG_FOLLOWED_BY));
        alertDialog.create().show();
    }


    /**public boolean onCreateOptionsMenu(Menu menu){
     getMenuInflater().inflate(R.menu.menu_main, menu);
     return true;
     }**/


}
