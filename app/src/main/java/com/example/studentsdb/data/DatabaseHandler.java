package com.example.studentsdb.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.studentsdb.model.Student;
import com.example.studentsdb.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DatabaseHandler extends SQLiteOpenHelper {
    private ContentValues contentValues = new ContentValues();


    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " ("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_FACULTY + " TEXT,"
                + Util.KEY_FIRSTNAME + " TEXT,"
                + Util.KEY_LASTNAME + " TEXT,"
                + Util.KEY_AVERAGE_GRADE + " DOUBLE)";

        sqLiteDatabase.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addStudent(Student student) {
        SQLiteDatabase writableDB = this.getWritableDatabase();

        contentValues.put(Util.KEY_FACULTY, student.getFaculty());
        contentValues.put(Util.KEY_FIRSTNAME, student.getFirstName());
        contentValues.put(Util.KEY_LASTNAME, student.getLastName());
        contentValues.put(Util.KEY_AVERAGE_GRADE, student.getAverageGrade());

        writableDB.insert(Util.TABLE_NAME, null, contentValues);
        writableDB.close();
    }

    public Student getStudent(int id) {
        SQLiteDatabase readableDB = this.getReadableDatabase();
        Student student = new Student();

        Cursor cursor = readableDB.query(Util.TABLE_NAME, new String[]{Util.KEY_ID, Util.KEY_FACULTY,
                        Util.KEY_FIRSTNAME, Util.KEY_LASTNAME, Util.KEY_AVERAGE_GRADE},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {

            try {
                cursor.moveToFirst();
                student = new Student(Objects.requireNonNull(cursor).getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getDouble(4));
            } finally {
                cursor.close();
            }

        }
        return student;
    }


    public List<Student> getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Student> carsList = new ArrayList<>();

        String selectAllStudents = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllStudents, null);

        if (cursor.moveToFirst()) {

            do {
                Student student = new Student();
                student.setId(cursor.getInt(0));
                student.setFaculty(cursor.getString(1));
                student.setFirstName(cursor.getString(2));
                student.setLastName(cursor.getString(3));
                student.setAverageGrade(cursor.getDouble(4));
                carsList.add(student);
            } while (cursor.moveToNext());

        }
        return carsList;
    }

    public int updateStudent(Student student) {
        SQLiteDatabase writableDB = this.getWritableDatabase();

        contentValues.put(Util.KEY_FACULTY, student.getFaculty());
        contentValues.put(Util.KEY_FIRSTNAME, student.getFirstName());
        contentValues.put(Util.KEY_LASTNAME, student.getLastName());
        contentValues.put(Util.KEY_AVERAGE_GRADE, student.getAverageGrade());

        return writableDB.update(Util.TABLE_NAME, contentValues, Util.KEY_ID + "=?",
                new String[]{String.valueOf(student.getId())});
    }

    public void deleteStudent(Student student) {
        SQLiteDatabase writableDB = this.getWritableDatabase();

        writableDB.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{String.valueOf(student.getId())});
        writableDB.close();
    }

    public int getStudentsCount() {
        SQLiteDatabase readableDB = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = readableDB.rawQuery(countQuery, null);

        return cursor.getCount();
    }
}
