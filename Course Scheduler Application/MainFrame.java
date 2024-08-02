
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author acv
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    private String currentSemester;
    private String author;
    private String project;
    private static Connection connection;
    
    public MainFrame() {
        initComponents();
        checkData();
        rebuildSemesterComboBoxes();
        rebuildAddClassComboBox();
        rebuildScheduleClassSelectClassComboBox();
        rebuildScheduleClassSelectStudentComboBox();
        rebuildDisplayScheduleSelectStudentComboBox();
        rebuildDisplayClassListClassComboBox();
        rebuildDropStudentComboBox();
        rebuildAdminDropClassComboBox();
        rebuildStudentDropClassStudentComboBox();
        rebuildStudentDropClassClassComoBox();
    }

    

    public void rebuildSemesterComboBoxes() {
        ArrayList<String> semesters = SemesterQueries.getSemesterList();
        currentSemesterComboBox.setModel(new javax.swing.DefaultComboBoxModel(semesters.toArray()));
        if (semesters.size() > 0) {
            currentSemesterLabel.setText(semesters.get(0));
            currentSemester = semesters.get(0);
        } else {
            currentSemesterLabel.setText("None, add a semester.");
            currentSemester = "None";
        }
    }
    
    public void rebuildAddClassComboBox()
    {
        ArrayList<String> classes = CourseQueries.getAllCourseCodes(currentSemester);
        addClassCourseCodeComboBox.setModel(new javax.swing.DefaultComboBoxModel(classes.toArray()));
    }
    
    public void rebuildScheduleClassSelectClassComboBox()
    {
//        ArrayList<String> classes = ClassQueries.getAllCourseCodes(currentSemester);    /// this is where
          ArrayList<ClassDescription> classes = MultiTableQueries.getAllClassDescriptions(currentSemester);
          ArrayList<String> class_names = new ArrayList<String>();
          
          for (ClassDescription c : classes)
          {
              class_names.add(c.getCourseCode());
          }
          
        
        scheduleClassesSelectClassComboBox.setModel(new javax.swing.DefaultComboBoxModel(class_names.toArray()));
    }
    
    public void rebuildScheduleClassSelectStudentComboBox()
    {
        ArrayList<StudentEntry> studentobj = StudentQueries.getAllStudents();
        ArrayList<String> names = new ArrayList<String>();
        for (StudentEntry s : studentobj)
        {
            names.add(s.getLastName() + "," + s.getFirstName());
        }
        scheduleClassesSelectStudentsComboBox.setModel(new javax.swing.DefaultComboBoxModel(names.toArray()));
    }
    
    public void rebuildDisplayScheduleSelectStudentComboBox()
    {
        ArrayList<StudentEntry> studentobj = StudentQueries.getAllStudents();
        ArrayList<String> names = new ArrayList<String>();
        for (StudentEntry s : studentobj)
        {
            names.add(s.getLastName() + "," + s.getFirstName());
        }
        displayScheduleSelectStudentComboBox.setModel(new javax.swing.DefaultComboBoxModel(names.toArray()));
    }
    
    public void rebuildDisplayClassListClassComboBox()
    {
        ArrayList<ClassDescription> classes = MultiTableQueries.getAllClassDescriptions(currentSemester);
          ArrayList<String> class_names = new ArrayList<String>();
          
          for (ClassDescription c : classes)
          {
              class_names.add(c.getCourseCode());
          }
          
        
        AdminDisplayClassListClassComboBox.setModel(new javax.swing.DefaultComboBoxModel(class_names.toArray()));
    }
    
    public void rebuildDropStudentComboBox()
    {
        ArrayList<StudentEntry> studentobj = StudentQueries.getAllStudents();
        ArrayList<String> names = new ArrayList<String>();
        for (StudentEntry s : studentobj)
        {
            names.add(s.getLastName() + "," + s.getFirstName());
        }
        AdminDropStudentChooseStudentComboBox.setModel(new javax.swing.DefaultComboBoxModel(names.toArray()));
    }
    
    public void rebuildAdminDropClassComboBox()
    {
        ArrayList<ClassDescription> classes = MultiTableQueries.getAllClassDescriptions(currentSemester);
          ArrayList<String> class_names = new ArrayList<String>();
          
          for (ClassDescription c : classes)
          {
              class_names.add(c.getCourseCode());
          }
          
        
        AdminDropClassComboBox.setModel(new javax.swing.DefaultComboBoxModel(class_names.toArray()));
    }
    
    public void rebuildStudentDropClassStudentComboBox()
    {
        ArrayList<StudentEntry> studentobj = StudentQueries.getAllStudents();
        ArrayList<String> names = new ArrayList<String>();
        for (StudentEntry s : studentobj)
        {
            names.add(s.getLastName() + "," + s.getFirstName());
        }
        StudentDropClassStudentComboBox.setModel(new javax.swing.DefaultComboBoxModel(names.toArray()));
    }
    
    public void rebuildStudentDropClassClassComoBox()
    {
        ArrayList<ClassDescription> classes = MultiTableQueries.getAllClassDescriptions(currentSemester);
        ArrayList<String> class_names = new ArrayList<String>();
          
        for (ClassDescription c : classes)
        {
            class_names.add(c.getCourseCode());
        }
          
        
        StudentDropClassClassComboBox.setModel(new javax.swing.DefaultComboBoxModel(class_names.toArray()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        addSemesterTextfield = new javax.swing.JTextField();
        addSemesterSubmitButton = new javax.swing.JButton();
        addSemesterStatusLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        addCourseCodeTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        addCourseDescriptionTextField = new javax.swing.JTextField();
        addCourseSubmitButton = new javax.swing.JButton();
        addCourseStatusLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        addClassCourseCodeComboBox = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        addClassSeatsSpinner = new javax.swing.JSpinner();
        addClassSubmitButton = new javax.swing.JButton();
        addClassStatusLabel = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        addStudentStudentIDTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        addStudentFirstNameTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        addStudentLastNameTextField = new javax.swing.JTextField();
        addStudentSubmitButton = new javax.swing.JButton();
        addStudentStatusLabel = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        AdminDisplayClassListClassComboBox = new javax.swing.JComboBox<>();
        AdminDisplayClassListDisplayButton = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        AdminDisplayClassListScheduledTable = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        AdminDisplayClassListWaitlistTable = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        AdminDropStudentChooseStudentComboBox = new javax.swing.JComboBox<>();
        AdminDropStudentSubmitButt = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        AdminDropStudentTextArea = new javax.swing.JTextArea();
        jPanel12 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        AdminDropClassComboBox = new javax.swing.JComboBox<>();
        AdminDropClassSubmitButt = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        AdminDropClassTextArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        displayClassesTable = new javax.swing.JTable();
        displayClassesDisplayButton = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        scheduleClassesSelectClassComboBox = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        scheduleClassesSelectStudentsComboBox = new javax.swing.JComboBox<>();
        scheduleClassesSubmitButton = new javax.swing.JButton();
        scheduleClassesStatusLabel = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        displayScheduleSelectStudentComboBox = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        displayScheduleTable = new javax.swing.JTable();
        displayScheduleDisplayButton = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        StudentDropClassClassComboBox = new javax.swing.JComboBox<>();
        StudentDropClassSubmitButton = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        StudentDropClassTextArea = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        StudentDropClassStudentComboBox = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        currentSemesterLabel = new javax.swing.JLabel();
        currentSemesterComboBox = new javax.swing.JComboBox<>();
        changeSemesterButton = new javax.swing.JButton();
        aboutButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Course Scheduler");

        jLabel3.setText("Semester Name:");

        addSemesterTextfield.setColumns(20);

        addSemesterSubmitButton.setText("Submit");
        addSemesterSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSemesterSubmitButtonActionPerformed(evt);
            }
        });

        addSemesterStatusLabel.setText("                                                   ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addSemesterTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(addSemesterSubmitButton))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(addSemesterStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(410, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(addSemesterTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(addSemesterSubmitButton)
                .addGap(18, 18, 18)
                .addComponent(addSemesterStatusLabel)
                .addContainerGap(239, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Add Semester", jPanel3);

        jLabel4.setText("Course Code:");

        jLabel5.setText("Course Description:");

        addCourseSubmitButton.setText("Submit");
        addCourseSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCourseSubmitButtonActionPerformed(evt);
            }
        });

        addCourseStatusLabel.setText("            ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addCourseStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(addCourseCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(addCourseSubmitButton)
                                .addComponent(addCourseDescriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(392, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(addCourseCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(addCourseDescriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(addCourseSubmitButton)
                .addGap(18, 18, 18)
                .addComponent(addCourseStatusLabel)
                .addContainerGap(182, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Add Course", jPanel4);

        jLabel9.setText("Course Code:");

        jLabel10.setText("Seats:");

        addClassSubmitButton.setText("Submit");
        addClassSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addClassSubmitButtonActionPerformed(evt);
            }
        });

        addClassStatusLabel.setText("        ");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addClassStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addClassSubmitButton)
                            .addComponent(addClassSeatsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addClassCourseCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(562, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addClassCourseCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(addClassSeatsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(addClassSubmitButton)
                .addGap(18, 18, 18)
                .addComponent(addClassStatusLabel)
                .addContainerGap(195, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Add Class", jPanel6);

        jLabel6.setText("Student ID:");

        jLabel7.setText("First Name:");

        jLabel8.setText("Last Name:");

        addStudentSubmitButton.setText("Submit");
        addStudentSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentSubmitButtonActionPerformed(evt);
            }
        });

        addStudentStatusLabel.setText("                  ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(addStudentStudentIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addStudentSubmitButton)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(addStudentFirstNameTextField)
                                .addComponent(addStudentLastNameTextField))))
                    .addComponent(addStudentStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(509, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(addStudentStudentIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(addStudentFirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(addStudentLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(addStudentSubmitButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addStudentStatusLabel)
                .addContainerGap(158, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Add Student", jPanel7);

        jLabel15.setText("Choose Class:");

        AdminDisplayClassListDisplayButton.setText("Display");
        AdminDisplayClassListDisplayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminDisplayClassListDisplayButtonActionPerformed(evt);
            }
        });

        jLabel16.setText("Scheduled Students In the Class");

        AdminDisplayClassListScheduledTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Last Name", "First Name", "Student ID"
            }
        ));
        jScrollPane4.setViewportView(AdminDisplayClassListScheduledTable);

        jLabel17.setText("Waitlisted Students In The Class");

        AdminDisplayClassListWaitlistTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Last Name", "First Name", "Student ID"
            }
        ));
        jScrollPane5.setViewportView(AdminDisplayClassListWaitlistTable);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(AdminDisplayClassListClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(AdminDisplayClassListDisplayButton))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel16)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel17))))
                .addContainerGap(171, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(AdminDisplayClassListClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AdminDisplayClassListDisplayButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Display Class List", jPanel10);

        jLabel18.setText("Choose Student:");

        AdminDropStudentSubmitButt.setText("Submit");
        AdminDropStudentSubmitButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminDropStudentSubmitButtActionPerformed(evt);
            }
        });

        AdminDropStudentTextArea.setColumns(20);
        AdminDropStudentTextArea.setRows(5);
        jScrollPane8.setViewportView(AdminDropStudentTextArea);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel18)
                        .addGap(35, 35, 35)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AdminDropStudentSubmitButt)
                            .addComponent(AdminDropStudentChooseStudentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(312, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(AdminDropStudentChooseStudentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(AdminDropStudentSubmitButt)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Drop Student", jPanel11);

        jLabel19.setText("Choose Class:");

        AdminDropClassSubmitButt.setText("Submit");
        AdminDropClassSubmitButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminDropClassSubmitButtActionPerformed(evt);
            }
        });

        AdminDropClassTextArea.setColumns(20);
        AdminDropClassTextArea.setRows(5);
        jScrollPane7.setViewportView(AdminDropClassTextArea);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 804, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AdminDropClassSubmitButt)
                            .addComponent(AdminDropClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(AdminDropClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(AdminDropClassSubmitButt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Drop Class", jPanel12);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Admin", jPanel1);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        displayClassesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Course Code", "Description", "Seats"
            }
        ));
        jScrollPane2.setViewportView(displayClassesTable);

        displayClassesDisplayButton.setText("Display");
        displayClassesDisplayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayClassesDisplayButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(313, 313, 313)
                .addComponent(displayClassesDisplayButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(displayClassesDisplayButton)
                .addGap(98, 98, 98)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Display Classes", jPanel5);

        jLabel11.setText("Select Class:");

        jLabel12.setText("Select Student:");

        scheduleClassesSubmitButton.setText("Submit");
        scheduleClassesSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scheduleClassesSubmitButtonActionPerformed(evt);
            }
        });

        scheduleClassesStatusLabel.setText("         ");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(scheduleClassesStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scheduleClassesSubmitButton)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(scheduleClassesSelectClassComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(scheduleClassesSelectStudentsComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(664, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(scheduleClassesSelectClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(scheduleClassesSelectStudentsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(scheduleClassesSubmitButton)
                .addGap(18, 18, 18)
                .addComponent(scheduleClassesStatusLabel)
                .addContainerGap(322, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Schedule Classes", jPanel8);

        jLabel13.setText("Select Student:");

        displayScheduleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "CourseCode", "Status"
            }
        ));
        jScrollPane3.setViewportView(displayScheduleTable);

        displayScheduleDisplayButton.setText("Display");
        displayScheduleDisplayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayScheduleDisplayButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(displayScheduleSelectStudentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(310, 310, 310)
                        .addComponent(displayScheduleDisplayButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(displayScheduleSelectStudentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(displayScheduleDisplayButton)
                .addContainerGap(232, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Display Schedule", jPanel9);

        jLabel20.setText("Choose Class:");

        StudentDropClassClassComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentDropClassClassComboBoxActionPerformed(evt);
            }
        });

        StudentDropClassSubmitButton.setText("Submit");
        StudentDropClassSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentDropClassSubmitButtonActionPerformed(evt);
            }
        });

        StudentDropClassTextArea.setColumns(20);
        StudentDropClassTextArea.setRows(5);
        jScrollPane6.setViewportView(StudentDropClassTextArea);

        jLabel21.setText("Choose Student:");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(StudentDropClassSubmitButton)
                            .addComponent(StudentDropClassClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(StudentDropClassStudentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(149, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(StudentDropClassStudentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(StudentDropClassClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(StudentDropClassSubmitButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Drop Class", jPanel13);

        jLabel14.setText("jLabel14");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(328, 328, 328)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Student", jPanel2);

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 16)); // NOI18N
        jLabel2.setText("Current Semester: ");

        currentSemesterLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        currentSemesterLabel.setText("           ");

        currentSemesterComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        currentSemesterComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentSemesterComboBoxActionPerformed(evt);
            }
        });

        changeSemesterButton.setText("Change Semester");
        changeSemesterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeSemesterButtonActionPerformed(evt);
            }
        });

        aboutButton.setText("About");
        aboutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(currentSemesterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(currentSemesterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(changeSemesterButton)
                                .addGap(31, 31, 31)
                                .addComponent(aboutButton)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(currentSemesterLabel)
                    .addComponent(currentSemesterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changeSemesterButton)
                    .addComponent(aboutButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(439, 439, 439))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void aboutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutButtonActionPerformed
        // TODO add your handling code here:
        // display about information.
        JOptionPane.showMessageDialog(null, "Author: " + author + " Project: " + project);
    }//GEN-LAST:event_aboutButtonActionPerformed

    private void addSemesterSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSemesterSubmitButtonActionPerformed
        String semester = addSemesterTextfield.getText();
        SemesterQueries.addSemester(semester);
        addSemesterStatusLabel.setText("Semester " + semester + " has been added.");
        rebuildSemesterComboBoxes();
    }//GEN-LAST:event_addSemesterSubmitButtonActionPerformed

    private void addCourseSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCourseSubmitButtonActionPerformed
        String course_code = addCourseCodeTextField.getText();
        String course_description = addCourseDescriptionTextField.getText();
        
        CourseEntry course = new CourseEntry(course_code, course_description);
        CourseQueries.addCourse(course);
        
