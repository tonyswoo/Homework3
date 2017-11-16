package core.api.impl;

import core.api.ICourseManager;

public class CourseManager implements ICourseManager {

	private Admin admin;
	
	public CourseManager(Admin admin) {
		DataManager.reset();
		this.admin = admin;
	}

	public void createClass(String name, int year, String instructor, int capacity) {
		if (year > 2017) return;
		if (capacity > 1000) return;
		this.admin.createClass(name, year, instructor, capacity);
	}

	public boolean classExists(String name, int year) {
		return this.admin.classExists(name, year);
	}
	
    public String getClassInstructor(String className, int year) {
		return this.admin.getClassInstructor(className, year);
	}

    public int getClassCapacity(String className, int year) {
        return this.admin.getClassCapacity(className, year);
    }
}
