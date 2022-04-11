package edu.jsu.mcis.cs310.tas_sp22;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import org.json.simple.*;

public class Feature6 {
    
    private TASDatabase db;
    
    @Before
    public void setup() {
        
        db = new TASDatabase();
        
    }
    
    @Test
    public void testJSONShift1Weekday() {
        
        /* Expected JSON Data */
        
        String expectedJSON = "[{\"originaltimestamp\":\"FRI 09\\/07\\/2018 06:50:35\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"FRI 09\\/07\\/2018 07:00:00\",\"adjustmenttype\":\"Shift Start\",\"terminalid\":\"104\",\"id\":\"3634\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"FRI 09\\/07\\/2018 12:03:54\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"FRI 09\\/07\\/2018 12:00:00\",\"adjustmenttype\":\"Lunch Start\",\"terminalid\":\"104\",\"id\":\"3687\",\"punchtype\":\"CLOCK OUT\"},{\"originaltimestamp\":\"FRI 09\\/07\\/2018 12:23:41\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"FRI 09\\/07\\/2018 12:30:00\",\"adjustmenttype\":\"Lunch Stop\",\"terminalid\":\"104\",\"id\":\"3688\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"FRI 09\\/07\\/2018 15:34:13\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"FRI 09\\/07\\/2018 15:30:00\",\"adjustmenttype\":\"Shift Stop\",\"terminalid\":\"104\",\"id\":\"3716\",\"punchtype\":\"CLOCK OUT\"}]";
        
        ArrayList<HashMap<String, String>> expected = (ArrayList)JSONValue.parse(expectedJSON);
		
        /* Get Punch */
        
        Punch p = db.getPunch(3634);
        Badge b = db.getBadge(p.getBadge().getId());
        Shift s = db.getShift(b);
		
        /* Get Daily Punch List */
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginalTimestamp().toLocalDate());
        
        /* Adjust Punches */
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
        
        /* JSON Conversion */
        
        String actualJSON = TAS.getPunchListAsJSON(dailypunchlist);
        
        ArrayList<HashMap<String, String>> actual = (ArrayList)JSONValue.parse(actualJSON);
		
        /* Compare to Expected JSON */
        
