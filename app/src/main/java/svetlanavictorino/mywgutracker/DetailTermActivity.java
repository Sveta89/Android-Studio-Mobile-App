package svetlanavictorino.mywgutracker;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DetailTermActivity extends AppCompatActivity {

    EditText tvTitle, tvStart, tvEnd;
    private TermsDataSource datasource;
    private SQLiteDatabase database;
    MySQLiteHelper mySQLiteHelper;
    public static List<Course> valuesCourse;
    private CoursesDataSource datasourceCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_term);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tvTitle = findViewById(R.id.termTitle);
        tvTitle.setText(Terms.term.getTitle());

        tvStart = findViewById(R.id.detailedTermStart);
        tvStart.setText(Terms.term.getStart());

        tvEnd = findViewById(R.id.detailedTermEnd);
        tvEnd.setText(Terms.term.getEnd());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        datasourceCourse = new CoursesDataSource(this);
        datasourceCourse.open();
       // System.out.println(Terms.term.getId());
        valuesCourse = datasourceCourse.getAllCourses(Terms.term.getId());
        datasourceCourse.close();
    }

    public void deleteSelectedTerm(Term term){
        datasource = new TermsDataSource(this);
        datasource.open();
        datasource.deleteTerm(term, this);
       // Toast.makeText(getBaseContext(), "Term has deleted", Toast.LENGTH_LONG).show();
        datasource.close();
    }

    public void updateTerm(){
        mySQLiteHelper = new MySQLiteHelper(getApplicationContext());
        database = mySQLiteHelper.getWritableDatabase();
        String strTitle, strStart, strEnd;

        strTitle = tvTitle.getText().toString();
        strStart = tvStart.getText().toString();
        strEnd = tvEnd.getText().toString();

        int count = mySQLiteHelper.updateTerm(Terms.term.getId(), strTitle, strStart, strEnd, database);
        Toast.makeText(getApplicationContext(), count + " term updated", Toast.LENGTH_LONG).show();
        finish();
    }

    public void launchListOfCourses(View v){
        Intent intent = new Intent (this, ListOfCoursesByTerm.class);
        startActivity(intent);
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
                updateTerm();
                Intent intent = new Intent(this, Terms.class);
                startActivity(intent);
                return true;

            case R.id.action_delete:
                deleteSelectedTerm(Terms.term);
                Intent intent2 = new Intent (this, Terms.class);
                startActivity(intent2);
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
            menu.findItem(R.id.action_delete).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }


}
