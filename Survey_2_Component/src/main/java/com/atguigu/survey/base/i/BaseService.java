package com.atguigu.survey.base.i;


public interface BaseService<T> {
	
	/**
	 * 根据OID查询单个对象
	 * @param entityId
	 * @return
	 */
	T getEntityById(Integer entityId);
	
	/**
	 * 根据实体类对象进行更新：此时数据库表中的所有字段都会被更新
	 * @param t
	 */
	void updateEntity(T t);
	
	/**
	 * 根据OID删除对象
	 * @param entityId
	 */
	void removeEntity(Integer entityId);
	
	/**
	 * 将一个实体类对象保存到数据库中
	 * @param t
	 * @return 自增的主键值
	 */
	Integer saveEntity(T t);

}
