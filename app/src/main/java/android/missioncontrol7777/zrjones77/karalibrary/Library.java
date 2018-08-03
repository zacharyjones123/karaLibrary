package android.missioncontrol7777.zrjones77.karalibrary;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Library extends AppCompatActivity {

    private ArrayList<Book> books;
    private ArrayList<Student> students;

    public Library() {
        books = new ArrayList<Book>();
        students = new ArrayList<Student>();

    }

    public Library(ArrayList<Book> books, ArrayList<Student> students) {
        this.books = books;
        this.students = students;
    }

    //Actions of the library

    public void deleteBook(Book b) {
        int indexToDelete = -1;
        for(int i = 0; i < books.size(); i++) {
            if(books.get(i).isEquals(b)) {
                indexToDelete = i;
            }
        }
        books.remove(indexToDelete);
    }

    public void reWriteLibrary() {

        //Need to rewrite the txt file
        File fold=new File("src/main/android/missioncontrol7777.zrjones77.karalibrary/isbn.txt");
        try {
            FileWriter f2 = new FileWriter(fold, false);
            for(int i = 0; i < books.size(); i++) {
                f2.write(books.get(i).toString());
            }

            f2.close();
        } catch (IOException f) {
            f.printStackTrace();
        }
    }

    public void viewBooks() {
        for(Book b : books) {
            System.out.println(b);
        }
    }

    public void viewStudents() {
        for(Student s : students) {
            System.out.println(s);
        }
    }

    public void addBook(Book b) {
        books.add(b);
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    //Getters
    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    //Setters
    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

}
