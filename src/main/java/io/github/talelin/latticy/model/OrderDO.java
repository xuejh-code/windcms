package io.github.talelin.latticy.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "`order`",autoResultMap = true)
public class OrderDO extends BaseModel {

    private String orderNo;

    /**
     * user表外键
     */
    private Integer userId;

    private BigDecimal totalPrice;

    private Integer totalCount;

    private String snapImg;

    private String snapTitle;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Map<String, Object>> snapItems;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, String> snapAddress;

    private String prepayId;

    private BigDecimal finalTotalPrice;

    private Integer status;

    private Date expiredTime;

    private Date placedTime;
}
