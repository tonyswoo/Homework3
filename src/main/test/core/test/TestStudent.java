package core.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

public class TestStudent
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
    public void testRegisterForClass1() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.student.registerForClass("Student", "Test", 2017);
    	assertTrue(this.student.isRegisteredFor("Student", "Test", 2017));
    }
    
    @Test
    public void testRegisterForClass2() {
    	this.student.registerForClass("Student", "Test", 2017);
    	assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
    }
    
    @Test
    public void testRegisterForClass3() {
    	this.admin.createClass("Test", 2017, "Instructor", 5);
    	for(int i = 0; i < 5; i++)
    		this.student.registerForClass("S" + i, "Test", 2017);
    	this.student.registerForClass("Student", "Test", 2017);
    	assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
    }
    
    @Test
    public void testDropClass1() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.student.registerForClass("Student", "Test", 2017);
    	this.student.dropClass("Student", "Test", 2017);
    	assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
    }
    
    @Test
    public void testDropClass2() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.student.dropClass("Student", "Test", 2017);
    	assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
    }
    
    @Test
    public void testSubmitHomework1() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
    	this.student.registerForClass("Student", "Test", 2017);
    	this.student.submitHomework("Student", "HW1", "Answer", "Test", 2017);
    	assertTrue(this.student.hasSubmitted("Student", "HW1", "Test", 2017));
    }
    
    @Test
    public void testSubmitHomework2() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.student.registerForClass("Student", "Test", 2017);
    	this.student.submitHomework("Student", "HW1", "Answer", "Test", 2017);
    	assertFalse(this.student.hasSubmitted("Student", "HW1", "Test", 2017));
    }
    
    @Test
    public void testSubmitHomework3() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
    	this.student.submitHomework("Student", "HW1", "Answer", "Test", 2017);
    	assertFalse(this.student.hasSubmitted("Student", "HW1", "Test", 2017));
    }
    
    @Test
    public void testSubmitHomework4() {
    	this.admin.createClass("Test", 2018, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test", 2018, "HW1");
    	this.student.registerForClass("Student", "Test", 2018);
    	this.student.submitHomework("Student", "HW1", "Answer", "Test", 2018);
    	assertFalse(this.student.hasSubmitted("Student", "HW1", "Test", 2018));
    }
}
