package polytech.vladislava.sudoku;

class Record {

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
}
