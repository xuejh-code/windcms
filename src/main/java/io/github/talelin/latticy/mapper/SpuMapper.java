package io.github.talelin.latticy.mapper;

import io.github.talelin.latticy.model.SpuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.talelin.latticy.model.SpuDetailDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-07-29
 */
public interface SpuMapper extends BaseMapper<SpuDO> {

    SpuDetailDO getDetail(Integer id);
    /**
     * 获取指定spu的规格id列表
     * @param id spu的id
     * @return spu关联的规格id列表
     */
    List<Integer> getSpecKeys(@Param("id") Integer id);
}
