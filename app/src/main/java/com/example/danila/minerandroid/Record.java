package com.example.danila.minerandroid;

class Record implements Comparable<Record> {

    private final String name;
    private final String time;
    private final int value;

    public Record(String name, String time) {
        this.name = name;
        this.time = time;

        String splitedTime[] = time.trim().split(":");
        this.value = Integer.valueOf(splitedTime[0]) * 60 + Integer.valueOf(splitedTime[1]);
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Record o) {
        return Integer.compare(value,o.value);
    }
}
