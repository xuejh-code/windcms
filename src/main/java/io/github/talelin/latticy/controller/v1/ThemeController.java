package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.ThemeDTO;
import io.github.talelin.latticy.dto.ThemeSpuDTO;
import io.github.talelin.latticy.model.SimplifySpuDO;
import io.github.talelin.latticy.model.SpuDO;
import io.github.talelin.latticy.service.ThemeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.talelin.latticy.model.ThemeDO;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.util.List;

/**
* @author generator@TaleLin
* @since 2021-09-05
*/
@RestController
@RequestMapping("/v1/theme")
@Validated
@PermissionModule("主题")
public class ThemeController {
    @Autowired
    private ThemeService themeService;

    @PostMapping("")
    @PermissionMeta("创建主题")
    public CreatedVO create(@RequestBody @Validated ThemeDTO dto) {
        ThemeDO theme = new ThemeDO();
        BeanUtils.copyProperties(dto,theme);
        themeService.save(theme);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新主题")
    public UpdatedVO update(
            @RequestBody @Validated ThemeDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        ThemeDO theme = themeService.getById(id);
        if (theme == null){
            throw new NotFoundException(30000);
        }
        BeanUtils.copyProperties(dto,theme);
        themeService.updateById(theme);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除主题")
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        ThemeDO theme = themeService.getById(id);
        if (theme == null){
            throw new NotFoundException(30000);
        }
        themeService.getBaseMapper().deleteById(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    public ThemeDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        ThemeDO theme = themeService.getById(id);
        if (theme == null){
            throw new NotFoundException(30000);
        }
        return theme;
    }

    @GetMapping("/page")
    public PageResponseVO<ThemeDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<ThemeDO> pager = new Page<>(page,count);
        IPage<ThemeDO> paging = themeService.getBaseMapper().selectPage(pager,null);
        return PageUtil.build(paging);
    }

    @GetMapping("/spus")
    public List<SimplifySpuDO> getSimplifySpus(@RequestParam(name = "id") @Positive(message = "{id}") Integer id){
        return themeService.getSimplifySpus(id);
    }

    @GetMapping("/spu/list")
    public List<SpuDO> getSpuList(@RequestParam(name = "id") @Positive(message = "id") Integer id){
        return themeService.getSpus(id);
    }

    @PostMapping("/spu")
    @PermissionMeta("创建专题下的SPU")
    public CreatedVO addThemeSpu(@RequestBody @Validated ThemeSpuDTO dto){
        themeService.addThemeSpu(dto);
        return new CreatedVO();
    }

    @DeleteMapping("/spu/{id}")
    @PermissionMeta("删除专题下的SPU")
    public DeletedVO deletedThemeSpu(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id){
        themeService.deleteThemeSpu(id);
        return new DeletedVO();
    }

}
