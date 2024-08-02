
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rajes
 */
public class CourseQueries {
    private static PreparedStatement courseconnection;
    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourseCodes;
    
    public static void addCourse(CourseEntry course) {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (coursecode,description) values (?,?)");
            addCourse.setString(1, course.getCourseCode());
            addCourse.setString(2, course.getDescription());
            addCourse.executeUpdate();
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
            getAllCourseCodes = connection.prepareStatement("select coursecode from app.course order by coursecode");
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
    
//    public static int getSeats(String Semester, String CourseCode){
//        int seats = 0;
//        connection = DBConnection.getConnection();
//        try
//        {
//            courseconnection = connection.prepareStatement("select count(seats) from app.class where semester = ? and courseCode = ?");
//            courseconnection.setString(1, Semester);
//            courseconnection.setString(2, CourseCode);
//            seats += resultSet.getInt(1);
//             
//            
//            //scheduleconnection.executeUpdate();     in semester queries but not updating table so dont think i need
//        }
//        catch(SQLException sqlException)
//        {
//            sqlException.printStackTrace();
//        }
//        
//        return seats;
//    }
    
    
}
