package com.task.priyanka.gitIssueTracker.utils;

public class AppConstant {

    public interface ApiUrl {
        String GIT_ISSUES = "issues";
    }

    public interface LastSyncTime {
        String LAST_SYNC_TIME = "lastSyncTime";
    }

    public interface Time {
        long SECOND1 = 1000L;
        long MINUTES1 = 60 * SECOND1;
        long MINUTES30 = 30 * MINUTES1;
        long HOUR = 2 * MINUTES30;
        long HOUR24 = 24 * HOUR;
        long DAYS15 = HOUR24 * 15;
    }
}
