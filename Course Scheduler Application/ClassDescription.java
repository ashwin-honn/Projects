/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rajes
 */
public class ClassDescription {
    
    private String CourseCode;
    private String Description;
    private int Seats;

    public ClassDescription(String CourseCode, String Description, int Seats) {
        this.CourseCode = CourseCode;
        this.Description = Description;
        this.Seats = Seats;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public String getDescription() {
        return Description;
    }

    public int getSeats() {
        return Seats;
    }
    
    
}
