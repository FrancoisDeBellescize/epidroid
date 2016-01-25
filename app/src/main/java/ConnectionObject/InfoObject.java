package ConnectionObject;

import java.util.List;

/**
 * Created by Francois on 13/01/2016.
 */
public class InfoObject {
    String ip = null;
    BoardObject board = null;

    public class BoardObject {
        private List<ProjetObject> projets = null;
        private List<ModuleObject> modules = null;

        public class ModuleObject {
            String title = null;
            String title_link = null;
            String timeline_start = null;
            String timeline_end = null;
            String timeline_barre = null;
            String date_inscription = null;

            public String getTitle() {
                return title;
            }

            public String getTitle_link() {
                return title_link;
            }

            public String getTimeline_start() {
                return timeline_start;
            }

            public String getTimeline_end() {
                return timeline_end;
            }

            public String getTimeline_barre() {
                return timeline_barre;
            }

            public String getDate_inscription() {
                return date_inscription;
            }
        }
        public class ProjetObject {
            String title = null;
            String title_link = null;
            String timeline_start = null;
            String timeline_end = null;
            String timeline_barre = null;
            String date_inscription = null;
            String id_activite = null;
            String soutenance_name = null;
            String soutenance_link = null;
            String soutenance_date = null;
            String soutenance_salle = null;

            public String getTitle() {
                return title;
            }

            public String getTitle_link() {
                return title_link;
            }

            public String getTimeline_start() {
                return timeline_start;
            }

            public String getTimeline_end() {
                return timeline_end;
            }

            public String getTimeline_barre() {
                return timeline_barre;
            }

            public String getDate_inscription() {
                return date_inscription;
            }

            public String getId_activite() {
                return id_activite;
            }

            public String getSoutenance_name() {
                return soutenance_name;
            }

            public String getSoutenance_link() {
                return soutenance_link;
            }

            public String getSoutenance_date() {
                return soutenance_date;
            }

            public String getSoutenance_salle() {
                return soutenance_salle;
            }
        }

        public List<ProjetObject> getProjets() {
            return projets;
        }
        public List<ModuleObject> getModules() {
            return modules;
        }
    }

    public BoardObject getBoard() {
        return board;
    }

    public String getIp() {
        return ip;
    }
}