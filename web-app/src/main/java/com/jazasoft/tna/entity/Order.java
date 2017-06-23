package com.jazasoft.tna.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mtalam on 6/10/2017.
 */
@Entity
@Table(name="orders", indexes = @Index(name = "order_index",columnList = "order_ref,label_id"))
public class Order implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_ref", nullable=false, unique = true )
    private String orderRef;

    @Column(name="style", nullable = false)
    private String style;

    @Column(name ="season", nullable = false)
    private String season;

    @Column(name="qty", nullable = false)
    private Long qty;

    @Column(name="order_at" , nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderAt;

    @JsonIgnore
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name="label_id", nullable=false,foreignKey = @ForeignKey(name = "label_order_fk"))
    private Label label;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified")
    private  Date lastModified;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetail> orderDetails =  new HashSet<>();


    public Order() {
    }

    public Order(Long id, String orderRef, String style, String season, Long qty, Date orderAt) {
        this.id = id;
        this.orderRef = orderRef;
        this.style = style;
        this.season = season;
        this.qty = qty;
        this.orderAt = orderAt;
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

    public Date getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(Date orderAt) {
        this.orderAt = orderAt;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void addOrderDetail(OrderDetail orderDetail){
        orderDetail.setOrder(this);
        orderDetails.add(orderDetail);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderRef='" + orderRef + '\'' +
                ", style='" + style + '\'' +
                ", season='" + season + '\'' +
                ", qty=" + qty +
                ", orderAt=" + orderAt +
                ", lastModified=" + lastModified +
                '}';
    }
}
