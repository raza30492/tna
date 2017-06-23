package com.jazasoft.tna.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by mtalam on 6/10/2017.
 */
@Entity
@Table(name="order_detail")
public class OrderDetail implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "completed_at")
    private Date completedAt;

    @Column(name ="remarks")
    private  String remarks;


    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name="order_id",foreignKey = @ForeignKey(name = "order_orderDetails_fk"))
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name="activity_id",foreignKey = @ForeignKey(name = "activity_orderDetails_fk"))
    private Activity activity ;

    public OrderDetail() {
    }

    public OrderDetail(Long id, Date completedAt, String remarks) {
        this.id = id;
        this.completedAt = completedAt;
        this.remarks = remarks;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", completedAt=" + completedAt +
                ", remarks='" + remarks + '\'' +
                ", order=" + order +
                ", activity=" + activity +
                '}';
    }
}
