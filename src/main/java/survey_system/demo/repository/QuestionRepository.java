package survey_system.demo.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import survey_system.demo.domain.Question;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionRepository {

    private final EntityManager em;

    public void save(Question question) {
        if (question.getId() == null) {
            em.persist(question); // 신규 등록
        } else {
            em.merge(question); // update 비슷한 것
        }
    }

    public Question findOne(Long id) {
        return em.find(Question.class, id);
    }

    public List<Question> findAll() {
        return em.createQuery("select q from Question q", Question.class)
            .getResultList();
    }

    public void delete(Question question) {
        em.remove(question);
    }
}
