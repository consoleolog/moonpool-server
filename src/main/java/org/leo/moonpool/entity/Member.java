package org.leo.moonpool.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 100, unique = true , nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 30)
    private String displayName;

    @Column(length = 100)
    private String intro;

    @Column(length = 30, nullable = false)
    private String educationState;

    @Builder.Default
    private Integer coin = 1000;

    @ToString.Exclude
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<MemberRole> memberRoleList = new ArrayList<>();

    public void changeUsername(String username) {
        this.username = username;
    }
    public String changePassword() {
        return password;
    }
    public void changePassword(String password) {
        this.password = password;
    }
    public void changeDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public void changeIntro(String intro) {
        this.intro = intro;
    }
    public void changeEducationState(String educationState) {
        this.educationState = educationState;
    }
    public void changeCoin(Integer coin) {
        this.coin = coin;
    }
}
