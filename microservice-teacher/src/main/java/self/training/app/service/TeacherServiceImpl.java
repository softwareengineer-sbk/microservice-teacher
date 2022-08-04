package self.training.app.service;

import org.springframework.stereotype.Service;
import self.training.app.dto.Teacher;
import self.training.app.model.TeacherDBO;
import self.training.app.repository.TeacherRepository;

import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService{

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository){
        this.teacherRepository = teacherRepository;
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
}
