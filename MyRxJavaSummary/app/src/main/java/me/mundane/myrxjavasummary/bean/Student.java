package me.mundane.myrxjavasummary.bean;

import java.util.List;

/**
 * Created by mundane on 2017/11/27 下午1:54
 */

public class Student {
	public static class Course {
		public String courseName;
		
		public Course(String courseName) {
			this.courseName = courseName;
		}
	}
	
	private List<Course> mCourseList;
	private String mStudentName;
	
	public String getStudentName() {
		return mStudentName;
	}
	
	public void setStudentName(String studentName) {
		mStudentName = studentName;
	}
	
	public List<Course> getCourses() {
		return mCourseList;
	}
	
	public void setCourses(List<Course> courses) {
		mCourseList = courses;
	}
}
