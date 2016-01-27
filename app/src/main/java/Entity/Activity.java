package Entity;

public class Activity {
    private String title;
    private String start;
    private String end;
    private String token;
    private float progress;

    public Activity(String title, String start, String end, float progress, String token) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.progress = progress;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}