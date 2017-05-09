package com.example.pranav.labdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText e,e1,e2;
    Button b;
    String s,s1,s2;
    public static final String LOGIN_URL = "http://192.168.0.4:8084/Lab_Project/RegisterServlet";
    public static final String KEY_USERNAME="rname";
    public static final String KEY_NUMBER="rmob";
    public static final String KEY_PASSWORD="rpass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        e = (EditText)findViewById(R.id.uname);
        e1 = (EditText)findViewById(R.id.mnum);
        e2 = (EditText)findViewById(R.id.rpass);
        b = (Button) findViewById(R.id.regs);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        userRegister();
    }

    private void userRegister() {

        s = e.getText().toString().trim();
        s1 = e1.getText().toString().trim();
        s2 = e2.getText().toString().trim();

        StringRequest sr = new StringRequest(Request.Method.POST,LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reg",response);
                if (response.contains("Registration successfull")) {
                    AlertDialog.Builder ab = new AlertDialog.Builder(Register.this);
                    AlertDialog ad = ab.create();
                    ab.setTitle("Congratulations");
                    ab.setMessage("Registration successfull !!!");
                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(Register.this,MainActivity.class));
                        }
                    });
                    ab.show();

                } else {
                    AlertDialog.Builder ab = new AlertDialog.Builder(Register.this);
                    AlertDialog ad = ab.create();
                    ab.setTitle("Register Failed");
                    ab.setMessage("Error !!!");
                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(Register.this,Register.class));
                        }
                    });
                    ab.show();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register.this,error.toString(),Toast.LENGTH_LONG ).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USERNAME,s);
                map.put(KEY_NUMBER,s1);
                map.put(KEY_PASSWORD,s2);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(sr);
    }
}
