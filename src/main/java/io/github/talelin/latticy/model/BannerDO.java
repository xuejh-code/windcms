package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("banner")
public class BannerDO {
    private Integer id;
    private String description;
    private String img;
    private String title;
    private String name;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
//    @TableLogic
    private Date deleteTime;
}
