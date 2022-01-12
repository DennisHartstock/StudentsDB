package com.example.studentsdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.studentsdb.data.DatabaseHandler;
import com.example.studentsdb.model.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        databaseHandler.addStudent(new Student("History", "Ivan", "Ivanov", 1.2));
        databaseHandler.addStudent(new Student("Maths", "Petr", "Petrov", 2.4));
        databaseHandler.addStudent(new Student("Physics", "Zachar", "Zacharov", 3.6));
        databaseHandler.addStudent(new Student("Chemistry", "Semen", "Semenov", 4.8));
        databaseHandler.addStudent(new Student("Biology", "Denis", "Denisov", 3));
        databaseHandler.addStudent(new Student("Zoology", "Fedor", "Fedorov", 5));

        List<Student> studentList = databaseHandler.getAllStudents();

        Student deletingStudent = databaseHandler.getStudent(5);
        databaseHandler.deleteStudent(deletingStudent);

        for (Student student : studentList) {
            Log.d("StudentInfo: ", "ID " + student.getId() + ", Faculty " + student.getFaculty()
                    + ", Firstname " + student.getFirstName() + ", Lastname " + student.getLastName()
            + ", average grade " + student.getAverageGrade());
        }
        Log.d("StudentsCount", "Count of students: " + databaseHandler.getStudentsCount());

    }
}