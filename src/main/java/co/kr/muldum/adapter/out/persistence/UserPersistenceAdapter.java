package co.kr.muldum.adapter.out.persistence;

import co.kr.muldum.application.port.out.LoadUserPort;
import co.kr.muldum.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPersistenceAdapter implements LoadUserPort {

    private final UserJpaRepository userJpaRepository;

    public UserPersistenceAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByGradeAndClassAndStudentNo(int grade, int classNo, int studentNo) {
        return userJpaRepository.findByGradeAndClassNoAndStudentNo(grade, classNo, studentNo)
                .map(UserMapper::toDomain);
    }
}
