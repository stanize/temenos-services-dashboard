package com.acn.servicesdashboard.service;

public class TsmService {

    // Simulate fetching the TSM status
    public String getTsmStatus() {
        // Later this will call TAPJREST or OFS
        return "STARTED";
    }

    // Simulate restarting the TSM
    public String restartTsm() {
        // Later this will call TAPJREST or OFS to restart the service
        return "TSM restarted successfully!";
    }
}
