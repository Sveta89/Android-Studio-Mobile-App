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
import svetlanavictorino.mywgutracker.CourseAddActivity;
public class DetailCourseActivity extends AppCompatActivity {

    private CoursesDataSource datasource;
    private SQLiteDatabase database;
    MySQLiteHelper mySQLiteHelper;
    EditText code, title, startDate, endDate, mentorName, mentorPhone, mentorEmail,
            mentor2Name, mentor2Phone, mentor2Email, notes;
    Spinner status;
    CheckBox cbStartDate;
    CheckBox cbEndDate;
    CheckBox startCheckbox;
    CheckBox endCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareNotes();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner = (Spinner) findViewById(R.id.edit2Status);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        code = (EditText) findViewById(R.id.edit2CourseCode);
        System.out.println(Courses.course);
        code.setText(Courses.course.getCode());

        title = (EditText) findViewById(R.id.edit2CourseTitle);
        title.setText(Courses.course.getTitle());
        System.out.println(Courses.course.getTitle());
        System.out.println(Courses.course.getStartCheckBox());

        startDate = (EditText) findViewById(R.id.edit2StartDate);
        startDate.setText(Courses.course.getStartDate());

        startCheckbox = (CheckBox) findViewById(R.id.start2DateAlert);
        startCheckbox.setChecked(Courses.course.getStartCheckBox());

        endDate = (EditText) findViewById(R.id.edit2EndDate);
        endDate.setText(Courses.course.getEndDate());

        endCheckbox = (CheckBox) findViewById(R.id.end2DateAlert);
        endCheckbox.setChecked(Courses.course.getEndCheckBox());


        mentorName = (EditText) findViewById(R.id.edit2MentorName);
        mentorName.setText(Courses.course.getMentorName());

        mentorPhone = (EditText) findViewById(R.id.edit2MentorPhone);
        mentorPhone.setText(Courses.course.getMentorPhone());

        mentorEmail = (EditText) findViewById(R.id.edit2MentorEmail);
        mentorEmail.setText(Courses.course.getMentorEmail());

        mentor2Name = (EditText) findViewById(R.id.edit2Mentor2Name);
        mentor2Name.setText(Courses.course.getMentor2Name());

        mentor2Phone =(EditText) findViewById(R.id.edit2Mentor2Phone);
        mentor2Phone.setText(Courses.course.getMentor2Phone());

        mentor2Email = (EditText) findViewById(R.id.edit2Mentor2Email);
        mentor2Email.setText(Courses.course.getMentor2Email());

        status = (Spinner)  findViewById(R.id.edit2Status);
        status.setSelection(adapter.getPosition(Courses.course.getStatus()));

        notes = (EditText) findViewById(R.id.edit2Notes);
        notes.setText(Courses.course.getNotes());

