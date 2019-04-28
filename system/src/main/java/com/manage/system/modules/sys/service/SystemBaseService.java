package com.manage.system.modules.sys.service;

import com.manage.system.core.dao.BaseDao;
import com.manage.system.core.entity.BaseEntity;
import com.manage.system.core.entity.Page;
import com.manage.system.core.security.Principal;
import com.manage.system.core.service.BaseService;
import com.manage.system.modules.sys.dao.SystemBaseDao;
import com.manage.system.modules.sys.entity.SystemBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统基础service
 */
public abstract class SystemBaseService<D extends SystemBaseDao<T>, T extends BaseEntity> extends BaseService {

    protected D dao;

    protected void setDao(D dao){
        this.dao=dao;
    }

    /**
     * 获取单条数据
     * @param id
     * @return
     */
    public T get(String id) {
        return dao.get(new BaseEntity(id){});
    }

    /**
     * 获取单条数据
     * @param entity
     * @return
     */
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询列表数据
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    /**
     * 查询分页数据
     * @param entity 分页对象
     * @param entity
     * @return
     */
    public Page<T> findPage(T entity) {
        Page page= entity.getPage();
        if(page==null){
            page=new Page();
        }
        page.setRecords(dao.findList(entity));
        return page;
    }

    /**
     * 保存数据（插入或更新）
     * @param entity
     */
    @Transactional(readOnly = false)
    public void save(T entity) throws Exception {
        entity.setDelFlag("0");
        if (entity.getIsNewRecord()){
            entity.preInsert();
            dao.insert(entity);
        }else{
            entity.preUpdate();
            dao.update(entity);
        }
    }

    /**
     * 删除数据
     * @param entity
     */
    @Transactional(readOnly = false)
    public void delete (T entity)throws Exception {
        dao.delete(entity);
    }
}
