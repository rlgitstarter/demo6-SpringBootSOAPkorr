package at.msol.demo.demo6.service;

import at.msol.demo.demo6.bean.Course;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CourseDetailsService {

    public enum Status {
        SUCCESS, FAILURE;
    }

    private static List<Course> courses = new ArrayList<>();

    static {
        Course course1 = new Course(1, "Spring", "10 Steps");
        courses.add(course1);

        Course course2 = new Course(2, "Spring MVC", "10 Examples");
        courses.add(course2);

        Course course3 = new Course(3, "Spring Boot", "6K Students");
        courses.add(course3);

        Course course4 = new Course(4, "Maven", "Most popular maven course on internet!");
        courses.add(course4);
    }

    // findbyId
    public Course findById(int id) {
        for(Course course: courses) {
            if(course.getId() == id) {
                return course;
            }
        }
        return null;
    }

    // all courses
    public List<Course> findAll() {
        return courses;
    }

    // updateing course & new course


    // delete course
    public Status deleteById(int id) {
        Iterator<Course> iterator = courses.iterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            if(course.getId() == id) {
                iterator.remove();
                return Status.SUCCESS;
            }
        }
        return Status.FAILURE;
    }
}
