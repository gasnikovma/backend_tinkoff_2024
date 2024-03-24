package edu.java.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatLink {
    private long chatId;
    private long linkId;

}
