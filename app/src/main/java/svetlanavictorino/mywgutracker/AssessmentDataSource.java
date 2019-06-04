package svetlanavictorino.mywgutracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AssessmentDataSource {

    //Database fields
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.ASSESSMENTS_COLUMN_ID, MySQLiteHelper.ASSESSMENTS_COLUMN_COURSE_ID,
            MySQLiteHelper.ASSESSMENTS_COLUMN_TITLE, MySQLiteHelper.ASSESSMENTS_COLUMN_TYPE, MySQLiteHelper.ASSESSMENTS_COLUMN_DUE_DATE,
            MySQLiteHelper.ASSESSMENTS_COLUMN_DUE_DATE_CHECKBOX};

    public AssessmentDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);

    }
    public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        public void close() {
            dbHelper.close();
        }

        public Assessment createAssessment(Assessment assessment){
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.ASSESSMENTS_COLUMN_COURSE_ID, assessment.getCourseId());
            values.put(MySQLiteHelper.ASSESSMENTS_COLUMN_TITLE, assessment.getTitle());
            values.put(MySQLiteHelper.ASSESSMENTS_COLUMN_TYPE, assessment.getType());
            values.put(MySQLiteHelper.ASSESSMENTS_COLUMN_DUE_DATE, assessment.getDueDate());
            values.put(MySQLiteHelper.ASSESSMENTS_COLUMN_DUE_DATE_CHECKBOX, assessment.getDueDateCheckbox());
            Long insertId = database.insert(MySQLiteHelper.TABLE_ASSESSMENTS, null, values);
            Cursor cursor = database.query(MySQLiteHelper.TABLE_ASSESSMENTS,
                    allColumns, MySQLiteHelper.ASSESSMENTS_COLUMN_ID + " = " + insertId, null, null, null, null);
            cursor.moveToFirst();
            Assessment newAssessment = cursorToAssessment(cursor);
            cursor.close();
            return newAssessment;
        }

        public void deleteAssessment(Assessment assessment){

            String id = assessment.getId();
            System.out.println("Assessment deleted with id: " + id);
            database.delete(MySQLiteHelper.TABLE_ASSESSMENTS, MySQLiteHelper.ASSESSMENTS_COLUMN_ID + " = " + id, null);
        }

        public void deleteByIdAssessment(int id){
            System.out.println("Assessment deleted with id: " + id);
            database.delete(MySQLiteHelper.TABLE_ASSESSMENTS, MySQLiteHelper.ASSESSMENTS_COLUMN_ID + " = " + id, null);
        }

        public List<Assessment> getAllAssessments(){
            List<Assessment> assessments = new ArrayList<Assessment>();
            Cursor cursor = database.query(MySQLiteHelper.TABLE_ASSESSMENTS, allColumns, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Assessment assessment = cursorToAssessment(cursor);
                assessments.add(assessment);
                cursor.moveToNext();
            }
            cursor.close();
            return assessments;
        }

    public List<Assessment> getAllAssessments(String id){
        List<Assessment> assessments = new ArrayList<Assessment>();


        Cursor cursor = database.query(MySQLiteHelper.TABLE_ASSESSMENTS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Assessment assessment = cursorToAssessment(cursor);
            if (assessment.getCourseId().equals(id)){

                assessments.add(assessment);

            }

            cursor.moveToNext();
        }

        //close cursor
        cursor.close();
        return assessments;
    }

        private Assessment cursorToAssessment(Cursor cursor){
        Assessment assessment = new Assessment();
        assessment.setId(cursor.getString(0));
        assessment.setCourseId(cursor.getString(1));
        assessment.setTitle(cursor.getString(2));
        assessment.setType(cursor.getString(3));
        assessment.setDueDate(cursor.getString(4));
        assessment.setDueDateCheckbox(cursor.getInt(5) > 0);
        return assessment;
        }

}
