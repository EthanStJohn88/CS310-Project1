
package edu.jsu.mcis.cs310.tas_sp22;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

class Shift {
    
    private int id, timeDock, gracePeriod, lunchTimeDock, roundInterval;
    private String description;
    private LocalTime start, stop, startLunch, stopLunch;
    private long lunchDuration, shiftDuration;
    public static final int WORKDAYS = 5;
   
    
    public Shift (HashMap<String, String> shiftParams)
    {
            
        this.id = Integer.parseInt(shiftParams.get("id"));
        this.description = shiftParams.get("description");
        this.start = LocalTime.parse(shiftParams.get("start")); 
        this.stop = LocalTime.parse(shiftParams.get("stop"));
        this.roundInterval = Integer.parseInt(shiftParams.get("roundInterval"));
        this.gracePeriod = Integer.parseInt(shiftParams.get("gracePeriod"));
        this.timeDock = Integer.parseInt(shiftParams.get("timeDock")); 
        this.startLunch = LocalTime.parse(shiftParams.get("startLunch"));
        this.stopLunch = LocalTime.parse(shiftParams.get("stopLunch"));;
        this.lunchTimeDock = Integer.parseInt(shiftParams.get("lunchTimeDock"));
        this.lunchDuration = MINUTES.between(startLunch, stopLunch);
        this.shiftDuration = MINUTES.between(start, stop);
        
        this.shiftDuration = java.time.Duration.between(start,stop).toMinutes();
        this.lunchDuration = java.time.Duration.between(startLunch,stopLunch).toMinutes();        
        
    }
    

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    
    public int getRoundInterval(){
        return roundInterval;
    }

    public int getTimeDock() {
        return timeDock;
    }

    public int getGracePeriod() {
        return gracePeriod;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getStop() {
        return stop;
    }

    public LocalTime getStartLunch() {
        return startLunch;
    }

    public LocalTime getStopLunch() {
        return stopLunch;
    }

    public int getLunchTimeDock() {
        return lunchTimeDock;
    }

    public long getLunchduration() {
        return lunchDuration;
    }

    public long getShiftduration() {
        return shiftDuration;
    }
    
    
    @Override
    public String toString(){
	//String id and description 
	StringBuilder results = new StringBuilder(); 
        
        results.append(description).append(": ");
        results.append(start).append(" - ").append(stop);
        results.append(" (").append(shiftDuration).append(" minutes);");
        results.append(" Lunch: ").append(startLunch).append(" - ").append(stopLunch);
        results.append(" (").append(lunchDuration).append(" minutes)");

	return results.toString(); 
    }  
}