        cbStartDate = (CheckBox) findViewById(R.id.start2DateAlert);
        cbEndDate = (CheckBox) findViewById(R.id.end2DateAlert);
    }

    public void deleteSelectedCourse(Course course){
        datasource = new CoursesDataSource(this);
        datasource.open();
        datasource.deleteCourse(course);
        Toast.makeText(getBaseContext(), "Course has deleted", Toast.LENGTH_LONG).show();
        datasource.close();
    }

    public void updateCourse(){
        mySQLiteHelper = new MySQLiteHelper(getApplicationContext());
        database = mySQLiteHelper.getWritableDatabase();
        String strCode, strTitle, strStartDate, strEndDate, strMentorName, strMentorPhone, strMentorEmail,
                strMentor2Name, strMentor2Phone, strMentor2Email, strStatus, strNotes;
        Boolean bStartCheckbox, bEndCheckbox;

        strCode = code.getText().toString();
        strTitle = title.getText().toString();
        strStartDate = startDate.getText().toString();
        bStartCheckbox = startCheckbox.isChecked();
        strEndDate = endDate.getText().toString();
        bEndCheckbox = endCheckbox.isChecked();
        strMentorName = mentorName.getText().toString();
        strMentorPhone = mentorPhone.getText().toString();
        strMentorEmail = mentorEmail.getText().toString();
        strMentor2Name = mentor2Name.getText().toString();
        strMentor2Phone = mentor2Phone.getText().toString();
        strMentor2Email = mentor2Email.getText().toString();
        strStatus = status.getSelectedItem().toString();
        strNotes = notes.getText().toString();
        int count = mySQLiteHelper.updateCourse(Courses.course.getId().toString(), Courses.course.getTermId().toString(), strCode, strTitle,
                strStartDate, bStartCheckbox, strEndDate, bEndCheckbox, strMentorName, strMentorPhone, strMentorEmail, strMentor2Name, strMentor2Phone, strMentor2Email,
                strStatus, strNotes, database);
        Toast.makeText(getApplicationContext(), count + " course updated", Toast.LENGTH_LONG).show();
        finish();
    }

    public void shareNotes(){



        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");

        intent.putExtra(Intent.EXTRA_SUBJECT, code.getText().toString() + " " +  title.getText().toString() + " notes. ");
        intent.putExtra(Intent.EXTRA_TEXT   , notes.getText().toString());

        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
            Toast.makeText(this, "Sending email..", Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void selectStartDate(View view) {

        String strStartDate = startDate.getText().toString();

        if (strStartDate.equals("")){
            cbStartDate.setChecked(false);

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
            cbStartDate.setChecked(true);
        }
    }
    public void selectStartDateItem() throws ParseException {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strStartDate = startDate.getText().toString();
        System.out.println(strStartDate);


        boolean checked = cbStartDate.isChecked();

            if(checked){
            Date dateStart = formatter.parse(strStartDate);
            long milliSecondsStart = dateStart.getTime();
            CourseAddActivity.txtForNotification = (strStartDate + " is the first day of " + code.getText().toString() + " " +  title.getText().toString());
            //System.out.println(CourseAddActivity.txtForNotification);

            Intent intent = new Intent(DetailCourseActivity.this,MyReceiver.class);
            PendingIntent sender= PendingIntent.getBroadcast(DetailCourseActivity.this,0,intent,0);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, milliSecondsStart, sender);
        }
    }

    public void selectEndDate(View view) {
        String strEndDate = endDate.getText().toString();

        if (strEndDate.equals("")) {

            cbEndDate.setChecked(false);

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
            cbEndDate.setChecked(true);
        }

    }

    public void selectEndDateItem() throws ParseException {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strEndDate = endDate.getText().toString();
        System.out.println(strEndDate);

        boolean checked = cbEndDate.isChecked();

           if (checked) {
            CourseAddActivity.txtForNotification = ("The Anticipated end of " + code.getText().toString() + " " +  title.getText().toString() + " is " + strEndDate);
            Date dateEnd = formatter.parse(strEndDate);
            long milliSecondsEnd = dateEnd.getTime();

            Intent intent = new Intent(DetailCourseActivity.this, MyReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(DetailCourseActivity.this, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, milliSecondsEnd, sender);
        }
    }
    public void launchListOfAssessments(View v){
        Intent intent = new Intent (this, ListOfAssessmentsByCourse.class);
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

        switch (id) {

            case R.id.action_save:
                updateCourse();
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
                /*Intent intent = new Intent(this, Terms.class);
                startActivity(intent);
                return true;*/
                if (Courses.courseScreen == false) {
                    Intent intent = new Intent(this, ListOfCoursesByTerm.class);
                    startActivity(intent);
                    return true;
                } else {
                    Intent intent2 = new Intent(this, Courses.class);
                    startActivity(intent2);
                    return true;
                }



            case R.id.action_delete:
             deleteSelectedCourse(Courses.course);
               /* Intent intent2 = new Intent (this, Courses.class);
                startActivity(intent2);
                return true;*/
                if (Courses.courseScreen == false) {
                    Intent intent = new Intent(this, ListOfCoursesByTerm.class);
                    startActivity(intent);
                    return true;
                } else {
                    Intent intent2 = new Intent(this, Courses.class);
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
