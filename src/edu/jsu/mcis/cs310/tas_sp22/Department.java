package edu.jsu.mcis.cs310.tas_sp22;

import java.util.HashMap;
import java.time.LocalDateTime;

public class Department {
    
    private int id, terminalid;
    private String description;

    public Department(HashMap<String, String> params) {
        this.id = Integer.parseInt(params.get("id"));
        this.terminalid = Integer.parseInt(params.get("terminalid"));
        this.description = params.get("description");
    }

    public int getId() {
        return id;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("#").append(id).append(" (").append(description).append("): terminalid: ").append(terminalid);
        
        return result.toString();
    }

}
