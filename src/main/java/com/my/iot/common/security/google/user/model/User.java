package com.my.iot.common.security.google.user.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.my.iot.common.security.google.user.connection.UserConnection;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    
    // Table Join
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "provider_id", referencedColumnName = "provider_id", nullable = false, updatable = false, unique = true)
    private UserConnection social;

    @Builder
    private User(String email, String nickname, UserConnection social) {
        this.email = email;
        this.nickname = nickname;
        this.social = social;
    }

    public static User signUp(UserConnection userConnection) {
        return User.builder()
                .email(userConnection.getEmail())
                .nickname(userConnection.getDisplayName())
                .social(userConnection)
                .build();
    }

    @Override
	public String toString() {
		return String.format("User [id=%s, email=%s, nickname=%s, social=%s]", id, email, nickname, social);
	}
}