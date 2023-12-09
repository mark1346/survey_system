package survey_system.demo.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import survey_system.demo.domain.Choice;
import survey_system.demo.domain.Option;
import survey_system.demo.domain.User;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        if (user.getId() == null) {
            em.persist(user); // 신규 등록
        } else {
            em.merge(user); // update 비슷한 것
        }
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
            .getResultList();
    }

    public User findByName(String name) {
        return em.createQuery("select u from User u where u.username = :name", User.class)
            .setParameter("name", name)
            .getSingleResult();
    }

    public void delete(User user) {
        em.remove(user);
    }

    public int totalScoreByUserId(Long userId) { // 총 점수
        User user = em.find(User.class, userId);
        int totalScore = 0;
        for (Choice choice : user.getChoices()) {
            Option option = choice.getOption();
            totalScore += option.getScore();
        }
        return totalScore;
//        Long totalScore =  em.createQuery("select SUM(c.option.score) from User u" +
//                        " JOIN u.choices c " +
//                        "where u.id = :userId", Long.class)
//            .setParameter("userId", userId)
//            .getSingleResult();
//        System.out.println("totalScore = " + totalScore);
//        return totalScore != null ? totalScore.intValue() : 0;
    }

}
