package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.bo.BannerWithItemsBO;
import io.github.talelin.latticy.dto.BannerDTO;
import io.github.talelin.latticy.mapper.BannerItemMapper;
import io.github.talelin.latticy.mapper.BannerMapper;
import io.github.talelin.latticy.model.BannerDO;
import io.github.talelin.latticy.model.BannerItemDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService extends ServiceImpl<BannerMapper, BannerDO> {

    @Autowired
    private BannerItemMapper bannerItemMapper;

    public BannerWithItemsBO getBannerWithItems(Long id){
        BannerDO banner = this.getById(id);
        if (banner == null){
            throw new NotFoundException(20000);
        }

        QueryWrapper<BannerItemDO> wrapper = new QueryWrapper<>();
//        wrapper.eq("banner_id",id);
//        wrapper.lambda().eq(bannerItemDO -> bannerItemDO.getBannerId(),id);
        wrapper.lambda().eq(BannerItemDO::getBannerId,id);
        List<BannerItemDO> bannerItems = bannerItemMapper.selectList(wrapper);

        return new BannerWithItemsBO(banner,bannerItems);
    }

    public void delete(Long id){
        BannerDO banner = this.getById(id);
        if (banner == null){
            throw new NotFoundException(20000);
        }
        this.getBaseMapper().deleteById(id);
    }

    public void update(BannerDTO dto,Long id){
        BannerDO bannerDO = this.getById(id);
        if (bannerDO == null){
            throw new NotFoundException(20000);
        }
        BeanUtils.copyProperties(dto,bannerDO);
        this.updateById(bannerDO);
    }
}
