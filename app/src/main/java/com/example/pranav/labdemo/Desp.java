package com.example.pranav.labdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
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
import com.android.volley.DefaultRetryPolicy;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Desp extends AppCompatActivity implements View.OnClickListener {

    Toolbar tb;
    TextView tv,tv1,tv2;
    String nam,un,stat,status;
    public String url = "http://116.75.138.232:8084/Lab_Project/JsonServlet";
    public String BuyUrl = "http://116.75.138.232:8084/Lab_Project/BorrowServlet";
    public static final String Key_Due = "ddate";
    public static final String Key_Renew = "rdate";
    public static final String Key_Assign = "adate";
    public static final String Key_Uname = "un";
    public static final String Key_Bname = "bn";
    public static final String KEY_DESP="descript";
    List<Decsript> li = new ArrayList<>();
    int p;
    DataBase db;
    Button b,b1;
    Calendar c = Calendar.getInstance();

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
        tv2 = (TextView)findViewById(R.id.ti);

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
                tv.setTextColor(Color.parseColor("#827690"));
                tv2.setText(nam);
                tv2.setPaintFlags(tv2.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

                status = li.get(p).getStat();

                if (status.equals("avaliable")){
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
            case R.id.action_drawer_cart:
                Intent inn=new Intent(this,Cart.class);
                inn.putExtra("name",nam);
                inn.putExtra("status",stat);
                startActivities(new Intent[]{inn});

                break;

        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.buy:

                //Current Date
                SimpleDateFormat S = new SimpleDateFormat("dd-MM-yyyy");
                String date = S.format(c.getTime());
                Log.d("current date", date);

                //Due Date
                c.add(Calendar.MONTH,1);
                String date1 = S.format(c.getTime());
                Log.d("Due Date",date1);

                //Renewal Date
                c.add(Calendar.MONTH,-1);
                c.add(Calendar.DAY_OF_MONTH,25);
                String date3 = S.format(c.getTime());
                Log.d("Renuwal date",date3);

                Log.d("user_name",un);
                Log.d("Book_name",nam);
                Log.d("Book_Status",stat);

               pro(date,date1,date3);

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

    public void pro(final String cdate, final String ddate, final String rdate){

        StringRequest sr = new StringRequest(Request.Method.POST,BuyUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res",response);
                Toast.makeText(Desp.this,response,Toast.LENGTH_LONG ).show();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Desp.this,error.toString(),Toast.LENGTH_LONG ).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Key_Assign,cdate);
                map.put(Key_Due,ddate);
                map.put(Key_Renew,rdate);
                map.put(Key_Bname,nam);
                map.put(Key_Uname,un);
                return map;
            }
        };

        sr.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(Desp.this);
        requestQueue.add(sr);

    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(Desp.this,User.class);
        Bundle bu = new Bundle();
        bu.putString("name",un);
        bu.putString("status",stat);
        in.putExtras(bu);
        startActivity(in);
    }
}
