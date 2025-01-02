package org.links.driftingbottleservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserBottleStatusResponse {
    private List<BottleResponse> inHandBottles;
    private Integer inOceanBottleCount;
}
