package com.springboot.back.mapper.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_service")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationServicePo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @Column(name = "api_name")
    private String apiName;

    @Column(name = "api_code")
    private String apiCode;

    @Column(name = "request_url")
    private String requestUrl;

    @Column(name = "request_method")
    private Integer requestMethod;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "creator_name")
    private String creatorName;

    @Column(name = "modifier_id")
    private Long modifierId;

    @Column(name = "modifier_name")
    private String modifierName;

    @Column(name = "gmt_create")
    private LocalDateTime gmtCreate;

    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;
}
