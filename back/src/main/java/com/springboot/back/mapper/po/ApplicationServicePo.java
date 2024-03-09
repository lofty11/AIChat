package com.springboot.back.mapper.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
