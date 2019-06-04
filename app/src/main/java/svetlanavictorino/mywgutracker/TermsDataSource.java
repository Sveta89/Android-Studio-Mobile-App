package svetlanavictorino.mywgutracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.CommonDataSource;

public class TermsDataSource extends AppCompatActivity {
    private CoursesDataSource datasource;
    List<Course> valuesCourse;


    //Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.TERMS_COLUMN_ID, MySQLiteHelper.TERMS_COLUMN_TITLE,
            MySQLiteHelper.TERMS_COLUMN_START , MySQLiteHelper.TERMS_COLUMN_END };

    public TermsDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }


    public void close(){
        dbHelper.close();
    }

    public Term createTerm (Term term){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TERMS_COLUMN_TITLE, term.getTitle());
        values.put(MySQLiteHelper.TERMS_COLUMN_START, term.getStart());
        values.put(MySQLiteHelper.TERMS_COLUMN_END, term.getEnd());
        Long insertId = database.insert(MySQLiteHelper.TABLE_TERMS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TERMS,
                allColumns, MySQLiteHelper.TERMS_COLUMN_ID + " = " + insertId, null,
                null, null,null);
        cursor.moveToFirst();
        Term newTerm = cursorToTerm(cursor);
        cursor.close();
        return newTerm;
    }
    public void deleteTerm (Term term, Context context){
        String id = term.getId();

        int numCourses;
       if (DetailTermActivity.valuesCourse == null) {
           numCourses = 0;
       }else{
               numCourses = DetailTermActivity.valuesCourse.size();
           }

        if (numCourses == 0){
            System.out.println("Term deleted with id: " + id);
            database.delete(MySQLiteHelper.TABLE_TERMS, MySQLiteHelper.TERMS_COLUMN_ID + " = " + id, null);
            Toast.makeText(context, "Term has deleted", Toast.LENGTH_LONG).show();

        } else{

                System.out.println("This Term can't be deleted.");
             Toast.makeText(context, "This Term can't be deleted.", Toast.LENGTH_LONG).show();

            }

        }


    public void deletebyIdTerm (int id){

        System.out.println("Term deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_TERMS, MySQLiteHelper.TERMS_COLUMN_ID + " = " + id, null);

    }

    public List<Term> getAllTerms(){
        List<Term> terms = new ArrayList<Term>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_TERMS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Term term = cursorToTerm(cursor);
            terms.add(term);
            cursor.moveToNext();
        }

        //close cursor
        cursor.close();
        return terms;
    }

    private Term cursorToTerm(Cursor cursor){
        Term term = new Term();
        term.setId(cursor.getString(0));
        term.setTitle(cursor.getString(1));
        term.setStart(cursor.getString(2));
        term.setEnd(cursor.getString(3));
        return term;
    }
}
