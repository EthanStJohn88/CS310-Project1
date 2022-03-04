
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
                shift.put("id", id);
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
        Badge badge;
        
        try{
            query = "SELECT * FROM event JOIN badge ON event.badgeid = badge.id WHERE event.id = ?;";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setInt(1, id);
            
            hasresults = pstSelect.execute();
            
            if(hasresults){
                ResultSet resultset = pstSelect.getResultSet();
                
                resultset.next();
                
                HashMap<String, String> shift = new HashMap<>();
                shift.put("terminalid", resultset.getString("terminalid"));
                shift.put("eventtypeid", resultset.getString("eventtypeid"));
                shift.put("badgeid", resultset.getString("badgeid"));
                shift.put("timestamp", String.valueOf(resultset.getTimestamp("timestamp").toLocalDateTime())); //Use LocalDateTime parse with formatter?
                
                HashMap<String, String> params = new HashMap<>();
                shift.put("id", resultset.getString("badge.id"));
                shift.put("description", resultset.getString("description"));
                
                badge = new Badge(params);
                
                outputPunch = new Punch(shift, badge);
            }
        }
        catch(SQLException e){ System.out.println("Error in badge getPunch() " + e);
        }
        
        return outputPunch;
    }
    
}
