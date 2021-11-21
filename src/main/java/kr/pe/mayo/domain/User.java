package kr.pe.mayo.domain;

import kr.pe.mayo.domain.dto.Role;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userIdx;
    private String username;  // 사용자 고유번호 (로그인할때 이걸로 판별)
    private String provider;  // 구글이냐? 카카오톡이냐?
    private String providerId;  // 구글에서 넘겨준 사용자 고유번호=id (sub 라고 표시되는듯)
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @CreationTimestamp
    private Timestamp createdAt;
    private String phone;
    private String birth;
    private String school;


    @Builder
    public User(String username, String name, String email, Role role, String provider, String providerId, Timestamp createdAt) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createdAt = createdAt;

    }
}
