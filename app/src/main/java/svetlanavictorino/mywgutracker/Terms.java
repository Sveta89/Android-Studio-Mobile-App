package svetlanavictorino.mywgutracker;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.content.Intent;


import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.CommonDataSource;

import svetlanavictorino.mywgutracker.MainActivity;

import static svetlanavictorino.mywgutracker.MySQLiteHelper.TABLE_TERMS;

public class Terms extends AppCompatActivity {


    public static  Term term;

    private TermsDataSource datasource;
    public List<Term> valuesTerms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = findViewById(R.id.list);
       datasource = new TermsDataSource(this);
       datasource.open();

        valuesTerms = datasource.getAllTerms();



    TermListAdaptor adapter = new TermListAdaptor(
            this,R.layout.list_item_term, valuesTerms);
        listView.setAdapter(adapter);


    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(Terms.this, DetailTermActivity.class);

            term = valuesTerms.get(position);

            startActivity(intent);
        }
    });

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {

            case R.id.action_add:
                Intent intent = new Intent(this, TermAddActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(Build.VERSION.SDK_INT > 11) {
            invalidateOptionsMenu();
            menu.findItem(R.id.action_add).setVisible(true);
            menu.findItem(R.id.action_save).setVisible(false);
            menu.findItem(R.id.action_cancel).setVisible(false);
            menu.findItem(R.id.action_delete).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

}
