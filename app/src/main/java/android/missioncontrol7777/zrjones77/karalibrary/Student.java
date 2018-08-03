package android.missioncontrol7777.zrjones77.karalibrary;

public class Student {

    private String firstName;
    private String lastName;
    private String grade;


    public Student() {
        this.firstName = null;
        this.lastName = null;
        this.grade = null;
    }

    public Student(String firstName, String lastName, String grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
    }

    //Getters

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGrade() {
        return grade;
    }

    //Setters

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    //Override
    public String toString() {
        String s = "";
        s += "---------------------------\n";
        s += "FirstName:   " + firstName + "\n";
        s += "LastName:    " + lastName + "\n";
        s += "Grade Level: " + grade + "\n";
        return s;
    }

}
