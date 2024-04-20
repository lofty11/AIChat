package com.springboot.back.mapper.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "extension_input")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtensionInputPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "input_id")
    private Long id;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field")
    private String field;

    @Column(name = "type")
    private Long type;

    @Column(name = "enumeration_range")
    private String enumerationRange;

    @Column(name = "required")
    private Integer required;

    @Column(name = "description")
    private String description;

    @Column(name = "application_id")
    private Long applicationId;

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
