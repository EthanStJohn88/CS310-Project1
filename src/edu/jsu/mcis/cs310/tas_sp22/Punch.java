package edu.jsu.mcis.cs310.tas_sp22;

import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;  

public class Punch {
    
    private int terminalid, id;
    private PunchType punchtypeid;
    private String adjustmenttype, badgeid;
    private LocalDateTime timestamp;
    private Badge badge;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    public Punch(HashMap<String, String> params, Badge empbadge) { 
        this.terminalid = Integer.parseInt(params.get("terminalid"));
        this.punchtypeid = PunchType.values()[Integer.parseInt(params.get("eventtypeid"))];
        this.id = 0;
        this.badgeid = params.get("badgeid");
        this.adjustmenttype = null;
        this.badge = empbadge;
        if(params.get("timestamp") != null){
            this.timestamp = LocalDateTime.parse(params.get("timestamp"));
        }
        else{
            this.timestamp = LocalDateTime.now();
        }
        
    }
    

    public int getTerminalid() {
        return terminalid;
    }

    public PunchType getPunchtypeid() {
        return punchtypeid;
    }

    public int getId() {
        return id;
    }

    public String getAdjustmenttype() {
        return adjustmenttype;
    }

    public String getBadgeid() {
        return badgeid;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String printOriginal() {
        StringBuilder result = new StringBuilder();
        
        result.append("#").append(badgeid).append(" ").append(punchtypeid)
            .append(": ").append(timestamp.getDayOfWeek()).append(" ").append(timestamp.format(format));
        
        return result.toString();
    }
    
    
}
