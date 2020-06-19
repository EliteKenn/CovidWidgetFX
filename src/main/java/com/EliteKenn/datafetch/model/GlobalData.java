package com.EliteKenn.datafetch.model;

public class GlobalData {

    private long recovered;
    private long deaths;
    private long cases;

    public void setRecovered(long recovered){
        this.recovered = recovered;
    }

    public long getRecovered(){
        return recovered;
    }

    public void setDeaths(long deaths){
        this.deaths = deaths;
    }

    public long getDeaths(){
        return deaths;
    }

    public void setCases(long cases){
        this.cases = cases;
    }

    public long getCases(){
        return cases;
    }

    @Override
    public String toString() {
        return "GlobalData{" +
                "recovered=" + recovered +
                ", deaths=" + deaths +
                ", cases=" + cases +
                '}';
    }
}
