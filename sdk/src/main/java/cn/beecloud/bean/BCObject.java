package cn.beecloud.bean;

import java.util.Date;

/**
 * BeeCloud基础类，标识BeeCloud基本对象
 *
 * @author Rui.Feng
 * @since 2016.7.26
 */
public class BCObject {

    private Date createDate;

    private Date updateDate;

    private String type;

    private String objectId;

    /**
     * 访问字段 {@link #createDate}
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置字段 {@link #createDate}
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 访问字段 {@link #updateDate}
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置字段 {@link #updateDate}
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 访问字段 {@link #type}
     */
    public String getType() {
        return type;
    }

    /**
     * 设置字段 {@link #type}
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 访问字段 {@link #objectId}
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * 设置字段 {@link #objectId}
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
