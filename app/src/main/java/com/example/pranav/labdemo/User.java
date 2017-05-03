package com.example.pranav.labdemo;

import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pranav.labdemo.Adapter.RecyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

public class User extends AppCompatActivity{

    Toolbar tb;
    RecyclerView rv;
    RecyclerView.LayoutManager lm;
    public String url = "http://192.168.0.4:8084/Lab_Project/JsonServlet";
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        /* TabHost Setup*/
        TabHost host = (TabHost)findViewById(R.id.tabhost);
        host.setup();

        /* Toolbar Setup*/
        tb=(Toolbar)findViewById(R.id.tbar);
        setSupportActionBar(tb);


        /* Recycler View Setup*/
        rv = (RecyclerView)findViewById(R.id.recycler_view);
        lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        getInformation();


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

    /* Recycler adapter View Setup*/
    public void getInformation()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
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
