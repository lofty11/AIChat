package com.springboot.back.mapper.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "extension_output")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtensionOutputPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "out_id")
    private Long id;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field")
    private String field;

    @Column(name = "type")
    private Integer type;

    @Column(name = "enumeration_range")
    private String enumerationRange;

    @Column(name = "required")
    private Integer required;

    @Column(name = "description")
    private String description;

    @Column(name = "application_id")
    private Long applicationId;
}
