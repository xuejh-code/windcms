package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.SpuDTO;
import io.github.talelin.latticy.model.SpuDetailDO;
import io.github.talelin.latticy.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.talelin.latticy.model.SpuDO;
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
* @since 2021-07-29
*/
@RestController
@RequestMapping("/v1/spu")
@Validated
@PermissionModule("SPU")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @PostMapping("")
    @PermissionMeta("创建SPU")
    public CreatedVO create(@RequestBody @Validated SpuDTO dto) {
        this.spuService.create(dto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新SPU")
    public UpdatedVO update(
            @RequestBody @Validated SpuDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        spuService.update(dto,id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除SPU")
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        spuService.delete(id);
        return new DeletedVO();
    }

    @GetMapping("/key")
    public List<Integer> getSpecKeys(@RequestParam(name = "id") @Positive(message = "{id}") Integer id) {
        return spuService.getBaseMapper().getSpecKeys(id);
    }

    @GetMapping("/{id}/detail")
    public SpuDetailDO getDetail(@PathVariable @Positive(message = "{id.positive}") Integer id){
        SpuDetailDO detail = this.spuService.getDetail(id);
        return detail;
    }

    @GetMapping("/page")
    public PageResponseVO<SpuDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        Page<SpuDO> pager = new Page<>(page,count);
        IPage<SpuDO> paging = spuService.getBaseMapper().selectPage(pager,null);
        return PageUtil.build(paging);
    }

    @GetMapping("/list")
    public List<SpuDO> getList(){
        return spuService.list();
    }

}
