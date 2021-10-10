package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.dto.SpuDTO;
import io.github.talelin.latticy.model.*;
import io.github.talelin.latticy.mapper.SpuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-07-29
 */
@Service
public class SpuService extends ServiceImpl<SpuMapper, SpuDO> {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SpuImgService spuImgService;
    @Autowired
    private SpuDetailImgService spuDetailImgService;
    @Autowired
    private SpuKeyService spuKeyService;

    public SpuDetailDO getDetail(Integer id){
        return this.getBaseMapper().getDetail(id);
    }

    public void create(SpuDTO dto){
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(dto,spuDO);
        CategoryDO categoryDO = categoryService.getCategoryById(dto.getCategoryId());
        spuDO.setRootCategoryId(categoryDO.getParentId());
        this.save(spuDO);

        List<String> spuImgList = new ArrayList<>();
        if (dto.getSpuImgList() == null){
            spuImgList.add(dto.getImg());
        }
        else {
            spuImgList = dto.getSpuImgList();
        }
        this.insertSpuImgList(spuImgList,spuDO.getId());
        if (dto.getSpuDetailImgList() != null){
            this.insertSpuDetailImgList(dto.getSpuDetailImgList(),spuDO.getId());
        }
        if (dto.getSpecKeyIdList() != null){
            this.insertSpuKeyList(dto.getSpecKeyIdList(),spuDO.getId());
        }
    }

    public void update(SpuDTO dto,Integer id){
        SpuDO spu = this.getById(id);
        if (spu == null){
            throw new NotFoundException(70000);
        }
        BeanUtils.copyProperties(dto,spu);
        CategoryDO category = categoryService.getCategoryById(dto.getCategoryId());
        if (category.getParentId() != null){
            spu.setRootCategoryId(category.getParentId());
        }
        this.updateById(spu);

        List<String> spuImgList = new ArrayList<>();
        if (dto.getSpuImgList() == null){
            spuImgList.add(dto.getImg());
        }else {
            spuImgList = dto.getSpuImgList();
        }
        spuImgService.getBaseMapper().hardDeleteImgsBySpuId(spu.getId());
        spuDetailImgService.getBaseMapper().hardDeleteImgsBySpuId(spu.getId());
        this.insertSpuImgList(spuImgList,spu.getId());
        if (dto.getSpuDetailImgList() != null){
            this.insertSpuDetailImgList(dto.getSpuDetailImgList(),spu.getId());
        }
        this.updateSpuKey(spu.getId(),dto.getSpecKeyIdList());
    }

    public void delete(Integer id){
        SpuDO spu = this.getById(id);
        if (spu == null){
            throw new NotFoundException(70000);
        }
        this.getBaseMapper().deleteById(id);
    }

    private void insertSpuImgList(List<String> spuImgList,Integer spuId){
        List<SpuImgDO> spuImgDOList = spuImgList.stream().map(s->{
            SpuImgDO spuImgDO = new SpuImgDO();
            spuImgDO.setImg(s);
            spuImgDO.setSpuId(spuId.intValue());
            return spuImgDO;
        }).collect(Collectors.toList());
        this.spuImgService.saveBatch(spuImgDOList);
    }

    private void insertSpuDetailImgList(List<String> spuDetailImglist,Integer spuId){
        List<SpuDetailImgDO> spuDetailImgDOList = new ArrayList<>();
        for (int i = 0;i < spuDetailImglist.size();i++){
            SpuDetailImgDO spuDetailImgDO = new SpuDetailImgDO();
            spuDetailImgDO.setImg(spuDetailImglist.get(i))
                    .setSpuId(spuId.intValue())
                    .setIndex(i);
            spuDetailImgDOList.add(spuDetailImgDO);
        }
        this.spuDetailImgService.saveBatch(spuDetailImgDOList);
    }

    private void insertSpuKeyList(List<Integer> specKeyIdList,Integer spuId){
        List<SpuKeyDO> spuKeyDOList = specKeyIdList.stream().map(sk->{
            SpuKeyDO spuKeyDO = new SpuKeyDO();
            spuKeyDO.setSpuId(spuId.intValue()).setSpecKeyId(sk);
            return spuKeyDO;
        }).collect(Collectors.toList());
        this.spuKeyService.saveBatch(spuKeyDOList);
    }

    /**
     * 更新spu_key表
     * @param spuId spu id
     * @param newSpecKeyIdList 前端传递过来的 spu_key id列表
     */
    private void updateSpuKey(Integer spuId,List<Integer> newSpecKeyIdList){
        QueryWrapper<SpuKeyDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SpuKeyDO::getSpuId,spuId);
        List<SpuKeyDO> exists = spuKeyService.getBaseMapper().selectList(wrapper);
        List<Integer> existsIds = new ArrayList<>();
        List<SpuKeyDO> newSpuKeyList = new ArrayList<>();
        for (SpuKeyDO exist : exists){
            existsIds.add(exist.getId());
        }
        for (Integer specKeyId : newSpecKeyIdList){
            SpuKeyDO spuKeyDO = new SpuKeyDO();
            spuKeyDO.setSpecKeyId(specKeyId);
            spuKeyDO.setSpuId(spuId);
            newSpuKeyList.add(spuKeyDO);
        }
        spuKeyService.removeByIds(existsIds);
        spuKeyService.saveBatch(newSpuKeyList);
    }
}
