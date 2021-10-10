package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.dto.SpecValueDTO;
import io.github.talelin.latticy.mapper.SkuMapper;
import io.github.talelin.latticy.mapper.SkuSpecMapper;
import io.github.talelin.latticy.model.SkuDO;
import io.github.talelin.latticy.model.SkuSpecDO;
import io.github.talelin.latticy.model.SpecKeyValueDO;
import io.github.talelin.latticy.model.SpecValueDO;
import io.github.talelin.latticy.mapper.SpecValueMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-08-02
 */
@Service
public class SpecValueService extends ServiceImpl<SpecValueMapper, SpecValueDO> {
    @Autowired
    private SkuSpecMapper skuSpecMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpecValueMapper specValueMapper;

    public void createSpecValue(SpecValueDTO dto){
        SpecValueDO specValue = new SpecValueDO();
        BeanUtils.copyProperties(dto,specValue);
        this.save(specValue);
    }

    public void updateSpecValue(SpecValueDTO dto,Integer id){
        SpecValueDO specValue = this.getById(id);
        if (specValue == null){
            throw new NotFoundException(60002);
        }
        BeanUtils.copyProperties(dto,specValue);
        this.updateById(specValue);

        List<Integer> skuIds = skuSpecMapper.getSkuIdsByValueId(id);
        if (skuIds.isEmpty()){
            return;
        }
        List<SkuDO> skuList = skuMapper.selectBatchIds(skuIds);
        skuList.forEach(sku -> {
            QueryWrapper<SkuSpecDO> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SkuSpecDO::getValueId,id);
            wrapper.lambda().eq(SkuSpecDO::getSkuId,sku.getId());
            List<SkuSpecDO> skuSpecs = skuSpecMapper.selectList(wrapper);
            List<SpecKeyValueDO> specs = new ArrayList<>();
            skuSpecs.forEach(skuSpec -> {
                SpecKeyValueDO specKeyValue = specValueMapper.getSpecKeyAndValueById(skuSpec.getKeyId(),skuSpec.getValueId());
                specs.add(specKeyValue);
            });
            sku.setSpecs(specs);
            skuMapper.updateById(sku);
        });
    }

    public void deleteSpecValue(Integer id){
        SpecValueDO specValueDO = this.getById(id);
        if (specValueDO == null){
            throw new NotFoundException(60002);
        }
        this.getBaseMapper().deleteById(id);
    }
}
