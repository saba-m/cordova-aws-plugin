package com.babyscripts.awsplugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dkopel on 7/13/17.
 */
public class BloodPressurePublisher {
    private String accessToken;
    private Integer systolic;
    private Integer diastolic;

    public BloodPressurePublisher(String accessToken, Integer systolic, Integer diastolic) {
        this.accessToken = accessToken;
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Integer getSystolic() {
        return systolic;
    }

    public Integer getDiastolic() {
        return diastolic;
    }
}
