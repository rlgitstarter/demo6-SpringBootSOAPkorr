package at.msol.demo.demo6.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CUSTOM,
        customFaultCode = "{http://demo6.demo.msol.at/coursesgenerated}001_COURSE_NOT_FOUND")
public class CourseNotFoundException extends RuntimeException {

    public static final long serialVersionUID = 1L;

    public CourseNotFoundException(String message) {
        super(message);
    }
}
