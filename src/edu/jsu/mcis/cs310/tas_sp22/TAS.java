package edu.jsu.mcis.cs310.tas_sp22;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONValue;
import static java.time.temporal.ChronoUnit.MINUTES;
import org.json.simple.JSONObject;

public class TAS {
    
    public static String getPunchListPlusTotalsAsJSON(ArrayList<Punch> punchlist, Shift s) {
        
        JSONObject jsonData = new JSONObject();
        
        String percent = String.format("%.2f", calculateAbsenteeism(punchlist, s)*100) + "%";
        
        int totalMin = 0;
        
        ArrayList<ArrayList<Punch>> punches = new ArrayList<>();
        ArrayList<Punch> tempList1 = new ArrayList<>();
        ArrayList<Punch> tempList2 = new ArrayList<>();
        ArrayList<Punch> tempList3 = new ArrayList<>();
        ArrayList<Punch> tempList4 = new ArrayList<>();
        ArrayList<Punch> tempList5 = new ArrayList<>();
        ArrayList<Punch> tempList6 = new ArrayList<>();
        
        for(Punch p: punchlist) {       
            LocalDateTime t1 = p.getOriginalTimestamp();
            String day = t1.getDayOfWeek().toString();
            switch(day) {
                case "MONDAY":
                    tempList1.add(p);
                    break;
                case "TUESDAY":
                    tempList2.add(p);
                    break;
                case "WEDNESDAY":
                    tempList3.add(p);
                    break;
                case "THURSDAY":
                    tempList4.add(p);
                    break;
                case "FRIDAY":
                    tempList5.add(p);
                    break;
                case "SATURDAY":
                    tempList6.add(p);
                    break;
            } 
        }
        
        punches.add(tempList1);
        punches.add(tempList2);
        punches.add(tempList3);
        punches.add(tempList4);
        punches.add(tempList5);
        punches.add(tempList6);
        
        for(ArrayList<Punch> a: punches)
            totalMin += calculateTotalMinutes(a, s);
        
        jsonData.put("absenteeism", percent);
        jsonData.put("totalminutes", String.valueOf(totalMin));
        jsonData.put("punchlist", JSONValue.parse(getPunchListAsJSON(punchlist)));
        
        return JSONValue.toJSONString(jsonData);
    }
    
    public static double calculateAbsenteeism(ArrayList<Punch> punchlist, Shift shift) {
        
        double totalMin = 0;
        double totalexpectedworktime = (shift.getShiftduration() - shift.getLunchduration()) * Shift.WORKDAYS;
        ArrayList<ArrayList<Punch>> punches = new ArrayList<>();
        ArrayList<Punch> tempList1 = new ArrayList<>();
        ArrayList<Punch> tempList2 = new ArrayList<>();
        ArrayList<Punch> tempList3 = new ArrayList<>();
        ArrayList<Punch> tempList4 = new ArrayList<>();
        ArrayList<Punch> tempList5 = new ArrayList<>();
        ArrayList<Punch> tempList6 = new ArrayList<>();
        
        for(Punch p: punchlist) {       
            LocalDateTime t1 = p.getOriginalTimestamp();
            String day = t1.getDayOfWeek().toString();
            switch(day) {
                case "MONDAY":
                    tempList1.add(p);
                    break;
                case "TUESDAY":
                    tempList2.add(p);
                    break;
                case "WEDNESDAY":
                    tempList3.add(p);
                    break;
                case "THURSDAY":
                    tempList4.add(p);
                    break;
                case "FRIDAY":
                    tempList5.add(p);
                    break;
                case "SATURDAY":
                    tempList6.add(p);
                    break;
            } 
        }
        
        punches.add(tempList1);
        punches.add(tempList2);
        punches.add(tempList3);
        punches.add(tempList4);
        punches.add(tempList5);
        punches.add(tempList6);
        
        for(ArrayList<Punch> a: punches)
            totalMin += calculateTotalMinutes(a, shift);
        
        double absenteeism = totalexpectedworktime - totalMin;
        double percentage = (absenteeism/totalexpectedworktime);
        return percentage;
        
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
        
        Badge b = new Badge(badge);
        System.err.println(badge);
        System.err.println(b.getId());
        System.err.println(b.getDescription());
        System.err.println(b.toString());
        
        /* Test getPayPeriodPunchList */
        
        Punch p1 = db.getPunch(1087);
        Badge b1 = p1.getBadge();
        Shift s1 = db.getShift(b1);
        
        LocalDateTime ts = p1.getOriginalTimestamp();
        ArrayList<Punch> punchlist = db.getPayPeriodPunchList(b1, ts.toLocalDate(), s1);
        
        System.err.println(punchlist);
        
        double percent = calculateAbsenteeism(punchlist, s1);

        System.err.println(percent);
        
        Punch p2 = db.getPunch(5307);
        Badge b2 = db.getBadge(p2.getBadge().getId());
        Shift s2 = db.getShift(b2);
        
        LocalTime time = p2.getOriginalTimestamp().toLocalTime();
        LocalTime stop = s2.getStop();
        
        float timediff = Math.abs(MINUTES.between(time, stop));
        
        System.err.println(time + " " + stop + " " + timediff);

    }
    
}
