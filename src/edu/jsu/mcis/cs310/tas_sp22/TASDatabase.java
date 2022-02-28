
package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.time.LocalTime;
class TASDatabase {
    
    private Connection conn = null;
    private String query;
    private PreparedStatement pstSelect = null, pstUpdate = null;    
    private boolean hasresults;
    private int updateCount;
    
    
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
                try{
                    pstSelect = null;
                    
                }
              
                catch(SQLException e){
              
                }
            }
        }
    }
        
    
    public Badge getBadge(String id){
        Badge outputBadge = null;
        
        try{
            query = "SELECT * from badge where id = ?";
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
        Badge outputBadge = null;
        
        try{
            query = "SELECT * from badge where id = ?";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setInt(1, id);
            
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
    
}
