package cnnnews.inclassfive.inclassfive;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ojasv on 2/6/2017.
 */

public class GetImage extends AsyncTask<String,Void,Bitmap> {

    Imagedata activity;
    public GetImage(Imagedata activity) {
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Bitmap bitmap = BitmapFactory.decodeStream(con.getInputStream());
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        activity.setupImagedata(bitmap);
    }

    static public interface Imagedata{
        public void setupImagedata(Bitmap bitmap);
    }

}
