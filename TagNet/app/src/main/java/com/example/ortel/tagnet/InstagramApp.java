package com.example.ortel.tagnet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.ortel.tagnet.InstagramDialog.OAuthDialogListener;
import lazyload.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 
 * @author Thiago Locatelli <thiago.locatelli@gmail.com>
 * @author Lorensius W. L T <lorenz@londatiga.net>
 * 
 */
public class InstagramApp {

	private InstagramSession mSession;
	private InstagramDialog mDialog;
	private OAuthAuthenticationListener mListener;
	private ProgressDialog mProgress;
	private HashMap<String, String> userInfo = new HashMap<String, String>();
	private String mAuthUrl;
	private String mTokenUrl;
	private String mAccessToken;
	private Context mCtx;

	private String mClientId;
	private String mClientSecret;

	static int WHAT_FINALIZE = 0;
	static int WHAT_ERROR = 1;
	private static int WHAT_FETCH_INFO = 2;

	/**
	 * Callback url, as set in 'Manage OAuth Costumers' page
	 * (https://developer.github.com/)
	 */

	public static String mCallbackUrl = "";
	private static final String AUTH_URL = "https://api.instagram.com/oauth/authorize/";
	private static final String TOKEN_URL = "https://api.instagram.com/oauth/access_token";
	private static final String API_URL = "https://api.instagram.com/v1";

	private static final String TAG = "InstagramAPI";

	public static final String TAG_DATA = "data";
	public static final String TAG_ID = "id";
	public static final String TAG_PROFILE_PICTURE = "profile_picture";
	public static final String TAG_USERNAME = "username";
	public static final String likedOne = "likedOne";
	public static final String likedTwo = "likedTwo";
	public static final String likedThree = "likedThree";
	public static final String likedFour = "likedFour";
	public static final String likedFive = "likedFive";
	public static final String OneT = "OneT";
	public static final String TwoT = "TwoT";

	public static final String TAG_BIO = "bio";
	public static final String TAG_WEBSITE = "website";
	public static final String TAG_COUNTS = "counts";
	public static final String TAG_FOLLOWS = "follows";
	public static final String TAG_FOLLOWED_BY = "followed_by";
	public static final String TAG_MEDIA = "media";
	public static final String TAG_FULL_NAME = "full_name";
	public static final String TAG_META = "meta";
	public static final String TAG_CODE = "code";

	public InstagramApp(Context context, String clientId, String clientSecret,
                        String callbackUrl) {


		mClientId = clientId;
		mClientSecret = clientSecret;
		mCtx = context;
		mSession = new InstagramSession(context);
		mAccessToken = mSession.getAccessToken();
		mCallbackUrl = callbackUrl;
		mTokenUrl = TOKEN_URL + "?client_id=" + clientId + "&client_secret="
				+ clientSecret + "&redirect_uri=" + mCallbackUrl
				+ "&grant_type=authorization_code";
		mAuthUrl = AUTH_URL
				+ "?client_id="
				+ clientId
				+ "&redirect_uri="
				+ mCallbackUrl
				+ "&response_type=code&display=touch&scope=likes+comments+relationships+follower_list+public_content";

		OAuthDialogListener listener = new OAuthDialogListener() {
			@Override
			public void onComplete(String code) {
				getAccessToken(code);
			}

			@Override
			public void onError(String error) {
				mListener.onFail("Authorization failed");
			}
		};

		mDialog = new InstagramDialog(context, mAuthUrl, listener);
		mProgress = new ProgressDialog(context);
		mProgress.setCancelable(false);
	}

	private void getAccessToken(final String code) {
		mProgress.setMessage("Getting access token ...");
		mProgress.show();

		new Thread() {
			@Override
			public void run() {
				Log.i(TAG, "Getting access token");
				int what = WHAT_FETCH_INFO;
				try {
					URL url = new URL(TOKEN_URL);
					// URL url = new URL(mTokenUrl + "&code=" + code);
					Log.i(TAG, "Opening Token URL " + url.toString());
					HttpURLConnection urlConnection = (HttpURLConnection) url
							.openConnection();
					urlConnection.setRequestMethod("POST");
					urlConnection.setDoInput(true);
					urlConnection.setDoOutput(true);
					// urlConnection.connect();
					OutputStreamWriter writer = new OutputStreamWriter(
							urlConnection.getOutputStream());
					writer.write("client_id=" + mClientId + "&client_secret="
							+ mClientSecret + "&grant_type=authorization_code"
							+ "&redirect_uri=" + mCallbackUrl + "&code=" + code);
					writer.flush();
					String response = Utils.streamToString(urlConnection
							.getInputStream());
					Log.i(TAG, "response " + response);
					JSONObject jsonObj = (JSONObject) new JSONTokener(response)
							.nextValue();

					mAccessToken = jsonObj.getString("access_token");
					Log.i(TAG, "Got access token: " + mAccessToken);

					String id = jsonObj.getJSONObject("user").getString("id");
					String user = jsonObj.getJSONObject("user").getString(
							"username");
					String name = jsonObj.getJSONObject("user").getString(
							"full_name");

					mSession.storeAccessToken(mAccessToken, id, user, name);

				} catch (Exception ex) {
					what = WHAT_ERROR;
					ex.printStackTrace();
				}

				mHandler.sendMessage(mHandler.obtainMessage(what, 1, 0));
			}
		}.start();
	}

