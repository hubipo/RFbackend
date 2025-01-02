package org.links.usermanagementservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class BatchOperationRequest {
    private List<Long> userIds;
    private String reason;
}
