package com.sky.parentalcontrol;

import java.util.Objects;

public enum ParentalControlLevel {
    U, PG, TWELVE, FIFTEEN, EIGHTEEN;

    public static ParentalControlLevel from(String parentalControlLevel) {
        if (Objects.nonNull(parentalControlLevel)) {
            switch (parentalControlLevel) {
                case "U":
                    return ParentalControlLevel.U;
                case "PG":
                    return ParentalControlLevel.PG;
                case "12":
                    return ParentalControlLevel.TWELVE;
                case "15":
                    return ParentalControlLevel.FIFTEEN;
                case "18":
                    return ParentalControlLevel.EIGHTEEN;
            }
        }
        throw new IllegalArgumentException();
    }
}
