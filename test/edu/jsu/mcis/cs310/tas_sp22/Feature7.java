package edu.jsu.mcis.cs310.tas_sp22;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class Feature7 {
    
    private TASDatabase db;
    
    @Before
    public void setup() {
        
        db = new TASDatabase();
        
    }
    
    @Test
    public void testAbsenteeismShift1Weekday() {
		
        /* Get Punch */
        
        Punch p = db.getPunch(3634);
        Badge b = p.getBadge();
        Shift s = db.getShift(b);
        
        /* Get Pay Period Punch List */
        
        LocalDateTime ts = p.getOriginalTimestamp();
        ArrayList<Punch> punchlist = db.getPayPeriodPunchList(b, ts.toLocalDate(), s);
        
        /* Compute Pay Period Total Absenteeism */
        
        double percentage = TAS.calculateAbsenteeism(punchlist, s);
        
        /* Insert Absenteeism Into Database */
        
        Absenteeism a1 = new Absenteeism(b, ts.toLocalDate(), percentage);
        db.insertAbsenteeism(a1);
        
        /* Retrieve Absenteeism From Database */
        
        Absenteeism a2 = db.getAbsenteeism(b, ts.toLocalDate());
        
        /* Compare to Expected Value */
        
        assertEquals("#28DC3FB8 (Pay Period Starting 09-02-2018): 2.50%", a2.toString());
        
    }
    
    @Test
    public void testAbsenteeismShift1Weekend() {
		
        /* Get Punch */
        
        Punch p = db.getPunch(1087);
        Badge b = p.getBadge();
        Shift s = db.getShift(b);
        
        /* Get Pay Period Punch List */
        
        LocalDateTime ts = p.getOriginalTimestamp();
        ArrayList<Punch> punchlist = db.getPayPeriodPunchList(b, ts.toLocalDate(), s);
        
        /* Compute Pay Period Total Absenteeism */
        
        double percentage = TAS.calculateAbsenteeism(punchlist, s);
        
        /* Insert Absenteeism Into Database */
        
        Absenteeism a1 = new Absenteeism(b, ts.toLocalDate(), percentage);
        db.insertAbsenteeism(a1);
        
        /* Retrieve Absenteeism From Database */
        
        Absenteeism a2 = db.getAbsenteeism(b, ts.toLocalDate());
        
        /* Compare to Expected Value */
        
        assertEquals("#F1EE0555 (Pay Period Starting 08-05-2018): -20.00%", a2.toString());
        
    }
    
    @Test
    public void testAbsenteeismShift2Weekend() {
		
        /* Get Punch */
        
        Punch p = db.getPunch(4943);
        Badge b = p.getBadge();
        Shift s = db.getShift(b);
        
        /* Get Pay Period Punch List */
        
        LocalDateTime ts = p.getOriginalTimestamp();
        ArrayList<Punch> punchlist = db.getPayPeriodPunchList(b, ts.toLocalDate(), s);
        
        /* Compute Pay Period Total Absenteeism */
        
        double percentage = TAS.calculateAbsenteeism(punchlist, s);
        
        /* Insert Absenteeism Into Database */
        
        Absenteeism a1 = new Absenteeism(b, ts.toLocalDate(), percentage);
        db.insertAbsenteeism(a1);
        
        /* Retrieve Absenteeism From Database */
        
        Absenteeism a2 = db.getAbsenteeism(b, ts.toLocalDate());
        
        /* Compare to Expected Value */
        
        assertEquals("#08D01475 (Pay Period Starting 09-16-2018): -27.50%", a2.toString());
        
    }
    
    /* Student Tests */
   
    //Student Test 1
    @Test
    public void studentTest1() {
        
        //Get Punch
        
        Punch p = db.getPunch(1420);
        Badge b = p.getBadge();
        Shift s = db.getShift(b);
        
        //Get Pay Period Punch List
        
        LocalDateTime ts = p.getOriginalTimestamp();
        ArrayList<Punch> punchlist = db.getPayPeriodPunchList(b, ts.toLocalDate(), s);
        
        //Compute Pay Period Total Absenteeism
        
        double percentage = TAS.calculateAbsenteeism(punchlist, s);
        
        //Insert Absenteeism Into Database
        
        Absenteeism a1 = new Absenteeism(b, ts.toLocalDate(), percentage);
        db.insertAbsenteeism(a1);
        
        //Retrieve Absenteeism From Database
        
        Absenteeism a2 = db.getAbsenteeism(b, ts.toLocalDate());
        
        //Compare To Expected Value 
        
        assertEquals("#2A7F5D99 (Pay Period Starting 08-12-2018): -12.50%", a2.toString());
    }
    
    //Student Test 2
    @Test
    public void studentTest2() {
        
        //Get Punch
        
        Punch p = db.getPunch(288);
        Badge b = p.getBadge();
        Shift s = db.getShift(b);
        
        //Get Pay Period Punch List
        
        LocalDateTime ts = p.getOriginalTimestamp();
        ArrayList<Punch> punchlist = db.getPayPeriodPunchList(b, ts.toLocalDate(), s);
        
        //Compute Pay Period Total Absenteeism
        
        double percentage = TAS.calculateAbsenteeism(punchlist, s);
        
        //Insert Absenteeism Into Database
        
        Absenteeism a1 = new Absenteeism(b, ts.toLocalDate(), percentage);
        db.insertAbsenteeism(a1);
        
        //Retrieve Absenteeism From Database
        
        Absenteeism a2 = db.getAbsenteeism(b, ts.toLocalDate());
        
        //Compare To Expected Value 
        
        assertEquals("#CF697DE6 (Pay Period Starting 07-29-2018): 40.00%", a2.toString());
    }
    
    @Test
    public void studentTest3() {
		
        /* Get Punch */
        
        Punch p = db.getPunch(936);
        Badge b = p.getBadge();
        Shift s = db.getShift(b);
        
        /* Get Pay Period Punch List */
        
        LocalDateTime ts = p.getOriginalTimestamp();
        ArrayList<Punch> punchlist = db.getPayPeriodPunchList(b, ts.toLocalDate(), s);
        
        /* Compute Pay Period Total Absenteeism */
        
        double percentage = TAS.calculateAbsenteeism(punchlist, s);
        
        /* Insert Absenteeism Into Database */
        
        Absenteeism a1 = new Absenteeism(b, ts.toLocalDate(), percentage);
        db.insertAbsenteeism(a1);
        
        /* Retrieve Absenteeism From Database */
        
        Absenteeism a2 = db.getAbsenteeism(b, ts.toLocalDate());
        
        /* Compare to Expected Value */
        
        assertEquals("#8C0644BA (Pay Period Starting 08-05-2018): -7.50%", a2.toString());

    }
    
    @Test
    public void studentTest4() {
		
        /* Get Punch */
        
        Punch p = db.getPunch(2525);
        Badge b = p.getBadge();
        Shift s = db.getShift(b);
        
        /* Get Pay Period Punch List */
        
        LocalDateTime ts = p.getOriginalTimestamp();
        ArrayList<Punch> punchlist = db.getPayPeriodPunchList(b, ts.toLocalDate(), s);
        
        /* Compute Pay Period Total Absenteeism */
        
        double percentage = TAS.calculateAbsenteeism(punchlist, s);
        
        /* Insert Absenteeism Into Database */
        
        Absenteeism a1 = new Absenteeism(b, ts.toLocalDate(), percentage);
        db.insertAbsenteeism(a1);
        
        /* Retrieve Absenteeism From Database */
        
        Absenteeism a2 = db.getAbsenteeism(b, ts.toLocalDate());
        
        /* Compare to Expected Value */
        
        assertEquals("#F1EE0555 (Pay Period Starting 08-19-2018): -30.63%", a2.toString());
        
    }
    
}