package self.training.app.service.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import self.training.app.dto.Student;

import java.util.List;


@FeignClient("microservice-student")
public interface StudentFeignClient {

    @PostMapping("/student")
    List<Student> getStudentsWithTeacherId(@RequestBody int teacherId);
}
