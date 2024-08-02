
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rajes
 */
public class ClassQueries {
    private static Connection connection;
    private static PreparedStatement classconnection;
    private static ResultSet resultSet;
    private static PreparedStatement addClass;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement dropClass;
    
    public static void addClass(ClassEntry claz) {       // can't use class in parameter because apache thinks its an actual class
        connection = DBConnection.getConnection();
        try
        {
            addClass = connection.prepareStatement("insert into app.class (semester, coursecode, seats) values (?,?,?)");
            addClass.setString(1, claz.getSemester());
            addClass.setString(2, claz.getCourseCode());
            addClass.setInt(3, claz.getSeats());
            addClass.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester){
        
        connection = DBConnection.getConnection();
        ArrayList<String> coursecodes = new ArrayList<String>();
        
        try
        {
            getAllCourseCodes = connection.prepareStatement("select coursecode from app.class order by coursecode");
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                coursecodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return coursecodes;
    }
    
    public static int getClassSeats(String semester, String courseCode){
        int class_seats = 0;
        
        connection = DBConnection.getConnection();
        try
        {
            classconnection = connection.prepareStatement("select seats from app.class where semester = ? and courseCode = ?");
            classconnection.setString(1, semester);
            classconnection.setString(2, courseCode);
            resultSet = classconnection.executeQuery();
            resultSet.next();
            class_seats += resultSet.getInt(1);
             
            
            //scheduleconnection.executeUpdate();     in semester queries but not updating table so dont think i need
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return class_seats;
    }
    
    
    // dropClass is in part 2
    // remove from scheudle table if people enrolled
    public static void dropClass(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try
        {
            dropClass = connection.prepareStatement("delete from app.class where semester = ? and courseCode = ?");
            dropClass.setString(1, semester);
            dropClass.setString(2, courseCode);
            dropClass.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
}
