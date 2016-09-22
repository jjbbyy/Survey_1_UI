package com.atguigu.survey.base.m;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.survey.base.i.BaseDao;

//@Repository：不要将基类加入IOC容器，将来要加入的是基类的子类
public class BaseDaoImpl<T> implements BaseDao<T> {
	
	@Autowired
	private SessionFactory factory;
	
	//子类Dao继承BaseDaoImpl时传入的泛型参数对应的Class对象
	private Class<T> entityType;
	
	public BaseDaoImpl() {
		
		//1.父类构造器中的this关键字，在创建子类对象时指向子类对象
		//System.out.println(this);
		
		//2.通过this获取父类的Class对象
		//System.out.println(this.getClass().getSuperclass());
		
		//3.通过this获取带泛型参数的父类类型
		Type type = this.getClass().getGenericSuperclass();
		//System.out.println(type);
		
		/**************************************/
		/*
		Type：代表类型
		如何理解类型：凡是可以修饰引用类型变量的都是类型
			String s = ...
			List<String> l = ...
		不带参数的类型：String
		带参数的类型：List<String>，由java.lang.reflect.ParameterizedType接口来描述的
		 */
		/**************************************/
		
		//4.将Type类型的变量转换为ParameterizedType类型
		ParameterizedType pt = (ParameterizedType) type;
		
		//5.获取实际的泛型参数组成的数组
		//之所以是数组，是因为泛型参数可以同时传入多个
		//Map<String,Object>
		Type[] types = pt.getActualTypeArguments();
		
		//6.从数组中获取下标为0的元素赋值给entityType
		entityType = (Class<T>) types[0];
		
		//System.out.println(entityType);
		
	}
	
	public Session getSession(){
		
		//重新开启一个新的Session
		//在没有Service方法时，单独测试Dao方法时使用
		//Session session = factory.openSession();
		
		//Spring的声明式事务将Session绑定到当前线程上
		//从当前线程上获取Session对象
		//有了Service方法，通过Service方法调用Dao方法时使用
		Session session = factory.getCurrentSession();
		
		return session;
	}
	
	public Query getQuery(String hql, Object ...param) {
		
		Query query = getSession().createQuery(hql);
		
		if(param != null) {
			//设置参数
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		
		return query;
		
	}
	
	public SQLQuery getSQLQuery(String sql, Object... param) {
		SQLQuery query = getSession().createSQLQuery(sql);
		
		if(param != null) {
			//设置参数
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		
		return query;
		
	}

	@Override
	public List getLimitedListBySql(String sql, int pageNo, int pageSize,
			Object... param) {
		
		int index = (pageNo - 1)*pageSize;
		
		return getSQLQuery(sql, param).setFirstResult(index).setMaxResults(pageSize).list();
	}

	@Override
	public int getTotalRecordNoBySql(String sql, Object... param) {
		
		BigInteger count = (BigInteger) getSQLQuery(sql, param).uniqueResult();
		
		return count.intValue();
	}

	@Override
	public List<T> getLimitedListByHql(String hql, int pageNo, int pageSize,
			Object... param) {
		
		return getQuery(hql, param).setFirstResult((pageNo-1)*pageSize)
								   .setMaxResults(pageSize)
								   .list();
	}

	@Override
	public int getTotalRecordNoByHql(String hql, Object... param) {
		
		long count = (Long) getQuery(hql, param).uniqueResult();
		
		return (int) count;
	}

	@Override
	public List getListBySql(String sql, Object... param) {
		return getSQLQuery(sql, param).list();
	}

	@Override
	public List<T> getListByHql(String hql, Object... param) {
		return getQuery(hql, param).list();
	}

	@Override
	public T getEntityByHql(String hql, Object... param) {
		return (T) getQuery(hql, param).uniqueResult();
	}

	@Override
	public T getEntityById(Integer entityId) {
		return (T) getSession().get(entityType, entityId);
	}

	@Override
	public void batchUpdate(final String sql, final Object[][] params) {
		
		getSession().doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				
				//1.获取PreparedStatement对象
				PreparedStatement ps = connection.prepareStatement(sql);		
				
				//2.遍历params数组给PreparedStatement对象赋值
				if(params != null) {
					for (int i = 0; i < params.length; i++) {
						Object[] param = params[i];
						//3.遍历一维数组
						for (int j = 0; j < param.length; j++) {
							ps.setObject(j+1, param[j]);
						}
						
						//4.积攒SQL语句
						ps.addBatch();
						
						if(i > 0 && i % 500 == 0) {
							//5.执行批量操作
							ps.executeBatch();
							ps.clearBatch();
						}
						
					}
					
					ps.executeBatch();
					
				}
				
				//6.关闭资源：仅关闭我们自己创建或开启的资源，不要关闭别人开启后传给我们的资源
				//原则：谁开的谁关
				if(ps != null) {
					ps.close();
				}
			}
		});
		
	}

	@Override
	public void updateEntityBySql(String sql, Object... param) {
		getSQLQuery(sql, param).executeUpdate();
	}

	@Override
	public void updateEntityByHql(String hql, Object... param) {
		getQuery(hql, param).executeUpdate();
	}

	@Override
	public void updateEntity(T t) {
		
		//在声明式事务中运行时的代码
		getSession().update(t);
		
		//不在声明式事务中运行时的代码
		//Session session = getSession();
		
		//Transaction transaction = session.beginTransaction();
		
		//session.update(t);
		
		//transaction.commit();
		
	}

	@Override
	public void removeEntity(Integer entityId) {
		//Object object = getSession().get(entityType, entityId);
		//getSession().delete(object);
		
		//1.动态获取目标实体类的简单类名
		String simpleName = entityType.getSimpleName();
		
		//2.动态获取目标实体类的id属性的属性名
		//①通过SessionFactory对象获取目标实体类的元数据
		ClassMetadata classMetadata = factory.getClassMetadata(entityType);
		
		//②通过元数据对象获取id属性名
		String idPropertyName = classMetadata.getIdentifierPropertyName();
		
		//delete from Employee e where e.empId=?
		//alias：别名
		String hql = "delete from "+simpleName+" alias where alias."+idPropertyName+"=?";
		
		//3.执行HQL语句
		getQuery(hql, entityId).executeUpdate();
		
	}

	@Override
	public Integer saveEntity(T t) {
		return (Integer) getSession().save(t);
	}

}
