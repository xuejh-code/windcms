package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.mapper.CUserMapper;
import io.github.talelin.latticy.model.CUserDO;
import org.springframework.stereotype.Service;

@Service
public class CUserService extends ServiceImpl<CUserMapper, CUserDO> {
    public CUserDO getParsedUserById(Integer id){
        CUserDO user = this.getBaseMapper().selectById(id);
        if (user == null){
            throw new NotFoundException(120000);
        }
        return user;
    }

    public IPage<CUserDO> getUserByPage(Integer count,Integer page){
        Page<CUserDO> pager = new Page<>(page,count);
        IPage<CUserDO> paging = this.getBaseMapper().selectPage(pager,null);
        return paging;
    }

    public IPage<CUserDO> search(Integer page, Integer count, String keyword) {
        Page<CUserDO> pager = new Page<>(page, count);
        keyword = "".equals(keyword) ? null : "%" + keyword + "%";
        IPage<CUserDO> iPage = this.getBaseMapper().searchCUserByKeyword(pager, keyword);
        return iPage;
    }
}
