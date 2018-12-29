package com.nimitadeshpande.fblatrivia.Model;

public enum Category {

    COMPETITIVE_EVENTS("Competitive Events"),
    NATIONAL_OFFICERS("National Officers"),
    NATIONAL_SPONSORS("National Sponsors"),
    FBLA_HISTORY("FBLA History"),
    FBLA_REGIONS("FBLA Regions");

    private String friendlyName;
    private Category(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @Override public String toString(){
        return friendlyName;
    }
}
