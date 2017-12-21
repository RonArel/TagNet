package com.example.ortel.tagnet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
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

public class RelationshipRemoved extends FragmentActivity implements MainActivity.GlobalConstants {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = new WebView(this);

        CookieManager.getInstance().setAcceptCookie(true);
        setContentView(R.layout.relationship);
        Log.d("OkHttp", "debug1");

        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,usersInfo);

            final RelationShipAdapterRemoved adapter = null;
            url = getIntent().getStringExtra("userInfo");
            urlTwo = getIntent().getStringExtra("userInfoTwo");
            lvRelationShipAllUser = (ListView) findViewById(R.id.lvRelationShip);
            lvRelationShipAllUser.setAdapter(adapter);
            context = RelationshipRemoved.this;
            /*lvRelationShipAllUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                    int test = i;
                    connectOrDisconnectUser(test);
                }
            });*/

            getAllMediaImages();


        //connectOrDisconnectUser();
    }


    /*public void onClick(View v) {
        if (v == lvRelationShip) {
            connectOrDisconnectUser();
        }
    } */


    private void setImageGridAdapter() {
        lvRelationShipAllUser.setAdapter(new RelationShipAdapterRemoved(context,
                usersInfo));
    }
    public void setListener(InstagramApp.OAuthAuthenticationListener listener) {
        mListener = listener;
    }

    private void connectOrDisconnectUser(final int position) {
        System.out.println(ar);

        final AlertDialog.Builder builder = new AlertDialog.Builder(
                RelationshipRemoved.this);
        builder.setMessage("Do you want to un tag " +usersInfo.get(position).get(
                RelationshipRemoved.likedOne))
                .setCancelable(false)
                .setPositiveButton("Yes",

                        new DialogInterface.OnClickListener() {
                            //do something

                            public void onClick(DialogInterface dialog,int randomvar) {
                                Log.d("OkHttpRemove","getting removing!");
                                String username = (globalInt);
                                String UT = usersInfo.get(position).get(
                                        RelationshipRemoved.likedOne);
                                String type = "remove";
                                BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
                                backgroundWorker.execute(type, username, UT);
                                startActivity(new Intent(RelationshipRemoved.this, MainActivity.class));
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


    public int i = 0;
    ArrayList<String> ar = new ArrayList<String>();
    private void getAllMediaImages() {
        pd = ProgressDialog.show(context, "", "Loading...");
        new Thread(new Runnable() {

            @Override
            public void run() {
                int what = WHAT_FINALIZE;
                try {

                    // URL url = new URL(mTokenUrl + "&code=" + code);
                    JSONParser jsonParser = new JSONParser();
                    //final JSONObject jsonObject = jsonParser.getJSONFromUrlByGet(urlTwo);
                    //JSONArray data = jsonObject.getJSONArray(TAG_DATA);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://tagnetserver.club/getRemoved.php")
                            .build();
                    Call call = client.newCall(request);

                    final int finalWhat = what;
                    call.enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            //observe reason of failure using
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

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

                                    for(int i=0;i<jsonArray.length();i++) {
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        //change it to likes....

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Log.d("OkHttpUsername", jsonObject.getString("username"));
                                        //Log.d("OkHttptest", String.valueOf(jsonObject));
                                        Log.d("OkHttpGlobal", globalInt);
                                        if (jsonObject.getString("username").equals(globalInt)) {

                                            //hashMap.put("username",jsonObject.getString("username"));
                                            if (TextUtils.isEmpty(jsonObject.getString("likedOne"))) {

                                            } else {
                                                Log.d("OkHttp", jsonObject.getString("likedOne"));
                                                hashMap.put(likedOne, jsonObject.getString("likedOne"));
                                                usersInfo.add(hashMap);

                                            }
                                            if (TextUtils.isEmpty(jsonObject.getString("likedTwo"))) {

                                            } else {
                                                Log.d("OkHttp", jsonObject.getString("likedTwo"));
                                                hashMap.put(likedTwo, jsonObject.getString("likedTwo"));
                                                usersInfo.add(hashMap);
                                            }
                                            if (TextUtils.isEmpty(jsonObject.getString("likedThree"))) {

                                            } else {
                                                Log.d("OkHttp", jsonObject.getString("likedThree"));
                                                hashMap.put(likedThree, jsonObject.getString("likedThree"));
                                                usersInfo.add(hashMap);
                                            }

                                            if (TextUtils.isEmpty(jsonObject.getString("likedFour"))) {

                                            } else {
                                                Log.d("OkHttp", jsonObject.getString("likedFour"));
                                                hashMap.put(likedFour, jsonObject.getString("likedFour"));
                                                usersInfo.add(hashMap);
                                            }
                                            if (TextUtils.isEmpty(jsonObject.getString("likedFive"))) {

                                            } else {
                                                Log.d("OkHttp", jsonObject.getString("likedFive"));
                                                hashMap.put(likedFive, jsonObject.getString("likedFive"));
                                                usersInfo.add(hashMap);
                                            }
                                            //hashMap.put(TAG_USERNAME, jsonObject.getString("username"));
                                            //usersInfo.add(hashMap);

                                            Log.d("OkHttp", String.valueOf(hashMap));

                                        }
                                    }

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
