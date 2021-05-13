package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Thread {


    private static final List<Details> STORAGE_OF_EARTH = new ArrayList<>();
    private static final List<Details> STORAGE_OF_MARS = new ArrayList<>();
    public static final List<Details> ROBOT = new ArrayList<>();
    private static int earthRobotsNumber;
    private static int marsRobotsNumber;
    private static final int MAXSCORE = 20;
    private static boolean win = false;

    public static void main(String[] args) throws InterruptedException {

        Collections.addAll(ROBOT, Details.values());
        System.out.println(ROBOT);

        Factory factory = new Factory();
        Runnable detailsCreating = factory::creating;

        Thread theEarth = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!win) {
                    for (int i = 0; i < factory.DETAILS_STORAGE.size(); i++) {
                        if (!STORAGE_OF_EARTH.contains(factory.DETAILS_STORAGE.get(i))) {
                            synchronized (factory.DETAILS_STORAGE) {
                                STORAGE_OF_EARTH.add(factory.DETAILS_STORAGE.get(i));
                                System.out.println("The Earth took the detail " + factory.DETAILS_STORAGE.get(i));
                                factory.DETAILS_STORAGE.remove(i);
                            }
                        }
                    }
                    /* (STORAGE_OF_EARTH.equals(ROBOT))*/
                    if (STORAGE_OF_EARTH.size() == 6) {
                        earthRobotsNumber++;
                        STORAGE_OF_EARTH.clear();
                        System.out.println("__________The EARTH has " + earthRobotsNumber + " robots.__________");
                    }
                }
            }
        });

        Thread theMars = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!win) {
                    for (int i = 0; i < factory.DETAILS_STORAGE.size(); i++) {
                        if (!STORAGE_OF_MARS.contains(factory.DETAILS_STORAGE.get(i))) {
                            synchronized (factory.DETAILS_STORAGE) {
                                STORAGE_OF_MARS.add(factory.DETAILS_STORAGE.get(i));
                                System.out.println("The Mars took the detail " + factory.DETAILS_STORAGE.get(i));
                                factory.DETAILS_STORAGE.remove(i);
                            }
                        }
                    }
                    /* (STORAGE_OF_EARTH.equals(ROBOT))*/
                    if (STORAGE_OF_MARS.size() == 6) {
                        marsRobotsNumber++;
                        STORAGE_OF_MARS.clear();
                        System.out.println("__________The MARS has " + marsRobotsNumber + " robots.__________");
                    }
                }
            }
        });

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
        executor.scheduleAtFixedRate(detailsCreating, 0, 500, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(theEarth,0,500,TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(theMars,0,500,TimeUnit.MILLISECONDS);

        Runnable stop = new Runnable() {
            @Override
            public void run() {
                if ((earthRobotsNumber >= MAXSCORE) || (marsRobotsNumber >= MAXSCORE)) {
                    executor.shutdown();
                    win = true;
                } else {
                    executor.schedule(this, 500, TimeUnit.MILLISECONDS);
                }
            }
        };

        executor.schedule(stop,1,TimeUnit.SECONDS);

        System.out.println("The Winner: " + (earthRobotsNumber >= MAXSCORE ? "THE EARTH" : "THE MARS"));

    }
}

