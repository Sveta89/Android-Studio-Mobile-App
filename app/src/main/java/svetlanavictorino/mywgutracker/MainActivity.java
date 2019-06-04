package svetlanavictorino.mywgutracker;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static svetlanavictorino.mywgutracker.R.string.btn_assessments_text;
import static svetlanavictorino.mywgutracker.R.string.btn_mentors_text;


public class MainActivity extends AppCompatActivity {
    private Button btnTerms;
    private Button btnCourses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        Button btnAssessments = new Button(this);
        btnAssessments.setText(btn_assessments_text);
        btnAssessments.setLayoutParams(new LinearLayout.LayoutParams(1050, 270));
        //btnAssessments.setHeight(270);
        LinearLayout linearLayout= (LinearLayout) View.inflate(this,R.layout.content_main, null);
        linearLayout.addView(btnAssessments);
        setContentView(linearLayout);
        btnAssessments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { launchAssessmentScreen(); }
        });

    }
    private void launchAssessmentScreen() {
        Assessments.assessmentScreen = true;
        Intent intent = new Intent(this, Assessments.class);
        startActivity(intent);
    }


    public void launchTermsMain(View v){
        Courses.courseScreen = false;
        Intent intent = new Intent (this, Terms.class);
        startActivity(intent);
    }

    public void launchCoursesMain(View v){
        Assessments.assessmentScreen = false;
        Courses.courseScreen = true;
        Intent intent = new Intent (this, Courses.class);
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

            case R.id.action_add:
                return true;
                }


        return super.onOptionsItemSelected(item);
    }
   @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
       menu.findItem(R.id.action_add).setVisible(false);
       menu.findItem(R.id.action_cancel).setVisible(false);
       menu.findItem(R.id.action_save).setVisible(false);
       menu.findItem(R.id.action_delete).setVisible(false);
        return true;
    }


}
