package survey_system.demo.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import survey_system.demo.domain.Option;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OptionRepository {

    private final EntityManager em;

    public void save(Option option) {
        if (option.getId() == null) {
            em.persist(option); // 신규 등록
        } else {
            em.merge(option); // update 비슷한 것
        }
    }

    public Option findOne(Long id) {
        return em.find(Option.class, id);
    }

    public List<Option> findAll() {
        return em.createQuery("select o from Option o", Option.class)
            .getResultList();
    }

    public void delete(Option option) {
        em.remove(option);
    }
}
