package at.msol.demo.demo6.websvc;

import at.msol.demo.demo6.bean.Course;
import at.msol.demo.demo6.coursesgenerated.*;
import at.msol.demo.demo6.exception.CourseNotFoundException;
import at.msol.demo.demo6.service.CourseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CourseDetailsEndpoint {

    @Autowired
    CourseDetailsService service;

    /* 1st Mapper; originally, the steps of this Mapping-Method have been
    part of the public processCourseDetailsRequest method
    */
    /*
    private GetCourseDetailsResponse mapCourse(Course course) {
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setId(course.getId());
        courseDetails.setName(course.getName());
        courseDetails.setDescription(course.getDescription());
        response.setCourseDetails(courseDetails);
        return response;
    }
    */

    /* 2nd Mappers - response has been extracted from the mapCourse method
    *  to the mapCourseDetails method
    * */
    private CourseDetails mapCourse(Course course) {
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setId(course.getId());
        courseDetails.setName(course.getName());
        courseDetails.setDescription(course.getDescription());
        return courseDetails;
    }

    private GetCourseDetailsResponse mapCourseDetails(Course course) {
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();
        // Get details of one specific course
        response.setCourseDetails(mapCourse(course));
        return response;
    }

    // @ResponsePayload: convert back into xml
    @PayloadRoot(namespace = "http://demo6.demo.msol.at/coursesgenerated",
            localPart = "GetCourseDetailsRequest")
    @ResponsePayload
    /*
    The Request Payload - or to be more precise: payload body of a HTTP Request -
    is the data normally send by a POST or PUT Request. It's the part after the headers
    and the CRLF of a HTTP Request. (convertto Java class)
     */
    public GetCourseDetailsResponse processCourseDetailsRequest
            (@RequestPayload GetCourseDetailsRequest request) {

        GetCourseDetailsResponse response = new GetCourseDetailsResponse();
        Course course = service.findById(request.getId());
        // custom exception
        if(course == null) {
            throw new CourseNotFoundException("Invalid Course Id " + request.getId());
        }
        // details of requested course
        return mapCourseDetails(course);
    }

    private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
        for (Course course : courses) {
            CourseDetails mapCourse = mapCourse(course);
            // Get details of multiple couorses (an ArrayList)
            response.getCourseDetails().add(mapCourse);
        }
        return response;
    }

    @PayloadRoot(namespace = "http://demo6.demo.msol.at/coursesgenerated",
            localPart = "GetAllCourseDetailsRequest")
    @ResponsePayload
    public GetAllCourseDetailsResponse processAllCourseDetailsRequest
            (@RequestPayload GetAllCourseDetailsRequest request) {

        List<Course> courses = service.findAll();
        // details of all courses
        return mapAllCourseDetails(courses);
    }

    @PayloadRoot(namespace = "http://demo6.demo.msol.at/coursesgenerated",
            localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteCourseDetailsResponse deleteCourseDetailsRequest
            (@RequestPayload DeleteCourseDetailsRequest request) {
        // Status in CourseDetailsService vs Status generated
        CourseDetailsService.Status status = service.deleteById(request.getId());
        DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
        // sets Status in the generated Class, mapStatus for necessary typecast
        response.setStatus(mapStatus(status));
        return response;
    }

    private at.msol.demo.demo6.coursesgenerated.Status mapStatus(CourseDetailsService.Status status) {
        if (status == CourseDetailsService.Status.FAILURE) {
            return at.msol.demo.demo6.coursesgenerated.Status.FAILURE;
        }
        return at.msol.demo.demo6.coursesgenerated.Status.SUCCESS;
    }
}
