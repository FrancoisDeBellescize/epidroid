package Entity;

public class Projet {
    private String title;
    private String start;
    private String end;
    private float progress;

    public Projet(String title, String start, String end, float progress) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.progress = progress;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}