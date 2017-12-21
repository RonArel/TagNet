package com.example.ortel.tagnet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RelationshipTogether extends Activity implements MainActivity.GlobalConstants {


    private String url = "";
    private String urlTwo = "";
    public HashMap<String, String> userInfoHashmap = new HashMap<String, String>();
    private List<String> where = new ArrayList<String>();
    private InstagramApp.OAuthAuthenticationListener mListener;
    private ListView lvRelationShipAllUser;

    private ArrayList<HashMap<String, String>> usersInfo = new ArrayList<HashMap<String, String>>();
    private Context context;
    private int WHAT_FINALIZE = 0;
    private static int WHAT_ERROR = 1;
    private ProgressDialog pd;
    private InstagramApp mApp;
    private int x = 0;
    private int y = 0;

    private Handler handler = new Handler(new Callback() {

        @Override
//FIX THE CHECK NETWORK GLITCH
        public boolean handleMessage(Message msg) {

            if (pd != null && pd.isShowing())
                pd.dismiss();
            //msg.what = 2;
            if (msg.what == WHAT_FINALIZE) {
                setImageGridAdapter();

            } else if (x==1){
                Toast.makeText(context, "Check your network.",
                        Toast.LENGTH_SHORT).show();
            }
            //FIX THE CHECK NETWORK GLITCH
            x = x + 1;
            return false;
        }
    });
    private Handler handlerOne = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            userInfoHashmap = mApp.getUserInfo();

            return false;
        }
    });
    public static final String TAG_DATA = "data";
    public static final String TAG_ID = "id";
    public static final String TAG_PROFILE_PICTURE = "profile_picture";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_BIO = "bio";
    public static final String TAG_WEBSITE = "website";
    public static final String TAG_FULL_NAME = "full_name";
    public static final String likedOne = "likedOne";
    public static final String likedTwo = "likedTwo";
    public static final String likedThree = "likedThree";
    public static final String likedFour = "likedFour";
    public static final String likedFive = "likedFive";
    public static final String OneT = "OneT";
    public static final String TwoT = "TwoT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = new WebView(this);

        CookieManager.getInstance().setAcceptCookie(true);
        setContentView(R.layout.relationship);
        Log.d("OkHttp", "debug1");

        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,usersInfo);

        final RelationShipAdapterTogether adapter = null;
        url = getIntent().getStringExtra("userInfo");
        urlTwo = getIntent().getStringExtra("userInfoTwo");
        lvRelationShipAllUser = (ListView) findViewById(R.id.lvRelationShip);
        lvRelationShipAllUser.setAdapter(adapter);
        context = RelationshipTogether.this;
            lvRelationShipAllUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                    int test = i;
                    connectOrDisconnectUser(test);
                }
            });

        getAllMediaImages();


        //connectOrDisconnectUser();
    }


    /*public void onClick(View v) {
        if (v == lvRelationShip) {
            connectOrDisconnectUser();
        }
    } */

