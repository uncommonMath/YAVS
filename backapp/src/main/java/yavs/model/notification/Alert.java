package yavs.model.notification;

import lombok.Data;

@Data
public class Alert {
    private Long lobbyId;
    private Long roomId;
    private Long userId;
    private String text;
}
