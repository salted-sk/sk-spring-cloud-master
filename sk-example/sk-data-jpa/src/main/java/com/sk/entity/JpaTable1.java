package com.sk.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2020/4/15 14:21
 */
@Data
@Table
@Entity
@org.hibernate.annotations.Table(appliesTo = "jpa_table1",comment="表注释...")
public class JpaTable1 {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(unique = true, columnDefinition = "varchar(100) COMMENT '字典名'")
    private String name;

    @JsonIgnore
    private String userName;

}
