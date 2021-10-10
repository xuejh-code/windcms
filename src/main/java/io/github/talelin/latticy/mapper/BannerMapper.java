package io.github.talelin.latticy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.talelin.latticy.model.BannerDO;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BannerMapper extends BaseMapper<BannerDO> {
    List<BannerDO> getAllBanners();

    Long insertBanner(BannerDO bannerDO);
}