// CHANGE
private void connectOrDisconnectUser(final int position) {
    System.out.println(ar);

    final AlertDialog.Builder builder = new AlertDialog.Builder(
            RelationshipTogether.this);
    builder.setMessage("Do you want to tag " +usersInfo.get(position).get(
            RelationshipTogether.TwoT))
            .setCancelable(false)
            .setPositiveButton("Yes",

                    new DialogInterface.OnClickListener() {
                        //do something

                        public void onClick(DialogInterface dialog,int randomvar) {

                            String username = (globalInt);
                            String UT = usersInfo.get(position).get(
                                    RelationshipTogether.TwoT);
                            String type = "removeT";
                            BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
                            backgroundWorker.execute(type, username, UT);
                            context.startActivity(new Intent(context, MainActivity.class));
                            Toast.makeText(context, "UnTagged!",
                                    Toast.LENGTH_SHORT).show();




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

}
    private void setImageGridAdapter() {
        lvRelationShipAllUser.setAdapter(new RelationShipAdapterTogether(context,
                usersInfo));
    }
    public void setListener(InstagramApp.OAuthAuthenticationListener listener) {
        mListener = listener;
    }


    public interface GlobalConstants {

    }
    public int i = 0;
    ArrayList<String> ar = new ArrayList<String>();
    private void getAllMediaImages() {
        pd = ProgressDialog.show(context, "", "Loading...");
        new Thread(new Runnable() {

            @Override
            public void run() {
                int what = WHAT_FINALIZE;
                try {
                    Log.d("OkHttpT",  "1");

                    // URL url = new URL(mTokenUrl + "&code=" + code);
                    JSONParser jsonParser = new JSONParser();
                    //CHANGE
                    //final JSONObject jsonObject = jsonParser.getJSONFromUrlByGet(urlTwo);
                    //JSONArray data = jsonObject.getJSONArray(TAG_DATA);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://tagnetserver.club/getTogether.php")
                            .build();
                    Call call = client.newCall(request);
                    //END CHANGE
                    final int finalWhat = what;
                    call.enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            //observe reason of failure using
                            e.printStackTrace();

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.d("OkHttpT",  "2");
                            int what = WHAT_FINALIZE;
                            if(response.isSuccessful()){
                                handler.sendEmptyMessage(finalWhat);
                                try{
                                    //Log.d("OkHttp",response.body().string());
                                    //Log.d("OkHttp", String.valueOf(jsonObject));
                                    Log.d("OkHttp","debug2");
                                    //HashMap<String, String> hashMap = new HashMap<String, String>();
                                    String TestVar = response.body().string();
                                    JSONArray jsonArray =new JSONArray(TestVar);
                                    Log.d("OkHttpT",  "3");
                                    for(int i=0;i<jsonArray.length();i++) {
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        //CHANGE
                                        Log.d("OkHttpT",  "4");
                                        Log.d("OkHttpT", String.valueOf(hashMap));
                                        Log.d("OkHttpUsernameOne", String.valueOf(jsonArray));
                                        //Log.d("OkHttptest", String.valueOf(jsonObject)););
                                        //Log.d("OkHttptest", String.valueOf(jsonObject));
                                        JSONObject jsonObjectj = jsonArray.getJSONObject(i);
                                        Log.d("OkHttpUsername", jsonObjectj.getString("OneT"));
                                        Log.d("OkHttpUsernameObject", String.valueOf(jsonObjectj));
                                        //Log.d("OkHttptest", String.valueOf(jsonObject));
                                        Log.d("OkHttpGlobaltest", globalInt);
                                        if (jsonObjectj.getString("OneT").equals(globalInt)) {

                                            hashMap.put(TwoT, jsonObjectj.getString("TwoT"));
                                            usersInfo.add(hashMap);

                                               /**NotificationManager notificationmgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                                            Intent intent = new Intent(this, resultpage.class);
                                            PendingIntent pintent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

                                            //   PendingIntent pintent = PendingIntent.getActivities(this,(int)System.currentTimeMillis(),intent, 0);


                                            Notification notif = new Notification.Builder(this)
                                                    .setSmallIcon(R.drawable.ic_stat_name)
                                                    .setContentTitle("Hello Android Hari")
                                                    .setContentText("Welcome to Notification Service")
                                                    .setContentIntent(pintent)
                                                    .build();


                                            notificationmgr.notify(0,notif);


**/

                                            Log.d("OkHttpT", String.valueOf(hashMap));


                                        }else if (jsonObjectj.getString("TwoT").equals(globalInt)){
                                            hashMap.put(TwoT, jsonObjectj.getString("OneT"));
                                            usersInfo.add(hashMap);
                                        }
                                        Log.d("OkHttpT", String.valueOf(hashMap));
                                    }
                                    //END CHANGE
                                    what = WHAT_ERROR;
                                }catch (JSONException e){
                                    e.printStackTrace();
                                    what = WHAT_ERROR;
                                }
                                handler.sendEmptyMessage(what);
                            }
                            else{
                                //observe error
                            }

                        }
                    });

                    //System.out.println("jsonObject::" + jsonObject);

                } catch (Exception exception) {
                    exception.printStackTrace();
                    what = WHAT_ERROR;
                }
                // pd.dismiss();
                //mListener.onSuccess();
                handler.sendEmptyMessage(what);
            }
        }).start();
    }
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }



}
