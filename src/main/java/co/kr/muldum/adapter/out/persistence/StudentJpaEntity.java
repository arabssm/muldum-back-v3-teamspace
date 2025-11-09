package co.kr.muldum.adapter.out.persistence;

import co.kr.muldum.domain.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
@DiscriminatorValue("STUDENT")
public class StudentJpaEntity extends UserJpaEntity {

    @Column(name = "team_id", columnDefinition = "uuid")
    private UUID teamId;

    @Column(name = "grade")
    private int grade;

    @Column(name = "class_no")
    private int classNo;

    @Column(name = "student_no")
    private int studentNo;

    protected StudentJpaEntity() {
    }

    public StudentJpaEntity(Long userId, String email, String name, UUID teamId, int grade, int classNo, int studentNo) {
        super(userId, email, name, Role.STUDENT);
        this.teamId = teamId;
        this.grade = grade;
        this.classNo = classNo;
        this.studentNo = studentNo;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public int getGrade() {
        return grade;
    }

    public int getClassNo() {
        return classNo;
    }

    public int getStudentNo() {
        return studentNo;
    }
}
