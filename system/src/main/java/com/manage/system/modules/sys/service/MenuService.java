package com.manage.system.modules.sys.service;

import com.manage.system.common.utils.StringUtils;
import com.manage.system.modules.sys.dao.MenuDao;
import com.manage.system.modules.sys.entity.SysMenu;
import com.manage.system.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService extends SystemBaseService<MenuDao, SysMenu> {


    private MenuDao menuDao;

    @Autowired
    @Override
    protected void setDao(MenuDao menuDao) {
        super.setDao(menuDao);
        this.menuDao=menuDao;
    }

    @Override
    public void save(SysMenu entity) throws Exception {
        String parentId=entity.getParentId();
        if(StringUtils.isNotBlank(parentId)){
            SysMenu parent=get(parentId);
            if (parent!=null){
                String parentIds = parent.getParentIds()+","+parent.getId();
                entity.setParentIds(parentIds);
            }else{
                entity.setParentId("0");
                entity.setParentIds("0");
            }

        }else{
            entity.setParentId("0");
            entity.setParentIds("0");
        }

        if(StringUtils.isNotBlank(entity.getId())){ //更新  修改关联上级parentIds
            SysMenu oldMenu = get(entity.getId());
            String updateOldParentIds = oldMenu.getParentIds()+","+entity.getId();
            String updateNewParentIds= entity.getParentIds()+","+entity.getId();
            menuDao.updateParentIds(updateOldParentIds,updateNewParentIds); //更新关联的信息
        }
        entity.setDelFlag("0");
        super.save(entity); //保存信息
    }

    @Override
    public void delete(SysMenu entity) throws Exception {
        if(StringUtils.isNotBlank(entity.getId())){ //更新  修改关联上级parentIds
            SysMenu oldMenu = get(entity.getId());
            String updateParentIds = oldMenu.getParentIds()+","+entity.getId();
            menuDao.deleteSubMenu(updateParentIds);
        }
        super.delete(entity);
    }


    /**
     * 加载用户菜单
     * @return
     */
    public List<SysMenu> loadUserMenu(SysUser user) {
        return menuDao.loadUserMenu(user);
    }
}
