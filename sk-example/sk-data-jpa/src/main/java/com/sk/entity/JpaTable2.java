package com.sk.entity;

import com.sk.enu.JpaEnum;
import lombok.Data;

import javax.persistence.*;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2020/4/17 15:20
 */
@Entity
@Table
@Data
public class JpaTable2 {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    //采用枚举类型的序号值与数据库进行交互  从0开始，
    //此时数据库的数据类型需要是数值类型
    @Enumerated(EnumType.ORDINAL)
    private JpaEnum jpaEnum;

    //采用枚举类型与数据库进行交互，
    //此时数据库的数据类型需要是NVACHAR2等字符串类型
    @Enumerated(EnumType.STRING)
    private JpaEnum jpaEnum2;

}
