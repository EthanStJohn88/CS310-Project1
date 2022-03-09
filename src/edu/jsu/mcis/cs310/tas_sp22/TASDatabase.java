
package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class TASDatabase {
    
    private Connection conn = null;
    private String query;
    private PreparedStatement pstSelect = null, pstUpdate = null;    
    private boolean hasresults;
    private int updateCount;    
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
        catch(Exception e){}
        
    }
    
    
    public void close(){
        try{
            conn.close();
        }
        catch(SQLException e){
        }
        finally{
            if(pstSelect != null){
                
                pstSelect = null;
                
            }
        }
    }
        
    
    public Badge getBadge(String id){
        Badge outputBadge = null;
        
        try{
            query = "SELECT * FROM badge WHERE id = ?;";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, id);
            
            hasresults = pstSelect.execute();
            
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
            query = "SELECT * FROM shift WHERE id = ?;";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setInt(1, id);
            
            hasresults = pstSelect.execute();
            
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
            query = "SELECT * FROM employee JOIN shift ON employee.shiftid = shift.id WHERE badgeid = ?;";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, badge.getId());
            
            hasresults = pstSelect.execute();
            
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
            query = "SELECT * FROM employee WHERE id = ?;";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setInt(1, id);
            
            hasresults = pstSelect.execute();
            
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
                if(resultset.getString("inactive") == "none" || resultset.getTimestamp("inactive") == null){
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
            query = "SELECT * from employee where badgeid = ?;";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, badge.getId());
            
            hasresults = pstSelect.execute();
            
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
                if(resultset.getString("inactive") == "none" || resultset.getTimestamp("inactive") == null){
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
    
    public Punch getPunch(int id){
        Punch outputPunch = null;
        
        try{
            query = "SELECT * FROM event JOIN badge ON event.badgeid = badge.id WHERE event.id = ?;";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setInt(1, id);
            
            hasresults = pstSelect.execute();
            
            if(hasresults){
                ResultSet resultset = pstSelect.getResultSet();
                
                resultset.next();
                
                HashMap<String, String> params = new HashMap<>();
                params.put("terminalid", resultset.getString("terminalid"));
                params.put("eventtypeid", resultset.getString("eventtypeid"));
                params.put("timestamp", String.valueOf(resultset.getTimestamp("timestamp").toLocalDateTime()));

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
            query = "SELECT * FROM department WHERE id = ?;";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setInt(1, id);
            
            hasresults = pstSelect.execute();
            
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
        int newID = 0, result = 0;
        Badge badge = getBadge(punch.getBadge().getId());
        Employee employee = getEmployee(badge);
        ResultSet keys;
        Department department = getDepartment(employee.getDeptid());
        
        if(punch.getTerminalid() == department.getTerminalid() || punch.getTerminalid() == 0){
            try{
                query = "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?, ?, ?, ?);";
                pstSelect = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                pstSelect.setInt(1, punch.getTerminalid());
                pstSelect.setString(2, punch.getBadge().getId());
                pstSelect.setString(3, String.valueOf(punch.getOriginalTimestamp()));
                pstSelect.setInt(4, punch.getPunchtype().ordinal());
                
                result = pstSelect.executeUpdate();
                
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
    
}
