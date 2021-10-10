package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.dto.ThemeSpuDTO;
import io.github.talelin.latticy.mapper.ThemeSpuMapper;
import io.github.talelin.latticy.model.SimplifySpuDO;
import io.github.talelin.latticy.model.SpuDO;
import io.github.talelin.latticy.model.ThemeDO;
import io.github.talelin.latticy.mapper.ThemeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.latticy.model.ThemeSpuDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-05
 */
@Service
public class ThemeService extends ServiceImpl<ThemeMapper, ThemeDO> {
    @Autowired
    private ThemeSpuMapper themeSpuMapper;

    public List<SimplifySpuDO> getSimplifySpus(Integer id){
        return this.getBaseMapper().getSimplifySpus(id);
    }

    public List<SpuDO> getSpus(Integer id){
        return themeSpuMapper.getSpus(id);
    }

    public void addThemeSpu(ThemeSpuDTO dto){
        ThemeSpuDO themeSpu = new ThemeSpuDO();
        themeSpu.setThemeId(dto.getThemeId());
        themeSpu.setSpuId(dto.getSpuId());
        themeSpuMapper.insert(themeSpu);
    }

    public void deleteThemeSpu(Integer id){
        themeSpuMapper.deleteById(id);
    }
}
