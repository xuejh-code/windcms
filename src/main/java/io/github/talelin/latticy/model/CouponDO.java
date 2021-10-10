package io.github.talelin.latticy.model;

import java.math.BigDecimal;
import io.github.talelin.latticy.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
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
@TableName("coupon")
public class CouponDO extends BaseModel {


    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String description;

    private BigDecimal fullMoney;

    private BigDecimal minus;

    private BigDecimal rate;

    /**
     * 1. 满减券 2.折扣券 3.无门槛券 4.满金额折扣券
     */
    private Integer type;

    private Integer activityId;

    private String remark;

    private Integer wholeStore;


}
