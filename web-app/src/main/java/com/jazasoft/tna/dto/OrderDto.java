package com.jazasoft.tna.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jazasoft.tna.entity.Label;
import com.jazasoft.tna.entity.OrderDetail;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mtalam on 6/14/2017.
 */
public class OrderDto {

    private Long id;

    @JsonIgnore
    private Label label;

    @NotNull
    @Size(min = 1, max = 100)
    private String orderRef;

    @NotNull
    @Size(min = 1, max = 100)
    private String style;

    @NotNull
    @Size(min = 1, max = 100)
    private String season;

    @NotNull
    @Min(1)
    private Long qty;

    private long orderAt;

    private  long lastModified;

    private Set<OrderDetail> orderDetails =  new HashSet<>();

    public OrderDto() {
    }

    public OrderDto(String orderRef, String style, String season, Long qty, long orderAt, Label label, long lastModified) {
        this.orderRef = orderRef;
        this.style = style;
        this.season = season;
        this.qty = qty;
        this.orderAt = orderAt;
        this.label = label;
        this.lastModified = lastModified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public long getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(long orderAt) {
        this.orderAt = orderAt;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", orderRef='" + orderRef + '\'' +
                ", style='" + style + '\'' +
                ", season='" + season + '\'' +
                ", qty=" + qty +
                ", orderAt=" + orderAt +
                ", label=" + label +
                ", lastModified=" + lastModified +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
