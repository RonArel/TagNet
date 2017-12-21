package com.example.ortel.tagnet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import lazyload.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.ortel.tagnet.MainActivity.GlobalConstants.globalInt;

public class RelationShipAdapterRemoved extends BaseAdapter implements MainActivity.GlobalConstants{
    private InstagramApp mApp;
    private ListView lvRelationShipAllUser;
    private ArrayList<HashMap<String, String>> usersInfo;
    private LayoutInflater inflater;
    private int y = 0;
    //private Context context;
    //lvRelationShipAllUser = (ListView) findViewById(R.id.lvRelationShip);
    private ImageLoader imageLoader;
    Context Mcontext;


    public RelationShipAdapterRemoved(Context context,
                                      ArrayList<HashMap<String, String>> usersInfo) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("OkHttpTestPart2","debug4");
        this.usersInfo = usersInfo;
        this.imageLoader = new ImageLoader(context);
        this.Mcontext = context;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("usersInfo", String.valueOf(usersInfo.size()));
        if (y == usersInfo.size()-1) {
            y = 0;
            View view = inflater.inflate(R.layout.relationship_inflater_two, null);
            Holder holder = new Holder();
            //connectOrDisconnectUser();
            holder.ivPhoto = (ImageView) view.findViewById(R.id.ivImage);
            holder.tvFullName = (TextView) view.findViewById(R.id.tvFullName);
            holder.tvFullName.setText(usersInfo.get(position).get(
                    RelationshipRemoved.likedOne));
        /*imageLoaddr.DisplayImage(
                usersInfo.get(position).get(RelationshipRemoved.likedOne),
                holder.ivPhoto);*/
            //Log.d("OkHttpLikedOne",usersInfo.get(position).get(
            //        RelationshipRemoved.likedOne));
            holder.ivPhoto = (ImageView) view.findViewById(R.id.ivImage);
            holder.tvFullNameTwo = (TextView) view.findViewById(R.id.tvFullNameTwo);
            //holder.tvFullNameThree = (TextView) view.findViewById(R.id.tvFullNameTwo);
            holder.tvFullNameTwo.setText(usersInfo.get(position).get(
                    RelationshipRemoved.likedTwo));


            holder.ivPhoto = (ImageView) view.findViewById(R.id.ivImage);
            holder.tvFullNameThree = (TextView) view.findViewById(R.id.tvFullNameThree);
            holder.tvFullNameThree.setText(usersInfo.get(position).get(
                    RelationshipRemoved.likedThree));

            holder.ivPhoto = (ImageView) view.findViewById(R.id.ivImage);
            holder.tvFullNameFour = (TextView) view.findViewById(R.id.tvFullNameFour);
            holder.tvFullNameFour.setText(usersInfo.get(position).get(
                    RelationshipRemoved.likedFour));

            holder.ivPhoto = (ImageView) view.findViewById(R.id.ivImage);
            holder.tvFullNameFive = (TextView) view.findViewById(R.id.tvFullNameFive);
            holder.tvFullNameFive.setText(usersInfo.get(position).get(
                    RelationshipRemoved.likedFive));


            //connectOrDisconnectUser();
            holder.tvFullName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("OkHttp",usersInfo.get(position).get(
                            RelationshipRemoved.likedOne));

                    //context = RelationshipRemoved.this;
                    //final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            Mcontext);
                    builder.setMessage("Do you want to un tag " + usersInfo.get(position).get(
                            RelationshipRemoved.likedOne))
                            .setCancelable(false)
                            .setPositiveButton("Yes",

                                    new DialogInterface.OnClickListener() {
                                        //do something

                                        public void onClick(DialogInterface dialog, int randomvar) {

                                            Log.d("OkHttpRemove", "getting removing!");
                                            String username = (globalInt);
                                            String UT = usersInfo.get(position).get(
                                                    RelationshipRemoved.likedOne);
                                            String type = "remove";
                                            BackgroundWorker backgroundWorker = new BackgroundWorker(Mcontext.getApplicationContext());
                                            backgroundWorker.execute(type, username, UT);
                                            Mcontext.startActivity(new Intent(Mcontext, MainActivity.class));
                                            Toast.makeText(Mcontext, "UnTagged!",
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
            });
            holder.tvFullNameTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("OkHttp",usersInfo.get(position).get(
                            RelationshipRemoved.likedTwo));
                    //context = RelationshipRemoved.this;
                    //final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            Mcontext);
                    builder.setMessage("Do you want to un tag " + usersInfo.get(position).get(
                            RelationshipRemoved.likedTwo))
                            .setCancelable(false)
                            .setPositiveButton("Yes",

                                    new DialogInterface.OnClickListener() {
                                        //do something

                                        public void onClick(DialogInterface dialog, int randomvar) {

                                            Log.d("OkHttpRemove", "getting removing!");
                                            String username = (globalInt);
                                            String UT = usersInfo.get(position).get(
                                                    RelationshipRemoved.likedTwo);
                                            String type = "remove";
                                            BackgroundWorker backgroundWorker = new BackgroundWorker(Mcontext.getApplicationContext());
                                            backgroundWorker.execute(type, username, UT);
                                            Mcontext.startActivity(new Intent(Mcontext, MainActivity.class));
                                            Toast.makeText(Mcontext, "UnTagged!",
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
            });

            holder.tvFullNameThree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("OkHttp",usersInfo.get(position).get(
                            RelationshipRemoved.likedThree));
                    //context = RelationshipRemoved.this;
                    //final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            Mcontext);
                    builder.setMessage("Do you want to un tag " + usersInfo.get(position).get(
                            RelationshipRemoved.likedThree))
                            .setCancelable(false)
                            .setPositiveButton("Yes",

                                    new DialogInterface.OnClickListener() {
                                        //do something

                                        public void onClick(DialogInterface dialog, int randomvar) {

                                            Log.d("OkHttpRemove", "getting removing!");
                                            String username = (globalInt);
                                            String UT = usersInfo.get(position).get(
                                                    RelationshipRemoved.likedThree);
                                            String type = "remove";
                                            BackgroundWorker backgroundWorker = new BackgroundWorker(Mcontext.getApplicationContext());
                                            backgroundWorker.execute(type, username, UT);
                                            Mcontext.startActivity(new Intent(Mcontext, MainActivity.class));
                                            Toast.makeText(Mcontext, "UnTagged!",
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
            });
            holder.tvFullNameFour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("OkHttp",usersInfo.get(position).get(
                            RelationshipRemoved.likedFour));
                    //context = RelationshipRemoved.this;
                    //final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            Mcontext);
                    builder.setMessage("Do you want to un tag " + usersInfo.get(position).get(
                            RelationshipRemoved.likedFour))
                            .setCancelable(false)
                            .setPositiveButton("Yes",

                                    new DialogInterface.OnClickListener() {
                                        //do something

                                        public void onClick(DialogInterface dialog, int randomvar) {

                                            Log.d("OkHttpRemove", "getting removing!");
                                            String username = (globalInt);
                                            String UT = usersInfo.get(position).get(
                                                    RelationshipRemoved.likedFour);
                                            String type = "remove";
                                            BackgroundWorker backgroundWorker = new BackgroundWorker(Mcontext.getApplicationContext());
                                            backgroundWorker.execute(type, username, UT);
                                            Mcontext.startActivity(new Intent(Mcontext, MainActivity.class));
                                            Toast.makeText(Mcontext, "UnTagged!",
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
            });
            holder.tvFullNameFive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("OkHttp",usersInfo.get(position).get(
                            RelationshipRemoved.likedFive));
                    //context = RelationshipRemoved.this;
                    //final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            Mcontext);
                    builder.setMessage("Do you want to un tag " + usersInfo.get(position).get(
                            RelationshipRemoved.likedFive))
                            .setCancelable(false)
                            .setPositiveButton("Yes",

                                    new DialogInterface.OnClickListener() {
                                        //do something

                                        public void onClick(DialogInterface dialog, int randomvar) {

                                            Log.d("OkHttpRemove", "getting removing!");
                                            String username = (globalInt);
                                            String UT = usersInfo.get(position).get(
                                                    RelationshipRemoved.likedFive);
                                            String type = "remove";
                                            BackgroundWorker backgroundWorker = new BackgroundWorker(Mcontext.getApplicationContext());
                                            backgroundWorker.execute(type, username, UT);
                                            Mcontext.startActivity(new Intent(Mcontext, MainActivity.class));
                                            Toast.makeText(Mcontext, "UnTagged!",
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
            });

        /*imageLoader.DisplayImage(
        /*imageLoader.DisplayImage(
                usersInfo.get(position).get(RelationshipRemoved.likedOne),
                holder.ivPhoto);*/
            //Log.d("OkHttpLikedOne",usersInfo.get(position).get(
            //        RelationshipRemoved.likedTwo));
            //
            return view;
        } else {
            View view1 = inflater.inflate(R.layout.nothing, null);
            //Holder holder = new Holder();
            //connectOrDisconnectUser();
            //holder.ivPhoto = (ImageView) view1.findViewById(R.id.ivImage);
            //holder.tvFullName = (TextView) view1.findViewById(R.id.tvFullName);

            y = y + 1;
            Log.d("OkHttpTestPart2", String.valueOf(view1));
            return view1;
        }

    }
    private void connectOrDisconnectUser(final int position) {

    }

    private class Holder {
        private ImageView ivPhoto;
        private TextView tvFullName;
        private TextView tvFullNameTwo;
        private TextView tvFullNameThree;
        private TextView tvFullNameFour;
        private TextView tvFullNameFive;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return usersInfo.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

}
