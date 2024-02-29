package com.diy.rental.repository;

import com.diy.rental.exception.StatusCode400Exception;
import com.diy.rental.model.AvailableTool;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentMap;

@Repository
public class AvailableToolRepository {
    ConcurrentMap<String, AvailableTool> availableTools = MockDatabase.availableTools;
    public ConcurrentMap<String, AvailableTool> getAvailableTool() {
        return MockDatabase.availableTools;
    }

    public String getToolType(String toolCode) {
        return availableTools.get(toolCode).getToolType() ;
    }

    public String getToolBrand(String toolCode) {
        return availableTools.get(toolCode).getToolBrand();
    }
}
