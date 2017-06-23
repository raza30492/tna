package com.jazasoft.tna.dto;

import java.util.Date;

/**
 * Created by Mojahid Hussain on 19-Jun-17.
 */
public class OrderDetailDto {

    private Long id;

    private Date completedAt;

    private  String remarks;

    private Long orderId;

    private Long activityId ;

    public OrderDetailDto() {
    }

    public OrderDetailDto(Long id, Date completedAt, String remarks, Long orderId, Long activityId) {
        this.id = id;
        this.completedAt = completedAt;
        this.remarks = remarks;
        this.orderId = orderId;
        this.activityId = activityId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    @Override
    public String toString() {
        return "OrderDetailDto{" +
                "id=" + id +
                ", completedAt=" + completedAt +
                ", remarks='" + remarks + '\'' +
                ", orderId=" + orderId +
                ", activityId=" + activityId +
                '}';
    }
}
