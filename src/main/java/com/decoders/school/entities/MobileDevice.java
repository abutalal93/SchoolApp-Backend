package com.decoders.school.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "MOBILE_DEVICE")
public class MobileDevice implements Serializable {

    @Id
    @SequenceGenerator(name = "MobileDeviceSeq", sequenceName = "MOBILE_DEVICE_SEQ"
            , initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "MobileDeviceSeq", strategy = GenerationType.AUTO)
    @Column(name = "mob_dev_id")
    private Long id;

    @Column(name = "mob_dev_platform")
    private String platform;

    @Column(name = "mob_dev_token")
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
