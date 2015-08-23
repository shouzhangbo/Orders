package com.test.order.db.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.test.order.db.dao.BaseDao;
import com.test.order.db.service.BaseService;
import com.test.order.db.util.CommonUtil;
import com.test.order.db.util.PageInfo;

@SuppressWarnings({"unchecked", "rawtypes"})
@Service
public class BaseServiceImpl<T> implements BaseService<T>{
	@Autowired
	protected BaseDao<T> baseDao;
	
    @Transactional(propagation= Propagation.SUPPORTS)
    public Object save(T entity) {
        return baseDao.save(entity);
    }

    @Transactional
    public List<String> saveBatch(List<T> batList) {
        return baseDao.saveBatch(batList);
    }

    @Transactional
    public void saveOrUpdateBatch(List<T> batList) {
        baseDao.saveOrUpdateBatch(batList);
    }

    @Transactional(propagation= Propagation.SUPPORTS)
    public void update(T entity) {
        baseDao.update(entity);
    }

    @Transactional(propagation= Propagation.SUPPORTS)
    public int update(String hqlStr, Map<String, Object> params) {
        return baseDao.update(hqlStr, params);
    }

    @Transactional
    public void updateBatch(List<T> batList) {
        baseDao.updateBatch(batList);
    }

    @Transactional(propagation= Propagation.SUPPORTS)
    public void saveOrUpdate(T entity) {
        baseDao.saveOrUpdate(entity);
    }

    @Transactional(propagation= Propagation.SUPPORTS)
    public T merage(T entity) {
        return (T) baseDao.merage(entity);
    }

    @Transactional(propagation= Propagation.SUPPORTS)
    public void delete(T entity) {
        baseDao.delete(entity);
    }

    @Transactional(propagation= Propagation.SUPPORTS)
    public void deleteById(Class<T> entityClass, String id) {
        baseDao.deleteById(entityClass, id);
    }

    @Transactional(propagation= Propagation.SUPPORTS)
    public int deleteByProperty(Class<T> entity, String propertyName, Object value) {
        return baseDao.deleteByProperty(entity, propertyName, value);
    }

    @Transactional
    public void deleteBatch(List<T> batchList) {
        baseDao.deleteBatch(batchList);
    }

    public List<T> findAll(Class<T> entity) {
        return baseDao.findAll(entity);
    }

    public T findById(Class<T> entityClass, String id) {
        return (T) baseDao.findById(entityClass, id);
    }
    
    public T findById(Class<T> entityClass, Integer id) {
        return (T) baseDao.findById(entityClass, id);
    }

    public List<T> findByProperty(Class<T> entity, String propertyName, final Object value) {
        return baseDao.findByProperty(entity, propertyName, value);
    }

    public List<T> findByPage(String hqlStr, Map<String, Object> params, PageInfo pageInfo) {
        return baseDao.findByPage(hqlStr, params, pageInfo);
    }

    public List<T> find(String hqlStr, Map<String, Object> params) {
        return baseDao.find(hqlStr, params);
    }
    
    public List find(final String hqlStr, final Map<String, Object> params, final int transformerType) {
        return baseDao.find(hqlStr, params, transformerType);
    }

    public T findSingle(String hqlStr, Map<String, Object> params) {
        return (T) baseDao.findSingle(hqlStr, params);
    }

    public List findSQL(String hqlStr, Map<String, Object> params) {
        return baseDao.findSQL(hqlStr, params);
    }

    public List findSQL(String hqlStr, Map<String, Object> params, Class<T> clazz) {
        return baseDao.findSQL(hqlStr, params, clazz);
    }

    public List<T> findSQLByPage(String hqlStr, Map<String, Object> params, PageInfo pageInfo) {
        return baseDao.findSQLByPage(hqlStr, params, pageInfo);
    }

    public int executeSQL(String hqlStr, Map<String, Object> params) {
        return baseDao.executeSQL(hqlStr, params);
    }

    public void flush() {
        baseDao.flush();
    }

    public T get(Class<T> entityClass, String id, LockMode lockMode) {
        return (T) baseDao.get(entityClass, id, lockMode);
    }

    public void clear() {
        baseDao.clear();
    }

    public void evict(T entity) {
        baseDao.evict(entity);
    }

    public T load(Class<T> entityClass, String id) {
        return (T) baseDao.load(entityClass, id);
    }

    public int getAllCount(String hqlStr) {
        return baseDao.getAllCount(hqlStr);
    }

    public List findByPage(String hqlStr, Map<String, Object> params, PageInfo pageInfo, int transformerType) {
        return baseDao.findByPage(hqlStr, params, pageInfo, transformerType);
    }

    public List findByManualPage(String hqlStr, Map<String, Object> params, PageInfo pageInfo, int transformerType) {
        return baseDao.findByManualPage(hqlStr, params, pageInfo, transformerType);
    }
    
    /**
     * {@inheritDoc }
     */
    public List<T> searchByConditions(Class<T> entityClass, Map<String, Object> paramsMap, 
    String dateFieldName, String sortFieldName, String sortType, PageInfo pageInfo) {
        
        String className = entityClass.getSimpleName();
        String hql = "from " + className + " model where 1=1 ";

        if (sortType == null || sortType.equals("") 
                || (!"asc".equalsIgnoreCase(sortType) && !"desc".equalsIgnoreCase(sortType) )) {
            sortType = "desc";
        }
        
        Map<String, Object> params = new HashMap<String, Object>();
        
        for (Iterator<Map.Entry<String, Object>> it = paramsMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> entry = it.next();
            
            if (entry.getValue() == null || "".equals(entry.getValue())) {
                continue;
            } else {
                hql += " and model."+ entry.getKey() +"=:"+ entry.getKey() +" ";
            }
        }
        
        if (sortFieldName != null && !"".equals(sortFieldName)) {
        	hql += " order by model."+sortFieldName+" " + sortType;
        }
        
        paramsMap.putAll(params);
        
        return baseDao.findByPage(hql, paramsMap, pageInfo);
    }
    
    public List<T> findByPage(Class<T> clazz, PageInfo pageInfo, String orderBy) {
        String className = clazz.getSimpleName();
    	String hql = "from " + className ;
    	
    	if (!CommonUtil.isEmpty(orderBy)) {
    		hql += " order by " + orderBy;
    	}
    	
    	return findByPage(hql, null, pageInfo);
    }
    
    /**
     * {@inheritDoc}
     */
    public Long findCounts(final String hql, final Map<String, Object> params) {
    	return baseDao.findCounts(hql, params);
    }
    
    public List findModelViewByPage(String hqlStr, PageInfo pageInfo) {
		return baseDao.findModelViewByPage(hqlStr, pageInfo);
	}
}
