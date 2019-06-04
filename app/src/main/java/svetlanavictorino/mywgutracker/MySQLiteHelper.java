package svetlanavictorino.mywgutracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySQLiteHelper extends SQLiteOpenHelper {

    //TERMS
    public static final String TABLE_TERMS = "terms";
    public static final String TERMS_COLUMN_ID = "_id";
    public static final String TERMS_COLUMN_TITLE = "title";
    public static final String TERMS_COLUMN_START = "start";
    public static final String TERMS_COLUMN_END = "end";

    //COURSES
    public static final String TABLE_COURSES = "courses";
    public static final String COURSES_COLUMN_ID = "_id";
    public static final String COURSES_COLUMN_TERM_ID = "termId";
    public static final String COURSES_COLUMN_CODE = "code";
    public static final String COURSES_COLUMN_TITLE = "title";
    public static final String COURSES_COLUMN_START_DATE = "startDate";
    public static final String COURSES_COLUMN_START_CHECKBOX = "startCheckbox";
    public static final String COURSES_COLUMN_END_DATE = "endDate";
    public static final String COURSES_COLUMN_END_CHECKBOX = "endCheckbox";
    public static final String COURSES_COLUMN_MENTOR_NAME = "mentorName";
    public static final String COURSES_COLUMN_MENTOR_PHONE = "mentorPhone";
    public static final String COURSES_COLUMN_MENTOR_EMAIL = "mentorEmail";
    public static final String COURSES_COLUMN_MENTOR2_NAME = "mentor2Name";
    public static final String COURSES_COLUMN_MENTOR2_PHONE = "mentor2Phone";
    public static final String COURSES_COLUMN_MENTOR2_EMAIL = "mentor2Email";
    public static final String COURSES_COLUMN_STATUS = "status";
    public static final String COURSES_COLUMN_NOTES = "notes";

   //ASSESSMENTS
    public static final String TABLE_ASSESSMENTS = "assessments";
    public static final String ASSESSMENTS_COLUMN_ID = "_id";
    public static final String ASSESSMENTS_COLUMN_COURSE_ID = "courseId";
    public static final String ASSESSMENTS_COLUMN_TITLE = "title";
    public static final String ASSESSMENTS_COLUMN_TYPE = "type";
    public static final String ASSESSMENTS_COLUMN_DUE_DATE = "dueDate";
    public static final String ASSESSMENTS_COLUMN_DUE_DATE_CHECKBOX = "dueDateCheckbox";


    private static final String DATABASE_NAME = "tracker.db";
    private static final int DATABASE_VERSION = 1;

    //Database creation sql statement

    //Terms table
    public static final String TERMS_TABLE_CREATE = "create table "
            + TABLE_TERMS + "(" +
            TERMS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TERMS_COLUMN_TITLE + " TEXT NOT NULL, " +
            TERMS_COLUMN_START + " TEXT, " +
            TERMS_COLUMN_END + " TEXT)";

    public static final String COURSES_TABLE_CREATE = "create table "
            + TABLE_COURSES + "(" +
            COURSES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COURSES_COLUMN_TERM_ID + " INEGER, " +
            COURSES_COLUMN_CODE + " TEXT, " +
            COURSES_COLUMN_TITLE + " TEXT, " +
            COURSES_COLUMN_START_DATE + " TEXT, " +
            COURSES_COLUMN_START_CHECKBOX + " TEXT, " +
            COURSES_COLUMN_END_DATE + " TEXT, " +
            COURSES_COLUMN_END_CHECKBOX + " TEXT, " +
            COURSES_COLUMN_MENTOR_NAME + " TEXT, " +
            COURSES_COLUMN_MENTOR_PHONE + " TEXT, " +
            COURSES_COLUMN_MENTOR_EMAIL + " TEXT, " +
            COURSES_COLUMN_MENTOR2_NAME + " TEXT, " +
            COURSES_COLUMN_MENTOR2_PHONE + " TEXT, " +
            COURSES_COLUMN_MENTOR2_EMAIL + " TEXT, " +
            COURSES_COLUMN_STATUS + " TEXT, " +
            COURSES_COLUMN_NOTES + " TEXT, " +
            " FOREIGN KEY (" + COURSES_COLUMN_TERM_ID + ") REFERENCES " + TABLE_TERMS + "(" + TERMS_COLUMN_ID + ")" +
            ")";

        public static final  String ASSESSMENTS_TABLE_CREATE = "create table "
                + TABLE_ASSESSMENTS + "(" +
                ASSESSMENTS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ASSESSMENTS_COLUMN_COURSE_ID + " INTEGER, " +
                ASSESSMENTS_COLUMN_TITLE + " TEXT, " +
                ASSESSMENTS_COLUMN_TYPE + " TEXT, " +
                ASSESSMENTS_COLUMN_DUE_DATE + " TEXT, " +
                ASSESSMENTS_COLUMN_DUE_DATE_CHECKBOX + " TEXT, " +
                " FOREIGN KEY (" + ASSESSMENTS_COLUMN_COURSE_ID + ") REFERENCES " + TABLE_COURSES + "(" + COURSES_COLUMN_ID + ")" +
                ")";

    public MySQLiteHelper(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TERMS_TABLE_CREATE);
        database.execSQL(COURSES_TABLE_CREATE);
        database.execSQL(ASSESSMENTS_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENTS);
        onCreate(db);
    }

    public int updateCourse (String id, String termId, String new_code, String new_title, String new_startDate, Boolean new_startCheckbox,
                             String new_endDate, Boolean new_endCheckbox, String new_mentorName, String new_mentorPhone, String new_mentorEmail,
                             String new_mentor2Name, String new_mentor2Phone, String new_mentor2Email, String new_status,
                             String new_notes, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_TERM_ID, termId);
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_CODE, new_code);
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_TITLE, new_title);
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_START_DATE, new_startDate);
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_START_CHECKBOX, new_startCheckbox);
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_END_DATE, new_endDate);
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_END_CHECKBOX, new_endCheckbox);
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_MENTOR_NAME, new_mentorName);
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_MENTOR_PHONE, new_mentorPhone);
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_MENTOR_EMAIL, new_mentorEmail);
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_MENTOR2_NAME, new_mentor2Name);
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_MENTOR2_PHONE, new_mentor2Phone);
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_MENTOR2_EMAIL, new_mentor2Email);
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_STATUS, new_status);
        contentValues.put(MySQLiteHelper.COURSES_COLUMN_NOTES, new_notes);
        String selection = MySQLiteHelper.COURSES_COLUMN_ID + " LIKE ?";
        String[] selection_args = {id};
        int count = sqLiteDatabase.update(MySQLiteHelper.TABLE_COURSES, contentValues, selection, selection_args);
        return count;
    }

    public int updateTerm(String id, String title, String startDate, String endDate, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySQLiteHelper.TERMS_COLUMN_TITLE, title);
        contentValues.put(MySQLiteHelper.TERMS_COLUMN_START, startDate);
        contentValues.put(MySQLiteHelper.TERMS_COLUMN_END, endDate);
        String selection = MySQLiteHelper.ASSESSMENTS_COLUMN_ID + " LIKE ?";
        String[] selection_args = {id};
        int count = sqLiteDatabase.update(MySQLiteHelper.TABLE_TERMS, contentValues, selection, selection_args);
        return count;
    }

    public int updateAssessment (String id, String courseId, String new_title, String new_type, String new_dueDate,
                                 Boolean new_dueDateCheckbox, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.ASSESSMENTS_COLUMN_COURSE_ID, courseId);
        contentValues.put(MySQLiteHelper.ASSESSMENTS_COLUMN_TITLE, new_title);
        contentValues.put(MySQLiteHelper.ASSESSMENTS_COLUMN_TYPE, new_type);
        contentValues.put(MySQLiteHelper.ASSESSMENTS_COLUMN_DUE_DATE, new_dueDate);
        contentValues.put(MySQLiteHelper.ASSESSMENTS_COLUMN_DUE_DATE_CHECKBOX, new_dueDateCheckbox);
        String selection = MySQLiteHelper.ASSESSMENTS_COLUMN_ID + " LIKE ?";
        String[] selection_args = {id};
        int count = sqLiteDatabase.update(MySQLiteHelper.TABLE_ASSESSMENTS, contentValues, selection, selection_args);
        return count;
    }
}