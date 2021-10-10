package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.CouponDTO;
import io.github.talelin.latticy.dto.CouponTemplateDTO;
import io.github.talelin.latticy.model.CouponTemplateDO;
import io.github.talelin.latticy.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.talelin.latticy.model.CouponDO;
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
* @since 2021-09-03
*/
@RestController
@RequestMapping("/v1/coupon")
@Validated
@PermissionModule("优惠券")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @PostMapping("")
    @PermissionMeta("创建优惠券")
    public CreatedVO create(@RequestBody @Validated CouponDTO dto) {
        couponService.create(dto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新优惠券")
    public UpdatedVO update(
            @RequestBody @Validated CouponDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        couponService.update(dto,id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除优惠券")
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        couponService.delete(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    public CouponDO get(@PathVariable(value = "id") @Positive(message = "{id}") Integer id) {
        CouponDO coupon = couponService.getById(id);
        if (coupon == null){
            throw new NotFoundException(100000);
        }
        return coupon;
    }

    @GetMapping("/page")
    public PageResponseVO<CouponDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<CouponDO> pager = new Page<>(page,count);
        IPage<CouponDO> paging = couponService.getBaseMapper().selectPage(pager,null);
        return PageUtil.build(paging);
    }

    @GetMapping("/template/{id}")
    public CouponTemplateDO getTemplate(@PathVariable @Positive(message = "{id}") Integer id){
        return couponService.getTemplate(id);
    }

    @PostMapping("/template")
    @PermissionMeta("创建优惠券模版")
    public CreatedVO createTemplate(@RequestBody @Validated CouponTemplateDTO dto){
        couponService.createTemplate(dto);
        return new CreatedVO();
    }

    @PutMapping("/template/{id}")
    @PermissionMeta("更新优惠券模版")
    public UpdatedVO updateTemplate(
            @RequestBody @Validated CouponTemplateDTO dto,
            @PathVariable @Positive(message = "{id}") Integer id
    ){
        couponService.updateTemplate(dto,id);
        return new UpdatedVO();
    }

    @DeleteMapping("/template/{id}")
    @PermissionMeta("删除优惠券模版")
    public DeletedVO deleteTemplate(@PathVariable @Positive(message = "{id}") Integer id){
        couponService.deleteTemplate(id);
        return new DeletedVO();
    }

    @GetMapping("/templates")
    public List<CouponTemplateDO> getTemplates(){
        List<CouponTemplateDO> templates = couponService.getTemplates();
        return templates;
    }

    @GetMapping("/list")
    public List<CouponDO> getListByActivityId(@RequestParam(name = "id") @Min(value = 1, message = "{id}") Integer id){
        List<CouponDO> coupons = couponService.getListByActivityId(id);
        return coupons;
    }
}
