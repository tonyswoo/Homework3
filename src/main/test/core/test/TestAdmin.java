package core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Student;

public class TestAdmin
{
	private IAdmin admin;
	private IStudent student;
	
	@Before
	public void setup() {
	    this.admin = new Admin();
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
}
