package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.Mockito;
import org.mockito.Spy;

import core.api.ICourseManager;
import core.api.impl.Admin;
import core.api.impl.CourseManager;

/**
 * Tests course manager. Since the Admin implementation is known to be bugging. 
 * 
 * @author Vincent
 *
 */
public class TestCourseManager {

	@Spy
	private Admin admin;
	private ICourseManager courseManager;
	
	@Before
	public void setup() {
		this.admin = Mockito.spy(new Admin());
		this.courseManager = new CourseManager(this.admin);
		setupMocking();
	}

	/*
	 * Shows some initial set-up for the mocking of Admin.
	 * This includes fixing a known bug (year in past is not correctly checked) in the Admin class by Mocking its behavior.
	 * Not all fixes to Admin can be made from here, so for the more complex constraints you can simply Mock the
	 * specific calls to Admin's createClass to yield the correct behavior in the unit test itself.
	 */
	public void setupMocking() {
		Mockito.doNothing().when(this.admin).createClass(Mockito.anyString(), AdditionalMatchers.lt(2017), Mockito.anyString(), Mockito.anyInt());
		Mockito.doNothing().when(this.admin).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), AdditionalMatchers.leq(0));
		
	}

	@Test
	public void testCreateClassCorrect() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", 1);
		assertTrue(this.courseManager.classExists("ECS161", 2017));
	}
	
	@Test
	public void testCreateClassInPast() {
		this.courseManager.createClass("ECS161", 2016, "Instructor", 1);
		assertFalse(this.courseManager.classExists("ECS161", 2016));
	}

	@Test
	public void testCreateClassInFuture() {
		this.courseManager.createClass("ECS161", 2018, "Instructor", 1);
		Mockito.verify(this.admin, Mockito.never()).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
	}
	
	@Test
	public void testCreateClassWith0() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", 0);
		assertFalse(this.courseManager.classExists("ECS161", 2017));
	}
	
	@Test
	public void testCreateClassWithNegative() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", -1);
		assertFalse(this.courseManager.classExists("ECS161", 2017));
	}
	
	@Test
	public void testCreateClassWith1000() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", 1000);
		Mockito.verify(this.admin, Mockito.never()).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
	}
	
	@Test
	public void testCreateClassWithMoreThan1000() {
		this.courseManager.createClass("ECS161", 2017, "Instructor", 1001);
		Mockito.verify(this.admin, Mockito.never()).createClass(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
	}
	
	@Test
	public void testCreateClassWithInstructor2() {
		Mockito.doCallRealMethod().doCallRealMethod().doNothing().when(this.admin).createClass(Mockito.anyString(), AdditionalMatchers.geq(2017), Mockito.eq("Instructor"), AdditionalMatchers.gt(0));
		this.courseManager.createClass("ECS161", 2017, "Instructor", 1);
		this.courseManager.createClass("ECS160", 2017, "Instructor", 2);
		assertTrue(this.courseManager.classExists("ECS160", 2017));
	}
	
	@Test
	public void testCreateClassWithInstructorMoreThan2() {
		Mockito.doCallRealMethod().doCallRealMethod().doNothing().when(this.admin).createClass(Mockito.anyString(), AdditionalMatchers.geq(2017), Mockito.eq("Instructor"), AdditionalMatchers.gt(0));
		this.courseManager.createClass("ECS161", 2017, "Instructor", 1);
		this.courseManager.createClass("ECS160", 2017, "Instructor", 2);
		this.courseManager.createClass("ECS162", 2017, "Instructor", 2);
		assertFalse(this.courseManager.classExists("ECS162", 2017));
	}
	
	@Test
	public void testCreateClassWithNonUniqueClass() {
		Mockito.doCallRealMethod().doNothing().when(this.admin).createClass(Mockito.anyString(), AdditionalMatchers.geq(2017), Mockito.eq("Instructor"), AdditionalMatchers.gt(0));
		this.courseManager.createClass("ECS161", 2017, "Instructor", 1);
		this.courseManager.createClass("ECS161", 2017, "Instructor", 2);
		assertEquals(1, this.courseManager.getClassCapacity("ECS161", 2017));
	}
	
	
}
