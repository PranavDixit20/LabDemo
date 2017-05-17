package com.example.pranav.labdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pranav.labdemo.JsonKeys.Decsript;
import com.example.pranav.labdemo.SqLite.DataBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Desp extends AppCompatActivity implements View.OnClickListener {

    Toolbar tb;
    TextView tv,tv1;
    String nam,un,stat;
    public String url = "http://192.168.0.5:8084/Lab_Project/JsonServlet";
    public static final String KEY_DESP="descript";
    List<Decsript> li = new ArrayList<>();
    int p;
    DataBase db;
    Button b,b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desp);

         /* Toolbar Setup*/
        tb=(Toolbar)findViewById(R.id.tbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(this);

       /* MenuItem item = (MenuItem) findViewById(R.id.action_drawer_cart);
        item.setVisible(false);
        this.invalidateOptionsMenu();*/


        tv = (TextView)findViewById(R.id.dsp);
        tv1 = (TextView)findViewById(R.id.note);

        tv1.setVisibility(View.GONE);

        b = (Button)findViewById(R.id.buy);
        b.setVisibility(View.GONE);
        b.setOnClickListener(this);

        b1 = (Button)findViewById(R.id.cart);
        b1.setVisibility(View.GONE);
        b1.setOnClickListener(this);

        Bundle b = this.getIntent().getExtras();
        nam = b.getString("nam");
        un = b.getString("name");
        p = b.getInt("pos");
        stat = b.getString("status");
        getDescr();
    }

    private void getDescr() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("res",response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                List<Decsript> list1 = Arrays.asList(gson.fromJson(response,Decsript[].class));
                li = list1;
                tv.setText(li.get(p).getDescript());
                if (stat.equals("avaliable")){
                    b.setVisibility(View.VISIBLE);
                    b1.setVisibility(View.VISIBLE);
                    tv1.setVisibility(View.GONE);
                }
                else {
                    b.setVisibility(View.GONE);
                    b1.setVisibility(View.VISIBLE);
                    tv1.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_DESP,nam);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Desp.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.aboutus:
                startActivity(new Intent(this,AboutUs.class));
                break;
            case R.id.help:
                startActivity(new Intent(this,Help.class));
                break;
            case R.id.exit:
                finish();
                System.exit(0);
                break;

        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.buy:
                break;
            case R.id.cart:
                db = new DataBase(this);
                int i = db.save(nam,un);
                if (i == 1) {
                    Toast.makeText(this, "Your have already save this book", Toast.LENGTH_LONG).show();
                    Log.d("ok", nam);

                } else {
                    v.setSelected(true);
                    Toast.makeText(this, "Your book is in cart", Toast.LENGTH_LONG).show();
                    Log.d("ok", nam);

                }

                break;
            default:
                Intent in = new Intent(Desp.this,User.class);
                Bundle bu = new Bundle();
                bu.putString("name",un);
                bu.putString("status",stat);
                in.putExtras(bu);
                startActivity(in);
                break;
        }

    }
}
