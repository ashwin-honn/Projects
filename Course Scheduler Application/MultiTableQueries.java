/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author rajes
 */
public class MultiTableQueries {
    private static Connection connection;
    private static PreparedStatement getAllClassDescriptions;
    private static ResultSet resultSet;
    private static PreparedStatement scheduledstudents;
    private static PreparedStatement inner;
    private static PreparedStatement secinner;
    private static ResultSet inner_set;
    private static PreparedStatement waitlist;
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester){
    
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> class_descriptions = new  ArrayList<ClassDescription>();
        
        try
        {
            getAllClassDescriptions = connection.prepareStatement("select app.class.courseCode, description, seats from app.class, app.course where semester = ? and app.class.courseCode = app.course.courseCode order by app.class.courseCode");
            getAllClassDescriptions.setString(1, semester);
            resultSet = getAllClassDescriptions.executeQuery();
            
            while(resultSet.next())
            {
                //String CourseCode, String Description, int Seats
                ClassDescription obj = new ClassDescription(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3));
                class_descriptions.add(obj);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return class_descriptions;
    }
    
    
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        // visit schedule first and get rows with mathcing semester anc course code, only need stduent id, then visit stduent table to get cirst name last name with stduent id
        // then build stdient entry and add to array list
        try
        {
            scheduledstudents = connection.prepareStatement("select app.schedule.studentID,firstname,lastname from app.schedule,app.student where semester = ? and courseCode = ? and status = ? and app.schedule.studentid = app.student.studentid");
            scheduledstudents.setString(1, semester);
            scheduledstudents.setString(2, courseCode);
            scheduledstudents.setString(3, "S");
            resultSet = scheduledstudents.executeQuery();
                
                
            while(resultSet.next())
            {
                StudentEntry obj = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                students.add(obj);
            }
               
            
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
    }
    
    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> wait_students = new ArrayList<StudentEntry>();
        
       try
       {
           waitlist = connection.prepareStatement("select app.schedule.studentID,firstname,lastname from app.schedule,app.student where semester = ? and courseCode = ? and status = ? and app.schedule.studentid = app.student.studentid");
           waitlist.setString(1, semester);
           waitlist.setString(2, courseCode);
           waitlist.setString(3, "W");
           resultSet = waitlist.executeQuery();
           
           while(resultSet.next())
            {
                
                StudentEntry obj = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                wait_students.add(obj);
            }
           
           
       }
       catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return wait_students;
    }
}
