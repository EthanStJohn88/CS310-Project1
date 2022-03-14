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
    private final LocalDateTime timestamp;
    private LocalDateTime adjustedtimestamp;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
    private Badge badge;

    public Punch(HashMap<String, String> params, Badge empbadge) { 
        this.terminalid = Integer.parseInt(params.get("terminalid"));
        this.punchtypeid = PunchType.values()[Integer.parseInt(params.get("eventtypeid"))];
        this.id = Integer.parseInt(params.get("id"));
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
        this.timestamp = LocalDateTime.now().withNano(0);
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
            .append(dtf.format(timestamp).toUpperCase());
        
        return result.toString();
    }
    
    /* Created adjust method which needs to implemented for Feature 4 */
    public void adjust(Shift s) {
        
    }
    
    /* Created printAdjusted method which needs to implemented for Feature 4 */
    public String printAdjusted() {
        StringBuilder result = new StringBuilder();
        
        result.append("#").append(badge.getId()).append(" ").append(punchtypeid).append(": ")
            .append(dtf.format(adjustedtimestamp).toUpperCase()).append(" (").append(adjustmenttype).append(")");
        
        return result.toString();
    }
}
