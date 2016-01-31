package ConnectionObject;

import java.util.List;

/**
 * Created by Francois on 13/01/2016.
 */
public class PlanningObject {

    private List<Planning> plannings = null;

    public class Planning{
        private String acti_title = null;
        private String title_module = null;
        private String start = null;
        private String end = null;

        private RoomObject room = null;

        public class RoomObject{
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
    }

    public List<Planning> getPlanning() {
        return plannings;
    }

    public void setPlanning(List<Planning> plannings) {
        this.plannings = plannings;
    }
}