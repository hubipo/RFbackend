package org.links.driftingbottleservice.dto;

/**
 * 漂流瓶创建请求 DTO。
 * <p>用于客户端提交创建新漂流瓶的内容。</p>
 */
public class BottleRequest {

    /**
     * 用户手机号。
     * <p>用于通过用户系统获取发布者的用户 ID。</p>
     */
    private String phoneNumber;

    /**
     * 漂流瓶的内容。
     * <p>即用户希望投放的文本信息。</p>
     */
    private String content;

    /**
     * 获取手机号。
     *
     * @return 用户手机号
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 设置手机号。
     *
     * @param phoneNumber 用户手机号
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 获取漂流瓶内容。
     *
     * @return 漂流瓶文本内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置漂流瓶内容。
     *
     * @param content 漂流瓶文本内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}
