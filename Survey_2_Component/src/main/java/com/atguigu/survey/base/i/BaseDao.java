package com.atguigu.survey.base.i;

import java.util.List;

public interface BaseDao<T> {
	
	/**
	 * 查询分页的List集合
	 * @param sql
	 * @param param
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List getLimitedListBySql(String sql, int pageNo, int pageSize, Object ... param);
	
	/**
	 * 根据SQL语句查询总记录数
	 * @param sql
	 * @param param
	 * @return
	 */
	int getTotalRecordNoBySql(String sql, Object ... param);
	
	/**
	 * 查询分页的List集合
	 * @param hql
	 * @param param
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<T> getLimitedListByHql(String hql, int pageNo, int pageSize, Object ... param);
	
	/**
	 * 根据HQL语句查询总记录数
	 * @param hql
	 * @param param
	 * @return
	 */
	int getTotalRecordNoByHql(String hql, Object ... param);
	
	/**
	 * 根据Sql语句查询List
	 * @param sql
	 * @param param
	 * @return
	 */
	List getListBySql(String sql, Object ... param);
	
	/**
	 * 根据Hql语句查询List
	 * @param hql
	 * @param param
	 * @return
	 */
	List<T> getListByHql(String hql, Object ... param);
	
	/**
	 * 根据HQL语句查询单个对象
	 * @param sql
	 * @param param
	 * @return
	 */
	T getEntityByHql(String hql, Object ... param);
	
	/**
	 * 根据OID查询单个对象
	 * @param entityId
	 * @return
	 */
	T getEntityById(Integer entityId);
	
	/**
	 * 批量增删改操作
	 * @param sql
	 * @param params
	 * INSERT INTO `survey_emps`(`EMP_NAME`,emp_age) VALUES(?,?) ['emp01',10]
	 * INSERT INTO `survey_emps`(`EMP_NAME`,emp_age) VALUES(?,?) ['emp02',20]
	 * INSERT INTO `survey_emps`(`EMP_NAME`,emp_age) VALUES(?,?) ['emp03',30]
	 * INSERT INTO `survey_emps`(`EMP_NAME`,emp_age) VALUES(?,?) ['emp04',40]
	 * INSERT INTO `survey_emps`(`EMP_NAME`,emp_age) VALUES(?,?) ['emp05',50]
	 * INSERT INTO `survey_emps`(`EMP_NAME`,emp_age) VALUES(?,?) ['emp06',60]
	 * INSERT INTO `survey_emps`(`EMP_NAME`,emp_age) VALUES(?,?) ['emp07',70]
	 * 
	 * 二维数组
	 * [
	 * ['emp01',10],
	 * ['emp02',20],
	 * ['emp03',30],
	 * ['emp04',40],
	 * ['emp05',50],
     * ['emp06',60],
     * ['emp07',70]
     * ]
	 */
	void batchUpdate(String sql, Object[][] params);
	
	/**
	 * 根据SQL语句更新记录
	 * @param sql
	 * @param param
	 */
	void updateEntityBySql(String sql, Object ... param);
	
	/**
	 * 根据HQL语句更新记录
	 * @param hql
	 * @param param
	 */
	void updateEntityByHql(String hql, Object ... param);
	
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
