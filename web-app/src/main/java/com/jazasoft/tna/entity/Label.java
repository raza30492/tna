package com.jazasoft.tna.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mtalam on 6/10/2017.
 */
@Entity
@Table(name = "LABEL")
public class Label implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String label;

    @ManyToOne(optional = false)
    @JoinColumn(name="buyer_id",foreignKey = @ForeignKey(name = "buyer_label_fk"))
    private Buyer buyer;

    @OneToMany(mappedBy = "label")
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "label")
    private Set<Activity> activities = new HashSet<>();

    public Label() {

    }

    public Label(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", buyer=" + buyer +
                ", orders=" + orders +
                ", activities=" + activities +
                '}';
    }
}
