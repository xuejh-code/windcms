package io.github.talelin.latticy.mapper;

import io.github.talelin.latticy.model.SimplifySpuDO;
import io.github.talelin.latticy.model.ThemeDO;
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
public interface ThemeMapper extends BaseMapper<ThemeDO> {
    List<SimplifySpuDO> getSimplifySpus(Integer id);
}
