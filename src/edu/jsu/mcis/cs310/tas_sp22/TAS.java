package edu.jsu.mcis.cs310.tas_sp22;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONValue;
import static java.time.temporal.ChronoUnit.MINUTES;

public class TAS {
    
    public static double calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s){
        double percent = 0;
        
        int totalmin = calculateTotalMinutes(punchlist, s);
        long shiftDuration = (s.getShiftduration() - s.getLunchduration()) * Shift.WORKDAYS;
        
        percent = Double.valueOf((shiftDuration - totalmin)/shiftDuration);
        
        return percent;
    }
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift){
        int totalMinutes = 0;
        boolean lunchout = false;
        boolean lunchpair = false;

        for (int i = 0; i < dailypunchlist.size(); i++){
            Punch p1 = dailypunchlist.get(i);
            if(p1.getPunchtype() == PunchType.CLOCK_IN){
                for(int j = i; j < dailypunchlist.size(); j++){
                    Punch p2 = dailypunchlist.get(j);
                    if(p2.getPunchtype() == PunchType.TIME_OUT){
                        i = j;
                        j = dailypunchlist.size();
                    }
                    else if(p2.getPunchtype() == PunchType.CLOCK_OUT){
                        if(p2.getAdjustedTimestamp().toLocalTime() == shift.getStartLunch()){
                            lunchout = true;
                        }
                        totalMinutes += MINUTES.between(p1.getAdjustedTimestamp(), p2.getAdjustedTimestamp());
                        i = j;
                        j = dailypunchlist.size();
                    }
                }
            }
            if(lunchout && p1.getAdjustedTimestamp().toLocalTime() == shift.getStopLunch()){
                lunchpair = true;
            }
            if(totalMinutes > shift.getLunchTimeDock() && !lunchpair){
                totalMinutes -= shift.getLunchduration();
                lunchpair = false;
                lunchout = false;
            }
        }

        return totalMinutes;
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
        
        TASDatabase db = new TASDatabase();
        
        //Badge Test
        HashMap<String, String> badge = new HashMap<>();
        badge.put("description", "Chapman, Joshua E");
        badge.put("id", "12565C60");
        
        Badge b1 = new Badge(badge);
        System.err.println(badge);
        System.err.println(b1.getId());
        System.err.println(b1.getDescription());
        System.err.println(b1.toString());
        
        /* Test getPayPeriodPunchList */
        
        Punch p = db.getPunch(1087);
        Badge b = p.getBadge();
        Shift s = db.getShift(b);
        
        LocalDateTime ts = p.getOriginalTimestamp();
        ArrayList<Punch> punchlist = db.getPayPeriodPunchList(b, ts.toLocalDate(), s);
        
        int totalmin = calculateTotalMinutes(punchlist, s);
        long shiftDuration = (s.getShiftduration() - s.getLunchduration()) * Shift.WORKDAYS;
        double percent = Double.valueOf((shiftDuration - totalmin)/shiftDuration)*100;
        
        System.err.println(totalmin);
        System.err.println(shiftDuration);
        System.err.println(percent);
        
    }
    
}