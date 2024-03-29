package self.training.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.training.app.configuration.ServerConfiguration;
import self.training.app.dto.Properties;
import self.training.app.dto.Teacher;
import self.training.app.dto.TeacherWithPayments;
import self.training.app.dto.TeacherWithStudents;
import self.training.app.service.TeacherService;

import javax.ws.rs.core.MediaType;

@Slf4j
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;
    private final ServerConfiguration serverConfiguration;

    public TeacherController(TeacherService teacherService, ServerConfiguration serverConfiguration){
        this.teacherService = teacherService;
        this.serverConfiguration = serverConfiguration;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    @Timed(value = "getById.time", description = "Time taken to return teacher object by id")
    public Teacher getById(@PathVariable int id){
        return teacherService.getById(id);
    }

    @GetMapping(value = "/withStudents/{id}", produces = MediaType.APPLICATION_JSON)
//    @CircuitBreaker(name = "getTeacherWithStudents", fallbackMethod = "getTeacherWithStudentsFallback")
//    @Retry(name = "getTeacherWithStudents", fallbackMethod = "getTeacherWithStudentsFallback")
    @RateLimiter(name = "getTeacherWithStudents", fallbackMethod = "getTeacherWithStudentsFallback")
    public TeacherWithStudents getTeacherWithStudents(@PathVariable int id){
        log.info("Controller method: getTeacherWithStudents");
        return teacherService.getTeacherWithStudents(id);
    }

    @GetMapping(value = "/withPayments/{id}", produces = MediaType.APPLICATION_JSON)
//    @CircuitBreaker(name = "getTeacherWithPayments", fallbackMethod = "getTeacherWithPaymentsFallback")
//    @Retry(name = "getTeacherWithPayments", fallbackMethod = "getTeacherWithPaymentsFallback")
    @RateLimiter(name = "getTeacherWithPayments", fallbackMethod = "getTeacherWithPaymentsFallback")
    public TeacherWithPayments getTeacherWithPayments(@PathVariable int id){
        log.info("Controller method: getTeacherWithPayments");
        return teacherService.getTeacherWithPayments(id);
    }

    @GetMapping(value = "/properties", produces = MediaType.APPLICATION_JSON)
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(serverConfiguration.getMsg(), serverConfiguration.getBuildVersion(), serverConfiguration.getMailDetails());
        return ow.writeValueAsString(properties);
    }

    private TeacherWithStudents getTeacherWithStudentsFallback(int id, Throwable throwable){
        System.out.println("This is a fallback method");
        return new TeacherWithStudents();
    }

    private TeacherWithPayments getTeacherWithPaymentsFallback(int id, Throwable throwable){
        System.out.println("This is a fallback method");
        return new TeacherWithPayments();
    }
}
