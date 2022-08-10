package self.training.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import self.training.app.dto.*;
import self.training.app.model.TeacherDBO;
import self.training.app.repository.TeacherRepository;
import self.training.app.service.feignclients.PaymentFeignClient;
import self.training.app.service.feignclients.StudentFeignClient;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService{

    private final TeacherRepository teacherRepository;
    private final StudentFeignClient studentFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    public TeacherServiceImpl(TeacherRepository teacherRepository, StudentFeignClient studentFeignClient, PaymentFeignClient paymentFeignClient){
        this.teacherRepository = teacherRepository;
        this.studentFeignClient = studentFeignClient;
        this.paymentFeignClient = paymentFeignClient;
    }


    @Override
    public Teacher getById(int id) {
        Optional<TeacherDBO> teacherDBO = teacherRepository.findById(id);
        Teacher teacher = new Teacher();
        if (teacherDBO.isPresent()){
            teacher.setName(teacherDBO.get().getName());
            teacher.setSubject(teacherDBO.get().getSubject());
        }
        return teacher;
    }

    @Override
    public TeacherWithStudents getTeacherWithStudents(int id) {
        log.info("Service method: getTeacherWithStudents");
        Optional<TeacherDBO> teacherDBO = teacherRepository.findById(id);
        TeacherWithStudents teacherWithStudents = new TeacherWithStudents();
        if (teacherDBO.isPresent()){
            List<Student> students = studentFeignClient.getStudentsWithTeacherId(id);
            teacherWithStudents.setStudents(students);
            teacherWithStudents.setName(teacherDBO.get().getName());
            teacherWithStudents.setSubject(teacherDBO.get().getSubject());
        }
        return teacherWithStudents;
    }

    @Override
    public TeacherWithPayments getTeacherWithPayments(int id) {
        log.info("Service method: getTeacherWithPayments");
        Optional<TeacherDBO> teacherDBO = teacherRepository.findById(id);
        TeacherWithPayments teacherWithPayments = new TeacherWithPayments();
        if (teacherDBO.isPresent()){
            Payment payment = paymentFeignClient.getPaymentsWithTeacherId(id);
            teacherWithPayments.setPayment(payment);
            teacherWithPayments.setName(teacherDBO.get().getName());
            teacherWithPayments.setSubject(teacherDBO.get().getSubject());
        }
        return teacherWithPayments;
    }
}
