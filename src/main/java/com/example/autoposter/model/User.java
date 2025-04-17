package com.example.autoposter.model;

import com.example.autoposter.dto.response.LoginResponse;
import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import java.time.LocalDateTime;


@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column()
    private String password;

    @Column(nullable = false)
    private String profilePic;

    @Column(length = 20)
    private String linkedin_urn;

    @Column(length = 1000)
    private String linkedinAccessToken;

    @Column(length = 1000)
    private String linkedinRefreshToken;

    private LocalDateTime linkedinAccessTokenExpiresAt;

    private LocalDateTime linkedinRefreshTokenExpiresAt;

    public User() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getLinkedin_urn() {return linkedin_urn;}

    public void setLinkedin_urn(String linkedin_urn) {this.linkedin_urn = linkedin_urn;}

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getLinkedinAccessToken() {
        return linkedinAccessToken;
    }

    public void setLinkedinAccessToken(String linkedinAccessToken) {
        this.linkedinAccessToken = linkedinAccessToken;
    }

    public String getLinkedinRefreshToken() {
        return linkedinRefreshToken;
    }

    public void setLinkedinRefreshToken(String linkedinRefreshToken) {
        this.linkedinRefreshToken = linkedinRefreshToken;
    }

    public LocalDateTime getLinkedinAccessTokenExpiresAt() {
        return linkedinAccessTokenExpiresAt;
    }

    public void setLinkedinAccessTokenExpiresAt(LocalDateTime linkedinAccessTokenExpiresAt) {
        this.linkedinAccessTokenExpiresAt = linkedinAccessTokenExpiresAt;
    }

    public LocalDateTime getLinkedinRefreshTokenExpiresAt() {
        return linkedinRefreshTokenExpiresAt;
    }

    public void setLinkedinRefreshTokenExpiresAt(LocalDateTime linkedinRefreshTokenExpiresAt) {
        this.linkedinRefreshTokenExpiresAt = linkedinRefreshTokenExpiresAt;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", linkedinAccessToken='" + linkedinAccessToken + '\'' +
                ", linkedinRefreshToken='" + linkedinRefreshToken + '\'' +
                ", linkedinAccessTokenExpiresAt=" + linkedinAccessTokenExpiresAt +
                ", linkedinRefreshTokenExpiresAt=" + linkedinRefreshTokenExpiresAt +
                '}';
    }








    private User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.name = builder.name;
        this.password = builder.password;
        this.profilePic = builder.profilePic;
        this.linkedinAccessToken = builder.linkedinAccessToken;
        this.linkedinRefreshToken = builder.linkedinRefreshToken;
        this.linkedinAccessTokenExpiresAt = builder.linkedinAccessTokenExpiresAt;
        this.linkedinRefreshTokenExpiresAt = builder.linkedinRefreshTokenExpiresAt;
        this.linkedin_urn = builder.linkedin_urn;
    }


    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private String password;
        private String profilePic;
        private String linkedin_urn;
        private String linkedinAccessToken;
        private String linkedinRefreshToken;
        private LocalDateTime linkedinAccessTokenExpiresAt;
        private LocalDateTime linkedinRefreshTokenExpiresAt;


        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder linkedin_urn(String linkedin_urn) {
            this.linkedin_urn = linkedin_urn;
            return this;
        }

        public Builder profilePic(String profilePic) {
            this.profilePic = profilePic;
            return this;
        }

        public Builder linkedinAccessToken(String linkedinAccessToken) {
            this.linkedinAccessToken = linkedinAccessToken;
            return this;
        }

        public Builder linkedinRefreshToken(String linkedinRefreshToken) {
            this.linkedinRefreshToken = linkedinRefreshToken;
            return this;
        }

        public Builder linkedinAccessTokenExpiresAt(LocalDateTime linkedinAccessTokenExpiresAt) {
            this.linkedinAccessTokenExpiresAt = linkedinAccessTokenExpiresAt;
            return this;
        }

        public Builder linkedinRefreshTokenExpiresAt(LocalDateTime linkedinRefreshTokenExpiresAt) {
            this.linkedinRefreshTokenExpiresAt = linkedinRefreshTokenExpiresAt;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
