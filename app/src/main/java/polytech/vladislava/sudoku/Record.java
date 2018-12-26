package polytech.vladislava.sudoku;

class Record implements Comparable<Record>{

    private final String name;
    private final String time;
    private final int tips;

    public Record(String name, String time, int tips) {
        this.name = name;
        this.time = time;
        this.tips = tips;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getTips() {
        return tips;
    }

    public int getTimeValue(){
        String[] times = time.trim().split(":");
        return Integer.valueOf(times[0]) * 60 + Integer.valueOf(times[1]);
    }


    @Override
    public int compareTo(Record o) {
        return Integer.compare(getTimeValue(), o.getTimeValue());
    }
}