//        addClassCourseCodeComboBox.addItem(course_code);
        
        addCourseStatusLabel.setText(course_code + " has been added.");
        rebuildAddClassComboBox();
    }//GEN-LAST:event_addCourseSubmitButtonActionPerformed

    private void addStudentSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentSubmitButtonActionPerformed
        String student_id = addStudentStudentIDTextField.getText();
        String first_name = addStudentFirstNameTextField.getText();
        String last_name = addStudentLastNameTextField.getText();
        
        StudentEntry student = new StudentEntry(student_id, first_name, last_name);
        StudentQueries.addStudent(student);
        
//        scheduleClassesSelectStudentsComboBox.addItem(last_name + "," + first_name);
//        displayScheduleSelectStudentComboBox.addItem(last_name + "," + first_name);
        
        addStudentStatusLabel.setText(last_name + "," + first_name + " has been added.");
        rebuildScheduleClassSelectStudentComboBox();
        rebuildDisplayScheduleSelectStudentComboBox();
        rebuildDropStudentComboBox();
        rebuildStudentDropClassStudentComboBox();
    }//GEN-LAST:event_addStudentSubmitButtonActionPerformed

    private void displayClassesDisplayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayClassesDisplayButtonActionPerformed
        
          ArrayList<ClassDescription> classes = MultiTableQueries.getAllClassDescriptions(currentSemester);
          DefaultTableModel displayCoursesTableModel = (DefaultTableModel) displayClassesTable.getModel();
        
          displayCoursesTableModel.setNumRows(0);
          Object[] rowData = new Object[3];
          
          for (int i = 0; i < classes.size(); i++) 
          {
            rowData[0] = classes.get(i).getCourseCode();
            rowData[1] = classes.get(i).getDescription();
            rowData[2] = classes.get(i).getSeats();
            displayCoursesTableModel.addRow(rowData);
          }
          
          displayClassesTable.setModel(displayCoursesTableModel);

        
    }//GEN-LAST:event_displayClassesDisplayButtonActionPerformed

    private void addClassSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addClassSubmitButtonActionPerformed
        String coursecode = addClassCourseCodeComboBox.getSelectedItem().toString();
        int seats = (Integer)addClassSeatsSpinner.getValue();
