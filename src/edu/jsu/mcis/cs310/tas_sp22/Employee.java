package edu.jsu.mcis.cs310.tas_sp22;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Employee 
{
    private int id,emptype,dept, shiftid;
    private LocalDateTime active, inactive;
    private String badgeid,first,middle,last;

    public Employee(HashMap <String, String> data)
    {
        this.id = Integer.parseInt(data.get("id"));
        this.badgeid = data.get("badgeid");
        this.first = data.get("firstname");
        this.middle = data.get("middlename");
        this.last = data.get("lastname");
        this.emptype = Integer.parseInt(data.get("employeetypeid"));
        this.dept = Integer.parseInt(data.get("departmentid"));
        this.shiftid = Integer.parseInt(data.get("shiftid"));
        this.active = LocalDateTime.parse(data.get("active"));
        if(data.get("inactive") == "none" || data.get("inactive") == null ){
            this.inactive = null;
        }
        else{
            this.inactive = LocalDateTime.parse(data.get("inactive")); //Null in database
        }
        
    }

    public int getId() {
        return id;
    }

    public int getEmpltype() {
        return emptype;
    }

    public int getDeptid() {
        return dept;
    }

    public String getBadgeid() {
        return badgeid;
    }

    public String getFname() {
        return first;
    }

    public String getMname() {
        return middle;
    }
    

    public String getLname() {
        return last;
    }

    public int getShiftid() {
        return shiftid;
    }

    public LocalDateTime getActive() {
        return active;
    }

    public LocalDateTime getInactive() {
        return inactive;
    }
    
    

    @Override
    public String toString() {    
        StringBuilder result = new StringBuilder();
        result.append("#").append(badgeid).append(" (").append(last).append(", ").append(first).append(" ").append(middle)
            .append("): employeetypeid: ").append(emptype).append(", departmentid: ").append(dept).append(", shiftid: ")
            .append(shiftid).append(", active: ").append(active.toLocalDate()).append(", inactive: ");
      
        if(inactive == null){
            result.append("none");
        }
        else{
            result.append(inactive);
        }
            
        return result.toString();  
    }

}