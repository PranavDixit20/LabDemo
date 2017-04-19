package com.example.pranav.labdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class User extends AppCompatActivity{

    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        TabHost host = (TabHost)findViewById(R.id.tabhost);
        host.setup();

        tb=(Toolbar)findViewById(R.id.tbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle(R.string.title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

}
