package com.example.ortel.tagnet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.ortel.tagnet.InstagramApp.TAG_PROFILE_PICTURE;
import static com.example.ortel.tagnet.InstagramApp.TAG_USERNAME;
import static com.example.ortel.tagnet.InstagramApp.WHAT_ERROR;
import static com.example.ortel.tagnet.InstagramApp.WHAT_FINALIZE;
import static com.example.ortel.tagnet.Relationship.TAG_DATA;
import static com.example.ortel.tagnet.Relationship.TAG_ID;

import android.os.Handler;
import android.os.Handler.Callback;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragmentThree.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragmentThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends android.support.v4.app.Fragment implements MainActivity.GlobalConstants, MainActivityOld.GlobalConstantsTwo {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ProgressDialog pd;
    public HashMap<String, String> userInfoHashmap = new HashMap<String, String>();
    private InstagramApp mApp;
    private String url = "";
    private String urlTwo = "";
    private static final String ARG_PARAM1 = "param1";
    private ArrayList<HashMap<String, String>> usersInfo = new ArrayList<HashMap<String, String>>();
    private Handler handler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (pd != null && pd.isShowing())
                pd.dismiss();
            if (msg.what == WHAT_FINALIZE) {
                //TEST
                userInfoHashmap = mApp.getUserInfo();
                Log.d("OkHttpTesting","Grid");

                setImageGridAdapter();

            } else {
                //Toast.makeText(context, "Check your network.",
                //      Toast.LENGTH_SHORT).show();
            }

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
    private static final String ARG_PARAM2 = "param2";
    private ListView lvRelationShipAllUser;
    private Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**    * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragmentThree.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void connectOrDisconnectUser(final int position) {
        //System.out.println(ar);

        final AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        builder.setMessage("Do you want to tag " +usersInfo.get(position).get(
                Relationship.TAG_USERNAME))
                .setCancelable(false)
                .setPositiveButton("Yes",

                        new DialogInterface.OnClickListener() {
                            //do something

                            public void onClick(DialogInterface dialog,int randomvar) {


                                String username = (globalInt);
                                String UT = usersInfo.get(position).get(
                                        Relationship.TAG_USERNAME);
                                String type = "add";
                                BackgroundWorker backgroundWorker = new BackgroundWorker(context.getApplicationContext());
                                backgroundWorker.execute(type, username, UT);



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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //url = getIntent().getStringExtra("userInfo");
        //urlTwo = getIntent().getStringExtra("userInfoTwo");

        CookieManager.getInstance().setAcceptCookie(true);
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,usersInfo);
        /**final RelationShipAdapter adapter = null;
        lvRelationShipAllUser = (ListView) findViewById(R.id.lvRelationShip);
        lvRelationShipAllUser.setAdapter(adapter);
        context = BlankFragment.this;**/

        getAllMediaImages();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private void setImageGridAdapter() {
        lvRelationShipAllUser.setAdapter(new RelationShipAdapter(context,
                usersInfo));
    }
    private void getAllMediaImages() {
        Log.d("OkHttpTwo", "test");

        //pd = ProgressDialog.show(context, "", "Loading...");
        new Thread(new Runnable() {

            @Override
            public void run() {
                int what = WHAT_FINALIZE;
                try {
                    Log.d("OkHttpThree", "test");
                    // URL url = new URL(mTokenUrl + "&code=" + code);
                    JSONParser jsonParser = new JSONParser();
                    Log.d("OkHttpFour", "test");
                    Log.d("OkHttpUser", InstagramApp.TAG_ID);
                    Log.d("OkHttpToken", mApp.getTOken());


                    //userInfoHashmap.get(InstagramApp.TAG_ID) and mApp.getTOken() is the issue!!!!!!!!!
                    final JSONObject jsonObject = jsonParser.getJSONFromUrlByGet("https://api.instagram.com/v1/users/"
                            + userInfoHashmap.get(InstagramApp.TAG_ID)
                            + "/follows?access_token=" + mApp.getTOken());
                    Log.d("OkHttpTwo", "https://api.instagram.com/v1/users/"
                            + userInfoHashmap.get(InstagramApp.TAG_ID)
                            + "/follows?access_token=" + mApp.getTOken());
                    Log.d("Test", "Test");
                    JSONObject jsonObjectTwo = jsonParser.getJSONFromUrlByGet("http://tagnetserver.club/getRemoved.php");
                    JSONArray data = jsonObject.getJSONArray(TAG_DATA);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://tagnetserver.club/getRemoved.php")
                            .build();
                    Call call = client.newCall(request);
                    call.enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            //observe reason of failure using
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if(response.isSuccessful()){

                                Log.d("OkHttp",response.body().string());
                                Log.d("OkHttp", String.valueOf(jsonObject));
                                String TestVar = response.body().string();
                            }
                            else{
                                //observe error
                            }

                        }
                    });
                    for (int data_i = 0; data_i < data.length(); data_i++) {

                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_id = data_obj.getString(TAG_ID);

                        hashMap.put(TAG_PROFILE_PICTURE,
                                data_obj.getString(TAG_PROFILE_PICTURE));

                        // String str_username =
                        // data_obj.getString(TAG_USERNAME);
                        //
                        // String str_bio = data_obj.getString(TAG_BIO);
                        //
                        // String str_website = data_obj.getString(TAG_WEBSITE);
                        //USERNAME

                        hashMap.put(TAG_USERNAME,
                                data_obj.getString(TAG_USERNAME));
                        Log.d("MyApp",data_obj.getString(TAG_USERNAME));
                        if (data_obj.getString(TAG_USERNAME).equals("test")){

                        }else{
                            usersInfo.add(hashMap);
                        }
                        //Log.d("MyApp", String.valueOf(data));




                    }
                    System.out.println("jsonObject::" + jsonObject);

                } catch (Exception exception) {
                    Log.d("OkHttp","Bad");
                    exception.printStackTrace();
                    what = WHAT_ERROR;
                }
                // pd.dismiss();
                //mListener.onSuccess();
                Log.d("OkHttp","Grid");

                handler.sendEmptyMessage(what);
            }
        }).start();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.relationship, container, false);
        lvRelationShipAllUser = (ListView) v.findViewById(R.id.lvRelationShip);

        final RelationShipAdapter adapter = null;
        lvRelationShipAllUser.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                int test = i;
                connectOrDisconnectUser(test);
            }
        });

        lvRelationShipAllUser.setAdapter(adapter);
        //context = BlankFragment.this;
        return v;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
