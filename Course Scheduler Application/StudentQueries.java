/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;
/**
 *
 * @author rajes
 */

public class StudentQueries {
    private static PreparedStatement studentconnection;
    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getSconnection;
    private static PreparedStatement dropStudent;
    
    public static void addStudent(StudentEntry student){
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<StudentEntry> getAllStudents(){
        
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> all_students = new ArrayList<StudentEntry>();
        
        try
        {
            getAllStudents = connection.prepareStatement("select studentid,firstname,lastname from app.student");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                StudentEntry obj = new StudentEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
                all_students.add(obj);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return all_students;
    }
    
    public static String getStudentID(String firstname, String lastname){
        String studentid = "";
        connection = DBConnection.getConnection();
        try
        {
            studentconnection = connection.prepareStatement("select (studentID) from app.student where firstname = ? and lastname = ?");
//            System.out.println("f=" + firstname + " l=" + lastname);
            studentconnection.setString(1, firstname);
            studentconnection.setString(2, lastname);
            resultSet = studentconnection.executeQuery();   // just added
            resultSet.next();
            studentid = resultSet.getString(1);
             
            
            //scheduleconnection.executeUpdate();     in semester queries but not updating table so dont think i need
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return studentid;
    }
    
    public static StudentEntry getStudent(String studentID)
    {
        String firstname = "";
        String lastname = "";
        StudentEntry objec = null;
        //String StudentID, String FirstName, String LastName
        connection = DBConnection.getConnection();
        try
        {
            getSconnection = connection.prepareStatement("select firstname,lastname from app.student where studentID = ?");
            getSconnection.setString(1, studentID);
            resultSet = getSconnection.executeQuery(); 
            resultSet.next();
            firstname = resultSet.getString(1);
            lastname = resultSet.getString(2);
            
            objec = new StudentEntry(studentID,firstname,lastname);
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return objec;
    }
    
    public static void dropStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("delete from app.student where studentID = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
