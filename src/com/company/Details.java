package com.company;

import java.util.ArrayList;
import java.util.List;

public enum Details {
    BODY, HEAD, LEFT_FOOT, LEFT_HAND, RIGHT_FOOT, RIGHT_HAND;

    public static Details getDetails() {
        return values()[(int)(Math.random() * values().length)];
    }
}
