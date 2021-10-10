package io.github.talelin.latticy.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.*;
import io.github.talelin.latticy.bo.BannerWithItemsBO;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.BannerDTO;
import io.github.talelin.latticy.model.BannerDO;
import io.github.talelin.latticy.service.BannerService;
import io.github.talelin.latticy.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@RequestMapping("/v1/banner")
@RestController
@Validated
@PermissionModule(value = "Banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @PostMapping
    @PermissionMeta(value = "创建Banner")
    @GroupRequired
    public CreatedVO create(@RequestBody @Validated BannerDTO dto){
        BannerDO bannerDO = new BannerDO();
        BeanUtils.copyProperties(dto,bannerDO);
        this.bannerService.save(bannerDO);
        return new CreatedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    @PermissionMeta(value = "查询Banner")
    @Logger(template = "{user.username}查询了Banner数据")
    public BannerWithItemsBO getWithItems(@PathVariable @Positive Long id){
        return bannerService.getBannerWithItems(id);
    }

    @DeleteMapping("/{id}")
    @PermissionMeta(value = "删除Banner")
    @GroupRequired
    public DeletedVO delete(@PathVariable @Positive Long id){
        bannerService.delete(id);
        return new DeletedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta(value = "更新Banner")
    @GroupRequired
    public UpdatedVO update(@RequestBody @Validated BannerDTO dto,
                       @PathVariable @Positive Long id){
        bannerService.update(dto,id);
        return new UpdatedVO();
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<BannerDO> getBanners(@RequestParam(required = false,defaultValue = "0")
                               @Min(value = 0) Integer page,
                                     @RequestParam(required = false,defaultValue = "10")
                           @Min(value = 1) @Max(value = 30) Integer count){
        Page<BannerDO> pager = new Page<>(page,count);
        IPage<BannerDO> paging = bannerService.getBaseMapper().selectPage(pager,null);
        return new PageResponseVO<>(paging.getTotal(),paging.getRecords(),paging.getCurrent(),paging.getSize());
    }
}
