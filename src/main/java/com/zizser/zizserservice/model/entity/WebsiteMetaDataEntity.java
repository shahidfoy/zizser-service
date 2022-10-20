package com.zizser.zizserservice.model.entity;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Website_Meta_Data")
public class WebsiteMetaDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String url;
    private String title;
    private String description;
    private String imageLink;
    private String type;
    private Long viewCount;
    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;
}
