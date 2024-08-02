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
public class ScheduleQueries {
    private static PreparedStatement scheduleconnection;
    private static PreparedStatement dropbyc;
    private static PreparedStatement dropcourse;
    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement waitlistedstudents;
    private static PreparedStatement updatesched;
    
    public static void addScheduleEntry(ScheduleEntry entry){
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester, coursecode, studentid, status, timestamp) values (?,?,?,?,?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentID());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimestamp());
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        
        try
        {
            getScheduleByStudent = connection.prepareStatement("select coursecode,status,timestamp from app.schedule where semester = ? and studentid = ?");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
                //String Semester, String CourseCode, String StudentID, String Status, Timestamp Timestamp
                ScheduleEntry obj = new ScheduleEntry(semester, resultSet.getString(1), studentID, resultSet.getString(2), resultSet.getTimestamp(3));
                schedule.add(obj);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedule;
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode){     // layout doc didnt have string identifiers on parameters
        int scheduled_students = 0;
        
        
                
        connection = DBConnection.getConnection();
        try
        {
            scheduleconnection = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ?");
            scheduleconnection.setString(1, currentSemester);
            scheduleconnection.setString(2, courseCode);
            resultSet = scheduleconnection.executeQuery();
//            resultSet.next();   // just added
            resultSet.next();
            scheduled_students = resultSet.getInt(1);
            //scheduleconnection.executeUpdate();     in semester queries but not updating table so dont think i need
            //            resultSet.next();   // just added
//            scheduled_students = resultSet.getInt(1);
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return scheduled_students;
       
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode)
    {
        //String Semester, String CourseCode, String StudentID, String Status, Timestamp Timestamp
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> waitlisted = new ArrayList<ScheduleEntry>();
        
        try
        {
            waitlistedstudents = connection.prepareStatement("select studentid,timestamp from app.schedule where semester = ? and courseCode = ? and status = ? order by timestamp");
            waitlistedstudents.setString(1, semester);
            waitlistedstudents.setString(2, courseCode);
            waitlistedstudents.setString(3, "W");
            resultSet = waitlistedstudents.executeQuery();
            
             while(resultSet.next())
            {
                //String Semester, String CourseCode, String StudentID, String Status, Timestamp Timestamp
                ScheduleEntry obj = new ScheduleEntry(semester, courseCode, resultSet.getString(1), "W", resultSet.getTimestamp(2));
                waitlisted.add(obj);
            }
                      
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        
        return waitlisted;
    }
    
    //check student waitlist is in main frame
    // call when admin drops class
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode)
    {
        connection = DBConnection.getConnection();
        
        try
        {
            dropbyc = connection.prepareStatement("delete from app.schedule where semester = ? and studentID = ? and courseCode = ?");
            dropbyc.setString(1, semester);
            dropbyc.setString(2, studentID);
            dropbyc.setString(3, courseCode);
            dropbyc.executeUpdate();
            
           
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    // delete evrybody in coirse in semester
    public static void dropScheduleByCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        
        try
        {
            dropcourse = connection.prepareStatement("delete from app.schedule where semester = ? and courseCode = ?");
            dropcourse.setString(1, semester);
            dropcourse.setString(2, courseCode);
            dropcourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(ScheduleEntry entry)
    {
        // get row with primary key and change status from w to s
        connection = DBConnection.getConnection();
        
        try
        {
            updatesched = connection.prepareStatement("update app.schedule set status='S' where semester = ? and courseCode = ? and studentID = ?");
            updatesched.setString(1, entry.getSemester());
            updatesched.setString(2, entry.getCourseCode());
            updatesched.setString(3, entry.getStudentID());
            updatesched.executeUpdate();
            
           
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
