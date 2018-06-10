package at.msol.demo.demo6.websvc;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import javax.security.auth.callback.CallbackHandler;
import java.util.Collections;
import java.util.List;

// Spring Configuration
@Configuration
// Enable WS
@EnableWs
// Extension WsConfigurerAdapter 4 Spring WS Security
public class WebServiceConfig extends WsConfigurerAdapter {

    // MessageDispatcherServlet
    // ApplicationContext
    // url -> /ws/*
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
        // create MessageDispatcherServlet-Instance
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(context);
        messageDispatcherServlet.setTransformWsdlLocations(true); // later
        return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
    }

    @Bean(name="courses")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("CoursePort");
        definition.setTargetNamespace("http://demo6.demo.msol.at/coursesgenerated");
        // URL defined on he schema course-details.xsd
        definition.setLocationUri("/ws");
        definition.setSchema(coursesSchema);
        return definition;
    }

    // /ws/courses.wsdl (based on the schema course-details.xsd)
    @Bean
    public XsdSchema coursesSchema() {
        return new SimpleXsdSchema (new ClassPathResource("course-details.xsd"));
    }

    // XwsSecurityInterceptor
    /*
    @Bean
    public XwsSecurityInterceptor securityInterceptor() {
        XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
        securityInterceptor.setCallbackHandler(callbackHandler());
        securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
        return securityInterceptor;
    }
    */

    /*
    @Bean
    private CallbackHandler callbackHandler() {
        return null;
    }
    */

    /*
    @Bean
    public SimplePasswordValidationCallbackHandler callbackHandler() {
        SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
        handler.setUsersMap(Collections.singletonMap("user1", "pw1"));
        return handler;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(securityInterceptor());
    }
    */

}