//        String semester = currentSemester;
        
        ClassEntry claz = new ClassEntry(currentSemester, coursecode, seats);
        ClassQueries.addClass(claz);
        
//        scheduleClassesSelectClassComboBox.addItem(coursecode);
        
        addClassStatusLabel.setText("Class " + coursecode + " has been added.");
        rebuildScheduleClassSelectClassComboBox();
        rebuildDisplayClassListClassComboBox();
        rebuildAdminDropClassComboBox();
        rebuildStudentDropClassClassComoBox();
        
    }//GEN-LAST:event_addClassSubmitButtonActionPerformed

    private void scheduleClassesSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scheduleClassesSubmitButtonActionPerformed
        String coursecode = scheduleClassesSelectClassComboBox.getSelectedItem().toString();
        String name =  scheduleClassesSelectStudentsComboBox.getSelectedItem().toString();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        String splitname[] = name.split(",");
        
        String studentid = StudentQueries.getStudentID(splitname[1], splitname[0]);
        
        int scheduled_students = ScheduleQueries.getScheduledStudentCount(currentSemester, coursecode);
        int total_seats = ClassQueries.getClassSeats(currentSemester, coursecode);
        
//        System.out.print("scheduled: " + scheduled_students);
//        System.out.print("total:" + total_seats);
        
        String coursestatus;
        if (scheduled_students >= total_seats)
        {
            coursestatus = "W";
        }
        else 
        {
            coursestatus = "S";
        }
        
        ScheduleEntry sched = new ScheduleEntry(currentSemester, coursecode, studentid, coursestatus, currentTimestamp);
        ScheduleQueries.addScheduleEntry(sched);
        
        if ("W".equals(coursestatus))
        {
            scheduleClassesStatusLabel.setText(name + " has been waitlisted for the class");
        }
        else if ("S".equals(coursestatus))
        {
            scheduleClassesStatusLabel.setText(name + " has been scheduled for the class");
        }
    }//GEN-LAST:event_scheduleClassesSubmitButtonActionPerformed

    private void displayScheduleDisplayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayScheduleDisplayButtonActionPerformed

        String name =  displayScheduleSelectStudentComboBox.getSelectedItem().toString();
        String splitname[] = name.split(",");
        String studentid = StudentQueries.getStudentID(splitname[1], splitname[0]);
        
        
        
        ArrayList<ScheduleEntry> schedule = ScheduleQueries.getScheduleByStudent(currentSemester, studentid);
        
        DefaultTableModel displayScheduleTableModel = (DefaultTableModel) displayScheduleTable.getModel();
        displayScheduleTableModel.setNumRows(0);
        Object[] rowData = new Object[2];
        
        for (int i = 0; i < schedule.size(); i++) 
          {
            rowData[0] = schedule.get(i).getCourseCode();
            rowData[1] = schedule.get(i).getStatus();
            
            displayScheduleTableModel.addRow(rowData);
          }

        
        displayScheduleTable.setModel(displayScheduleTableModel);
          
        
    }//GEN-LAST:event_displayScheduleDisplayButtonActionPerformed

    private void currentSemesterComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentSemesterComboBoxActionPerformed
        // don't think i need this
    }//GEN-LAST:event_currentSemesterComboBoxActionPerformed

    private void changeSemesterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeSemesterButtonActionPerformed
        currentSemester = currentSemesterComboBox.getSelectedItem().toString();
        currentSemesterLabel.setText(currentSemester);
        
        //call all class rebuilds
        rebuildScheduleClassSelectClassComboBox();
        rebuildDisplayClassListClassComboBox();
        rebuildAdminDropClassComboBox();
        rebuildStudentDropClassClassComoBox();

    }//GEN-LAST:event_changeSemesterButtonActionPerformed
 
    
    private void AdminDisplayClassListDisplayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminDisplayClassListDisplayButtonActionPerformed
        // done
        
        String c_name = AdminDisplayClassListClassComboBox.getSelectedItem().toString();
        
        ArrayList<StudentEntry> scheduled = MultiTableQueries.getScheduledStudentsByClass(currentSemester, c_name);
        ArrayList<StudentEntry> waitlisted = MultiTableQueries.getWaitlistedStudentsByClass(currentSemester, c_name);
        
        DefaultTableModel displayScheduledTableModel = (DefaultTableModel) AdminDisplayClassListScheduledTable.getModel();
        displayScheduledTableModel.setNumRows(0);
        Object[] rowData = new Object[3];
        
        for (int i = 0; i < scheduled.size(); i++) 
          {
            rowData[0] = scheduled.get(i).getLastName();
            rowData[1] = scheduled.get(i).getFirstName();
            rowData[2] = scheduled.get(i).getStudentID();
            
            displayScheduledTableModel.addRow(rowData);
          }

        AdminDisplayClassListScheduledTable.setModel(displayScheduledTableModel);
      
        
        // wait list table
        DefaultTableModel displayWaitlistTableModel = (DefaultTableModel) AdminDisplayClassListWaitlistTable.getModel();
        displayWaitlistTableModel.setNumRows(0);
        Object[] rowD = new Object[3];
        
        for (int i = 0; i < waitlisted.size(); i++) 
          {
            rowD[0] = waitlisted.get(i).getLastName();
            rowD[1] = waitlisted.get(i).getFirstName();
            rowD[2] = waitlisted.get(i).getStudentID();
            
            displayWaitlistTableModel.addRow(rowD);
          }
        
        AdminDisplayClassListWaitlistTable.setModel(displayWaitlistTableModel);
        
        
    }//GEN-LAST:event_AdminDisplayClassListDisplayButtonActionPerformed

    private void AdminDropStudentSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminDropStudentSubmitButtonActionPerformed
        //OLD
    }//GEN-LAST:event_AdminDropStudentSubmitButtonActionPerformed

    private void AdminDropClassSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminDropClassSubmitButtonActionPerformed
        // OLD
    }//GEN-LAST:event_AdminDropClassSubmitButtonActionPerformed

    private void StudentDropClassSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentDropClassSubmitButtonActionPerformed
        
        
        StudentDropClassTextArea.setText("");
        
        String name =  StudentDropClassStudentComboBox.getSelectedItem().toString();
        String splitname[] = name.split(",");
        String studentid = StudentQueries.getStudentID(splitname[1], splitname[0]);
        String coursename = StudentDropClassClassComboBox.getSelectedItem().toString();
        // get alls semesters 
        ArrayList<String> sems = SemesterQueries.getSemesterList();
        // loop trhough semester
        for (String semester : sems)
        {
            ArrayList<ScheduleEntry> sched = ScheduleQueries.getScheduleByStudent(semester, studentid);
            
            for (ScheduleEntry curr_s : sched)
            {
                ScheduleQueries.dropStudentScheduleByCourse(semester, studentid, coursename);
                // check if scheduled or waitlisted
                if (curr_s.getStatus().equals("S") && curr_s.getCourseCode().equals(coursename))
                {
                    //ScheduleQueries.dropStudentScheduleByCourse(semester, studentid, curr_s.getCourseCode());
                    StudentDropClassTextArea.append("\n"+name + " has been dropped from " + curr_s.getCourseCode());
                    
                    ArrayList<ScheduleEntry> waitlist = ScheduleQueries.getWaitlistedStudentsByClass(semester, curr_s.getCourseCode());
                    if (waitlist.size() > 0)
                        {
                            //String Semester, String CourseCode, String StudentID, String Status, Timestamp Timestamp
                            ScheduleEntry stud = waitlist.get(0);
                            ScheduleEntry new_s = new ScheduleEntry(semester, stud.getCourseCode(), stud.getStudentID(), stud.getStatus(), stud.getTimestamp());
                            ScheduleQueries.updateScheduleEntry(new_s);
                            
                            String new_student_id = new_s.getStudentID();
                            StudentEntry new_student = StudentQueries.getStudent(new_student_id);
                            
                            
                            StudentDropClassTextArea.append("\n"+ new_student.getLastName() + "," + new_student.getFirstName()+ " has been scheduled into " + new_s.getCourseCode());
                        }
                   
                }
                else if (curr_s.getStatus().equals("W")&& curr_s.getCourseCode().equals(coursename))
                    {
                        //ScheduleQueries.dropStudentScheduleByCourse(semester, studentid, curr_s.getCourseCode());
                        StudentDropClassTextArea.append("\n"+name + " has been dropped from the waitlist for " + curr_s.getCourseCode());
                    }
            }
        }
        //get schedule for stduent in that semester
        // loop through that and drop them from schedule table and check if they were shceyled or waitist. if just waitlist then drop from schedule table otherwise get waitlist and schedule perosn at top
    }//GEN-LAST:event_StudentDropClassSubmitButtonActionPerformed

    private void AdminDropStudentSubmitButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminDropStudentSubmitButtActionPerformed
        
        AdminDropStudentTextArea.setText("");
        
        // Current
        String name =  AdminDropStudentChooseStudentComboBox.getSelectedItem().toString();
        String splitname[] = name.split(",");
        String studentid = StudentQueries.getStudentID(splitname[1], splitname[0]);
        
        // drop student
        
        AdminDropStudentTextArea.append(name + " " + studentid + " has been dropped from the list of students.");
        
        //loop through semesters
        ArrayList<String> sems = SemesterQueries.getSemesterList();
        
        
        
        for (String s : sems)
        {
            
            //System.out.println(s);
            
            AdminDropStudentTextArea.append("\n\nFor Semester: " + s);
            // get stduenr schedule then loop through schedule and rop out of scheule table if they were edhedule an add from waitlist if applicabke
            // if waitlisted then drop
            ArrayList<ScheduleEntry> sched = ScheduleQueries.getScheduleByStudent(currentSemester, studentid );
            
            for (ScheduleEntry se : sched)
            {
                if (se.getStatus().equals("S"))
                    {
                        ScheduleQueries.dropStudentScheduleByCourse(currentSemester, studentid, se.getCourseCode());
                        AdminDropStudentTextArea.append("\n"+name + " has been dropped from " + se.getCourseCode());
                        
                        ArrayList<ScheduleEntry> waitlist = ScheduleQueries.getWaitlistedStudentsByClass(currentSemester, se.getCourseCode());
                        if (waitlist.size() > 0)
                        {
                            //String Semester, String CourseCode, String StudentID, String Status, Timestamp Timestamp
                            ScheduleEntry stud = waitlist.get(0);
                            ScheduleEntry new_s = new ScheduleEntry(currentSemester, stud.getCourseCode(), stud.getStudentID(), stud.getStatus(), stud.getTimestamp());
                            ScheduleQueries.updateScheduleEntry(new_s);
                            
                            String new_student_id = new_s.getStudentID();
                            StudentEntry new_student = StudentQueries.getStudent(new_student_id);
                            
                            
                            AdminDropStudentTextArea.append("\n"+ new_student.getLastName() + "," + new_student.getFirstName()+ " has been scheduled into " + new_s.getCourseCode());
                        }
                    }
                else if (se.getStatus().equals("W"))
                    {
                        ScheduleQueries.dropStudentScheduleByCourse(currentSemester, studentid, se.getCourseCode());
                        AdminDropStudentTextArea.append("\n"+name + " has been dropped from the waitlist for " + se.getCourseCode());
                    }
                
            }
            
        }
        
        StudentQueries.dropStudent(studentid);
        
        rebuildScheduleClassSelectStudentComboBox();
        rebuildDisplayScheduleSelectStudentComboBox();
        rebuildDropStudentComboBox();
        rebuildStudentDropClassStudentComboBox();
        
        
        
        
    }//GEN-LAST:event_AdminDropStudentSubmitButtActionPerformed

    private void StudentDropClassClassComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentDropClassClassComboBoxActionPerformed
        // combo box dont need
    }//GEN-LAST:event_StudentDropClassClassComboBoxActionPerformed

    private void AdminDropClassSubmitButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminDropClassSubmitButtActionPerformed
        
        AdminDropClassTextArea.setText("");
        // Display class list command
        // get class from combo box
        String c_name = AdminDropClassComboBox.getSelectedItem().toString();
        ArrayList<StudentEntry> scheduled = MultiTableQueries.getScheduledStudentsByClass(currentSemester, c_name);
        ArrayList<StudentEntry> waitlisted = MultiTableQueries.getWaitlistedStudentsByClass(currentSemester, c_name);
        
        // two diff for loops
        AdminDropClassTextArea.append("Scheduled Students dropped from the course:");
        for (StudentEntry s: scheduled)
        {
            AdminDropClassTextArea.append("\n"+s.getLastName() + "," + s.getFirstName());
        }
        
        AdminDropClassTextArea.append("\n\n\nWaitlisted Students dropped from the course:");
        for(StudentEntry w: waitlisted)
        {
            AdminDropClassTextArea.append("\n"+w.getLastName() + "," + w.getFirstName());
        }
        
        
        // drop from class querues drop class and drop students from schedule after loops
        ClassQueries.dropClass(currentSemester, c_name);
        ScheduleQueries.dropScheduleByCourse(currentSemester, c_name);
        
        // rebuild class boxes
        rebuildScheduleClassSelectClassComboBox();
        rebuildDisplayClassListClassComboBox();
        rebuildAdminDropClassComboBox();
        rebuildStudentDropClassClassComoBox();
        
        
    }//GEN-LAST:event_AdminDropClassSubmitButtActionPerformed

    
    
    
    private void checkData() {
        try {
            FileReader reader = new FileReader("xzq789yy.txt");
            BufferedReader breader = new BufferedReader(reader);

            String encodedAuthor = breader.readLine();
            String encodedProject = breader.readLine();
            byte[] decodedAuthor = Base64.getDecoder().decode(encodedAuthor);
            author = new String(decodedAuthor);
            byte[] decodedProject = Base64.getDecoder().decode(encodedProject);
            project = new String(decodedProject);
            reader.close();

        } catch (FileNotFoundException e) {
            //get user info and create file
            author = JOptionPane.showInputDialog("Enter your first and last name.");
            project = "Course Scheduler Fall 2023";

            //write data to the data file.
            try {
                FileWriter writer = new FileWriter("xzq789yy.txt", true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                // encode the output data.
                String encodedAuthor = Base64.getEncoder().encodeToString(author.getBytes());

                bufferedWriter.write(encodedAuthor);
                bufferedWriter.newLine();

                String encodedProject = Base64.getEncoder().encodeToString(project.getBytes());
                bufferedWriter.write(encodedProject);

                bufferedWriter.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.exit(1);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        //fill the combo boxes
//        rebuildSemesterComboBoxes();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> AdminDisplayClassListClassComboBox;
    private javax.swing.JButton AdminDisplayClassListDisplayButton;
    private javax.swing.JTable AdminDisplayClassListScheduledTable;
    private javax.swing.JTable AdminDisplayClassListWaitlistTable;
    private javax.swing.JComboBox<String> AdminDropClassComboBox;
    private javax.swing.JButton AdminDropClassSubmitButt;
    private javax.swing.JTextArea AdminDropClassTextArea;
    private javax.swing.JComboBox<String> AdminDropStudentChooseStudentComboBox;
    private javax.swing.JButton AdminDropStudentSubmitButt;
    private javax.swing.JTextArea AdminDropStudentTextArea;
    private javax.swing.JComboBox<String> StudentDropClassClassComboBox;
    private javax.swing.JComboBox<String> StudentDropClassStudentComboBox;
    private javax.swing.JButton StudentDropClassSubmitButton;
    private javax.swing.JTextArea StudentDropClassTextArea;
    private javax.swing.JButton aboutButton;
    private javax.swing.JComboBox<String> addClassCourseCodeComboBox;
    private javax.swing.JSpinner addClassSeatsSpinner;
    private javax.swing.JLabel addClassStatusLabel;
    private javax.swing.JButton addClassSubmitButton;
    private javax.swing.JTextField addCourseCodeTextField;
    private javax.swing.JTextField addCourseDescriptionTextField;
    private javax.swing.JLabel addCourseStatusLabel;
    private javax.swing.JButton addCourseSubmitButton;
    private javax.swing.JLabel addSemesterStatusLabel;
    private javax.swing.JButton addSemesterSubmitButton;
    private javax.swing.JTextField addSemesterTextfield;
    private javax.swing.JTextField addStudentFirstNameTextField;
    private javax.swing.JTextField addStudentLastNameTextField;
    private javax.swing.JLabel addStudentStatusLabel;
    private javax.swing.JTextField addStudentStudentIDTextField;
    private javax.swing.JButton addStudentSubmitButton;
    private javax.swing.JButton changeSemesterButton;
    private javax.swing.JComboBox<String> currentSemesterComboBox;
    private javax.swing.JLabel currentSemesterLabel;
    private javax.swing.JButton displayClassesDisplayButton;
    private javax.swing.JTable displayClassesTable;
    private javax.swing.JButton displayScheduleDisplayButton;
    private javax.swing.JComboBox<String> displayScheduleSelectStudentComboBox;
    private javax.swing.JTable displayScheduleTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JComboBox<String> scheduleClassesSelectClassComboBox;
    private javax.swing.JComboBox<String> scheduleClassesSelectStudentsComboBox;
    private javax.swing.JLabel scheduleClassesStatusLabel;
    private javax.swing.JButton scheduleClassesSubmitButton;
    // End of variables declaration//GEN-END:variables
}
