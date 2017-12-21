package com.example.ortel.tagnet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Relationship extends Activity implements MainActivity.GlobalConstants {


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
	private Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			if (pd != null && pd.isShowing())
				pd.dismiss();
			if (msg.what == WHAT_FINALIZE) {
				setImageGridAdapter();
			} else {
				Toast.makeText(context, "Check your network.",
						Toast.LENGTH_SHORT).show();
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
	public static final String TAG_DATA = "data";
	public static final String TAG_ID = "id";
	public static final String TAG_PROFILE_PICTURE = "profile_picture";
	public static final String TAG_USERNAME = "username";
	public static final String TAG_BIO = "bio";
	public static final String TAG_WEBSITE = "website";
	public static final String TAG_FULL_NAME = "full_name";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WebView webview = new WebView(this);
		CookieManager.getInstance().setAcceptCookie(true);
		setContentView(R.layout.relationship);

		//ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,usersInfo);
		final RelationShipAdapter adapter = null;
		url = getIntent().getStringExtra("userInfo");
		urlTwo = getIntent().getStringExtra("userInfoTwo");
		lvRelationShipAllUser = (ListView) findViewById(R.id.lvRelationShip);
		lvRelationShipAllUser.setAdapter(adapter);
		context = Relationship.this;
		lvRelationShipAllUser.setOnItemClickListener(new AdapterView.OnItemClickListener(){

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


	private void setImageGridAdapter() {
		lvRelationShipAllUser.setAdapter(new RelationShipAdapter(context,
				usersInfo));
	}
	public void setListener(InstagramApp.OAuthAuthenticationListener listener) {
		mListener = listener;
	}

	private void connectOrDisconnectUser(final int position) {
		System.out.println(ar);

			final AlertDialog.Builder builder = new AlertDialog.Builder(
					Relationship.this);
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
									BackgroundWorker backgroundWorker = new BackgroundWorker(getApplicationContext());
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
					final JSONObject jsonObject = jsonParser.getJSONFromUrlByGet(url);
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
