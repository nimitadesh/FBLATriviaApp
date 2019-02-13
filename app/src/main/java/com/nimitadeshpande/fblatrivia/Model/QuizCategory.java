package com.nimitadeshpande.fblatrivia.Model;

import java.util.HashMap;

public class QuizCategory {

    private HashMap<String, String> categoryMap;

    public QuizCategory ()
    {
        this.categoryMap = new HashMap<String, String>();
        this.categoryMap.put("Competitive Events", "competitiveEvents");
        this.categoryMap.put("National Officers", "nationalOfficers");
        this.categoryMap.put("National Sponsors and Partners", "nationalSponsors");
        this.categoryMap.put("FBLA History", "fblaHistory");
        this.categoryMap.put("FBLA Regions", "fblaRegions");
    }

    public HashMap<String, String> getCategoryMap() {
        return categoryMap;
    }
}
