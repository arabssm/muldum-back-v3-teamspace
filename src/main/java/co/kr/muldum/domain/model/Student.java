package co.kr.muldum.domain.model;

import lombok.Getter;

@Getter
public class Student extends User {

    private final Long teamId;
    private final int grade;
    private final int classNo;
    private final int studentNo;

    private Student(Long userId, String email, String name, Long teamId, int grade, int classNo, int studentNo) {
        super(userId, email, name, Role.STUDENT);
        this.teamId = teamId;
        this.grade = grade;
        this.classNo = classNo;
        this.studentNo = studentNo;
    }

    public static Student create(String email, String name, Long teamId, int grade, int classNo, int studentNo) {
        return new Student(null, email, name, teamId, grade, classNo, studentNo);
    }

    public static Student of(Long userId, String email, String name, Long teamId, int grade, int classNo, int studentNo) {
        return new Student(userId, email, name, teamId, grade, classNo, studentNo);
    }

    @Override
    public Student withUserId(Long userId) {
        return new Student(userId, this.getEmail(), this.getName(), this.teamId, this.grade, this.classNo, this.studentNo);
    }
}
