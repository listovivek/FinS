package com.myappilication.xpress.finjan2017.menulist.mycamsschemelist;

/**
 * Created by suresh on 19/7/17.
 */
public class SchemeListResponse {

    public String status;
    public SchemeListResult result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SchemeListResult getResult() {
        return result;
    }

    public void setResult(SchemeListResult result) {
        this.result = result;
    }
}
