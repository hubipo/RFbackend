package org.links.driftingbottleservice.dto;

/**
 * 漂流瓶响应 DTO。
 * <p>用于向客户端返回漂流瓶的基本信息。</p>
 */
public class BottleResponse {

    /**
     * 漂流瓶 ID。
     */
    private Long id;

    /**
     * 漂流瓶拥有者的用户 ID。
     */
    private Long ownerId;

    /**
     * 漂流瓶的文本内容。
     */
    private String content;

    /**
     * 漂流瓶的创建时间（格式为字符串）。
     */
    private String createdAt;

    /**
     * 构造函数。
     *
     * @param id 漂流瓶 ID
     * @param ownerId 拥有者用户 ID
     * @param content 漂流瓶内容
     * @param createdAt 创建时间字符串
     */
    public BottleResponse(Long id, Long ownerId, String content, String createdAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.content = content;
        this.createdAt = createdAt;
    }

    /**
     * 获取漂流瓶 ID。
     *
     * @return 漂流瓶 ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置漂流瓶 ID。
     *
     * @param id 漂流瓶 ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取拥有者用户 ID。
     *
     * @return 拥有者 ID
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * 设置拥有者用户 ID。
     *
     * @param ownerId 拥有者 ID
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * 获取漂流瓶内容。
     *
     * @return 文本内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置漂流瓶内容。
     *
     * @param content 文本内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取漂流瓶创建时间。
     *
     * @return 创建时间字符串
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置漂流瓶创建时间。
     *
     * @param createdAt 创建时间字符串
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
