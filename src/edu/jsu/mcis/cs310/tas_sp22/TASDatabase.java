package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class TASDatabase {
    
    private Connection conn = null;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    public TASDatabase()
    {
        try
        {
            String server = ("jdbc:mysql://localhost/tas_sp22_v1");
            String username = "tasuser";
            String password = "Team_A";
            System.out.println("Connecting to " + server + "...");
            
            conn = DriverManager.getConnection(server, username, password);
            
            if(conn.isValid(0)){
                System.out.println("Connected Successfully!");
            }
            else{
                throw new SQLException();
            }
        }
        catch(SQLException e){ System.out.println("SQL Connection failed!" +
                " Invalid database setup?" + e); 
        }    
        catch(Exception e){ e.printStackTrace();}
        
    }
    
    
    public void close(){
        try{
            conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
        
    
    public Badge getBadge(String id){
        Badge outputBadge = null;
        
        try{
            String query = "SELECT * FROM badge WHERE id = ?;";
            PreparedStatement pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, id);
            
            boolean hasresults = pstSelect.execute();
            
            if (hasresults){
                ResultSet resultset = pstSelect.getResultSet();
                
                resultset.next();
                
                HashMap<String, String> shift = new HashMap<>();
                shift.put("id", resultset.getString("id"));
                shift.put("description", resultset.getString("description"));
                
                outputBadge = new Badge(shift);
            }
            
        }
        catch(SQLException e){ System.out.println("Error in getBadge() " + e);
        
        }
    
    return outputBadge;
    }
    
    public Shift getShift(int id){
        Shift outputShift = null;
        
        try{
            String query = "SELECT * FROM shift WHERE id = ?;";
            PreparedStatement pstSelect = conn.prepareStatement(query);
            pstSelect.setInt(1, id);
            
            boolean hasresults = pstSelect.execute();
            
            if(hasresults){
                ResultSet resultset = pstSelect.getResultSet();
                
                resultset.next();
                
                HashMap<String, String> shift = new HashMap<>();
                shift.put("id", String.valueOf(id));
                shift.put("description", resultset.getString("description"));
                shift.put("start", resultset.getString("shiftstart"));
                shift.put("stop", resultset.getString("shiftstop"));
                shift.put("roundInterval", resultset.getString("roundinterval"));
                shift.put("gracePeriod", resultset.getString("graceperiod"));
                shift.put("timeDock", resultset.getString("dockpenalty"));
                shift.put("startLunch", resultset.getString("lunchstart"));
                shift.put("stopLunch", resultset.getString("lunchstop"));
                shift.put("lunchTimeDock", resultset.getString("lunchthreshold"));
                
                outputShift = new Shift(shift);
            }
            
        }
        catch(SQLException e){ System.out.println("Error in id getShift() " + e);
        
        }
        
        return outputShift;
    }
    
    public Shift getShift(Badge badge){
        Shift outputShift = null;
        
        try{
            String query = "SELECT * FROM employee JOIN shift ON employee.shiftid = shift.id WHERE badgeid = ?;";
            PreparedStatement pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, badge.getId());
            
            boolean hasresults = pstSelect.execute();
            
            if(hasresults){
                ResultSet resultset = pstSelect.getResultSet();
                
                resultset.next();
                
                HashMap<String, String> shift = new HashMap<>();
                shift.put("id", resultset.getString("shift.id"));
                shift.put("description", resultset.getString("description")); 
                shift.put("start", resultset.getString("shiftstart"));
                shift.put("stop", resultset.getString("shiftstop"));
                shift.put("roundInterval", resultset.getString("roundinterval"));
                shift.put("gracePeriod", resultset.getString("graceperiod"));
                shift.put("timeDock", resultset.getString("dockpenalty"));
                shift.put("startLunch", resultset.getString("lunchstart"));
                shift.put("stopLunch", resultset.getString("lunchstop"));
                shift.put("lunchTimeDock", resultset.getString("lunchthreshold"));
                
                outputShift = new Shift(shift);
            }
        }
        catch(SQLException e){ System.out.println("Error in badge getShift() " + e);
        }
        
        return outputShift;
    }
    
    public Employee getEmployee(int id){
        Employee outputEmployee = null;
        
        try{
            String query = "SELECT * FROM employee WHERE id = ?;";
            PreparedStatement pstSelect = conn.prepareStatement(query);
            pstSelect.setInt(1, id);
            
            boolean hasresults = pstSelect.execute();
            
            if(hasresults){
                ResultSet resultset = pstSelect.getResultSet();
                
                resultset.next();
                
                HashMap<String, String> shift = new HashMap<>();
                shift.put("id", String.valueOf(id));
                shift.put("badgeid", resultset.getString("badgeid"));
                shift.put("firstname", resultset.getString("firstname"));
                shift.put("middlename", resultset.getString("middlename"));
                shift.put("lastname", resultset.getString("lastname"));
                shift.put("employeetypeid", resultset.getString("employeetypeid"));
                shift.put("departmentid", resultset.getString("departmentid"));
                shift.put("shiftid", resultset.getString("shiftid"));
                shift.put("active", String.valueOf(resultset.getTimestamp("active").toLocalDateTime()));
                if("none".equals(resultset.getString("inactive")) || resultset.getTimestamp("inactive") == null){
                    shift.put("inactive", resultset.getString("inactive"));
                }
                else{
                    shift.put("active", String.valueOf(resultset.getTimestamp("inactive").toLocalDateTime()));
                }
                
                outputEmployee = new Employee(shift);
            }
            
        }
        catch(SQLException e){ System.out.println("Error in id getEmployee() " + e);
        }
        
        return outputEmployee;
    }
    
    public Employee getEmployee(Badge badge){
        Employee outputEmployee = null;
        
        try{
            String query = "SELECT * from employee where badgeid = ?;";
            PreparedStatement pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, badge.getId());
            
            boolean hasresults = pstSelect.execute();
            
            if(hasresults){
                ResultSet resultset = pstSelect.getResultSet();
                
                resultset.next();
                
                HashMap<String, String> shift = new HashMap<>();
                shift.put("id", resultset.getString("id"));
                shift.put("badgeid", resultset.getString("badgeid"));
                shift.put("firstname", resultset.getString("firstname"));
                shift.put("middlename", resultset.getString("middlename"));
                shift.put("lastname", resultset.getString("lastname"));
                shift.put("employeetypeid", resultset.getString("employeetypeid"));
                shift.put("departmentid", resultset.getString("departmentid"));
                shift.put("shiftid", resultset.getString("shiftid"));
                shift.put("active", String.valueOf(resultset.getTimestamp("active").toLocalDateTime()));
                if("none".equals(resultset.getString("inactive")) || resultset.getTimestamp("inactive") == null){
                    shift.put("inactive", resultset.getString("inactive"));
                }
                else{
                    shift.put("active", String.valueOf(resultset.getTimestamp("inactive").toLocalDateTime()));
                }                
                outputEmployee = new Employee(shift);
            }
        }
        catch(SQLException e){ System.out.println("Error in badge getEmployee() " + e);
        }
        
        return outputEmployee;
    }
    
    public Punch getPunch(int id) {
        Punch outputPunch = null;
        
        try{
            String query = "SELECT * FROM event JOIN badge ON event.badgeid = badge.id WHERE event.id = ?;";
            PreparedStatement pstSelect = conn.prepareStatement(query);
            pstSelect.setInt(1, id);
            
            boolean hasresults = pstSelect.execute();
            
            if(hasresults){
                ResultSet resultset = pstSelect.getResultSet();
                
                resultset.next();
                
                HashMap<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                params.put("terminalid", resultset.getString("terminalid"));
                params.put("eventtypeid", resultset.getString("eventtypeid"));
                params.put("timestamp", resultset.getTimestamp("timestamp").toLocalDateTime().toString());

                outputPunch = new Punch(params, getBadge(resultset.getString("badgeid")));
            }
        }
        catch(SQLException e){ System.out.println("Error in badge getPunch() " + e);
        }
        
        return outputPunch;
    }
    
    public Department getDepartment(int id){
        Department outputDept = null;
        
        try{
            String query = "SELECT * FROM department WHERE id = ?;";
            PreparedStatement pstSelect = conn.prepareStatement(query);
            pstSelect.setInt(1, id);
            
            boolean hasresults = pstSelect.execute();
            
            if(hasresults){
                ResultSet resultset = pstSelect.getResultSet();
                
                resultset.next();
                
                HashMap<String, String> shift = new HashMap<>();
                shift.put("id", resultset.getString("id"));
                shift.put("terminalid", resultset.getString("terminalid"));
                shift.put("description", resultset.getString("description"));
                
                outputDept = new Department(shift);
            }
        }
        catch(SQLException e){ System.out.println("Error in badge getPunch() " + e);
        }
        
        return outputDept;
    }
    
    public int insertPunch(Punch punch){
        int newID = 0;
        Badge badge = getBadge(punch.getBadge().getId());
        Employee employee = getEmployee(badge);
        ResultSet keys;
        Department department = getDepartment(employee.getDeptid());
        
        if(punch.getTerminalid() == department.getTerminalid() || punch.getTerminalid() == 0){
            try{
                String query = "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?, ?, ?, ?);";
                PreparedStatement pstSelect = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                pstSelect.setInt(1, punch.getTerminalid());
                pstSelect.setString(2, punch.getBadge().getId());
                pstSelect.setString(3, punch.getOriginalTimestamp().toString());
                pstSelect.setInt(4, punch.getPunchtype().ordinal());
                
                int result = pstSelect.executeUpdate();
                
                if (result == 1) {
                    keys = pstSelect.getGeneratedKeys();
                    if (keys.next()) {
                       newID = keys.getInt(1);
                    }
                }
                
            }
            catch(SQLException e){ 
                System.out.println("Error in insertPunch() " + e);
            }
        }
        
        return newID;
    }
    
    public ArrayList<Punch> getDailyPunchList(Badge badge, LocalDate date) {
        ArrayList<Punch> Punchlist = new ArrayList<>();
        //Query and filling of Arraylist
        try{
            String query = "SELECT *, DATE(`timestamp`) AS tsdate FROM event WHERE badgeid = ? HAVING tsdate = ? ORDER BY timestamp;";
            PreparedStatement pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, badge.getId());
            pstSelect.setString(2, String.valueOf(date));
            
            boolean hasresults = pstSelect.execute();
            
            if(hasresults){
                ResultSet resultset = pstSelect.getResultSet();
                
                while(resultset.next()){
                    int id = resultset.getInt("id");
                    Punch p = getPunch(id);
                    Punchlist.add(p);
                }
                
            }
            
            query = "SELECT *, DATE(`timestamp`) AS tsdate FROM event WHERE badgeid = ? AND eventtypeid != ? HAVING tsdate = ? ORDER BY timestamp;";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, badge.getId());
            pstSelect.setInt(2, 1);
            pstSelect.setString(3, String.valueOf(date.plusDays(1)));
            
            hasresults = pstSelect.execute();
            
            if(hasresults){
                ResultSet resultset = pstSelect.getResultSet();
                int id = resultset.getInt("id");
                Punch p = getPunch(id);
                Punchlist.add(p);
            }
        }
        catch(SQLException e){ 
                System.out.println("Error in getDailyPunchList() " + e);
            }
        
        
        return Punchlist;
    }
    
    public ArrayList<Punch> getPayPeriodPunchList(Badge badge, LocalDate date, Shift shift){
        ArrayList<Punch> Punchlist = new ArrayList<>();
        LocalDate start = date;
        LocalDate end = date;
        if(date.getDayOfWeek() != DayOfWeek.SUNDAY){ //find start of payperiod
            while(start.getDayOfWeek() != DayOfWeek.SUNDAY){
                start = start.minusDays(1);
            }
        }
        if(date.getDayOfWeek() != DayOfWeek.SATURDAY){ //find end of payperiod
            while(end.getDayOfWeek() != DayOfWeek.SATURDAY){
                end = end.plusDays(1);
            }
        }
        
        try{
            String query = "SELECT * FROM event WHERE badgeid = ? AND DATE(timestamp) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) ORDER BY timestamp;";
            PreparedStatement pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, badge.getId());
            pstSelect.setDate(2, Date.valueOf(start));
            pstSelect.setDate(3, Date.valueOf(end));
            
            boolean hasresults = pstSelect.execute();
            
            if(hasresults){
                ResultSet resultset = pstSelect.getResultSet();
                while(resultset.next()){
                    int id = resultset.getInt("id");
                    Punch p = getPunch(id);
                    p.adjust(shift);
                    Punchlist.add(p);
                }
            }
        }
        catch(SQLException e){ System.out.println("Error in getPayPeriodPunchList() " + e);
        }
        
        return Punchlist;
    }
    
    public Absenteeism getAbsenteeism(Badge badge, LocalDate date){
        Absenteeism output = null;
        if(date.getDayOfWeek() != DayOfWeek.SUNDAY){
            while(date.getDayOfWeek() != DayOfWeek.SUNDAY){
                date = date.minusDays(1);
            }
        }
        
        try{
            String query = "SELECT * FROM absenteeism WHERE badgeid = ? AND payperiod = ?;";
            PreparedStatement pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, badge.getId());
            pstSelect.setString(2, String.valueOf(date));
            
            boolean hasresults = pstSelect.execute();
            
            if(hasresults){
                ResultSet resultset = pstSelect.getResultSet();
                
                resultset.next();
                
                HashMap<String, String> params = new HashMap<>();
                params.put("percentage", resultset.getString("percentage"));
                params.put("payperiod", String.valueOf(date));
                
                output = new Absenteeism(params, badge);
            }
        }
        catch(SQLException e){ System.out.println("Error in getAbsenteeism() " + e);
        }
        
        return output;
    }
    
    public int insertAbsenteeism(Absenteeism absence){
        int newID = 0;
        ResultSet keys;
        
        if(getAbsenteeism(absence.getBadge(), absence.getPayperiod()) == null){ //add new record
            try{
                String query = "INSERT INTO absenteeism (badgeid, payperiod, percentage) VALUES (?, ?, ?);";
                PreparedStatement pstSelect = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                pstSelect.setString(1, absence.getBadge().getId());
                pstSelect.setDate(2, Date.valueOf(absence.getPayperiod()));
                pstSelect.setDouble(3, absence.getPercentage());
                
                int result = pstSelect.executeUpdate();
                
                if (result == 1) {
                    keys = pstSelect.getGeneratedKeys();
                    if (keys.next()) {
                       newID = keys.getInt(1);
                    }
                }
                
            }
            catch(SQLException e){ 
                System.out.println("Error in insertAbsenteeism() " + e);
            }
        }
        else{ //replace existing record
            try{
                String query = "REPLACE INTO absenteeism (badgeid, payperiod, percentage) VALUES (?, ?, ?);";
                PreparedStatement pstSelect = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                pstSelect.setString(1, absence.getBadge().getId());
                pstSelect.setDate(2, Date.valueOf(absence.getPayperiod()));
                pstSelect.setDouble(3, absence.getPercentage());
                
                int result = pstSelect.executeUpdate();
                
                if (result == 1) {
                    keys = pstSelect.getGeneratedKeys();
                    if (keys.next()) {
                       newID = keys.getInt(1);
                    }
                }
            }
            catch(SQLException e){ 
                System.out.println("Error in insertAbsenteeism() " + e);
            }
        }
        
        return newID;
    }
}
