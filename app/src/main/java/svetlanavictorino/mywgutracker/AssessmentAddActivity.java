package svetlanavictorino.mywgutracker;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssessmentAddActivity extends AppCompatActivity {

    private AssessmentDataSource datasource;
    MySQLiteHelper mySQLiteHelper;
    EditText title, dueDate;
    Spinner type;
    CheckBox goalCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner = (Spinner) findViewById(R.id.edit2AssessmentType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.assessment_type_array, android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        title = (EditText) findViewById(R.id.edit2AssessmentTitle);
        type = (Spinner) findViewById(R.id.edit2AssessmentType);
        dueDate = (EditText) findViewById(R.id.edit2AssessmentDueDate);
        goalCheckbox = (CheckBox) findViewById(R.id.assessment2GoalAlert);
    }

    public void selectAssessmentDueDate (View view){
        String strDueDate = dueDate.getText().toString();

        if (strDueDate.equals("")){
            goalCheckbox.setChecked(false);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setCancelable(true);
            builder.setTitle("Warning!");
            builder.setMessage("Please enter your Due Date.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();

        }else{
            goalCheckbox.setChecked(true);
        }
    }
    public void selectDueDateItem() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDueDate = dueDate.getText().toString();
        System.out.println(strDueDate);

        boolean checked = goalCheckbox.isChecked();

        if(checked){
            Date assDueDate = formatter.parse(strDueDate);
            long milliSecondsGoalDate = assDueDate.getTime();
            CourseAddActivity.txtForNotification = (strDueDate + " is a goal date for " + title.getText().toString() + " assessment." );

            Intent intent = new Intent(this, MyReceiver.class);
            PendingIntent sender= PendingIntent.getBroadcast(this,0,intent,0);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, milliSecondsGoalDate, sender);

        }
    }

    public void addAssessment(){
        String courseId = Courses.course.getId().toString();
        String strTitle = title.getText().toString();
        String strType = type.getSelectedItem().toString();
        String strDueDate = dueDate.getText().toString();
        Boolean strGoalCheckbox = goalCheckbox.isChecked();

        Assessment newAssessment = new Assessment(courseId, strTitle, strType, strDueDate, strGoalCheckbox);
        datasource = new AssessmentDataSource(this);
        datasource.open();
        datasource.createAssessment(newAssessment);
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

                addAssessment();

                try {
                    selectDueDateItem();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(this, ListOfAssessmentsByCourse.class);
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
