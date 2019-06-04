package svetlanavictorino.mywgutracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CoursesDataSource {


    //Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COURSES_COLUMN_ID, MySQLiteHelper.COURSES_COLUMN_TERM_ID, MySQLiteHelper.COURSES_COLUMN_CODE, MySQLiteHelper.COURSES_COLUMN_TITLE, MySQLiteHelper.COURSES_COLUMN_START_DATE, MySQLiteHelper.COURSES_COLUMN_START_CHECKBOX,
            MySQLiteHelper.COURSES_COLUMN_END_DATE, MySQLiteHelper.COURSES_COLUMN_END_CHECKBOX, MySQLiteHelper.COURSES_COLUMN_MENTOR_NAME, MySQLiteHelper.COURSES_COLUMN_MENTOR_PHONE, MySQLiteHelper.COURSES_COLUMN_MENTOR_EMAIL, MySQLiteHelper.COURSES_COLUMN_MENTOR2_NAME, MySQLiteHelper.COURSES_COLUMN_MENTOR2_PHONE,
            MySQLiteHelper.COURSES_COLUMN_MENTOR2_EMAIL, MySQLiteHelper.COURSES_COLUMN_STATUS, MySQLiteHelper.COURSES_COLUMN_NOTES};

    public CoursesDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
       // dbHelper.onCreate(database);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Course createCourse (Course course){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COURSES_COLUMN_TERM_ID, course.getTermId());
        values.put(MySQLiteHelper.COURSES_COLUMN_CODE, course.getCode());
        values.put(MySQLiteHelper.COURSES_COLUMN_TITLE, course.getTitle());
        values.put(MySQLiteHelper.COURSES_COLUMN_START_DATE, course.getStartDate());
        values.put(MySQLiteHelper.COURSES_COLUMN_START_CHECKBOX, course.getStartCheckBox());
        values.put(MySQLiteHelper.COURSES_COLUMN_END_DATE, course.getEndDate());
        values.put(MySQLiteHelper.COURSES_COLUMN_END_CHECKBOX, course.getEndCheckBox());
        values.put(MySQLiteHelper.COURSES_COLUMN_MENTOR_NAME, course.getMentorName());
        values.put(MySQLiteHelper.COURSES_COLUMN_MENTOR_PHONE, course.getMentorPhone());
        values.put(MySQLiteHelper.COURSES_COLUMN_MENTOR_EMAIL, course.getMentorEmail());
        values.put(MySQLiteHelper.COURSES_COLUMN_MENTOR2_NAME, course.getMentor2Name());
        values.put(MySQLiteHelper.COURSES_COLUMN_MENTOR2_PHONE, course.getMentor2Phone());
        values.put(MySQLiteHelper.COURSES_COLUMN_MENTOR2_EMAIL, course.getMentor2Email());
        values.put(MySQLiteHelper.COURSES_COLUMN_STATUS, course.getStatus());
        values.put(MySQLiteHelper.COURSES_COLUMN_NOTES, course.getNotes());
        Long insertId = database.insert(MySQLiteHelper.TABLE_COURSES, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COURSES,
                allColumns, MySQLiteHelper.COURSES_COLUMN_ID + " = " + insertId, null,
                null, null,null);
        cursor.moveToFirst();
        Course newCourse = cursorToCourse(cursor);
        cursor.close();
        return newCourse;
    }
     public void deleteCourse (Course course){
        String id = course.getId();
        System.out.println("Course deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COURSES, MySQLiteHelper.COURSES_COLUMN_ID + " = " + id, null);

     }

    public void deletebyIdCourse (int id){

        System.out.println("Course deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COURSES, MySQLiteHelper.COURSES_COLUMN_ID + " = " + id, null);

    }

    public List<Course> getAllCourses(){
        List<Course> courses = new ArrayList<Course>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COURSES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Course course = cursorToCourse(cursor);
            courses.add(course);
            cursor.moveToNext();
        }

        //close cursor
        cursor.close();
        return courses;
    }

    public List<Course> getAllCourses(String id){
        List<Course> courses = new ArrayList<Course>();


        Cursor cursor = database.query(MySQLiteHelper.TABLE_COURSES,
                allColumns, null, null, null, null, null);


        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Course course = cursorToCourse(cursor);
            if (course.getTermId().equals(id)){

                    courses.add(course);

            }

            cursor.moveToNext();
        }

        //close cursor
        cursor.close();
        return courses;
    }

    private Course cursorToCourse(Cursor cursor){
        Course course = new Course();
        course.setId(cursor.getString(0));
        course.setTermId(cursor.getString(1));
        course.setCode(cursor.getString(2));
        course.setTitle(cursor.getString(3));
        course.setStartDate(cursor.getString(4));
        course.setStartCheckBox(cursor.getInt(5) > 0);
        course.setEndDate(cursor.getString(6));
        course.setEndCheckBox(cursor.getInt(7) > 0);
        course.setMentorName(cursor.getString(8));
        course.setMentorPhone(cursor.getString(9));
        course.setMentorEmail(cursor.getString(10));
        course.setMentor2Name(cursor.getString(11));
        course.setMentor2Phone(cursor.getString(12));
        course.setMentor2Email(cursor.getString(13));
        course.setStatus(cursor.getString(14));
        course.setNotes(cursor.getString(15));
        return course;
    }
}

