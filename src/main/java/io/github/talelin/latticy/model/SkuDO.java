package io.github.talelin.latticy.model;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.github.talelin.latticy.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-07-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sku")
public class SkuDO extends BaseModel {


    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer online;

    private String img;

    private String title;

    private Integer spuId;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<SpecKeyValueDO> specs;

    private String code;

    private Integer stock;

    private Integer categoryId;

    private Integer rootCategoryId;


}