        assertEquals(expected, actual);
        
    }
    
    @Test
    public void testJSONShift1Weekend() {
        
        /* Expected JSON Data */
        
        String expectedJSON = "[{\"originaltimestamp\":\"SAT 08\\/11\\/2018 05:54:58\",\"badgeid\":\"F1EE0555\",\"adjustedtimestamp\":\"SAT 08\\/11\\/2018 06:00:00\",\"adjustmenttype\":\"Interval Round\",\"terminalid\":\"105\",\"id\":\"1087\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"SAT 08\\/11\\/2018 12:04:02\",\"badgeid\":\"F1EE0555\",\"adjustedtimestamp\":\"SAT 08\\/11\\/2018 12:00:00\",\"adjustmenttype\":\"Interval Round\",\"terminalid\":\"105\",\"id\":\"1162\",\"punchtype\":\"CLOCK OUT\"}]";
        
        ArrayList<HashMap<String, String>> expected = (ArrayList)JSONValue.parse(expectedJSON);
		
        /* Get Punch */
        
        Punch p = db.getPunch(1087);
        Badge b = db.getBadge(p.getBadge().getId());
        Shift s = db.getShift(b);
        
        /* Get Daily Punch List */
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginalTimestamp().toLocalDate());
        
        /* Adjust Punches */
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
        
        /* JSON Conversion */
        
        String actualJSON = TAS.getPunchListAsJSON(dailypunchlist);
        
        ArrayList<HashMap<String, String>> actual = (ArrayList)JSONValue.parse(actualJSON);
		
        /* Compare to Expected JSON */
        
        assertEquals(expected, actual);
        
    }
    
    @Test
    public void testJSONShift2Weekday() {
        
        /* Expected JSON Data */
        
        String expectedJSON = "[{\"originaltimestamp\":\"TUE 09\\/18\\/2018 11:59:33\",\"badgeid\":\"08D01475\",\"adjustedtimestamp\":\"TUE 09\\/18\\/2018 12:00:00\",\"adjustmenttype\":\"Shift Start\",\"terminalid\":\"104\",\"id\":\"4943\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"TUE 09\\/18\\/2018 21:30:27\",\"badgeid\":\"08D01475\",\"adjustedtimestamp\":\"TUE 09\\/18\\/2018 21:30:00\",\"adjustmenttype\":\"None\",\"terminalid\":\"104\",\"id\":\"5004\",\"punchtype\":\"CLOCK OUT\"}]";
        
        ArrayList<HashMap<String, String>> expected = (ArrayList)JSONValue.parse(expectedJSON);
		
        /* Get Punch */
        
        Punch p = db.getPunch(4943);
        Badge b = db.getBadge(p.getBadge().getId());
        Shift s = db.getShift(b);
        
        /* Get Daily Punch List */
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginalTimestamp().toLocalDate());
        
        /* Adjust Punches */
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
        
        /* JSON Conversion */
        
        String actualJSON = TAS.getPunchListAsJSON(dailypunchlist);
        
        ArrayList<HashMap<String, String>> actual = (ArrayList)JSONValue.parse(actualJSON);
		
        /* Compare to Expected JSON */
        
        assertEquals(expected, actual);
        
    }
    
    
    /* Student Tests */
    
    @Test
    public void StudentTest1() {
        
        /* Expected JSON Data */
        
        String expectedJSON = "[{\"originaltimestamp\":\"WED 08\\/15\\/2018 06:47:48\",\"badgeid\":\"2A7F5D99\",\"adjustedtimestamp\":\"WED 08\\/15\\/2018 07:00:00\",\"adjustmenttype\":\"Shift Start\",\"terminalid\":\"104\",\"id\":\"1420\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"WED 08\\/15\\/2018 15:32:02\",\"badgeid\":\"2A7F5D99\",\"adjustedtimestamp\":\"WED 08\\/15\\/2018 15:30:00\",\"adjustmenttype\":\"Shift Stop\",\"terminalid\":\"104\",\"id\":\"1495\",\"punchtype\":\"CLOCK OUT\"}]";
        
        ArrayList<HashMap<String, String>> expected = (ArrayList)JSONValue.parse(expectedJSON);
		
        /* Get Punch */
        
        Punch p = db.getPunch(1420);
        Badge b = db.getBadge(p.getBadge().getId());
        Shift s = db.getShift(b);
        
        /* Get Daily Punch List */
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginalTimestamp().toLocalDate());
        
        /* Adjust Punches */
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
        
        /* JSON Conversion */
        
        String actualJSON = TAS.getPunchListAsJSON(dailypunchlist);
        
        ArrayList<HashMap<String, String>> actual = (ArrayList)JSONValue.parse(actualJSON);
		
        /* Compare to Expected JSON */
        
        assertEquals(expected, actual);
        
    }
    
    @Test
    public void StudentTest2() {
        
        /* Expected JSON Data */
        
        String expectedJSON = "[{\"originaltimestamp\":\"MON 09\\/10\\/2018 10:56:25\",\"badgeid\":\"2986FF85\",\"adjustedtimestamp\":\"MON 09\\/10\\/2018 11:00:00\",\"adjustmenttype\":\"Interval Round\",\"terminalid\":\"104\",\"id\":\"3874\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"MON 09\\/10\\/2018 16:33:46\",\"badgeid\":\"2986FF85\",\"adjustedtimestamp\":\"MON 09\\/10\\/2018 16:30:00\",\"adjustmenttype\":\"Interval Round\",\"terminalid\":\"104\",\"id\":\"3966\",\"punchtype\":\"CLOCK OUT\"}]";
        
        ArrayList<HashMap<String, String>> expected = (ArrayList)JSONValue.parse(expectedJSON);
		
        /* Get Punch */
        
        Punch p = db.getPunch(3874);
        Badge b = db.getBadge(p.getBadge().getId());
        Shift s = db.getShift(b);
        
        /* Get Daily Punch List */
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginalTimestamp().toLocalDate());
        
        /* Adjust Punches */
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
        
        /* JSON Conversion */
        
        String actualJSON = TAS.getPunchListAsJSON(dailypunchlist);
        
        ArrayList<HashMap<String, String>> actual = (ArrayList)JSONValue.parse(actualJSON);
		
        /* Compare to Expected JSON */
        
        assertEquals(expected, actual);
        
    }
    
    @Test
    public void StudentTest3() {
        
        /* Expected JSON Data */
        
        String expectedJSON = "[{\"originaltimestamp\":\"THU 08\\/02\\/2018 06:57:38\",\"badgeid\":\"CF697DE6\",\"adjustedtimestamp\":\"THU 08\\/02\\/2018 07:00:00\",\"adjustmenttype\":\"Shift Start\",\"terminalid\":\"104\",\"id\":\"288\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"THU 08\\/02\\/2018 15:32:45\",\"badgeid\":\"CF697DE6\",\"adjustedtimestamp\":\"THU 08\\/02\\/2018 15:30:00\",\"adjustmenttype\":\"Shift Stop\",\"terminalid\":\"104\",\"id\":\"346\",\"punchtype\":\"CLOCK OUT\"}]";
        
        ArrayList<HashMap<String, String>> expected = (ArrayList)JSONValue.parse(expectedJSON);
		
        /* Get Punch */
        
        Punch p = db.getPunch(288);
        Badge b = db.getBadge(p.getBadge().getId());
        Shift s = db.getShift(b);
        
        /* Get Daily Punch List */
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginalTimestamp().toLocalDate());
        
        /* Adjust Punches */
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
        
        /* JSON Conversion */
        
        String actualJSON = TAS.getPunchListAsJSON(dailypunchlist);
        
        ArrayList<HashMap<String, String>> actual = (ArrayList)JSONValue.parse(actualJSON);
		
        /* Compare to Expected JSON */
        
        assertEquals(expected, actual);
        
    }
    
}