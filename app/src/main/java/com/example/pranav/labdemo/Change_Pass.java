package com.example.pranav.labdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Change_Pass extends AppCompatActivity implements View.OnClickListener {

    Toolbar tb;
    EditText e,e1,e2;
    String nam,s1,s2,s3;
    Intent in;
    Bundle bu;
    public String url = "http://116.75.138.232:8084/Lab_Project/ChangePasswordServlet";
    public static final String KEY_name="uname";
    public static final String KEY_old_pass="oldpass";
    public static final String KEY_new_pass="newpass";
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__pass);

        tb=(Toolbar)findViewById(R.id.tbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(this);

        e = (EditText)findViewById(R.id.u_id);
        e1 = (EditText)findViewById(R.id.old_pass);
        e2 = (EditText)findViewById(R.id.new_pass);
        btn = (Button)findViewById(R.id.ok);
        btn.setOnClickListener(this);

        bu = this.getIntent().getExtras();
        nam = bu.getString("name");
    }

    private void passChange(final String s1,final String s2, final String s3) {

        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res",response);
                if (response.contains("Password changed successfully!!")) {
                    Toast.makeText(Change_Pass.this,"Password changed successfully!!",Toast.LENGTH_LONG ).show();
                    startActivity(new Intent(Change_Pass.this,MainActivity.class));
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Change_Pass.this,error.toString(),Toast.LENGTH_LONG ).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_name,s1);
                map.put(KEY_old_pass,s2);
                map.put(KEY_new_pass,s3);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Change_Pass.this);
        requestQueue.add(sr);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ok :

                s1 = e.getText().toString().trim();
                s2 = e1.getText().toString().trim();
                s3 = e2.getText().toString().trim();


                boolean p = validate(s1,s2,s3);
                if (p)
                {
                    passChange(s1,s2,s3);
                }
                else
                {
                    Toast.makeText(Change_Pass.this,"something is wrong",Toast.LENGTH_LONG ).show();
                }


                break;
            default:
                Intent in = new Intent(Change_Pass.this,User.class);
                Bundle bu = new Bundle();
                bu.putString("name",nam);
                in.putExtras(bu);
                startActivity(in);
                break;

        }



    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(Change_Pass.this,User.class);
        Bundle bu = new Bundle();
        bu.putString("name",nam);
        in.putExtras(bu);
        startActivity(in);
    }

    private boolean validate(String p1, String p2, String p3) {

        if (p1.length() == 0){
            e.setError("enter your id");
            return false;
        }

        if (p2.length() == 0){
            e1.setError("enter your old pass");
            return false;
        }

        if (p3.length() == 0){
            e2.setError("enter your new pass");
            return false;
        }

        return true;
    }
}
