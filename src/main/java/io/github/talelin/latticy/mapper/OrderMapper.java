package io.github.talelin.latticy.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.model.OrderDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-03
 */
@Repository
public interface OrderMapper extends BaseMapper<OrderDO> {
    /**
     * 修改订单状态
     * @param status 状态
     * @param id 订单id
     * @return int
     */
    int changeOrderStatus(@Param("status") Integer status, @Param("id") Integer id);

    /**
     * 搜索订单
     * @param pager 分页对象
     * @param keyword 关键字
     * @param start 开始时间
     * @param end 结束时间
     * @return IPage
     */
    IPage<OrderDO> searchOrders(Page<OrderDO> pager, String keyword, Date start, Date end);

//    OrderDO getById(Integer id);
}
