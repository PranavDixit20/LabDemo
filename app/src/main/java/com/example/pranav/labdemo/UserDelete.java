package com.example.pranav.labdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UserDelete extends AppCompatActivity implements View.OnClickListener {

    EditText et;
    Button b;
    Intent in;
    Bundle bu;
    String s,nm;
    Toolbar tb;
    public String url = "http://116.75.138.232:8084/Lab_Project/DeleteAccServlet";
    public static final String KEY_name="dname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delete);

        tb=(Toolbar)findViewById(R.id.tbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(this);

        et = (EditText)findViewById(R.id.d_id);
        b = (Button)findViewById(R.id.del);
        b.setOnClickListener(this);

        bu = this.getIntent().getExtras();
        nm = bu.getString("name");

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.del:
                s = et.getText().toString().trim();
                boolean p = validate(s);
                if (p)
                {
                    accDelete(s);
                }
                else
                {
                    Toast.makeText(this,"something is wrong",Toast.LENGTH_LONG ).show();
                }
                break;
            default:
                Intent in = new Intent(this,User.class);
                Bundle bu = new Bundle();
                bu.putString("name",nm);
                in.putExtras(bu);
                startActivity(in);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this,User.class);
        Bundle bu = new Bundle();
        bu.putString("name",nm);
        in.putExtras(bu);
        startActivity(in);
    }

    private boolean validate(String p1) {

        if (p1.length() == 0){
            et.setError("enter your id");
            return false;
        }
        return true;
    }

    private void accDelete(final String s) {

        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res",response);
                if (response.contains("account deleted")) {
                    Toast.makeText(UserDelete.this,"Account Deleted successfully!!",Toast.LENGTH_LONG ).show();
                    startActivity(new Intent(UserDelete.this,MainActivity.class));
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserDelete.this,error.toString(),Toast.LENGTH_LONG ).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_name,s);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(UserDelete.this);
        requestQueue.add(sr);
    }
}
