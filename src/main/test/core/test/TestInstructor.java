package core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

public class TestInstructor
{
    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }
    
    @Test
    public void testAddHomework1() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
    	assertTrue(this.instructor.homeworkExists("Test", 2017, "HW1"));
    } // success
    
    @Test
    public void testAddHomework2() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor1", "Test", 2017, "HW1");
    	assertFalse(this.instructor.homeworkExists("Test", 2017, "HW1"));
    } // fail, instructor not assigned to course
    
    @Test
    public void testAddHomework3() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test1", 2017, "HW1");
    	assertFalse(this.instructor.homeworkExists("Test", 2017, "HW1"));
    } // fail, course not created
    
    @Test
    public void testAddHomework4() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test", 2018, "HW1");
    	assertFalse(this.instructor.homeworkExists("Test", 2017, "HW1"));
    } // fail, course in wrong year
    
    @Test
    public void testAssignGrade1() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
    	// Should students be registered??
    	this.student.registerForClass("Student", "Test", 2017);
    	this.student.submitHomework("Student", "HW1", "Answer", "Test", 2017);
    	this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student", 100);
    	assertEquals(new Integer(100), this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    } // success
    
    @Test
    public void testAssignGrade2() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
    	this.student.registerForClass("Student", "Test", 2017);
    	this.student.submitHomework("Student", "HW1", "Answer", "Test", 2017);
    	this.instructor.assignGrade("Instructor2", "Test", 2017, "HW1", "Student", 100);
    	assertNotEquals(new Integer(100), this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    } // fail, instructor not assigned to class
    
    @Test
    public void testAssignGrade3() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.student.submitHomework("Student", "HW1", "Answer", "Test", 2017);
    	this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student", 100);
    	assertNotEquals(new Integer(100), this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    } // fail, homework not added
    
    @Test
    public void testAssignGrade4() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
    	this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student", 100);
    	assertNotEquals(new Integer(100), this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    } // fail, homework not submitted
    
    @Test
    public void testAssignGrade5() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
    	this.student.submitHomework("Student", "HW1", "Answer", "Test", 2017);
    	this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student", 100);
    	assertNotEquals(new Integer(100), this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    } // fail, student not registered for class
}
