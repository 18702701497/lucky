package com.ly.lucky.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ly.lucky.entity.Resource;
import com.ly.lucky.dao.ResourceMapper;
import com.ly.lucky.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ly.lucky.vo.ResourceVO;
import com.ly.lucky.vo.TreeVO;
import org.springframework.stereotype.Service;
import sun.reflect.generics.tree.Tree;
import sun.reflect.misc.ConstructorUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liuyang
 * @since 2021-03-21
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    /**
     * 根据角色Id，查询出该角色所具有的资源
     *
     * @param roleId
     * @return
     */
    @Override
    public List<ResourceVO> ListResourceByRoleId(int roleId) {
        //QueryWrapper<Resource> queryWrapper=new QueryWrapper<Resource>();
        //另一种创建条件构造器的方式，与原实例化创建方式并无差别
        QueryWrapper<Resource> query = Wrappers.<Resource>query();
        query.eq("rr.role_id", roleId).isNull("re.parent_id").orderByAsc("re.sort");
        List<ResourceVO> resourceVOS = baseMapper.listResource(query);

        resourceVOS.forEach(r -> {
            Integer resourceId = r.getResourceId();
            QueryWrapper<Resource> subWrapper = Wrappers.<Resource>query();
            subWrapper.eq("rr.role_id", roleId).eq("re.parent_id", resourceId).orderByAsc("re.sort");
            List<ResourceVO> subResourceOVS = baseMapper.listResource(subWrapper);
            if (CollectionUtils.isNotEmpty(subResourceOVS)) {
                r.setSubs(subResourceOVS);
            }
        });
        return resourceVOS;
    }

    /**
     * 查询所有资源，供前端组件渲染(单单只返回给前端所有资源信息，List<TreeVO>类型)
     *
     * @return
     */
    @Override
    public List<TreeVO> listResource(Long roleId,Long flag) {
        if(roleId==null){
            LambdaQueryWrapper<Resource> wrapper = Wrappers.<Resource>lambdaQuery()
                    .isNull(Resource::getParentId).orderByAsc(Resource::getSort);
            List<Resource> resources = list(wrapper);
            List<TreeVO> treeVOS = resources.stream().map(r -> {
                TreeVO treeVO = new TreeVO();
                treeVO.setId(r.getResourceId());
                treeVO.setTitle(r.getResourceName());

                LambdaQueryWrapper<Resource> subWrapper = Wrappers.<Resource>lambdaQuery()
                        .eq(Resource::getParentId, r.getResourceId()).orderByAsc(Resource::getSort);
                List<Resource> subResources = list(subWrapper);
                if (CollectionUtils.isNotEmpty(subResources)) {
                    List<TreeVO> children = subResources.stream().map(sub -> {
                        TreeVO subTreeVO = new TreeVO();
                        subTreeVO.setId(sub.getResourceId());
                        subTreeVO.setTitle(sub.getResourceName());
                        return subTreeVO;
                    }).collect(Collectors.toList());
                    treeVO.setChildren(children);
                }
                return treeVO;
            }).collect(Collectors.toList());

            return treeVOS;
        }else{
            QueryWrapper<Resource> query=Wrappers.<Resource>query();
            query.eq(flag==1,"rr.role_id",roleId)
                    .isNull("re.parent_id").orderByAsc("re.sort");
            List<TreeVO> treeVOS = baseMapper.listResourceByRoleId(query, roleId);
            treeVOS.forEach(t->{
                t.setChecked(false);
                Long id=t.getId();
                QueryWrapper<Resource> subWrapper=Wrappers.<Resource>query();
                subWrapper.eq(flag==1,"rr.role_id",roleId)
                        .eq("re.parent_id",id).orderByAsc("re.sort");
                List<TreeVO> children = baseMapper.listResourceByRoleId(subWrapper, roleId);
                if(CollectionUtil.isNotEmpty(children)){
                    t.setChildren(children);
                }
            });
            return treeVOS;
        }
    }
}
