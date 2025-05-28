package org.links.usermanagementservice.dto;

import lombok.Data;

import java.util.List;

/**
 * 批量操作请求数据传输对象。
 * <p>用于批量封禁或解封用户等操作。</p>
 */
@Data
public class BatchOperationRequest {

    /**
     * 用户 ID 列表。
     * <p>需要执行操作的用户的唯一标识列表。</p>
     */
    private List<Long> userIds;

    /**
     * 操作原因（如封禁理由）。
     * <p>对用户执行操作的说明或原因，通常用于封禁。</p>
     */
    private String reason;
}
