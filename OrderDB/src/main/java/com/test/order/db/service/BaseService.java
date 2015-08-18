package com.test.order.db.service;

import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;

import com.test.order.db.util.PageInfo;



public interface BaseService<T> {
	/**
     * 保存一个对象
     *
     * @param entity
     * @return
     */
    Object save(T entity);

    /**
     * 批量保存
     *
     * @param batList
     * @return
     */
    List<String> saveBatch(List<T> batList);

    /**
     * 批量保存或更新
     * 
     * @param batList
     * @return 
     */
    void saveOrUpdateBatch(List<T> batList);
    
    /**
     * 更新一个对象
     *
     * @param entity
     */
    void update(T entity);

    /**
     * 执行更新hql
     * 
     * @param hql
     * @param params
     * @return 
     */
    int update(final String hqlStr, final Map<String,Object> params);
    
    /**
     * 批量更新对象
     *
     * @param batList
     */
    void updateBatch(List<T> batList);

    /**
     * 保存或者更新,
     *
     * @param entity
     */
    void saveOrUpdate(T entity);

    /**
     * 保存或者更新,
     *
     * @param entity
     */
    T merage(T entity);

    /**
     * 删除一个对象
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * 按id删除对象.
     */
    void deleteById(Class<T> entityClass, String id);

    /**
     * 根据条件批量删除操作
     */
    public int deleteByProperty(Class<T> entity, String propertyName, final Object value);
    
    /**
     * 批量删除对象
     * @param batchList
     * @return 
     */
    void deleteBatch(List<T> batchList);

    /**
     * 获取全部对象.
     */
    List<T> findAll(Class<T> entity);

    /**
     * 按id获取对象.
     */
    T findById(Class<T> entityClass, String id);
    
    /**
     * 按id获取对象.
     */
    T findById(Class<T> entityClass, Integer id);
    
    /**
     * 通过某一属性查找对象
     * 
     * @param entity
     * @param propertyName
     * @param value
     * @return 
     */
    List<T> findByProperty(Class<T> entity, String propertyName, final Object value);

    /**
     * 分页查询
     *
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @return
     */
    List<T> findByPage(final String hqlStr, final Map<String, Object> params,
            final PageInfo pageInfo);
    
    /**
     * 分页查询 <br/>
     * 
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @param transformerType     0 是默认 1 是 ALIAS_TO_ENTITY_MAP 2 是 TO_LIST
     * @return 
     */
    List findByPage(final String hqlStr,
            final Map<String, Object> params, final PageInfo pageInfo, final int transformerType);

    
    /**
     * 手动分页查询
     * 
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @param transformerType
     * @return 
     */
    List findByManualPage(final String hqlStr,
            final Map<String, Object> params, final PageInfo pageInfo, final int transformerType);
    
    /**
     * 查询列表
     *
     * @param hqlStr
     * @param params
     * @return
     */
    List<T> find(final String hqlStr, final Map<String, Object> params);
    
    /**
     * 查询列表 <br/>
     * 
     * @param hqlStr
     * @param params
     * @param transformerType   0 是默认 1 是 ALIAS_TO_ENTITY_MAP 2 是 TO_LIST
     * @return 
     */
    List find(final String hqlStr, final Map<String, Object> params, final int transformerType);

    /**
     * 查询单个对象
     *
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @return
     */
    T findSingle(final String hqlStr, final Map<String, Object> params);

    /**
     * 执行原生的SQL语句
     *
     * @param hqlStr
     * @param params
     * @return
     */
    List<T> findSQL(final String hqlStr, final Map<String, Object> params);

    /**
     * 原生的SQL语句分页
     *
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @return
     */
    List findSQLByPage(final String hqlStr, final Map<String, Object> params,
            final PageInfo pageInfo);

    /**
     * 执行原生的SQL语句
     *
     * @param string
     * @param params
     * @return
     */
    int executeSQL(final String hqlStr, final Map<String, Object> params);
    
    /**
     * 把hibernate缓存写入库中
     * 
     */
    void flush();
    
    /**
     * 加载实体
     * 
     * @param entityClass
     * @param id
     * @param lockMode
     * @return 
     */
    T get(Class<T> entityClass, String id, LockMode lockMode);
    
    /**
     * 清理session缓存
     * 
     */
    void clear();
    
    /**
     * 把实体从session缓存中去除
     * 
     * @param entity 
     */
    void evict(T entity);
    
    /**
     * 加载一个指定id得实体 <br/>
     * 
     * @param entityClass
     * @param id
     * @return 
     */
    T load(Class<T> entityClass, String id);
    
    
     /**
     * 查找HQL结果集的总数量 <br/>
     * 
     */
    int getAllCount(String hqlStr);
    
    /**
     * 执行原生的SQL语句
     *
     * @param hqlStr
     * @param params
     * @return
     */
    List findSQL(String hqlStr, Map<String, Object> params,Class<T> clazz) ;
    
    /**
     * 根据天剑搜索
     * 
     * @param entityClass       泛型类class
     * @param paramsMap         要查询的参数和值
     * @param dateFieldName     时间查询的字段
     * @param sortFieldName     排序的字段
     * @param sortType          排序方式: 1 asc 2 desc
     * @param pageInfo          分页信息
     * @return 
     */
    public List<T> searchByConditions(Class<T> entityClass, Map<String, Object> paramsMap, 
    String dateFieldName, String sortFieldName, String sortType, PageInfo pageInfo);
    
    /**
     * 分页查找
     * 
     * @param clazz
     * 			查找对象的class
     * @param pageInfo
     * 			分页信息
     * @param orderBy
     * 			排序信息，格式： "id, userName, age"
     * @return
     */
    public List<T> findByPage(Class<T> clazz, PageInfo pageInfo, String orderBy);
    
    /**
     * 根据条件查询满足条件的数量
     * 
     * @param clazz
     * @param params
     * @return
     */
    public Long findCounts(final String hql, final Map<String, Object> params);
    
    List findModelViewByPage(String hsql, PageInfo pageInfo);
}
