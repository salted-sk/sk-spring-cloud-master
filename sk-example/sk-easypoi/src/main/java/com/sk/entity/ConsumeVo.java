package com.sk.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class ConsumeVo {
    @ExcelIgnore
    private Long consumeId;
    @ExcelProperty(value = "批次号", index = 0)
    @ColumnWidth(35)
    private String batchNo;
    @ExcelIgnore
    private Long merchantId;
    @ExcelProperty(value = "商户名称", index = 1)
    @ColumnWidth(20)
    private String merchantName;
    @ExcelIgnore
    private Long accountId;
    @ExcelIgnore
    private Long productId;
    @ExcelProperty(value = "产品名称", index = 2)
    @ColumnWidth(20)
    private String productName;
    @ExcelIgnore
    private Long accessId;
    @ExcelIgnore
    private Byte accessType;
    @ExcelIgnore
    private String statusCode;
    @ExcelIgnore
    private String resultCode;
    @ExcelIgnore
    private Date requestTime;
    @ExcelProperty(value = "费用(单位：元)", index = 3)
    @ColumnWidth(25)
    private BigDecimal amount;
    /**
     * 0:未支付, 1:已支付
     */
    @ColumnWidth(20)
    private Byte status;
    @ExcelIgnore
    private Date createTime;
    @ExcelIgnore
    private Date updateTime;
}
