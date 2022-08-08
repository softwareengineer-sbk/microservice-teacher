package self.training.app.service;

import self.training.app.dto.Teacher;
import self.training.app.dto.TeacherWithPayments;
import self.training.app.dto.TeacherWithStudents;

public interface TeacherService {

    Teacher getById(int id);

    TeacherWithStudents getTeacherWithStudents(int id);

    TeacherWithPayments getTeacherWithPayments(int id);
}
