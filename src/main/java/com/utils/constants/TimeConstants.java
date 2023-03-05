package com.utils.constants;

import java.util.concurrent.TimeUnit;

public interface TimeConstants {
    int MIN_TO_MILES = (int) TimeUnit.MINUTES.toMillis(1);
    int MIN_TO_SECS = (int) TimeUnit.MINUTES.toSeconds(1);
}
