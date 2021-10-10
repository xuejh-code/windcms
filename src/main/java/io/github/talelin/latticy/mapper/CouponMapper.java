package io.github.talelin.latticy.mapper;

import io.github.talelin.latticy.model.CouponDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-03
 */
@Repository
public interface CouponMapper extends BaseMapper<CouponDO> {
    List<Integer> getCouponsByActivityId(@Param("id") Integer id);
}
