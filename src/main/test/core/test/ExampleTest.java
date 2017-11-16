package core.test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class ExampleTest {

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
    public void testMakeClass1() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    } // success

    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    } // fail, year in past
    
    @Test
    public void testMakeClass3() {
    	this.admin.createClass("Test", 2017, "Instructor", -1);
    	assertFalse(this.admin.classExists("Test", 2017));
    } // fail, negative capacity
    
    @Test
    public void testMakeClass4() {
    	this.admin.createClass("Test", 2017, "Instructor", 0);
    	assertFalse(this.admin.classExists("Test", 2017));
    } // fail, zero capacity
    
    @Test
    public void testMakeClass5() {
    	this.admin.createClass("Test", 2017, "Instructor", 5);
    	this.admin.createClass("Test", 2017, "Instructor2", 3);
    	assertEquals(5, this.admin.getClassCapacity("Test", 2017));
    	assertTrue(this.admin.getClassInstructor("Test", 2017).equals("Instructor"));
    } // fail, duplicate course/year pair
    
    @Test
    public void testMakeClass6() {
    	this.admin.createClass("Test", 2017, "Instructor", 5);
    	this.admin.createClass("Test2", 2017, "Instructor", 3);
    	assertTrue(this.admin.classExists("Test2", 2017));
    } // success
    
    @Test
    public void testMakeClass7() {
    	this.admin.createClass("Test", 2017, "Instructor", 5);
    	this.admin.createClass("Test2", 2017, "Instructor", 3);
    	this.admin.createClass("Test3", 2017, "Instructor", 3);
    	assertFalse(this.admin.classExists("Test3", 2017));
    } // fail, more than one course for instructor
    
    @Test
    public void testChangeCapacity1() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.admin.changeCapacity("Test", 2017, 3);
    	assertEquals(3, this.admin.getClassCapacity("Test", 2017));
    } // success
    
    @Test
    public void testChangeCapacity2() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.admin.changeCapacity("Test", 2017, 0);
    	assertEquals(10, this.admin.getClassCapacity("Test", 2017));
    } // fail, change to size 0
    
    @Test
    public void testChangeCapacity3() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.admin.changeCapacity("Test", 2017, -1);
    	assertEquals(10, this.admin.getClassCapacity("Test", 2017));
    } // fail, change to negative size
    
    @Test
    public void testChangeCapacity4() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	for(int i = 0; i < 5; i++)
    		this.student.registerForClass("S" + i, "Test", 2017);
    	this.admin.changeCapacity("Test", 2017, 5);
    	assertEquals(5, this.admin.getClassCapacity("Test", 2017));
    } // success
    
    @Test
    public void testChangeCapacity5() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	for(int i = 0; i < 5; i++)
    		this.student.registerForClass("S" + i, "Test", 2017);
    	this.admin.changeCapacity("Test", 2017, 3);
    	assertEquals(10, this.admin.getClassCapacity("Test", 2017));
    } // fail, change to size less than enrolled
    
    @Test
    public void testChangeCapacity6() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.admin.changeCapacity("Test", 2017, 12);
    	assertEquals(12, this.admin.getClassCapacity("Test", 2017));
    } // success
    
    @Test
    public void testAddHomework1() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
    	assertTrue(this.instructor.homeworkExists("Test", 2017, "HW1"));
    }
    
    @Test
    public void testAddHomework2() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor1", "Test", 2017, "HW1");
    	assertFalse(this.instructor.homeworkExists("Test", 2017, "HW1"));
    }
    
    @Test
    public void testAddHomework3() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test1", 2017, "HW1");
    	assertFalse(this.instructor.homeworkExists("Test", 2017, "HW1"));
    }
    
    @Test
    public void testAddHomework4() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test", 2018, "HW1");
    	assertFalse(this.instructor.homeworkExists("Test", 2017, "HW1"));
    }
    
    @Test
    public void testAssignGrade1() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
    	// Should students be registered??
    	this.student.registerForClass("Student", "Test", 2017);
    	this.student.submitHomework("Student", "HW1", "Answer", "Test", 2017);
    	this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student", 100);
    	assertEquals(new Integer(100), this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    }
    
    @Test
    public void testAssignGrade2() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
    	this.student.submitHomework("Student", "HW1", "Answer", "Test", 2017);
    	this.instructor.assignGrade("Instructor2", "Test", 2017, "HW1", "Student", 100);
    	assertNotEquals(new Integer(100), this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    }
    
    @Test
    public void testAssignGrade3() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.student.submitHomework("Student", "HW1", "Answer", "Test", 2017);
    	this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student", 100);
    	assertNotEquals(new Integer(100), this.instructor.getGrade("Test", 2017, "HW1", "Student"));
    }
    
    @Test
    public void testAssignGrade4() {
    	this.admin.createClass("Test", 2017, "Instructor", 10);
    	this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
    	this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student", 100);
    	assertNotEquals(new Integer(100), this.instructor.getGrade("Test", 2017, "HW1", "Student"));
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
