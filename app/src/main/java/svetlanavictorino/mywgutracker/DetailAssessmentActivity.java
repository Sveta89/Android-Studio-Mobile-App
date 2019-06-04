package svetlanavictorino.mywgutracker;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailAssessmentActivity extends AppCompatActivity {

    private AssessmentDataSource datasource;
    private SQLiteDatabase database;
    MySQLiteHelper mySQLiteHelper;
    EditText title, dueDate;
    Spinner type;
    CheckBox goalCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_assessment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner = (Spinner) findViewById(R.id.editAssessmentType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.assessment_type_array, android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        title = (EditText) findViewById(R.id.editAssessmentTitle);
        title.setText(Assessments.assessment.getTitle());

        type = (Spinner) findViewById(R.id.editAssessmentType);
        type.setSelection(adapter.getPosition(Assessments.assessment.getType()));

        dueDate = (EditText) findViewById(R.id.editAssessmentDueDate);
        dueDate.setText(Assessments.assessment.getDueDate());

        goalCheckbox = (CheckBox) findViewById(R.id.assessmentGoalAlert);
        goalCheckbox.setChecked(Assessments.assessment.getDueDateCheckbox());
    }

    public void deleteSelectedAssessment(Assessment assessment) {
        datasource = new AssessmentDataSource(this);
        datasource.open();
        datasource.deleteAssessment(assessment);
        Toast.makeText(getBaseContext(), "Assessment has deleted", Toast.LENGTH_LONG).show();
        datasource.close();
    }

    public void updateAssessment(){
        mySQLiteHelper = new MySQLiteHelper(getApplicationContext());
        database = mySQLiteHelper.getWritableDatabase();
        String strTitle, strType, strDueDate;
        Boolean bGoalAlert;

        strTitle = title.getText().toString();
        strType = type.getSelectedItem().toString();
        strDueDate = dueDate.getText().toString();
        bGoalAlert = goalCheckbox.isChecked();

        int count = mySQLiteHelper.updateAssessment(Assessments.assessment.getId().toString(), Assessments.assessment.getCourseId().toString(),
                strTitle, strType, strDueDate, bGoalAlert, database);
        Toast.makeText(getApplicationContext(), count + " assessment updated", Toast.LENGTH_LONG).show();
        finish();
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
    public void selectDueDateItem() throws ParseException{
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
                updateAssessment();
                try {
                    selectDueDateItem();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (Assessments.assessmentScreen == false) {
                    Intent intent = new Intent(this, ListOfAssessmentsByCourse.class);
                    startActivity(intent);
                    return true;
                } else {
                    Intent intent2 = new Intent(this, Assessments.class);
                    startActivity(intent2);
                    return true;
                }

            case R.id.action_delete:
                deleteSelectedAssessment(Assessments.assessment);

                if (Assessments.assessmentScreen == false) {
                    Intent intent = new Intent(this, ListOfAssessmentsByCourse.class);
                    startActivity(intent);
                    return true;
                } else {
                    Intent intent2 = new Intent(this, Assessments.class);
                    startActivity(intent2);
                    return true;
                }
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
