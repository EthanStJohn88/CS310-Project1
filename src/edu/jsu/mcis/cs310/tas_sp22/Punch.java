package edu.jsu.mcis.cs310.tas_sp22;

import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;  
import java.time.format.TextStyle;
import java.util.Locale;

public class Punch {
    
    private int terminalid, id;
    private PunchType punchtypeid;
    private String adjustmenttype;
    private LocalDateTime timestamp;
    private Badge badge;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    public Punch(HashMap<String, String> params, Badge empbadge) { 
        this.terminalid = Integer.parseInt(params.get("terminalid"));
        this.punchtypeid = PunchType.values()[Integer.parseInt(params.get("eventtypeid"))];
        this.id = 0;
        this.adjustmenttype = null;
        this.badge = empbadge;
        this.timestamp = LocalDateTime.parse(params.get("timestamp"));
    }
    
    public Punch(int terminalid, Badge empbadge, int eventtypeid){
        this.terminalid = terminalid;
        this.punchtypeid = PunchType.values()[eventtypeid];
        this.id = 0;
        this.adjustmenttype = null;
        this.badge = empbadge;
        this.timestamp = LocalDateTime.now();
    }
    

    public int getTerminalid() {
        return terminalid;
    }

    public PunchType getPunchtype() {
        return punchtypeid;
    }

    public int getId() {
        return id;
    }

    public String getAdjustmenttype() {
        return adjustmenttype;
    }

    public LocalDateTime getOriginalTimestamp() {
        return timestamp;
    }

    public Badge getBadge() {
        return badge;
    }

    public String printOriginal() {
        StringBuilder result = new StringBuilder();
        
        result.append("#").append(badge.getId()).append(" ").append(punchtypeid).append(": ")
            .append(timestamp.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US).toUpperCase())
            .append(" ").append(timestamp.format(format));
        
        return result.toString();
    }
    
    
}
