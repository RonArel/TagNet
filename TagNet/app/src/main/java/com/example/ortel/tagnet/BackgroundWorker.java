package com.example.ortel.tagnet;


import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by ProgrammingKnowledge on 1/5/2016.
 */

public class BackgroundWorker extends AsyncTask<String, Void, String> {
        Context context;

        AlertDialog alertDialog;
        BackgroundWorker(Context ctx){
        context=ctx;
        }
@Override
protected String doInBackground(String...params){
        Thread t;

        String type=params[0];
        String login_url="http://tagnetserver.club/login.php";
        String register_url="http://tagnetserver.club/register.php";
        String add_url="http://tagnetserver.club/addTagged.php";
        String remove_url="http://tagnetserver.club/removeTagged.php";
        String remove_urlT="http://tagnetserver.club/removeTogether.php";
        if(type.equals("login")){
        try{
        String user_name=params[1];
        String password=params[2];
        URL url=new URL(login_url);
        HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream outputStream=httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
        String post_data=URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
        bufferedWriter.write(post_data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        InputStream inputStream=httpURLConnection.getInputStream();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
        String result="";
        String line="";
        while((line=bufferedReader.readLine())!=null){
        result+=line;
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return result;
        }catch(MalformedURLException e){
        e.printStackTrace();
        }catch(IOException e){
        e.printStackTrace();
        }
        }else if(type.equals("register")) {
            //btnConnect.setText("Disconnect");
            Log.d("TheRealapp", "I am here");

            try {
                Thread.sleep(100);

                /*try {
                    Thread.sleep(2000);
                } catch (I=]nterruptedException e) {
                    e.printStakTrace();*/

                String username = params[1];
                Log.d("TheRealapp", "I am hereOne");
                Log.d("TheRealappUsername", username);
                String password = params[2];
                Log.d("TheRealapp", "I am hereOne");
                Log.d("TheRealappPassword", password);
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                //Log.d("UnderstandingConnection", username);
                //Log.d("UnderstandingConnection", password);
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //}
        }else if(type.equals("add")){


        try{


                String username = params[1];
                String UT = params[2];
                URL url = new URL(add_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("TUsername", "UTF-8") + "=" + URLEncoder.encode(UT, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

            return result;

        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }else if(type.equals("remove")){


            try{


                    Log.d("OkHttpRemove","removing!");
                    String username = params[1];
                    String UT = params[2];
                    URL url = new URL(remove_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    Log.d("OkHttpRemovePart","removing!");
                    String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                            + URLEncoder.encode("TUsername", "UTF-8") + "=" + URLEncoder.encode(UT, "UTF-8");
                    bufferedWriter.write(post_data);
                    Log.d("OkHttpRemovePart.5","removing!");
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    Log.d("OkHttpRemovePart.55","removing!");
                    InputStream inputStream = httpURLConnection.getInputStream();
                    Log.d("OkHttpRemovePart.555","removing!");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    Log.d("OkHttpRemovePart.6","removing!");

                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    Log.d("OkHttpRemovePart1","removing!");
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    Log.d("OkHttpRemovePart2","removing!");
                    return result;

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }else if (type.equals("removeT")){
            try{


                Log.d("OkHttpRemove","removing!");
                String username = params[1];
                String UT = params[2];
                URL url = new URL(remove_urlT);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                Log.d("OkHttpRemovePart","removing!");
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("TUsername", "UTF-8") + "=" + URLEncoder.encode(UT, "UTF-8");
                bufferedWriter.write(post_data);
                Log.d("OkHttpRemovePart.5","removing!");
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                Log.d("OkHttpRemovePart.55","removing!");
                InputStream inputStream = httpURLConnection.getInputStream();
                Log.d("OkHttpRemovePart.555","removing!");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                Log.d("OkHttpRemovePart.6","removing!");

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.d("OkHttpRemovePart1","removing!");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("OkHttpRemovePart2","removing!");
                return result;

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        return null;
        }

@Override
protected void onPreExecute(){
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
        }

        }