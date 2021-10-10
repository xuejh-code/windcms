package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.service.OrderService;
import io.github.talelin.latticy.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import io.github.talelin.latticy.model.OrderDO;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
* @author generator@TaleLin
* @since 2021-09-03
*/
@RestController
@RequestMapping("/v1/order")
@PermissionModule("订单")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PutMapping("/status")
    @PermissionMeta("修改订单状态")
    public UpdatedVO update(
            @RequestParam(name="id") @Positive Integer id,
            @RequestParam(name="status") @Min(value = 0) Integer status) {
        orderService.changeOrderStatus(id,status);
        return new UpdatedVO();
    }

    @GetMapping("/{id}/detail")
    public OrderDO getDetail(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        OrderDO order = orderService.getById(id);
        if (order == null){
            throw new NotFoundException(110000);
        }
        return order;
    }

    @GetMapping("/page")
    public PageResponseVO<OrderSimplifyVO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        IPage<OrderDO> iPage = orderService.getOrderByPage(page,count);
        List<OrderSimplifyVO> orderSimplifyVOList = orderService.convertFromDO(iPage.getRecords());
        return PageUtil.build(iPage,orderSimplifyVOList);
    }

    @GetMapping("/search")
    public PageResponseVO<OrderSimplifyVO> search(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end
    ) {
        IPage<OrderDO> iPage = orderService.search(page, count, keyword, start, end);
        List<OrderSimplifyVO> orderSimplifyVOList = orderService.convertFromDO(iPage.getRecords());
        return PageUtil.build(iPage, orderSimplifyVOList);
    }

}
