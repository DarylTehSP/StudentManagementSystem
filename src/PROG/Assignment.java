/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PROG;

import java.awt.*;
import javax.swing.plaf.FontUIResource;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import sun.audio.*;

/*
 *
 * @author Admin
 */
public class Assignment {

    static int iNumStudents;
    static Student[] students = new Student[10];

    public static void main(String[] args) throws IOException {
        int iChoice = 0, i = 0, iTemp, iMaxNameLength = 0, iMaxCourseLength = 0;
        String sChoice = "", sInput = "", sTemp, sName, sCourse, sMobile;
        char cGender;
        boolean bError = false, bChangesMade = false;
        //assigning students to array
        readFile();
        do {
            //display the main menu
            iChoice = mainMenu();
            switch (iChoice) {
                //display
                case 1:
                    if (iNumStudents == 0) {
                        errorMsg("There are no students to display");
                    } else {
                        //set and adjust the names and courses to maximum name & course lengths
                        iMaxNameLength = getMaxLengths(1);
                        iMaxCourseLength = getMaxLengths(2);
                        sName = students[0].adjustLength("Name", iMaxNameLength);
                        sCourse = students[0].adjustLength("Course", iMaxCourseLength);
                        //set the header for displaying
                        sTemp = "S/N  " + sName + "  Gender   " + sCourse + "     Mobile\n";
                        for (i = 0; i < iNumStudents; i++) {
                            sTemp = sTemp + " " + (i + 1) + "   " + students[i].toString(iMaxNameLength, 1, iMaxCourseLength, 8) + "\n";
                        }
                        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Monospaced", Font.BOLD, 12)));
                        JOptionPane.showMessageDialog(null, sTemp);
                        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Arial", Font.BOLD, 12)));
                    }
                    break;
                //search        
                case 2:
                    if (iNumStudents == 0) {
                        errorMsg("There are no students to search for");
                    } else {
                        //starts partial search for the name of the student 
                        i = validateRecord("Enter the name to search.", null);//get a valid name 
                        if (i == -2) {
                            break;
                        } else if (i == -1) {
                            errorMsg("Student cannot be found.");
                        } else {
                            //find the image corresponding to the student's name
                            ImageIcon icon = new ImageIcon(students[i].getName() + ".jpg");
                            JOptionPane.showMessageDialog(null, "Name: " + students[i].getName() + "\nGender: " + students[i].getGender() + "\nCourse: " + students[i].getCourse() + "\nMobile Number: " + students[i].getMobile(),
                                    "DMIT Students",
                                    JOptionPane.INFORMATION_MESSAGE, icon);
                        }
                    }
                    break;
                //delete
                case 3:
                    if (iNumStudents == 0) {
                        errorMsg("There are no students to delete");
                    } else {
                        //starts partial search for the name of the student
                        i = validateRecord("Enter the name to delete.", null);//get a valid name  
                        if (i == -2) {
                            break;
                        } else if (i == -1) {
                            errorMsg("Student cannot be found.");
                        } else {
                            //confirm if the student selected is correct
                            int response = JOptionPane.showConfirmDialog(null, "Do you want to delete " + students[i].getName() + "?", "DMIT Students",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (response == JOptionPane.YES_OPTION) {
                                //replace current info with next student's info
                                while (i < (iNumStudents - 1)) {

                                    students[i] = students[i + 1];
                                    i++;
                                }
                                JOptionPane.showMessageDialog(null, "Student has been deleted successfully.");
                                students[i] = new Student(null, '\0', null, null); //set last elements to null
                                iNumStudents = iNumStudents - 1;
     
                                bChangesMade = true;
                            } else {
                                break;
                            }
                        }

                    }
                    break;
                //add       
                case 4: //start the full name search to check if name is already inside the array
                    sName = validateName("Enter the name.", null, "add");//get valid name 
                    if (sName == null) {
                        break;
                    } else if (sName.equals("dupe"))//duplicate
                    {
                        errorMsg("The student is already in the list.");
                    } else//not duplicate
                    {
                        //get the new student object ready for assigning values

                        if (sName != null) {
                            cGender = (validateGender("Select the gender.", '\0'));//get a valid gender
                            if (cGender != '\0') {
                                sCourse = (validateCourse("Enter the course.", null));//get a valid course
                                if (sCourse != null) {
                                    sMobile = (validateMobile("Enter the mobile number.", null));//get a valid mobile
                                    if (sMobile != null) {
                                        i = iNumStudents;
                                        students[i] = new Student(sName, cGender, sCourse, sMobile);
                                        JOptionPane.showMessageDialog(null, students[i].getName() + " has been added successfully.");
                                        iNumStudents = iNumStudents + 1;
                                        bChangesMade = true;
                                    }
                                }
                            }
                        }
                    }
                    break;
                //edit        
                case 5:
                    if (iNumStudents == 0) {
                        errorMsg("There are no students to edit");
                    } else {
                        //start partial search for the name of the student
                        i = validateRecord("Enter the name to edit", null);
                        if (i == -2) {
                            break;
                        } else if (i == -1) {
                            errorMsg("Student cannot be found.");
                        } else {
                            sName = (validateName("Enter the new name.", students[i].getName(), "edit"));//get a valid name
                            if (sName != null) {
                                students[i].setName(sName);
                                cGender = (validateGender("Select the gender.", students[i].getGender()));//get a valid gender
                                if (cGender != '\0') {
                                    students[i].setGender(cGender);
                                    sCourse = (validateCourse("Enter the course.", students[i].getCourse()));//get a valid course
                                    if (sCourse != null) {
                                        students[i].setCourse(sCourse);
                                        sMobile = (validateMobile("Enter the mobile number.", students[i].getMobile()));//get a valid mobile
                                        if (sMobile != null) {
                                            students[i].setMobile(sMobile);
                                            JOptionPane.showMessageDialog(null, students[i].getName() + " has been edited successfully.");
                                            bChangesMade = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                //exit        
                case 6:
                    playAudio();
                    JOptionPane.showMessageDialog(null, "Program Terminated.\nThank You!");
                    break;
                //table       
                case 7:
                    if (iNumStudents == 0) {
                        errorMsg("The are no students to display");
                    } else {
                        String[] header = {"S/N", "NAME", "GENDER", "COURSE", "MOBILE"};
                        DefaultTableModel data = new DefaultTableModel(header, 0);
                        for (i = 0; i < iNumStudents; i++) {
                            data.addRow(new Object[]{1 + i, students[i].getName(), students[i].getGender(), students[i].getCourse(), students[i].getMobile()});
                        }
                        JTable table = new JTable(data);
                        table.setAutoCreateRowSorter(true);
                        JOptionPane.showMessageDialog(null, new JScrollPane(table));
                        break;
                    }
            }
            //check if any changes made to the array that needs to be re-written in the file
            if (bChangesMade) {
                //write the new information into the file
                writeFile();
                bChangesMade = false;
            }
        } while (iChoice != 6);
    }//end of main

    public static void playAudio() throws IOException {
        //initialise sound and play
        String soundFile = "C:\\Users\\dteh6\\Music\\wow.mp3";
        InputStream input = new FileInputStream(soundFile);
        AudioStream audioStream = new AudioStream(input);
        AudioPlayer.player.start(audioStream);
    }

    public static void readFile() throws IOException {
        //read the AllArrays text file and assigns data to Student objects/array
        File info = new File("AllArrays.txt");
        Scanner readFile = new Scanner(info);
        readFile.useDelimiter(">>");
        int iCountStudents = 0;
        String sTemp, sName, sGender, sCourse, sMobile;
        //check if file is empty
        if (!readFile.hasNext()) {
            errorMsg("The file 'AllArrays.txt' is empty.");
            iNumStudents = 0;
        } else {
            sTemp = readFile.next();
            iNumStudents = Integer.parseInt(sTemp);
        }
        //assigning the names, gender, course & mobile
        for (int i = 0; i < iNumStudents; i++) {
            if (readFile.hasNext()) {
                sName = readFile.next().trim();
                if (readFile.hasNext()) {
                    sGender = readFile.next().trim();
                    if (readFile.hasNext()) {
                        sCourse = readFile.next().trim();
                        if (readFile.hasNext()) {
                            sMobile = readFile.next().trim();
                            students[i] = new Student(sName, sGender.charAt(0), sCourse, sMobile);
                            iCountStudents = iCountStudents + 1;
                        } else {
                            errorMsg("The file doesnt have completed data.");
                        }
                    } else {
                        errorMsg("The file doesnt have completed data.");
                    }
                } else {
                    errorMsg("The file doesnt have completed data.");
                }
            } else {
                errorMsg("The file doesnt have completed data.");
            }
        }
    }

    public static void writeFile() throws IOException {
        //re-write the data in the array back into the file
        String sTemp;
        try (FileWriter fileWriter = new FileWriter("AllArrays.txt")) {
            fileWriter.write(iNumStudents + ">>" + System.lineSeparator());
            for (int i = 0; i < iNumStudents; i++) {
                sTemp = students[i].toFileString();
                fileWriter.write(sTemp + System.lineSeparator());
            }
            fileWriter.write(">EndOfFile<");
        }
    }

    public static int mainMenu() {
        //lists options 1 to 7 and gets input for selection
        //returns a string that will be used in other sections of main()
        String sChoice, sInput, sError = "";
        int iChoice;
        boolean bError = false;
        do {
            sChoice = JOptionPane.showInputDialog(null,
                    sError + "1-Display Students\n2-Search Students\n3-Delete Student\n4-Add New Student\n5-Edit Student's Info\n6-Exit\n7-Display table of students\n\nClass size: " + iNumStudents,
                    "DMIT Students",
                    JOptionPane.INFORMATION_MESSAGE);
            if (sChoice == null) {
                bError = true;
            } else {
                sInput = sChoice;
                if (sInput.length() != 1) {
                    bError = true;
                    sError = "The option must only be one character\n\n";
                } else if (sInput.compareTo("1") < 0 || sInput.compareTo("7") > 0) {
                    bError = true;
                    sError = "The option must be an integer from 1-7\n\n";
                } else {
                    bError = false;
                }
            }
        } while (bError);
        iChoice = Integer.parseInt(sChoice);
        return iChoice;
    }

    public static void errorMsg(String errType) {
        //method shows an error message with the error type
        JOptionPane.showMessageDialog(null,
                "Error: " + errType,
                "ERROR",
                JOptionPane.ERROR_MESSAGE);
    }

    public static String validateName(String sText, String sName, String sTask) {
        //method will check for any record using "full search", or no record is found
        //returns String sName if no record is found, and returns "dupe" if a record is found
        boolean bError = false;
        String sError = "";
        int i;
        do {
            sName = (String) JOptionPane.showInputDialog(null, sError + sText, "DMIT Students", JOptionPane.QUESTION_MESSAGE, null, null, sName);
            if (sName != null) {
                if (sName.length() < 1) {
                    bError = true;
                    sError = "Name length cannot be less than 1\n\n";
                } else {
                    bError = false;
                }
                //check if the student name is already inside the array
                if (sTask.equals("add")) {
                    for (i = 0; i < iNumStudents; i++) {
                        if (students[i].getName().equalsIgnoreCase(sName.trim())) {
                            return "dupe";
                        }
                    }
                }
            }
        } while (bError);
        return sName;
    }

    public static int validateRecord(String sText, String sName) {
        //method will check for duplicated records using "partial search", or if a single record is found
        //returns an integer i which is used to find the student object in the student class
        int i, iTemp = 0, iNamesFound;
        boolean bError;
        String sError = "", sTemp = "";
        do {
            bError = false;
            iNamesFound = 0;
            sName = (String) JOptionPane.showInputDialog(null, sError + sText, "DMIT Students", JOptionPane.QUESTION_MESSAGE, null, null, sName);
            if (sName != null) {
                if (sName.length() < 1) {
                    bError = true;
                    sError = "Name length cannot be less than 1\n\n";
                } else {
                    //partial search for the names of the student using contains() method
                    for (i = 0; i < iNumStudents; i++) {
                        if (students[i].getName().toUpperCase().contains(sName.toUpperCase())) {
                            iTemp = i;
                            iNamesFound = iNamesFound + 1;
                            if (students[i].getName().length() == sName.length()) {
                                return i;
                            }
                        }
                    }
                    if (iNamesFound > 1) {
                        bError = true;
                        sError = "Multiple records were found, try entering the full name\n\n";
                    } else {
                        bError = false;
                    }
                }
            }
        } while (bError);
        //if one student found, then return the i value for the student's place in the array
        if (iNamesFound == 1) {
            return iTemp;
        } //if user pressed cancel or the exit button
        else if (sName == null) {
            return -2;
        } //if no record is found then return -1
        else {
            return -1;
        }
    }

    public static String validateMobile(String sText, String sMobile) {
        //method will validate the mobile number input
        //returns the mobile number if it is valid; consists of only 1-9 and is 8 digits long
        int iTemp;
        String sError = "";
        boolean bError = false;
        do {
            sMobile = (String) JOptionPane.showInputDialog(null, sError + sText, "DMIT Students", JOptionPane.QUESTION_MESSAGE, null, null, sMobile);
            if (sMobile != null) {
                if (sMobile.length() == 8) {
                    for (iTemp = 0; iTemp < 8; iTemp++) {
                        if (sMobile.substring(iTemp, iTemp + 1).compareTo("0") >= 0 && sMobile.substring(iTemp, iTemp + 1).compareTo("9") <= 0) {
                            bError = false;
                        } else {
                            bError = true;
                            sError = "Mobile number can only have integers from 1 to 9\n\n";
                            break;
                        }
                    }
                } else {
                    bError = true;
                    sError = "Mobile number must have 8 digits only\n\n";
                }
            }
        } while (bError);
        return sMobile;
    }

    public static String validateCourse(String sText, String sCourse) {
        //method will validate the course input
        //returns the course if it is valid; course length is more than 3
        boolean bError = false;
        String sError = "";
        do {
            sCourse = (String) JOptionPane.showInputDialog(null, sError + sText, "DMIT Students", JOptionPane.QUESTION_MESSAGE, null, null, sCourse);
            if (sCourse != null) {
                if (sCourse.length() < 3) {
                    bError = true;
                    sError = "Course length cannot be less than 3\n\n";
                } else {
                    bError = false;
                }
            }

        } while (bError);
        return sCourse;
    }

    public static char validateGender(String sText, char cGender) {
        //method will validate the gender choice
        //returns the gender chosen from the selection table
        boolean bError = false;
        String[] options = {"Male", "Female"};
        String sGender;
        if (cGender == 'M') {
            sGender = "Male";
        } else {
            sGender = "Female";
        }
        sGender = ((String) JOptionPane.showInputDialog(null, sText, "DMIT Students", JOptionPane.QUESTION_MESSAGE, null, options, sGender));
        if (sGender == null) {
            cGender = '\0';
        } else {
            cGender = sGender.charAt(0);
        }
        return cGender;
    }

    public static int getMaxLengths(int iChoice) {
        //method will get the max lengths of name and course
        //returns integer values after getting the maximum lengths
        int iLength = 0;
        for (int i = 0; i < iNumStudents; i++) {
            switch (iChoice) {
                //get the max length
                case 1:
                    if (students[i].getName().length() > iLength) {
                        iLength = students[i].getName().length();
                        if (iLength > 20) {
                            iLength = 20;
                        }
                    }
                    break;
                case 2:
                    if (students[i].getCourse().length() > iLength) {
                        iLength = students[i].getCourse().length();
                        if (iLength < 6) {
                            iLength = 6;
                        } else if (iLength > 20) {
                            iLength = 20;
                        }
                    }
                    break;
            }
        }
        return iLength;
    }
}
