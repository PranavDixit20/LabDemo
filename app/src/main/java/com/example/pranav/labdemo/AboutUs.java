package com.example.pranav.labdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AboutUs extends AppCompatActivity implements View.OnClickListener {
    Toolbar tb;
    String nm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        tb=(Toolbar)findViewById(R.id.tbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(this);

        Bundle b = this.getIntent().getExtras();
        nm = b.getString("name");

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
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                break;

        }

        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public void onClick(View v) {
        Intent in=new Intent(this,User.class);
        in.putExtra("name",nm);
        startActivities(new Intent[]{in});
    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(this,User.class);
        in.putExtra("name",nm);
        startActivities(new Intent[]{in});
    }
}
