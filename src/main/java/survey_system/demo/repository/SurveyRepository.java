package survey_system.demo.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import survey_system.demo.domain.Survey;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SurveyRepository {

    private final EntityManager em;

    public void save(Survey survey) {em.persist(survey);}

    public Survey findOne(Long id) {return em.find(Survey.class, id);}

    public List<Survey> findAll() {return em.createQuery("select s from Survey s", Survey.class).getResultList();}

    public void delete(Survey survey) {
        em.remove(survey);
    }
}
