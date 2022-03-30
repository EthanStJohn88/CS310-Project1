package edu.jsu.mcis.cs310.tas_sp22;

import java.time.DayOfWeek;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONValue;

public class TAS {
    
     public static final int CLOCKIN = 1;
     public static final int CLOCKOUT = 0;
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift){
        return 0;
    }
    
    
    public static String getPunchListAsJSON(ArrayList<Punch> dailyPunchList){
        
            final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

            
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();
        
        for(Punch p : dailyPunchList){
            
            HashMap<String, String> punchdata = new HashMap<>();
            punchdata.put("originaltimestamp", String.valueOf(dtf.format(p.getOriginalTimestamp()).toUpperCase()));
            punchdata.put("adjustedtimestamp", String.valueOf(dtf.format(p.getAdjustedTimestamp()).toUpperCase()));
            punchdata.put("id", String.valueOf(p.getId()));
            punchdata.put("badgeid", String.valueOf(p.getBadge().getId()));
            punchdata.put("terminalid", String.valueOf(p.getTerminalid()));
            punchdata.put("punchtype", String.valueOf(p.getPunchtype()));
            punchdata.put("adjustmenttype", p.getAdjustmenttype());
            
            
            
            jsonData.add(punchdata);
            
        }
        
        return JSONValue.toJSONString(jsonData);
    }

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