package yavs.model.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Notification {
    private String username;
    private String lobbyName;
    private String text;
//    private String time;
}
