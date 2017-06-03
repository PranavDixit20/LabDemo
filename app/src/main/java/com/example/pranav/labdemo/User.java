package com.example.pranav.labdemo;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pranav.labdemo.Adapter.RecyclerAdapter;
import com.example.pranav.labdemo.Adapter.RecyclerInfoAdapter;
import com.example.pranav.labdemo.JsonKeys.Contact;
import com.example.pranav.labdemo.JsonKeys.Decsript;
import com.example.pranav.labdemo.JsonKeys.Info;
import com.example.pranav.labdemo.JsonKeys.ProfileKey;
import com.example.pranav.labdemo.Singleton.MySingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    Toolbar tb;
    RecyclerView rv,rv1;
    RecyclerView.LayoutManager lm,lm1;
    public String url = "http://116.74.187.233:8084/Lab_Project/JsonServlet";
    public String infoUrl = "http://116.74.187.233:8084/Lab_Project/InfoServlet";
    public String prourl = "http://116.74.187.233:8084/Lab_Project/ProfileServlet";
    RecyclerAdapter adapter;
    RecyclerInfoAdapter adapter1;
    public static final String KEY_USERNAME="sname";
    public static final String KEY_Profile="usrnm";
    TextView tv1,tv2;
    List<ProfileKey> li = new ArrayList<>();
    String nm,sta,d;
    Button btn,btn1;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Bundle b = this.getIntent().getExtras();
        nm = b.getString("name");
        sta = b.getString("status");
        Log.d("login name to end",nm);

        tv1 = (TextView)findViewById(R.id.u_name);
        tv2 = (TextView)findViewById(R.id.u_mob);
        btn = (Button)findViewById(R.id.u_pass);
        btn1 =(Button)findViewById(R.id.delete);

        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);

        /* TabHost Setup*/
        TabHost host = (TabHost)findViewById(R.id.tabhost);
        host.setup();

        /* Toolbar Setup*/
        tb=(Toolbar)findViewById(R.id.tbar);
        searchView = (SearchView) tb.findViewById(R.id.srch);
        if (searchView != null) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(this);

        }
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
        spec.setIndicator("",getResources().getDrawable(R.drawable.ic_home));
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Info");
        spec.setContent(R.id.Info);
        spec.setIndicator("",getResources().getDrawable(R.drawable.ic_list));
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Profile");
        spec.setContent(R.id.Profile);
        spec.setIndicator("",getResources().getDrawable(R.drawable.ic_profile));
        host.addTab(spec);

        getProfile(nm);
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
                Intent in=new Intent(this,AboutUs.class);
                in.putExtra("name",nm);
                startActivities(new Intent[]{in});
                break;
            case R.id.help:
                Intent i=new Intent(this,Help.class);
                i.putExtra("name",nm);
                startActivities(new Intent[]{i});
                break;
            case R.id.exit:
                finish();
                System.exit(0);
                break;
            case R.id.action_drawer_cart:
                Intent inn=new Intent(this,Cart.class);
                inn.putExtra("name",nm);
                inn.putExtra("status",sta);
                startActivities(new Intent[]{inn});
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
                adapter = new RecyclerAdapter(nm,list,User.this);
                rv.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(this).addToRequestQue(stringRequest);

    }

    public void getProfile(String na)
    {
        final String nam = na;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,prourl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("res",response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                List<ProfileKey> list1 = Arrays.asList(gson.fromJson(response,ProfileKey[].class));
                li = list1;
                tv1.setText(list1.get(0).getUname());
                tv2.setText(list1.get(0).getContact());
                d = list1.get(0).getStatus();
                Log.d("delete acc",d);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_Profile,nam);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(User.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.u_pass:

                Intent in = new Intent(this,Change_Pass.class);
                Bundle bu = new Bundle();
                bu.putString("name",nm);
                in.putExtras(bu);
                startActivity(in);
                break;
            case R.id.delete:

                if ( d.equals("not return")){
                    Toast.makeText(this, "please first return book", Toast.LENGTH_LONG).show();
                }
                else{

                    Intent inte = new Intent(this,UserDelete.class);
                    Bundle bun = new Bundle();
                    bun.putString("name",nm);
                    inte.putExtras(bun);
                    startActivity(inte);
                }
                break;
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("search query ", query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        Log.d("search text ", newText);
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }
}
