package ConnectionObject;

import java.util.List;

/**
 * Created by Francois on 13/01/2016.
 */
public class PlanningObject {

    private String acti_title = null;
    private String title_module = null;
    private String start = null;
    private String end = null;
    private Boolean module_registered;
    private Boolean event_registered;
    private RoomObject room = null;
    private String scolaryear = null;
    private String codemodule = null;
    private String codeinstance = null;
    private String codeacti = null;
    private String codeevent = null;

    public class RoomObject {
        private String code;
        private Integer seats;

        public Integer getSeats() {
            return seats;
        }

        public void setSeats(Integer seats) {
            this.seats = seats;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public String getActi_title() {
        return acti_title;
    }

    public void setActi_title(String acti_title) {
        this.acti_title = acti_title;
    }

    public String getTitle_module() {
        return title_module;
    }

    public void setTitle_module(String title_module) {
        this.title_module = title_module;
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

    public RoomObject getRoom() {
        return room;
    }

    public void setRoom(RoomObject room) {
        this.room = room;
    }

    public Boolean getModule_registered() {
        return module_registered;
    }

    public void setModule_registered(Boolean module_registered) {
        this.module_registered = module_registered;
    }

    public String getCodemodule() {
        return codemodule;
    }

    public void setCodemodule(String codemodule) {
        this.codemodule = codemodule;
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

    public String getCodeevent() {
        return codeevent;
    }

    public void setCodeevent(String codeevent) {
        this.codeevent = codeevent;
    }

    public String getScolaryear() {
        return scolaryear;
    }

    public void setScolaryear(String scolaryear) {
        this.scolaryear = scolaryear;
    }

    public Boolean getEvent_registered() {
        return event_registered;
    }

    public void setEvent_registered(Boolean event_registered) {
        this.event_registered = event_registered;
    }
}