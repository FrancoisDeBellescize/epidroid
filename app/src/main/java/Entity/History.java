package Entity;

public class History {
    private String title;
    private String content;
    private String end;
    private float progress;

    public History(String title, String content, String end) {
        this.title = title;
        this.content = content;
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}