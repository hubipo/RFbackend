package org.links.driftingbottleservice.enums;

/**
 * 漂流瓶状态枚举。
 * <p>用于标识漂流瓶当前所处的状态。</p>
 */
public enum BottleStatus {

    /**
     * 漂流瓶在海洋中，可被他人捞取。
     */
    IN_OCEAN,

    /**
     * 漂流瓶已被回收到用户背包，不可再被捞取。
     */
    RECYCLED
}
