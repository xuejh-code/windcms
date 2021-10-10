package io.github.talelin.latticy.controller.v1;


import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.dto.SpecValueDTO;
import io.github.talelin.latticy.service.SpecValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.talelin.latticy.model.SpecValueDO;
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
* @since 2021-08-02
*/
@RestController
@RequestMapping("/v1/spec-value")
@Validated
@PermissionModule("规格值")
public class SpecValueController {
    @Autowired
    private SpecValueService specValueService;

    @PostMapping("")
    @PermissionMeta("创建规格值")
    public CreatedVO create(@RequestBody @Validated SpecValueDTO dto) {
        specValueService.createSpecValue(dto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新规格值")
    public UpdatedVO update(
            @RequestBody @Validated SpecValueDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        specValueService.updateSpecValue(dto,id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除规格值")
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        specValueService.deleteSpecValue(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    public SpecValueDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        SpecValueDO specValue = specValueService.getById(id);
        if (specValue == null){
            throw new NotFoundException(60002);
        }
        return specValue;
    }

    @GetMapping("/page")
    public PageResponseVO<SpecValueDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        return null;
    }

    @GetMapping("/by/spec-key/{id}")
    public List<SpecValueDO> getBySpecKeyId(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id){
        return this.specValueService.lambdaQuery().eq(SpecValueDO::getSpecId,id).list();
    }

}
