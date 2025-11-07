package co.kr.muldum.application.service;

import co.kr.muldum.application.port.in.GoogleLoginUseCase;
import co.kr.muldum.application.port.in.command.GoogleLoginCommand;
import co.kr.muldum.application.port.in.response.LoginResponse;
import co.kr.muldum.application.port.out.GoogleOAuthPort;
import co.kr.muldum.application.port.out.JwtPort;
import co.kr.muldum.application.port.out.LoadUserPort;
import co.kr.muldum.domain.constants.ErrorMessages;
import co.kr.muldum.domain.exception.UnregisteredUserException;
import co.kr.muldum.domain.model.Student;
import co.kr.muldum.domain.model.Teacher;
import co.kr.muldum.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GoogleLoginService implements GoogleLoginUseCase {

    private final GoogleOAuthPort googleOAuthPort;
    private final LoadUserPort loadUserPort;
    private final JwtPort jwtPort;

    public GoogleLoginService(GoogleOAuthPort googleOAuthPort, LoadUserPort loadUserPort, JwtPort jwtPort) {
        this.googleOAuthPort = googleOAuthPort;
        this.loadUserPort = loadUserPort;
        this.jwtPort = jwtPort;
    }

    @Override
    public LoginResponse login(GoogleLoginCommand command) {

        String email = googleOAuthPort.getEmailFromAuthCode(command.getAuthorizationCode());

        User user = loadUserPort.findByEmail(email)
                .orElseThrow(() -> new UnregisteredUserException(email));

        String accessToken = jwtPort.generateAccessToken(
                user.getUserId(),
                user.getEmail(),
                user.getRole().getValue()
        );

        if (user instanceof Student student) {
            return LoginResponse.ofStudent(
                    student.getUserId(),
                    student.getName(),
                    student.getTeamId(),
                    accessToken
            );
        } else if (user instanceof Teacher teacher) {
            return LoginResponse.ofTeacher(
                    teacher.getUserId(),
                    teacher.getName(),
                    accessToken
            );
        }

        throw new IllegalStateException(String.format(ErrorMessages.UNKNOWN_USER_TYPE, user.getClass().getName()));
    }
}
