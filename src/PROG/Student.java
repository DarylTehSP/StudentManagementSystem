/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PROG;

/**
 *
 * @author Admin
 */
class Student extends Assignment{
	// declare student properties
	private String sName;
	private char cGender;
	private String sCourse;
        private String sMobile;

	// initialise student property in constructor
	Student(String name, char cGender, String course, String mobile) {
            this.sName = name;
            this.cGender = cGender;
            this.sCourse = course;
            this.sMobile = mobile;
	}
        //function overloading for empty student object
        Student() {
            this.sName="";
            this.cGender=' ';
            this.sCourse="";
            this.sMobile="";
	}
        
        public void setName(String name) {
            //this method sets the name of an instance of the student object
            this.sName = name.trim();
        }
        
        public String getName() {
            //this method returns the name of an instance of the student object
            return sName;
        }
        
        public void setGender(char gender) {
            //this method sets the gender of an instance of the student object
            this.cGender = gender;
        }
        
        public char getGender() {
            //this method returns the gender of an instance of the student object
            return cGender;
        }
        
        public void setCourse(String course) {
            //this method sets the course of an instance of the student object
            this.sCourse = course.trim();
        }
        public String getCourse() {
            //this method returns the course of an instance of the student object
            return sCourse;
        }
        
        public void setMobile(String mobile) {
            //this method sets the mobile of an instance of the student object
            this.sMobile = mobile.trim();
        }
        
        public String getMobile() {
            //this method returns the mobile of an instance of the student object
            return sMobile;
        }
        
	public String toString(int iNameLength, int iGenderLength, int iCourseLength, int iMobileLength) {
            //this method adjusts the length of name, gender, course and mobile and assigns it to a string
            return adjustLength(sName, iNameLength)+"     "+adjustLength(Character.toString(cGender), iGenderLength)+"     "+adjustLength(sCourse, iCourseLength)+"     "+adjustLength(sMobile, iMobileLength);
        }
        
        public String padSpace(String sString, int ilength){
            //add spaces behind strings so all lengths are equal
            //returns the string with the  whitespace-padded string
            while(sString.length()!=ilength)
                {
                    sString=sString+" ";
                }
            return sString;
        }
        
        public String toFileString() {
            //used for formatting the file when writing back into the file
            //returns a string that is used when writing to the file
            return sName+">>"+cGender+">>"+sCourse+">>"+sMobile+">>";
        }
        
        public String adjustLength(String sString, int iLength) {
            //this method adjusts the length of the string that is passed in
            //returns a string that has the same length as the other strings
            if(sString.length()<=iLength)
                return sString=padSpace(sString, iLength);
            //if the string's length is more than iLength, it will truncate and return the string
            else 
            {
                return sString=sString.substring(0, iLength-3)+"...";
            }
        }
    }
