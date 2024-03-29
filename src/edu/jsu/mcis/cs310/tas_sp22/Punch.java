package edu.jsu.mcis.cs310.tas_sp22;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;  
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Punch {
    
    private int terminalid, id, eventtypeid;
    private final PunchType punchtypeid;
    private String adjustmenttype;
    private final LocalDateTime timestamp;
    private LocalDateTime adjustedtimestamp;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
    private final Badge badge;
    private LocalTime adjustedtime;

    public Punch(HashMap<String, String> params, Badge empbadge) { 
        this.terminalid = Integer.parseInt(params.get("terminalid"));
        this.eventtypeid = Integer.parseInt(params.get("eventtypeid"));
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
        this.adjustedtimestamp = null;
        this.badge = empbadge;
        this.timestamp = LocalDateTime.now().withNano(0);
    }
    

    public int getTerminalid() {
        return terminalid;
    }

    public PunchType getPunchtype() {
        return punchtypeid;
    }
    
    public int getEventTypeId() {
        return eventtypeid;
    }

    public int getId() {
        return id;
    }

    public String getAdjustmenttype() {
        return adjustmenttype;
    }
    
    public LocalDateTime getAdjustedTimestamp(){
        return adjustedtimestamp;
    }

    public LocalDateTime getOriginalTimestamp() {
        return timestamp;
    }

    public Badge getBadge() {
        return badge;
    }

    @Override
    public String toString() {
        return printOriginal();
    }

    public String printOriginal() {
        StringBuilder result = new StringBuilder();
        
        result.append("#").append(badge.getId()).append(" ").append(punchtypeid).append(": ")
            .append(dtf.format(timestamp).toUpperCase());
        
        return result.toString();
    }
    
    public LocalTime roundInterval(int roundinterval, LocalTime time) {
        int minutes = time.getMinute();
        int remainder = minutes % roundinterval; 

        if (remainder < (roundinterval/2)) {
            return adjustedtime = time.minusMinutes(remainder).withSecond(0);
        } 
        else {
            return adjustedtime = time.plusMinutes(roundinterval - remainder).withSecond(0);
        }
    }
    
    /* Created adjust method which needs to implemented for Feature 4 */
    public void adjust(Shift s) {
        
        LocalTime start = s.getStart();
        LocalTime stop = s.getStop();
        LocalTime lunchStart = s.getStartLunch();
        LocalTime lunchStop = s.getStopLunch();
        int roundInt = s.getRoundInterval();
        int gracePeriod = s.getGracePeriod();
        int dockPenalty = s.getTimeDock();
        
        
        LocalTime time = getOriginalTimestamp().toLocalTime();
        adjustmenttype = "None";
        float timediff = 0;
        
        if (timestamp.getDayOfWeek() == DayOfWeek.SATURDAY || timestamp.getDayOfWeek() == DayOfWeek.SUNDAY) {
            adjustedtime = roundInterval(roundInt,time);
            adjustmenttype = "Interval Round";
            
        }
        
        else {

            if (punchtypeid == PunchType.CLOCK_IN) { 

                timediff = Math.abs(MINUTES.between(time, start)); 
                System.err.println("Time diff is: " + timediff);

                if (time.isBefore(start)) { 
                    if (timediff <= gracePeriod || timediff <= roundInt) { 
                        adjustedtime = start;
                        adjustmenttype = "Shift Start";
                    }
                    else if(timediff <= roundInt) { //Round interval
                        adjustedtime = roundInterval(roundInt,time);
                        adjustmenttype = "Interval Round";
                    }
                    else{
                        adjustedtime = roundInterval(roundInt,time);
                    }
                }

                else if (time.isAfter(start) && time.isBefore(lunchStart)) { 
                    if (timediff < 1 || timediff == 60) {
                        adjustedtime = time.withSecond(0);
                    }
                    else if (timediff <= gracePeriod) { //Grace Period
                        adjustedtime = start;
                        adjustmenttype = "Shift Start";
                    }
                    else if (timediff >= gracePeriod && timediff <= dockPenalty) { 
                        adjustedtime = start.plusMinutes(dockPenalty);
                        adjustmenttype = "Shift Dock";
                    }
                    else if (timediff >= dockPenalty) {
                        adjustedtime = roundInterval(roundInt,time);
                        adjustmenttype = "Interval Round";
                    }
                }

                if (time.isAfter(lunchStart) && time.isBefore(lunchStop)) {
                    adjustedtime = lunchStop;
                    adjustmenttype = "Lunch Stop";
                }
            }



            if (punchtypeid == PunchType.CLOCK_OUT) { 

                timediff = Math.abs(MINUTES.between(time, stop)); //Mintues between clock out and end of shift
                System.err.println("Time diff is: " + timediff);

                if (time.isAfter(stop)) { //After shift stop
                    if (timediff <= gracePeriod || timediff <= roundInt) { //Grace Period
                        adjustedtime = stop;
                        adjustmenttype = "Shift Stop";
                    }
                    else if (LocalTime.of(time.getHour(), time.getMinute()).equals(roundInterval(roundInt,time))){
                        adjustedtime = time.withSecond(0);
                    }
                    else{
                        adjustedtime = roundInterval(roundInt,time);
                        adjustmenttype = "Interval Round";
                    }
                }

                else if (time.isAfter(lunchStop) && time.isBefore(stop)) {
                    if (timediff <= gracePeriod) { 
                        adjustedtime = stop;
                        adjustmenttype = "Shift Stop";
                    }
                    if (timediff >= gracePeriod && timediff <= dockPenalty) { //Dock Penalty
                        adjustedtime = stop.minusMinutes(dockPenalty);
                        adjustmenttype = "Shift Dock";
                    }
                    else if (timediff >= dockPenalty) {
                        adjustedtime = roundInterval(roundInt,time);
                        adjustmenttype = "Interval Round";
                    }
                }

                else if (time.isAfter(lunchStart) && time.isBefore(lunchStop)) {
                    adjustedtime = lunchStart;
                    adjustmenttype = "Lunch Start";
                }
            }
        }
        
        adjustedtimestamp = LocalDateTime.of(timestamp.toLocalDate(), adjustedtime);
    }
        
        
    
    
    /* Created printAdjusted method which needs to implemented for Feature 4 */
    public String printAdjusted() {
        StringBuilder result = new StringBuilder();
        
        result.append("#").append(badge.getId()).append(" ").append(punchtypeid).append(": ")
            .append(dtf.format(adjustedtimestamp).toUpperCase()).append(" (").append(adjustmenttype).append(")");
        
        return result.toString();
    }
}
