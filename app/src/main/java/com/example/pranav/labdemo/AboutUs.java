package com.example.pranav.labdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class AboutUs extends AppCompatActivity {
    Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        tb=(Toolbar)findViewById(R.id.tbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle(R.string.title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                break;

        }

        return super.onOptionsItemSelected(menuItem);
    }


}
