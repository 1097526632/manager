package com.manage.system.modules.sys.dao;

import com.manage.system.core.dao.BaseDao;
import com.manage.system.core.entity.BaseEntity;

import java.util.List;

public interface SystemBaseDao<T extends BaseEntity> extends BaseDao {
    /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer insert(T entity);
    /**
     * <p>
     * 根据 whereEntity 条件，更新记录
     * </p>
     *
     * @param entity  实体对象
     * @return
     */
    Integer update(T entity) throws Exception;


    T get(BaseEntity entity);

    List<T> findList(T entity);

    void delete(T entity) throws Exception;
}
