package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.dto.GridCategoryDTO;
import io.github.talelin.latticy.mapper.CategoryMapper;
import io.github.talelin.latticy.model.CategoryDO;
import io.github.talelin.latticy.model.GridCategoryDO;
import io.github.talelin.latticy.mapper.GridCategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-04
 */
@Service
public class GridCategoryService extends ServiceImpl<GridCategoryMapper, GridCategoryDO> {
    @Value("${sleeve.grid-category-maximum-quantity}")
    private int maximunQuality;

    @Autowired
    private CategoryMapper categoryMapper;

    public void createGridCategory(GridCategoryDTO dto){
        Integer count = this.baseMapper.selectCount(null);
        if (count >= maximunQuality){
            throw new ForbiddenException(50001);
        }
        CategoryDO category = categoryMapper.selectById(dto.getCategoryId());
        if (category == null){
            throw new NotFoundException(40000);
        }
        GridCategoryDO gridCategory = new GridCategoryDO();
        BeanUtils.copyProperties(dto,gridCategory);
        this.save(gridCategory);
    }

    public void updateGridCategory(GridCategoryDTO dto,Integer id){
        GridCategoryDO gridCategory = this.getById(id);
        if (gridCategory == null){
            throw new NotFoundException(50000);
        }
        CategoryDO category = categoryMapper.selectById(dto.getCategoryId());
        if (category == null){
            throw new NotFoundException(40000);
        }
        setGridCategoryByCondition(dto,gridCategory,category);
        this.updateById(gridCategory);
    }

    private void setGridCategoryByCondition(GridCategoryDTO dto,GridCategoryDO gridCategory,CategoryDO category){
        if (dto.getName() == null){
            gridCategory.setName(category.getName());
        }else {
            gridCategory.setName(dto.getName());
        }
        if (dto.getTitle() != null){
            gridCategory.setTitle(dto.getTitle());
        }else {
            gridCategory.setTitle(category.getName());
        }
        gridCategory.setImg(dto.getImg());
        if (category.getParentId() == null){
            gridCategory.setRootCategoryId(category.getId());
        }else {
            gridCategory.setRootCategoryId(category.getParentId());
            gridCategory.setCategoryId(category.getId());
        }
    }

    public void deleteGridCategory(Integer id){
        GridCategoryDO gridCategory = this.getById(id);
        if (gridCategory == null){
            throw new NotFoundException(50000);
        }
        this.getBaseMapper().deleteById(id);
    }
}
