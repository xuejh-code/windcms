package io.github.talelin.latticy.mapper;

import io.github.talelin.latticy.model.SpuDO;
import io.github.talelin.latticy.model.ThemeSpuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-05
 */
@Repository
public interface ThemeSpuMapper extends BaseMapper<ThemeSpuDO> {
    List<SpuDO> getSpus(Integer id);
}
