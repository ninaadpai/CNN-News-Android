package cnnnews.inclassfive.inclassfive;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by ojasv on 2/13/17.
 */
public class GetNewsFeedAsyncTask extends AsyncTask<String, Void, ArrayList<News>> {
    private INews inews = null;

    public GetNewsFeedAsyncTask(INews inews) {
        this.inews = inews;
    }

    @Override
    protected ArrayList<News> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();
                return NewsUtil.NewsPullParser.parseNews(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        inews.showProgressDialog();

    }

    @Override
    protected void onPostExecute(ArrayList<News> newses) {
        super.onPostExecute(newses);
        inews.stopProgress();
        inews.getNewsList(newses);
    }

    static public interface INews {
        public void showProgressDialog();

        public void stopProgress();

        public void getNewsList(ArrayList<News> newses);

    }
}

