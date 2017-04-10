package com.example.pranav.labdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et, et1;
    ProgressBar pb;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.email);
        et1 = (EditText) findViewById(R.id.pass);
        pb = (ProgressBar) findViewById(R.id.pb);
        b = (Button) findViewById(R.id.login);

        pb.setVisibility(View.GONE);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        pb.setVisibility(View.VISIBLE);

        String s1 = et.getText().toString();
        String s2 = et1.getText().toString();
        new ExecuteTask().execute(s1, s2);
    }


    private class ExecuteTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            return PostData(params);
        }

        @Override
        protected void onPostExecute(String result) {
            pb.setVisibility(View.GONE);
            //progess_msz.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this,result, Toast.LENGTH_LONG).show();

            if(result.contains("success")){
                startActivity(new Intent(MainActivity.this,User.class));
            }
            else {
                AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                AlertDialog ad = ab.create();
                ab.setTitle("Login Failed");
                ab.setMessage("Incorrect Username and Password !!!");
                ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                    }
                });
                ab.show();
            }
        }

        String PostData(String[] valuse) {
            String s = "";
            try {
                HttpParams httpRequestParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpRequestParams, 3000);
                HttpConnectionParams.setSoTimeout(httpRequestParams, 3000);

                HttpClient hp = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://192.168.0.102:8084/Lab_Project/DbConnection");
                Log.d("connection", "success");
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("name", valuse[0]));
                list.add(new BasicNameValuePair("pass", valuse[1]));
                httpPost.setEntity(new UrlEncodedFormEntity(list));
                HttpResponse httpResponse = hp.execute(httpPost);

                HttpEntity httpEntity = httpResponse.getEntity();
                s = readResponse(httpResponse);
                Log.d("conn", s);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return s;

        }

        private String readResponse(HttpResponse res) {
            InputStream is = null;
            String return_text = "";
            try {
                is = res.getEntity().getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String line = "";
                StringBuilder sb = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                return_text = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return return_text;
        }
    }
}


