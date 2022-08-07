package self.training.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.training.app.configuration.ServerConfiguration;
import self.training.app.dto.Properties;
import self.training.app.dto.Teacher;
import self.training.app.service.TeacherService;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;
    private final ServerConfiguration serverConfiguration;

    public TeacherController(TeacherService teacherService, ServerConfiguration serverConfiguration){
        this.teacherService = teacherService;
        this.serverConfiguration = serverConfiguration;
    }

    @GetMapping("/{id}")
    public Teacher getById(@PathVariable int id){
        return teacherService.getById(id);
    }

    @GetMapping("/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(serverConfiguration.getMsg(), serverConfiguration.getBuildVersion(), serverConfiguration.getMailDetails());
        return ow.writeValueAsString(properties);
    }
}
