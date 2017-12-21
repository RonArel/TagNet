package com.example.ortel.tagnet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import lazyload.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class RelationShipAdapterTogether extends BaseAdapter {
    private InstagramApp mApp;
    public static final String OneT = "OneT";
    public static final String TwoT = "TwoT";
    private ListView lvRelationShipAllUser;
    private ArrayList<HashMap<String, String>> usersInfo;
    private LayoutInflater inflater;
    //lvRelationShipAllUser = (ListView) findViewById(R.id.lvRelationShip);
    private ImageLoader imageLoader;
    Context context;
    Context Mcontext;

    public RelationShipAdapterTogether(Context context,
                                       ArrayList<HashMap<String, String>> usersInfo) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("OkHttp","debug4");
        this.usersInfo = usersInfo;
        this.imageLoader = new ImageLoader(context);
        this.Mcontext = context;

    }
    private void DasGood2(){


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.relationship_inflater, null);
        Holder holder = new Holder();
        //connectOrDisconnectUser();
        holder.ivPhoto = (ImageView) view.findViewById(R.id.ivImage);
        holder.tvFullName = (TextView) view.findViewById(R.id.tvFullName);
        holder.tvFullName.setText(usersInfo.get(position).get(
                RelationshipTogether.TwoT));
        Log.d("tvFullNameTestIng",usersInfo.get(position).get(
                RelationshipTogether.TwoT));
        /*imageLoader.DisplayImage(
                usersInfo.get(position).get(RelationshipTogether.TAG_PROFILE_PICTURE),
                holder.ivPhoto);*/
        //imageLoader.DisplayImage(
        //       usersInfo.get(position).get(Relationship.TAG_PROFILE_PICTURE),
        //        holder.ivPhoto);
        return view;
    }

    private class Holder {
        private ImageView ivPhoto;
        private TextView tvFullName;
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
