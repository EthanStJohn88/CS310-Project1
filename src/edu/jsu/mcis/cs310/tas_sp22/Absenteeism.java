package edu.jsu.mcis.cs310.tas_sp22;

import java.time.LocalDate;
import java.util.HashMap;

public class Absenteeism {
    
    private Badge badge;
    private LocalDate payperiod;
    private double percentage;

    public Absenteeism(Badge badge, HashMap<String, String> params) {
        this.badge = badge;
        this.payperiod = LocalDate.parse(params.get("payperiod"));
        this.percentage = Double.parseDouble(params.get("percentage"));
    }

    public Absenteeism(Badge badge, LocalDate payperiod, double percentage) {
        this.badge= badge;
        this.payperiod = payperiod;
        this.percentage = percentage;
    }

    public Badge getBadge() {
        return badge;
    }

    public LocalDate getPayperiod() {
        return payperiod;
    }

    public double getPercentage() {
        return percentage;
    }

    @Override
    public String toString() {
        StringBuilder results = new StringBuilder();
        
        results.append("#").append(badgeid).append(" (Pay Period Starting ")
                .append(payperiod).append("): ").append(percentage).append("%");
        
        return results.toString();
    }
    
}
