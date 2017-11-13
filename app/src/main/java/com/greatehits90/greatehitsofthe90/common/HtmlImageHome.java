package com.greatehits90.greatehitsofthe90.common;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.greatehits90.greatehitsofthe90.object.Channel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Objects;

public class HtmlImageHome extends AsyncTask<String, Void, ArrayList<Channel>> {

    private ArrayList<Channel> arrWall;
    private ShareArrWallpaper mShareArrSams;
    private Context mContext;

    public HtmlImageHome(ShareArrWallpaper mShareArrSams, Context mContext) {
        this.mShareArrSams = mShareArrSams;
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        arrWall = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected ArrayList<Channel> doInBackground(String... params) {
        String link = params[0];
        try {
            Document doc = Jsoup.connect(link).get();
            Element element = doc.select("#container-0").first();
            Elements elements = element.select(":not(:first-child)");
            for (int i = 0; i < elements.size(); i++) {
                String title = elements.get(i).select("div > a > div > div").text();
                String artist = elements.get(i).select("div > a > div > p").text();
                String image = elements.get(i).select("div > div > div > a > img").attr("src");
                String url = elements.get(i).select("div > a").attr("href");
                if (!Objects.equals(title, "")) {
                    arrWall.add(new Channel(title, artist, image, url));
                }
            }
            return arrWall;
        } catch (Exception e) {
            Log.d("123", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Channel> itemSamses) {
        super.onPostExecute(itemSamses);
        if (itemSamses != null) {
            mShareArrSams.WallArrr(itemSamses);
        }
    }

    public interface ShareArrWallpaper {
        void WallArrr(ArrayList<Channel> arrImg);
    }

}
