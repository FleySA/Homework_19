package com.proftelran.org.homework_19;

import java.time.LocalTime;

class Horse implements Runnable {

    private String name;
    private int step;
    private int position;

    public Horse(String name, int step) {
        this.name = name;
        this.step = step == 0 ? 1 : step;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void nextStep() {
        position += step;
    }

    @Override
    public void run() {
        while (position <= HippodromeApp.LOOP_LENGTH) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nextStep();
        }
        System.out.println("Horse " + name + " is finished " + LocalTime.now());
        HippodromeApp.finishedHorses.add(this);
        HippodromeApp.resultMap.put(this, System.currentTimeMillis());
    }
}