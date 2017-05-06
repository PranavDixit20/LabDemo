package com.example.pranav.labdemo;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et, et1;
    ProgressBar pb;
    Button b;

    public static final String LOGIN_URL = "http://192.168.0.4:8084/Lab_Project/DbConnection";
    public static final String KEY_USERNAME="name";
    public static final String KEY_PASSWORD="pass";
    private String Username;
    private String Password;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        pb.setVisibility(View.VISIBLE);

       // String s1 = et.getText().toString();
       // String s2 = et1.getText().toString();
       // new ExecuteTask().execute(s1, s2);
        userLogin();

    }


    /* login function using volley demo use*/
    private void userLogin() {

        Username = et.getText().toString().trim();
        Password = et1.getText().toString().trim();
        pb.setVisibility(View.GONE);
        StringRequest sr = new StringRequest(Request.Method.POST,LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res",response);
                if (response.contains("success")) {
                    startActivity(new Intent(MainActivity.this,User.class));
                } else {
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
        },new Response.ErrorListener(){
            @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                }

            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                    map.put(KEY_USERNAME,Username);
                    map.put(KEY_PASSWORD,Password);
                    return map;
                }
        };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(sr);
    }





            /* login function using http/json demo but not use */
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
                HttpPost httpPost = new HttpPost("http://192.168.0.4:8084/Lab_Project/DbConnection");
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


