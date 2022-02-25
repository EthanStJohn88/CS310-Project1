package edu.jsu.mcis.cs310.tas_sp22;

import java.util.HashMap;

public class TAS {

    public static void main(String[] args) {
        final String username = "tasuser";
        final String password = "Team_A";
        
        //Badge Test
        HashMap<String, String> badge = new HashMap<>();
        badge.put("description", "Chapman, Joshua E");
        badge.put("id", "12565C60");
        
        Badge b1 = new Badge(badge);
        System.err.println(badge);
        System.err.println(b1.getId());
        System.err.println(b1.getDescription());
        System.err.println(b1.toString());
    }
    
}