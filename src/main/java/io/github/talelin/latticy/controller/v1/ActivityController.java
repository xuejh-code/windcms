package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.ActivityDTO;
import io.github.talelin.latticy.model.ActivityDetailDO;
import io.github.talelin.latticy.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.talelin.latticy.model.ActivityDO;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

/**
* @author generator@TaleLin
* @since 2021-07-23
*/
@RestController
@RequestMapping("/v1/activity")
@Validated
@PermissionModule("活动")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @PostMapping("")
    @PermissionMeta("创建活动")
    public CreatedVO create(@RequestBody @Validated ActivityDTO dto) {
        activityService.create(dto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新活动")
    public UpdatedVO update(
            @RequestBody @Validated ActivityDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        activityService.update(dto,id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("/删除活动")
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        ActivityDO activity = activityService.getById(id);
        if (activity == null){
            throw new NotFoundException(90000);
        }
        activityService.getBaseMapper().deleteById(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}/detail")
    public ActivityDetailDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        ActivityDetailDO activityDetailDO = activityService.getDetailById(id);
        return activityDetailDO;
    }

    @GetMapping("/page")
    public PageResponseVO<ActivityDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<ActivityDO> pager = new Page<>(page,count);
        IPage<ActivityDO> paging = activityService.getBaseMapper().selectPage(pager,null);
        return PageUtil.build(paging);
    }

}
