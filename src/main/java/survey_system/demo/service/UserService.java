package survey_system.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import survey_system.demo.domain.User;
import survey_system.demo.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public int totalScore(Long userId) { // 총 점수
        return userRepository.totalScoreByUserId(userId);
    }

    @Transactional
    public Long save(User user) { // 회원 가입
        validateDuplicateUser(user); // 중복 회원 검증
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        // EXCEPTION
        if (userRepository.findByName(user.getUsername()) != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 유저 전체 조회
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    // 유저 한 명 조회
    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

    //유저 업데이트
    @Transactional
    public void update(Long id, String name, String password) {
        User user = userRepository.findOne(id);
        user.setUsername(name);
        user.setPassword(password);
    }

    // 유저 이름 업데이트
    @Transactional
    public void updateName(Long id, String name) {
        User user = userRepository.findOne(id);
        user.setUsername(name);
    }

    // 유저 삭제
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }
}
