package com.myappilication.xpress.finjan2017.models.login.mycamssetting;

/**
 * Created by suresh on 7/9/17.
 */
public class MycamSettingResponse {

    public String status;
    public McamSettingResult result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public McamSettingResult getResult() {
        return result;
    }

    public void setResult(McamSettingResult result) {
        this.result = result;
    }

}
