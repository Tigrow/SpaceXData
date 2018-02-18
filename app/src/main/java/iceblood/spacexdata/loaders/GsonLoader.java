package iceblood.spacexdata.loaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import iceblood.spacexdata.RocketData;

/**
 * Created by Titan'ik on 13.02.2018.
 */

public class GsonLoader extends AsyncTaskLoader<List<RocketData>> {
    public static final String LOG_TAG = "my_tag";
    public static final String ARG_WORD = "year";
    private static final String URL = "https://api.spacexdata.com/v2/launches?launch_year=";
    private List<RocketData> rocketDataList;
    private int arg;

    public GsonLoader(Context context, Bundle args) {
        super(context);
        rocketDataList = new ArrayList<>();
        if (args != null)
            arg = args.getInt(ARG_WORD);
    }

    @Override
    public List<RocketData> loadInBackground() {
        boolean error = false;
        String content = new String();
        try {
            content = getContent(URL+Integer.toString(arg));
        } catch (IOException ex) {
            error = true;
        }
        if(!error){
            rocketDataList = getRocketDataList(content);
            return rocketDataList;
        }else {
            return null;
        }
    }
    protected String getContent(String path) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(path);
            HttpsURLConnection c = (HttpsURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(10000);
            c.connect();
            reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buf.append(line + "\n");
            }
            return (buf.toString());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
    protected List<RocketData> getRocketDataList(String json){
        List<RocketData> dataList = new ArrayList<>();
        try {
        JSONArray jsonArray = new JSONArray(json);
        for(int i = 0;i<jsonArray.length();i++)
        {
            dataList.add(new RocketData(
            jsonArray.getJSONObject(i).getJSONObject("rocket").getString("rocket_name"),
                    jsonArray.getJSONObject(i).getLong("launch_date_unix"),
                    jsonArray.getJSONObject(i).getJSONObject("links").getString("mission_patch"),
                    jsonArray.getJSONObject(i).getString("details"),
                    jsonArray.getJSONObject(i).getJSONObject("links").getString("video_link")/*,
                    getImageByUrl(jsonArray.getJSONObject(i).getJSONObject("links").getString("mission_patch"))*/));
        }} catch (JSONException e) {
            //e.printStackTrace();
        }
        return dataList;
    }
    protected Bitmap getImageByUrl(String url){
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }
}
