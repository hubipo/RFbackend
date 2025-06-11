package org.links.anonymouschatroomservice.dto;

import lombok.Data;

@Data
public class MatchRequest {
    private String phoneNumber;
    private String tag;

}
