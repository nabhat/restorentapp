package com.proj.customerservice.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeDifferenceChecker {

    public static boolean isDifference10Minutes(LocalDateTime time1, LocalDateTime time2) {
        // Calculate the difference in duration between two LocalDateTime variables
        Duration duration = Duration.between(time1, time2);

        // Check if the duration is exactly 10 minutes (600 seconds)
        return Math.abs(duration.toMinutes()) < 10;
    }
}