package self.training.app.service.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import self.training.app.dto.Payment;
import self.training.app.dto.Student;

import java.util.List;


@FeignClient("microservice-payment")
public interface PaymentFeignClient {

    @PostMapping("/payment")
    Payment getPaymentsWithTeacherId(@RequestBody int teacherId);
}
