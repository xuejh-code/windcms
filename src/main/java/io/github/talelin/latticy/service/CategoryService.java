package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.enumeration.CategoryRootOrNotEnum;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.CategoryDTO;
import io.github.talelin.latticy.model.CategoryDO;
import io.github.talelin.latticy.mapper.CategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-08-05
 */
@Service
public class CategoryService extends ServiceImpl<CategoryMapper, CategoryDO>{
    public CategoryDO getCategoryById(Integer id){
        CategoryDO categoryDO = this.getById(id);
        if (categoryDO == null){
            throw new NotFoundException(40000);
        }
        return categoryDO;
    }

    public void update(CategoryDTO dto,Integer id){
        CategoryDO category = this.getCategoryById(id);
        if (category == null){
            throw new NotFoundException(40000);
        }
        BeanUtils.copyProperties(dto,category);
        this.updateById(category);
    }

    public void delete(Integer id){
        CategoryDO category = this.getById(id);
        if (category == null){
            throw new NotFoundException(40000);
        }
        if (category.getIsRoot() == CategoryRootOrNotEnum.ROOT.getValue()){
            QueryWrapper<CategoryDO> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(CategoryDO::getParentId,id);
            wrapper.lambda().eq(CategoryDO::getIsRoot,CategoryRootOrNotEnum.NOT_ROOT.getValue());
            wrapper.last("limit 1");
            CategoryDO subCategory = this.baseMapper.selectOne(wrapper);
            if (subCategory != null){
                throw new ForbiddenException(40001);
            }
        }
        this.getBaseMapper().deleteById(id);
    }

    public IPage<CategoryDO> getCategoriesByPage(Integer count,Integer page,Integer root){
        Page<CategoryDO> pager = new Page<>(page,count);
        QueryWrapper<CategoryDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CategoryDO::getIsRoot,root);
        return this.getBaseMapper().selectPage(pager,wrapper);
    }

    public IPage<CategoryDO> getSubCategoriesByPage(Integer count,Integer page,Integer id){
        Page<CategoryDO> pager = new Page<>(page,count);
        QueryWrapper<CategoryDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CategoryDO::getIsRoot,CategoryRootOrNotEnum.NOT_ROOT.getValue());
        wrapper.lambda().eq(CategoryDO::getParentId,id);
        return this.getBaseMapper().selectPage(pager,wrapper);
    }
}
