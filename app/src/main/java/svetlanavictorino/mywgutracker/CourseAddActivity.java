package svetlanavictorino.mywgutracker;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import java.text.ParseException;
import java.util.*;
import java.lang.*;


import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class CourseAddActivity extends AppCompatActivity {

    EditText code, title, startDate, endDate, mentorName, mentorPhone, mentorEmail,
        mentor2Name, mentor2Phone, mentor2Email, notes;
    //CheckBox cbStartDate;
    //CheckBox cbEndDate;
    CheckBox  startCheckbox, endCheckbox;
    Spinner status;
    private CoursesDataSource datasource;
    public static String txtForNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner = (Spinner) findViewById(R.id.editStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        code = (EditText) findViewById(R.id.editCourseCode);
        title = (EditText) findViewById(R.id.editCourseTitle);
        startDate = (EditText) findViewById(R.id.editStartDate);
        startCheckbox = (CheckBox) findViewById(R.id.startDateAlert);
        endDate = (EditText) findViewById(R.id.editEndDate);
        endCheckbox = (CheckBox) findViewById(R.id.endDateAlert);
        mentorName = (EditText) findViewById(R.id.editMentorName);
        mentorPhone = (EditText) findViewById(R.id.editMentorPhone);
        mentorEmail = (EditText) findViewById(R.id.editMentorEmail);
        mentor2Name = (EditText) findViewById(R.id.editMentor2Name);
        mentor2Phone =(EditText) findViewById(R.id.editMentor2Phone);
        mentor2Email = (EditText) findViewById(R.id.editMentor2Email);
        status = (Spinner)  findViewById(R.id.editStatus);
        notes = (EditText) findViewById(R.id.editNotes);


    }

    public void selectStartDate(View view) {

        String strStartDate = startDate.getText().toString();

        if (strStartDate.equals("")){
            startCheckbox.setChecked(false);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setCancelable(true);
            builder.setTitle("Warning!");
            builder.setMessage("Please enter your Start Date.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();

        }else{
            startCheckbox.setChecked(true);
        }
    }
    public void selectStartDateItem() throws ParseException {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strStartDate = startDate.getText().toString();
        System.out.println(strStartDate);


        boolean checked = startCheckbox.isChecked();

            if(checked){
                    Date dateStart = formatter.parse(strStartDate);
                    long milliSecondsStart = dateStart.getTime();
                    txtForNotification = (strStartDate + " is the first day of " + code.getText().toString() + " " +  title.getText().toString());
                    System.out.println(txtForNotification);

                    Intent intent = new Intent(CourseAddActivity.this,MyReceiver.class);
                    PendingIntent sender= PendingIntent.getBroadcast(CourseAddActivity.this,0,intent,0);
                    AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, milliSecondsStart, sender);
                }
        }

    public void selectEndDate(View view) {
        String strEndDate = endDate.getText().toString();

        if (strEndDate.equals("")) {

            endCheckbox.setChecked(false);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setCancelable(true);
            builder.setTitle("Warning!");
            builder.setMessage("Please enter your Anticipated End Date.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();

        } else{
            endCheckbox.setChecked(true);
        }

    }


    public void selectEndDateItem() throws ParseException {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strEndDate = endDate.getText().toString();
        System.out.println(strEndDate);

        boolean checked = endCheckbox.isChecked();

            if (checked) {

                    txtForNotification = ("The Anticipated end of " + code.getText().toString() + " " +  title.getText().toString() + " is " + strEndDate);
                    Date dateEnd = formatter.parse(strEndDate);
                    long milliSecondsEnd = dateEnd.getTime();

                    Intent intent = new Intent(CourseAddActivity.this, MyReceiver.class);
                    PendingIntent sender = PendingIntent.getBroadcast(CourseAddActivity.this, 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, milliSecondsEnd, sender);
                }
        }

    public void addCourse(){
        String termId = Terms.term.getId().toString();
        String strCode = code.getText().toString();
        String strTitle = title.getText().toString();
        String strStart = startDate.getText().toString();
        Boolean strStartCheckbox = startCheckbox.isChecked();
        String strEnd = endDate.getText().toString();
        Boolean strEndCheckbox = endCheckbox.isChecked();
        String strName = mentorName.getText().toString();
        String strPhone = mentorPhone.getText().toString();
        String strEmail = mentorEmail.getText().toString();
        String strName2 = mentor2Name.getText().toString();
        String strPhone2 = mentor2Phone.getText().toString();
        String strEmail2 = mentor2Email.getText().toString();
        String strStatus = status.getSelectedItem().toString();
        String strNotes = notes.getText().toString();

        Course newCourse = new Course(termId, strCode, strTitle, strStart, strStartCheckbox, strEnd, strEndCheckbox, strName, strPhone, strEmail, strName2,
                strPhone2, strEmail2, strStatus, strNotes);
        datasource = new CoursesDataSource(this);
        datasource.open();
        datasource.createCourse(newCourse);
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

                addCourse();

                try {
                    selectStartDateItem();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    selectEndDateItem();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(this, ListOfCoursesByTerm.class);
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
