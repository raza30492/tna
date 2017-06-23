package com.jazasoft.tna.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by mtalam on 6/10/2017.
 */
@Entity
@Table(name="activity")
public class Activity implements Serializable {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotNull
    @Column(name="lead_time", nullable = false)
    private Long leadTime;

    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private Long activityNameId;

    @ManyToOne(optional = false)
    @JoinColumn(name="activity_name_id",foreignKey = @ForeignKey(name = "activityName_activity_fk"))
    private ActivityName activityName;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name="label_id",foreignKey = @ForeignKey(name = "label_activity_fk"))
    private Label label;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_modified")
    private Date lastModified;


    public Activity() {
    }

    public Activity(Long id,  Long leadTime, Long activityNameId) {
        this.id = id;
        this.leadTime = leadTime;
        this.activityNameId=activityNameId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Long leadTime) {
        this.leadTime = leadTime;
    }

    public ActivityName getActivityName() {
        return activityName;
    }

    public void setActivityName(ActivityName activityName) {
        this.activityName = activityName;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Long getActivityNameId() {
        return activityNameId;
    }

    public void setActivityNameId(Long activityNameId) {
        this.activityNameId = activityNameId;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", leadTime=" + leadTime +


                ", lastModified=" + lastModified +
                '}';
    }
}
