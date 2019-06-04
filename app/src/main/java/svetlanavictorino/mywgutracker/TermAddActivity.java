package svetlanavictorino.mywgutracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;

public class TermAddActivity extends AppCompatActivity {

    EditText title, startDate, endDate;
    private TermsDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (EditText) findViewById(R.id.term2Title);
        startDate = (EditText) findViewById(R.id.detailed2TermStart);
        endDate = (EditText) findViewById(R.id.detailed2TermEnd);
    }

    public void addTerm(){
        String strTitle = title.getText().toString();
        String strStartDate = startDate.getText().toString();
        String strEndDate = endDate.getText().toString();

        Term newTerm = new Term (strTitle, strStartDate, strEndDate);
        datasource = new TermsDataSource(this);
        datasource.open();
        datasource.createTerm(newTerm);
        Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
        datasource.close();
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

            case R.id.action_save:

                addTerm();

                Intent intent = new Intent(this, Terms.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(Build.VERSION.SDK_INT > 11) {
            invalidateOptionsMenu();
            menu.findItem(R.id.action_add).setVisible(false);
            menu.findItem(R.id.action_cancel).setVisible(false);
            menu.findItem(R.id.action_save).setVisible(true);
            menu.findItem(R.id.action_delete).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

}
