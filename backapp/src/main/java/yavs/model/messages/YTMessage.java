package yavs.model.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YTMessage {
    private String from;
    private String text;
    private Integer state;
    private Double time;
}
