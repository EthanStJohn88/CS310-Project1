package edu.jsu.mcis.cs310.tas_sp22;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;

public class Absenteeism {
    
    private Badge badge;
    private LocalDate payperiod;
    private double percentage;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    public Absenteeism(HashMap<String, String> params, Badge badge) {
        this.badge = badge;
        this.percentage = Double.parseDouble(params.get("percentage"));
        if(LocalDate.parse(params.get("payperiod")).getDayOfWeek() != DayOfWeek.SUNDAY){
            LocalDate date = LocalDate.parse(params.get("payperiod"));
            while(date.getDayOfWeek() != DayOfWeek.SUNDAY){
                date = date.minusDays(1);
            }
            this.payperiod = date;
        }
        else{
            this.payperiod = LocalDate.parse(params.get("payperiod"));
        }
    }

    public Absenteeism(Badge badge, LocalDate payperiod, double percentage) {
        this.badge= badge;
        this.percentage = percentage;
        if(payperiod.getDayOfWeek() != DayOfWeek.SUNDAY){
            while(payperiod.getDayOfWeek() != DayOfWeek.SUNDAY){
                payperiod = payperiod.minusDays(1);
            }
            this.payperiod = payperiod;
        }
        else{
            this.payperiod = payperiod;
        }
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
        
        results.append("#").append(badge.getId()).append(" (Pay Period Starting ")
                .append(payperiod.format(dtf)).append("): ").append(String.format("%.2f", percentage*100)).append("%");
        
        return results.toString();
    }
    
}
