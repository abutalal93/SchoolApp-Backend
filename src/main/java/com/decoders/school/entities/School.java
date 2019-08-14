package com.decoders.school.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "SCHOOL")
public class School implements Serializable {

    @Id
    @SequenceGenerator(name = "SchoolSeq", sequenceName = "SCHOOL_SEQ"
            , initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SchoolSeq", strategy = GenerationType.AUTO)
    @Column(name = "scl_id")
    private Long id;

    @Column(name = "scl_name")
    private String name;

    @Column(name = "scl_code", nullable = false , unique = true)
    private String code;

    @Column(name = "scl_create_date")
    private LocalDateTime createDate;

    @Column(name = "scl_expire_date")
    private LocalDateTime expireDate;

    @Column(name = "scl_username")
    private String username;

    @Column(name = "scl_password")
    private String password;

    @Column(name = "scl_image")
    private String image;

    @Column(name = "scl_email")
    private String email;

    @Column(name = "scl_mobile")
    private String mobile;

    @Column(name = "scl_lng")
    private String longitude;

    @Column(name = "scl_lat")
    private String latitude;

    @Column(name = "scl_facebook_url")
    private String facebookUrl;

    @Column(name = "scl_twitter_url")
    private String twitterUrl;

    @Column(name = "scl_chairman_message", columnDefinition = "LONGTEXT")
    private String chairmanMessage;

    @OneToOne
    @JoinColumn(name = "scl_sts_id", nullable = false)
    private Status status;

    @Column(name = "scl_about_text" , columnDefinition = "LONGTEXT")
    private String aboutText;

    @Column(name = "scl_about_image")
    private String aboutImage;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getChairmanMessage() {
        return chairmanMessage;
    }

    public void setChairmanMessage(String chairmanMessage) {
        this.chairmanMessage = chairmanMessage;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAboutText() {
        return aboutText;
    }

    public void setAboutText(String aboutText) {
        this.aboutText = aboutText;
    }

    public String getAboutImage() {
        return aboutImage;
    }

    public void setAboutImage(String aboutImage) {
        this.aboutImage = aboutImage;
    }
}
