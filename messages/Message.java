package org.de.eloy.fnaf.messages;

import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.files.MessagesFile;

import java.util.ArrayList;
import java.util.List;

public class Message {
    //Config messages
    public static final MessagesFile MESSAGES_FILE = FNAF.getFiles().getMessagesFile();
    private final String PREFIX = MESSAGES_FILE.getMessage("prefix");
    private final String INPUT_NOVALID = MESSAGES_FILE.getMessage("input_noValid");
    private final String NO_PERMISSION = MESSAGES_FILE.getMessage("no_permission");
    private final String GAME_STARTING = MESSAGES_FILE.getMessage("game_starting");
    private final String SELECT_ROLES_TITLE = MESSAGES_FILE.getMessage("selectRoles_title");
    private final String ASSIGNED_ROLE = MESSAGES_FILE.getMessage("selectRoles_assignedRole");
    private final String ROLE_ALREADY_ASSINED = MESSAGES_FILE.getMessage("selectRoles_roleAlreadyAssigned");
    private final String GO_BACK_TITLE = MESSAGES_FILE.getMessage("goBack_title");
    private final String ENTER_CAMERAS = MESSAGES_FILE.getMessage("enter_cameras");
    private final String ARENA_FULL = MESSAGES_FILE.getMessage("arena_full");
    private final String FREDDY_TITLE = MESSAGES_FILE.getMessage("selectRoles_freddy_title");
    private final String BONNIE_TITLE = MESSAGES_FILE.getMessage("selectRoles_bonnie_title");
    private final String CHICA_TITLE = MESSAGES_FILE.getMessage("selectRoles_chica_title");
    private final String FOXY_TITLE = MESSAGES_FILE.getMessage("selectRoles_foxy_title");
    private final String GUARD_TITLE = MESSAGES_FILE.getMessage("selectRoles_guard_title");
    private final List<String> FREDDY_SUBTITLE = MESSAGES_FILE.getDesc("selectRoles_freddy_desc");
    private final List<String> BONNIE_SUBTITLE = MESSAGES_FILE.getDesc("selectRoles_bonnie_desc");
    private final List<String> CHICA_SUBTITLE = MESSAGES_FILE.getDesc("selectRoles_chica_desc");
    private final List<String> FOXY_SUBTITLE = MESSAGES_FILE.getDesc("selectRoles_foxy_desc");
    private final List<String> GUARD_SUBTITLE = MESSAGES_FILE.getDesc("selectRoles_guard_desc");
    private final String JOIN_MESSAGE = MESSAGES_FILE.getMessage("selectRoles_join");
    private final String LEAVE_MESSAGE = MESSAGES_FILE.getMessage("selectRoles_leave");
    private final List<String> SELECTROLES_SCOREBOARD = MESSAGES_FILE.getDesc("selectRoles_scoreboard");
    private final List<String> ANIMATRONIC_SCOREBOARD = MESSAGES_FILE.getDesc("gameAnimatronic_scoreboard");
    private final List<String> GUARD_SCOREBOARD = MESSAGES_FILE.getDesc("gameGuard_scoreboard");

    private final String DOOR_COOLDOWN = MESSAGES_FILE.getMessage("door_cooldown");

    //Getters
    public String getPREFIX() {
        return PREFIX.replace("&","§");
    }
    public String getINPUT_NOVALID() {
        return INPUT_NOVALID.replace("&","§");
    }
    public String getNO_PERMISSION() {
        return NO_PERMISSION.replace("&","§");
    }
    public String getGAME_STARTING() {
        return GAME_STARTING.replace("&","§");
    }
    public String getASSIGNED_ROLE() {
        return ASSIGNED_ROLE.replace("&","§");
    }

    public String getROLE_ALREADY_ASSINED() {
        return ROLE_ALREADY_ASSINED.replace("&","§");
    }
    public String getSELECT_ROLES_TITLE() {
        return SELECT_ROLES_TITLE.replace("&","§");
    }
    public String getGO_BACK_TITLE() {
        return GO_BACK_TITLE.replace("&","§");
    }
    public String getENTER_CAMERAS() {
        return ENTER_CAMERAS.replace("&","§");
    }
    public String getARENA_FULL() {
        return ARENA_FULL.replace("&","§");
    }
    public String getFREDDY_TITLE() {
        return FREDDY_TITLE.replace("&","§");
    }
    public String getBONNIE_TITLE() {
        return BONNIE_TITLE.replace("&","§");
    }
    public String getCHICA_TITLE() {
        return CHICA_TITLE.replace("&","§");
    }
    public String getFOXY_TITLE() {
        return FOXY_TITLE.replace("&","§");
    }
    public String getGUARD_TITLE() {
        return GUARD_TITLE.replace("&","§");
    }
    public List<String> getFREDDY_SUBTITLE() {
        List<String> title = new ArrayList<>();
        for (String text : FREDDY_SUBTITLE) {
            title.add(text.replace("&","§"));
        }
        return title;
    }
    public List<String> getBONNIE_SUBTITLE() {
        List<String> title = new ArrayList<>();
        for (String text : BONNIE_SUBTITLE) {
            title.add(text.replace("&","§"));
        }
        return title;
    }
    public List<String> getCHICA_SUBTITLE() {
        List<String> title = new ArrayList<>();
        for (String text : CHICA_SUBTITLE) {
            title.add(text.replace("&","§"));
        }
        return title;
    }
    public List<String> getFOXY_SUBTITLE() {
        List<String> title = new ArrayList<>();
        for (String text : FOXY_SUBTITLE) {
            title.add(text.replace("&","§"));
        }
        return title;
    }
    public List<String> getGUARD_SUBTITLE() {
        List<String> title = new ArrayList<>();
        for (String text : GUARD_SUBTITLE) {
            title.add(text.replace("&","§"));
        }
        return title;
    }
    public String getJOIN_MESSAGE() {
        return JOIN_MESSAGE.replace("&","§");
    }

    public String getLEAVE_MESSAGE() {
        return LEAVE_MESSAGE.replace("&","§");
    }

    public List<String> getSELECTROLES_SCOREBOARD() {
        List<String> title = new ArrayList<>();
        for (String text : SELECTROLES_SCOREBOARD) {
            title.add(text.replace("&","§"));
        }
        return title;
    }

    public List<String> getANIMATRONIC_SCOREBOARD() {
        List<String> title = new ArrayList<>();
        for (String text : ANIMATRONIC_SCOREBOARD) {
            title.add(text.replace("&","§"));
        }
        return title;
    }

    public List<String> getGUARD_SCOREBOARD() {
        List<String> title = new ArrayList<>();
        for (String text : GUARD_SCOREBOARD) {
            title.add(text.replace("&","§"));
        }
        return title;
    }
    public String getDOOR_COOLDOWN() {
        return DOOR_COOLDOWN.replace("&","§");
    }
}
