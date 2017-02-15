package cnnnews.inclassfive.inclassfive;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetNewsFeedAsyncTask.INews, GetImage.Imagedata{
    Button getnews, finishbtn;
    ProgressDialog progressDialog;
    ArrayList<News> newsList = new ArrayList<News>();
    TextView textViewTitle, textViewDate, textViewDescription;
    int element = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getnews = (Button) findViewById(R.id.getnews);
        finishbtn = (Button) findViewById(R.id.finish);
        ImageView first = (ImageView)findViewById(R.id.firstbtn);
        ImageView previous = (ImageView)findViewById(R.id.prevbtn);
        ImageView last = (ImageView)findViewById(R.id.lastbtn);
        ImageView next = (ImageView)findViewById(R.id.nextbtn);
        getnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectedOnline()) {
                    new GetNewsFeedAsyncTask(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_tech.rss");
                } else {
                    Toast.makeText(MainActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        first.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                displayList(0);
            }
        });

        previous.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                element--;
                if(element<0) {
                    element++;
                    displayList(element);
                }else{
                    displayList(element);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                element++;

                if(element>=newsList.size()) {
                    element--;
                    displayList(element);
                }else{
                    displayList(element);
                }
            }
        });

        last.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                displayList(newsList.size()-1);
            }
        });


        finishbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void showProgressDialog() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading News");
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

    }

    @Override
    public void stopProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void getNewsList(ArrayList<News> newses) {
        newsList.addAll(newses);
        displayList(element);
    }
    private void displayList(int pos) {
        Log.i("REACHED","Display");
        News n = newsList.get(pos);
        new GetImage(MainActivity.this).execute(n.getThumbnailImageUrl());

        textViewTitle = (TextView)findViewById(R.id.textViewTitle);
        textViewDate = (TextView)findViewById(R.id.textViewDate);
        textViewDescription = (TextView)findViewById(R.id.textView3);

        textViewTitle.setText("Title: "+ n.getTitle().toString());
            textViewDate.setText("Publication Date: "+n.getPubDate().toString());
            textViewDescription.setText(n.getNewsDescription().toString());
    }

    @Override
    public void setupImagedata(Bitmap bitmap) {
        ImageView news_image = (ImageView) findViewById(R.id.imageView2);
        news_image.setImageBitmap(bitmap);
    }
}
