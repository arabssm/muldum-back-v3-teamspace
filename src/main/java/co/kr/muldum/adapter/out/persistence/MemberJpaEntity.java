package co.kr.muldum.adapter.out.persistence;

import co.kr.muldum.domain.model.MemberRole;
import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class MemberJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamJpaEntity team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private MemberRole role;

    protected MemberJpaEntity() {
    }

    public MemberJpaEntity(Long memberId, TeamJpaEntity team, UserJpaEntity user, MemberRole role) {
        this.memberId = memberId;
        this.team = team;
        this.user = user;
        this.role = role;
    }

    public Long getMemberId() {
        return memberId;
    }

    public TeamJpaEntity getTeam() {
        return team;
    }

    public UserJpaEntity getUser() {
        return user;
    }

    public MemberRole getRole() {
        return role;
    }
}
