package Entity;

public class Projet {
    private String title;
    private String start;
    private String end;
    private float progress;
    private String date_inscription;
    private String title_link;

    public Projet(String title, String start, String end, float progress, String date_inscription, String title_link) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.progress = progress;
        this.date_inscription = date_inscription;
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

    public String getDate_inscription() {
        return date_inscription;
    }

    public void setDate_inscription(String date_inscription) {
        this.date_inscription = date_inscription;
    }

    public String getTitle_link() {
        return title_link;
    }

    public void setTitle_link(String title_link) {
        this.title_link = title_link;
    }
}