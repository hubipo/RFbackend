package org.links.driftingbottleservice.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户漂流瓶状态响应 DTO。
 * <p>用于返回用户当前持有的漂流瓶和仍在海洋中的漂流瓶数量。</p>
 */
@Data
public class UserBottleStatusResponse {

    /**
     * 用户当前手中的漂流瓶列表（即已回收到背包的漂流瓶）。
     */
    private List<BottleResponse> inHandBottles;

    /**
     * 当前仍在海洋中的漂流瓶数量。
     */
    private Integer inOceanBottleCount;
}
