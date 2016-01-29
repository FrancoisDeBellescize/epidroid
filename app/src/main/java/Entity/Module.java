package Entity;

public class Module {
    private String title;
    private String start;
    private String end;
    private float progress;
    private String title_link;

    public Module(String title, String start, String end, float progress, String title_link) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.progress = progress;
        this.title_link = title_link;
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

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getTitle_link() {
        return title_link;
    }

    public void setTitle_link(String title_link) {
        this.title_link = title_link;
    }
}