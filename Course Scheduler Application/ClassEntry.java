/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rajes
 */
public class ClassEntry {
    
    private String semester;
    private String CourseCode;
    private int Seats;

    public ClassEntry(String semester, String CourseCode, int Seats) {
        this.semester = semester;
        this.CourseCode = CourseCode;
        this.Seats = Seats;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public int getSeats() {
        return Seats;
    }
    
    
}
