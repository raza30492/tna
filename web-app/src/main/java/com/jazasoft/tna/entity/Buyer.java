package com.jazasoft.tna.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;


/**
 * Created by mtalam on 6/10/2017.
 */
@Entity
@Table(name = "BUYER", indexes = @Index(name = "buyer_index",columnList = "name"))
public class Buyer implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "buyer")
    private Set<Label> labels = new HashSet<>();

    @ManyToMany(mappedBy = "buyers")
    private Set<User> users = new HashSet<>();

    @Column(name = "freezed")
    private boolean freezed ;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified")
    private Date lastModified ;


    public Buyer() {
    }

    public Buyer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFreezed() {
        return freezed;
    }

    public void setFreezed(boolean freezed) {
        this.freezed = freezed;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public Set<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", labels=" + labels +
                ", users=" + users +
                ", freezed=" + freezed +
                ", lastModified=" + lastModified +
                '}';
    }
}
