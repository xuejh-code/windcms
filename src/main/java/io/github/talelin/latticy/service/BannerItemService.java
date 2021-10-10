package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.dto.BannerItemDTO;
import io.github.talelin.latticy.model.BannerItemDO;
import io.github.talelin.latticy.mapper.BannerItemMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-07-29
 */
@Service
public class BannerItemService extends ServiceImpl<BannerItemMapper, BannerItemDO> {

    public void update(BannerItemDTO dto,Integer id){
        BannerItemDO bannerItem = this.getById(id);
        if (bannerItem == null){
            throw new NotFoundException(20001);
        }
        BeanUtils.copyProperties(dto,bannerItem);
        this.updateById(bannerItem);
    }

    public void delete(Integer id){
        BannerItemDO bannerItem = this.getById(id);
        if (bannerItem == null){
            throw new NotFoundException(20001);
        }
        this.getBaseMapper().deleteById(id);
    }
}
