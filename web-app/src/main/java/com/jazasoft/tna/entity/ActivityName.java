package com.jazasoft.tna.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by mtalam on 6/11/2017.
 */
@Entity
@Table(name = "activity_name")
public class ActivityName implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_modified")
    private Date lastModified;

    public ActivityName() {
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

    @Override
    public String toString() {
        return "ActivityName{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }
}
