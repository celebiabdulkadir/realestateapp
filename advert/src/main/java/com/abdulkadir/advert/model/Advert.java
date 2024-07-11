package com.abdulkadir.advert.model;

import com.abdulkadir.advert.config.JpaAuditingConfig;
import com.abdulkadir.advert.model.enums.AdvertStatus;
import com.abdulkadir.advert.model.enums.AdvertType;
import com.abdulkadir.advert.model.enums.Heating;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "/advert/")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Number userId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "price")
    private Number price;

    @Column(name = "area")
    private Number area;

    @Column(name = "room")
    private Number room;

    @Column(name = "floor")
    private Number floor;

    @Column(name = "total_floor")
    private Number totalFloor;

    @Column(name = "heating")
    private Heating heating;

    @Column(name = "balcony")
    private Boolean balcony;

    @Column(name = "elevator")
    private Boolean elevator;

    @Column(name = "from_home_owner")
    private Boolean fromHomeOwner;

    @Column(name = "from_agency")
    private Boolean fromAgency;

    @Column(name = "furnished")
    private Boolean furnished;

    @Column(name = "credit")
    private Boolean credit;

    @Column(name = "swap")
    private Boolean swap;

    @Column(name = "advert_type") // For Sale or For Rent
    private AdvertType advertType;

    @CreatedDate
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "update_date", insertable = false)
    private LocalDateTime updateDate;

    @Builder.Default
    @Column(name = "advert_status")
    private AdvertStatus advertStatus = AdvertStatus.IN_REVIEW;
}