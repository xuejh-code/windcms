package io.github.talelin.latticy.controller.v1;


import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.dto.GridCategoryDTO;
import io.github.talelin.latticy.service.GridCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.talelin.latticy.model.GridCategoryDO;
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
* @since 2021-09-04
*/
@RestController
@RequestMapping("/v1/grid-category")
@Validated
@PermissionModule("六宫格")
public class GridCategoryController {

    @Autowired
    private GridCategoryService gridCategoryService;

    @PostMapping("")
    @PermissionMeta("创建六宫格")
    public CreatedVO create(@RequestBody @Validated GridCategoryDTO dto) {
        gridCategoryService.createGridCategory(dto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新六宫格")
    public UpdatedVO update(
            @RequestBody @Validated GridCategoryDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        gridCategoryService.updateGridCategory(dto,id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除六宫格")
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        gridCategoryService.deleteGridCategory(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    public GridCategoryDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        GridCategoryDO gridCategory = gridCategoryService.getById(id);
        if (gridCategory == null){
            throw new NotFoundException(50000);
        }
        return gridCategory;
    }

    @GetMapping("/page")
    public PageResponseVO<GridCategoryDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        return null;
    }

    @GetMapping("/list")
    public List<GridCategoryDO> getList(){
        return gridCategoryService.list();
    }

}
