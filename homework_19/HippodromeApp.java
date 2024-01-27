package com.proftelran.org.homework_19;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;



public class HippodromeApp {

    public static final int LOOP_LENGTH = 1000;
    public static final Map<Horse, Long> resultMap = new ConcurrentHashMap<>();
    public static final Set<Horse> finishedHorses = Collections.synchronizedSet(new HashSet<>());
    public static final List<Horse> horses = new ArrayList<>();

    public static void main(String[] args) {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            horses.add(new Horse("a" + i, random.nextInt(5)));
        }

        List<Thread> threads = new ArrayList<>();
        horses.forEach(horse -> threads.add(new Thread(horse)));

        Thread resultMapThread = new Thread(() -> {
            while (finishedHorses.size() < horses.size()) {
                try {
                    Thread.sleep(2300);
                    monitorResults();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        resultMapThread.setDaemon(true);

        threads.forEach(Thread::start);
        resultMapThread.start();

        try {
            resultMapThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void monitorResults() {
        if (finishedHorses.size() == horses.size()) {
            List<Horse> sortedHorses = new ArrayList<>(finishedHorses);
            sortedHorses.sort(Comparator.comparingInt(Horse::getPosition));

            System.out.println("Результаты гонки:");
            int place = 1;
            for (Horse horse : sortedHorses) {
                System.out.println("Место " + place + ": " + horse.getName() +
                        " финишировала на позиции " + horse.getPosition());
                place++;
            }
            System.exit(0);
        }
    }
}
