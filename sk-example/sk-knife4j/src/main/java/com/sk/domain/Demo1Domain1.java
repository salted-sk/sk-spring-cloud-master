package com.sk.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2020/11/28 22:27
 */
@Data
public class Demo1Domain1 {

    @ApiModelProperty(value = "名字")
    String name;

    @ApiModelProperty(value = "年龄")
    Integer age;

}
