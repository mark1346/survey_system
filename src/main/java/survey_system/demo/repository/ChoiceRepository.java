package survey_system.demo.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import survey_system.demo.domain.Choice;
import survey_system.demo.domain.Option;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChoiceRepository {

    private final EntityManager em;

    public void save(Choice choice) {
        if (choice.getId() == null) {
            em.persist(choice); // 신규 등록
        } else {
            em.merge(choice); // update 비슷한 것
        }
    }

    public Choice findOne(Long id) {
        return em.find(Choice.class, id);
    }

    public List<Choice> findAll() {
        return em.createQuery("select c from Choice c", Choice.class)
            .getResultList();
    }

    public void delete(Choice choice) {
        em.remove(choice);
    }
    public void deleteAll(List<Choice> choices){
        for(Choice choice : choices){
            em.remove(choice);
        }
    }

    public List<Choice> findByOption(Option optionId){
        return em.createQuery("select c from Choice c where c.option = :option", Choice.class)
            .setParameter("option", optionId)
            .getResultList();
    }
}
