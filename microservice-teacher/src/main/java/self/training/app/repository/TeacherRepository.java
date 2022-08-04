package self.training.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import self.training.app.model.TeacherDBO;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherDBO, Integer> {

}
