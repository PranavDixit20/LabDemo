package com.example.pranav.labdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pranav.labdemo.Adapter.RecyclerAdapter;
import com.example.pranav.labdemo.Adapter.RecyclerInfoAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User extends AppCompatActivity{

    Toolbar tb;
    RecyclerView rv,rv1;
    RecyclerView.LayoutManager lm,lm1;
    public String url = "http://192.168.0.4:8084/Lab_Project/JsonServlet";
    public String infoUrl = "http://192.168.0.4:8084/Lab_Project/InfoServlet";
    RecyclerAdapter adapter;
    RecyclerInfoAdapter adapter1;
    public static final String KEY_USERNAME="sname";
    String nm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Bundle b = this.getIntent().getExtras();
        nm = b.getString("name");

        /* TabHost Setup*/
        TabHost host = (TabHost)findViewById(R.id.tabhost);
        host.setup();

        /* Toolbar Setup*/
        tb=(Toolbar)findViewById(R.id.tbar);
        setSupportActionBar(tb);


        /* Recycler View Setup 1*/
        rv = (RecyclerView)findViewById(R.id.recycler_view);
        lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        getInformation();

        /* Recycler View Setup 2*/
        rv1 = (RecyclerView)findViewById(R.id.recycler_view_info);
        lm1 = new LinearLayoutManager(this);
        rv1.setLayoutManager(lm1);
        rv1.setHasFixedSize(true);
        getInfo();


        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Home");
        spec.setContent(R.id.Home);
        spec.setIndicator("Home");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Info");
        spec.setContent(R.id.Info);
        spec.setIndicator("Info");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Cart");
        spec.setContent(R.id.Cart);
        spec.setIndicator("Cart");
        host.addTab(spec);

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

    /* Recycler adapter View for information Setup 1*/
    private void getInfo() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,infoUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

               Log.d("response",response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                List<Info> list1 = Arrays.asList(gson.fromJson(response,Info[].class));
                adapter1 = new RecyclerInfoAdapter(list1);
                rv1.setAdapter(adapter1);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USERNAME,nm);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(User.this);
        requestQueue.add(stringRequest);
    }

    /* Recycler adapter View for book list Setup*/
    public void getInformation()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String ad;
                ad = response;
                Log.d("res",ad);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                List<Contact> list = Arrays.asList(gson.fromJson(ad,Contact[].class));
                adapter = new RecyclerAdapter(list);
                rv.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(this).addToRequestQue(stringRequest);

    }

}
