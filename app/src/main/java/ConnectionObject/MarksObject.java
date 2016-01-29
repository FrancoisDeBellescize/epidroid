package ConnectionObject;

import java.util.List;

/**
 * Created by Francois on 13/01/2016.
 */
public class MarksObject {

    private List<Note> notes = null;

    public class Note{
        private String scolaryear = null;
        private String codemodule = null;
        private String titlemodule = null;
        private String codeinstance = null;
        private String codeacti = null;
        private String title = null;
        private String date = null;
        private String correcteur = null;
        private String final_note = null;
        private String comment = null;

        public String getScolaryear() {
            return scolaryear;
        }

        public void setScolaryear(String scolaryear) {
            this.scolaryear = scolaryear;
        }

        public String getCodemodule() {
            return codemodule;
        }

        public void setCodemodule(String codemodule) {
            this.codemodule = codemodule;
        }

        public String getTitlemodule() {
            return titlemodule;
        }

        public void setTitlemodule(String titlemodule) {
            this.titlemodule = titlemodule;
        }

        public String getCodeinstance() {
            return codeinstance;
        }

        public void setCodeinstance(String codeinstance) {
            this.codeinstance = codeinstance;
        }

        public String getCodeacti() {
            return codeacti;
        }

        public void setCodeacti(String codeacti) {
            this.codeacti = codeacti;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCorrecteur() {
            return correcteur;
        }

        public void setCorrecteur(String correcteur) {
            this.correcteur = correcteur;
        }

        public String getFinal_note() {
            return final_note;
        }

        public void setFinal_note(String final_note) {
            this.final_note = final_note;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}