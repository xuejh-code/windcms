package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.BannerDTO;
import io.github.talelin.latticy.dto.BannerItemDTO;
import io.github.talelin.latticy.service.BannerItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.talelin.latticy.model.BannerItemDO;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

/**
* @author generator@TaleLin
* @since 2021-07-29
*/
@RestController
@RequestMapping("/v1/banner-item")
@Validated
@PermissionModule("Banner Item")
public class BannerItemController {
    @Autowired
    private BannerItemService bannerItemService;

    @PostMapping("")
    @PermissionMeta("创建Banner Item")
    public CreatedVO create(@RequestBody @Validated BannerItemDTO bannerItemDTO) {
        BannerItemDO bannerItemDO = new BannerItemDO();
        BeanUtils.copyProperties(bannerItemDTO,bannerItemDO);
        bannerItemService.save(bannerItemDO);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新Banner Item")
    public UpdatedVO update(
            @RequestBody @Validated BannerItemDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        bannerItemService.update(dto,id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除Banner Item")
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        bannerItemService.delete(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    public BannerItemDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        BannerItemDO bannerItem = bannerItemService.getById(id);
        if (bannerItem == null){
            throw new NotFoundException(20001);
        }
        return bannerItem;
    }

    @GetMapping("/page")
    public PageResponseVO<BannerItemDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<BannerItemDO> pager = new Page<>(page,count);
        IPage<BannerItemDO> paging = bannerItemService.getBaseMapper().selectPage(pager,null);
        return PageUtil.build(paging);
    }

}
