package org.links.driftingbottleservice.dto;

/**
 * 漂流瓶评论请求 DTO。
 * <p>用于提交对某个漂流瓶的评论内容。</p>
 */
public class BottleCommentRequest {

    /**
     * 评论者的手机号。
     * <p>用于标识当前用户。</p>
     */
    private String phoneNumber;

    /**
     * 评论的文本内容。
     */
    private String content;

    /**
     * 获取评论者手机号。
     *
     * @return 手机号
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 设置评论者手机号。
     *
     * @param phoneNumber 手机号
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 获取评论内容。
     *
     * @return 评论文本
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评论内容。
     *
     * @param content 评论文本
     */
    public void setContent(String content) {
        this.content = content;
    }
}
