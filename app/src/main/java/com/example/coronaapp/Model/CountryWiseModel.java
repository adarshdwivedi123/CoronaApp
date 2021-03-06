package com.example.coronaapp.Model;

public class CountryWiseModel {
    private String country;
    private String confirmed;
    private String newConfirmed;
    private String active;
    private String deceased;
    private String newDeceased;
    private String recovered;
    private String tests;
    private String flag;

    public CountryWiseModel(String country, String confirmed, String newConfirmed, String active, String deceased, String newDeceased, String recovered, String tests, String flag) {
        this.country = country;
        this.confirmed = confirmed;
        this.newConfirmed = newConfirmed;
        this.active = active;
        this.deceased = deceased;
        this.newDeceased = newDeceased;
        this.recovered = recovered;
        this.tests = tests;
        this.flag = flag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(String newConfirmed) {
        this.newConfirmed = newConfirmed;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDeceased() {
        return deceased;
    }

    public void setDeceased(String deceased) {
        this.deceased = deceased;
    }

    public String getNewDeceased() {
        return newDeceased;
    }

    public void setNewDeceased(String newDeceased) {
        this.newDeceased = newDeceased;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