	public void fetchUserName(final Handler handler) {
		mProgress = new ProgressDialog(mCtx);
		mProgress.setMessage("Loading ...");
		mProgress.show();

		new Thread() {
			@Override
			public void run() {
				Log.i(TAG, "Fetching user info");
				int what = WHAT_FINALIZE;
				try {
					URL url = new URL(API_URL + "/users/" + mSession.getId()
							+ "/?access_token=" + mAccessToken);

					Log.d(TAG, "Opening URL " + url.toString());
					HttpURLConnection urlConnection = (HttpURLConnection) url
							.openConnection();
					urlConnection.setRequestMethod("GET");
					urlConnection.setDoInput(true);
					urlConnection.connect();
					String response = Utils.streamToString(urlConnection
							.getInputStream());
					System.out.println(response);
					JSONObject jsonObj = (JSONObject) new JSONTokener(response)
							.nextValue();

					// String name = jsonObj.getJSONObject("data").getString(

							// "full_name");
					// String bio =
					// jsonObj.getJSONObject("data").getString("bio");
					// Log.i(TAG, "Got name: " + name + ", bio [" + bio + "]");

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
								Log.d("OkHttp2",response.body().string());
								String TestVar = response.body().string();
								try {
									JSONArray jsonArray = new JSONArray(TestVar);
									for(int i=0;i<jsonArray.length();i++) {

										JSONObject jsonObject = jsonArray.getJSONObject(i);

										userInfo.put(likedOne, jsonObject.getString(likedOne));
										userInfo.put(likedTwo, jsonObject.getString(likedTwo));
										userInfo.put(likedThree, jsonObject.getString(likedThree));
										userInfo.put(likedFour, jsonObject.getString(likedFour));
										userInfo.put(likedFive, jsonObject.getString(likedFive));
									}
									//Log.d("Http", String.valueOf(userInfo));
								} catch (JSONException e) {
									e.printStackTrace();
								}

							}
							else{
								//observe error
							}
						}
					});
					OkHttpClient client2 = new OkHttpClient();
					Request request2 = new Request.Builder()
							.url("http://tagnetserver.club/getTogether.php")
							.build();
					Call call2 = client2.newCall(request2);
					call2.enqueue(new okhttp3.Callback() {
						@Override
						public void onFailure(Call call, IOException e) {
							//observe reason of failure using
							e.printStackTrace();
						}

						@Override
						public void onResponse(Call call2, Response response2) throws IOException {
							if(response2.isSuccessful()){
								Log.d("OkHttp2",response2.body().string());
								String TestVar2 = response2.body().string();
								try {
									JSONArray jsonArray2 = new JSONArray(TestVar2);
									for(int i=0;i<jsonArray2.length();i++) {

										JSONObject jsonObject2 = jsonArray2.getJSONObject(i);

										userInfo.put(TwoT, jsonObject2.getString(TwoT));

									}
									//Log.d("Http", String.valueOf(userInfo));
								} catch (JSONException e) {
									e.printStackTrace();
								}

							}
							else{
								//observe error
							}
						}
					});

					JSONObject data_obj = jsonObj.getJSONObject(TAG_DATA);
					userInfo.put(TAG_ID, data_obj.getString(TAG_ID));




					userInfo.put(TAG_PROFILE_PICTURE,
							data_obj.getString(TAG_PROFILE_PICTURE));

					userInfo.put(TAG_USERNAME, data_obj.getString(TAG_USERNAME));

					userInfo.put(TAG_BIO, data_obj.getString(TAG_BIO));

					userInfo.put(TAG_WEBSITE, data_obj.getString(TAG_WEBSITE));

					JSONObject counts_obj = data_obj.getJSONObject(TAG_COUNTS);

					userInfo.put(TAG_FOLLOWS, counts_obj.getString(TAG_FOLLOWS));

					userInfo.put(TAG_FOLLOWED_BY,
							counts_obj.getString(TAG_FOLLOWED_BY));

					userInfo.put(TAG_MEDIA, counts_obj.getString(TAG_MEDIA));

					userInfo.put(TAG_FULL_NAME,
							data_obj.getString(TAG_FULL_NAME));

					JSONObject meta_obj = jsonObj.getJSONObject(TAG_META);

					userInfo.put(TAG_CODE, meta_obj.getString(TAG_CODE));
					Log.d("Http", String.valueOf(userInfo));
				} catch (Exception ex) {
					what = WHAT_ERROR;
					ex.printStackTrace();
				}
				mProgress.dismiss();
				handler.sendMessage(handler.obtainMessage(what, 2, 0));
			}
		}.start();

	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == WHAT_ERROR) {
				mProgress.dismiss();
				if (msg.arg1 == 1) {
					mListener.onFail("Failed to get access token");
				} else if (msg.arg1 == 2) {
					mListener.onFail("Failed to get user information");
				}
			} else if (msg.what == WHAT_FETCH_INFO) {
				// fetchUserName();
				mProgress.dismiss();
				//mListener.OnLogin();
				mListener.onSuccess();

			}
		}
	};

	public HashMap<String, String> getUserInfo() {
		return userInfo;
	}

	public boolean hasAccessToken() {
		return (mAccessToken == null) ? false : true;
	}

	public void setListener(OAuthAuthenticationListener listener) {
		mListener = listener;
	}

	public String getUserName() {
		return mSession.getUsername();
	}

	public String getId() {
		return mSession.getId();
	}

	public String getName() {
		return mSession.getName();
	}
	public String getTOken() {
		return mSession.getAccessToken();
	}
	public void authorize() {
		// Intent webAuthIntent = new Intent(Intent.ACTION_VIEW);
		// webAuthIntent.setData(Uri.parse(AUTH_URL));
		// mCtx.startActivity(webAuthIntent);
		mDialog.show();
	}

	

	public void resetAccessToken() {
		if (mAccessToken != null) {
			mSession.resetAccessToken();
			mAccessToken = null;
		}
	}

	public interface OAuthAuthenticationListener {
		//public abstract void OnLogin();
		public abstract void onSuccess();


		public abstract void onFail(String error);
	}

	
}