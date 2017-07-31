package com.myappilication.xpress.finjan2017.models.login.modulelist;

/**
 * Created by suresh on 15/5/17.
 */
public class ListOfModuleResponse {

    private String status;
    private ListOfModuleResult result;



    public void setStatus(String status) {
        this.status = status;
    }

    public ListOfModuleResult getLomResult() {
        return result;
    }

    public void setLomResult(ListOfModuleResult lomResult) {
        this.result = lomResult;
    }

    public String getStatus() {
        return status;
    }








}
