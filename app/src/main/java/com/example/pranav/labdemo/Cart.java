package com.example.pranav.labdemo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pranav.labdemo.SqLite.DataBase;

public class Cart extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, PopupMenu.OnMenuItemClickListener {

    Toolbar tb;
    String nm,cat,mat,stat;
    DataBase db;
    String pr2[] =new String[10];
    String pr[] =new String[10];
    ListView lv;
    TextView tv;
    Desp ds;
    Intent in;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tb=(Toolbar)findViewById(R.id.tbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(Cart.this);

        lv=(ListView)findViewById(R.id.lv);
        tv = (TextView)findViewById(R.id.note1);
        tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tv.setSingleLine(true);
        tv.setMarqueeRepeatLimit(5);
        tv.setSelected(true);

       /* MenuItem item = (MenuItem) findViewById(R.id.action_drawer_cart);
        item.setVisible(false);
        this.invalidateOptionsMenu();*/

        b = this.getIntent().getExtras();
        nm = b.getString("name");
        stat=b.getString("status");

        db=new DataBase(this);
        pr2=db.cheak(nm);

        ArrayAdapter<String> adpt = new ArrayAdapter<String>(this,R.layout.list_item,R.id.product_name,pr2);
        lv.setAdapter(adpt);
        Log.d("adapter","done");
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        lv.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onClick(View v) {

        Intent ib = new Intent(this,User.class);
        Bundle bu = new Bundle();
        bu.putString("name",nm);
        bu.putString("status",stat);
        ib.putExtras(bu);
        startActivity(ib);

    }

    @Override
    public void onBackPressed() {
        Intent ib = new Intent(this,User.class);
        Bundle bu = new Bundle();
        bu.putString("name",nm);
        bu.putString("status",stat);
        ib.putExtras(bu);
        startActivity(ib);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        menu.findItem(R.id.action_drawer_cart).setVisible(false);
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

        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        /*cat = parent.getItemAtPosition(position).toString();
        db = new DataBase(this);
        ds = new Desp();
        cat = parent.getItemAtPosition(position).toString();


        in = new Intent(this,Desp.class);
        b = new Bundle();
        b.putString("nam",cat);
        b.putString("name",nm);
        b.putString("status",stat);
        in.putExtras(b);
        startActivity(in);*/

        mat = parent.getItemAtPosition(position).toString();

        PopupMenu pop = new PopupMenu(this,view);
        pop.setOnMenuItemClickListener(this);
        pop.getMenuInflater().inflate(R.menu.buymenu, pop.getMenu());
        pop.show();


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        mat = parent.getItemAtPosition(position).toString();

        PopupMenu pop = new PopupMenu(this,view);
        pop.setOnMenuItemClickListener(this);
        pop.getMenuInflater().inflate(R.menu.menufile, pop.getMenu());
        pop.show();
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Log.d("function", "ok");

        switch (item.getItemId()) {
            case R.id.remove:
                db.delete(mat,nm);
                Toast.makeText(getBaseContext(),mat+" Book Is Remove From Cart",Toast.LENGTH_LONG).show();
                pr=db.cheak(nm);
                ArrayAdapter<String> adpt = new ArrayAdapter<String>(this,R.layout.list_item,R.id.product_name,pr);
                lv.setAdapter(adpt);
                return true;
            default:
                return false;
        }
    }
}
