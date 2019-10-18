package com.decoders.school.resource;

import com.decoders.school.entities.MobileDevice;
import com.decoders.school.entities.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MobileDeviceResource {

    private Long id;

    private String platform;

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

    public static MobileDeviceResource toResource(MobileDevice mobileDevice) {
        MobileDeviceResource mobileDeviceResource = new MobileDeviceResource();

        mobileDeviceResource.setId(mobileDevice.getId());
        mobileDeviceResource.setPlatform(mobileDevice.getPlatform());
        mobileDeviceResource.setToken(mobileDevice.getToken());

        return mobileDeviceResource;
    }

    public static List<MobileDeviceResource> toResource(List<MobileDevice> mobileDeviceList) {
        List<MobileDeviceResource> mobileDeviceResourceList = new ArrayList<>();
        mobileDeviceList.forEach(mobileDevice -> {
            MobileDeviceResource mobileDeviceResource = toResource(mobileDevice);
            mobileDeviceResourceList.add(mobileDeviceResource);
        });
        return mobileDeviceResourceList;
    }

    public MobileDevice toMobileDevice() {
        MobileDevice mobileDevice = new MobileDevice();

        mobileDevice.setId(this.id);
        mobileDevice.setPlatform(this.platform);
        mobileDevice.setToken(this.token);

        return mobileDevice;
    }

}
