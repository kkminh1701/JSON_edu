package com.example.ka.json_edu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView text_view_MonHoc,text_view_NoiHoc, text_view_Fanpage;
    ImageView imageView;
    Bitmap bitmap = null;
    private String linkUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        new ReadJson().execute("https://khoapham.vn/KhoaPhamTraining/json/tien/demo1.json");
       // new LoadImage().execute("http://khoapham.vn/public/images/logo-370.png");

    }

    private void addControls() {
        text_view_MonHoc = findViewById(R.id.text_view_MonHoc);
        text_view_NoiHoc = findViewById(R.id.text_view_NoiHoc);
        text_view_Fanpage = findViewById(R.id.text_view_Fanpage);

    }

    class LoadImage extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openConnection().getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }


    class ReadJson extends AsyncTask<String, Void, String>{
        StringBuilder builder = new StringBuilder();

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    builder.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                if(jsonObject.has("monhoc"))
                {
                    text_view_MonHoc.setText(jsonObject.getString("monhoc"));
                }
                if(jsonObject.has("noihoc"))
                {
                    text_view_NoiHoc.setText(jsonObject.getString("noihoc"));
                }
                if(jsonObject.has("fanpage"))
                {
                    text_view_Fanpage.setText(jsonObject.getString("fanpage"));
                }
                if(jsonObject.has("logo"))
                {
                    linkUrl = jsonObject.getString("logo");
                    Toast.makeText(MainActivity.this, linkUrl, Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
