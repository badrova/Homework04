package com.company;

import java.util.ArrayList;
import java.util.List;

public class Factory {

    public final List<Details> DETAILS_STORAGE = new ArrayList<>();
    private static Details newDetails;

    public void creating() {
        newDetails = Details.getDetails();
        DETAILS_STORAGE.add(newDetails);
        System.out.println(newDetails + " created");
    }
}


