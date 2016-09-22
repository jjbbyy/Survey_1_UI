package com.atguigu.survey.component.dao.m;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.atguigu.survey.base.m.BaseDaoImpl;
import com.atguigu.survey.component.dao.i.SurveyLogDao;
import com.atguigu.survey.entities.manager.SurveyLog;
import com.atguigu.survey.utils.DataprocessUtils;

@Repository
public class SurveyLogDaoImpl extends BaseDaoImpl<SurveyLog> implements SurveyLogDao{

	@Override
	public void createTable(String tableName) {
		
		String sql = "CREATE TABLE IF NOT EXISTS "+tableName+" LIKE `survey0427`.`survey_log`";
		//updateEntityBySql(sql);
		getSQLQuery(sql).executeUpdate();
	}

	@Override
	public void saveSurveyLog(SurveyLog surveyLog) {
		
		String tableName = DataprocessUtils.generateTableName(0);
		
		String sql = "INSERT INTO "+tableName+"("
				+ "`METHOD_NAME`,"
				+ "`PARAMS`,"
				+ "`RETURN_VALUE`,"
				+ "`OPERATOR`,"
				+ "`OPERATE_TIME`,"
				+ "`EXCEPTION_TYPE`,"
				+ "`EXCEPTION_MESSAGE`,"
				+ "`TYPE_NAME`) VALUES(?,?,?,?,?,?,?,?)";
		
		getSQLQuery(sql, surveyLog.getMethodName(),
						 surveyLog.getParams(),
						 surveyLog.getReturnValue(),
						 surveyLog.getOperator(),
						 surveyLog.getOperateTime(),
						 surveyLog.getExceptionType(),
						 surveyLog.getExceptionMessage(),
						 surveyLog.getTypeName()).executeUpdate();
		
	}

	@Override
	public List<String> getAllTableName() {
		
		String sql = "SELECT table_name FROM `information_schema`.`TABLES` WHERE TABLE_SCHEMA='survey0427_log'";
		
		return getListBySql(sql);
	}

	@Override
	public int getLogCount() {
		
		//1.查询表名List
		List<String> tableNameList = getAllTableName();
		
		//2.根据表名List生成子查询字符串
		String subQuery = DataprocessUtils.genearteSubQuery(tableNameList);
		
		//3.拼查询总记录数的SQL语句
		String sql = "select count(u.log_id) from ("+subQuery+") u";
		
		System.out.println(sql);
		
		//4.执行SQL语句
		return getTotalRecordNoBySql(sql);
	}

	@Override
	public List<SurveyLog> getLimitedLogList(int pageNo, int pageSize) {
		
		//1.查询所有的表名
		List<String> tableNameList = getAllTableName();
		
		//2.跟表名List生成子查询字符串
		String subQuery = DataprocessUtils.genearteSubQuery(tableNameList);
		
		//3.拼SQL
		String sql = "select "
				+ "u.`LOG_ID`,"
				+ "u.`METHOD_NAME`,"
				+ "u.`PARAMS`,"
				+ "u.`RETURN_VALUE`,"
				+ "u.`OPERATOR`,"
				+ "u.`OPERATE_TIME`,"
				+ "u.`EXCEPTION_TYPE`,"
				+ "u.`EXCEPTION_MESSAGE`,"
				+ "u.`TYPE_NAME` from ("+subQuery+") u";
		
		//4.执行SQL语句
		List<Object[]> list = getLimitedListBySql(sql, pageNo, pageSize);
		
		List<SurveyLog> logList = new ArrayList<>();
		
		//5.解析查询得到的List
		for (Object[] objects : list) {
			
			Integer logId = (Integer) objects[0];
			String methodName = (String) objects[1];
			String params = (String) objects[2];
			String returnValue = (String) objects[3];
			String operator = (String) objects[4];
			String operateTime = (String) objects[5];
			String exceptionType = (String) objects[6];
			String exceptionMessage = (String) objects[7];
			String typeName = (String) objects[8];
			
			SurveyLog surveyLog = new SurveyLog(logId, methodName, params, returnValue, operator, operateTime, exceptionType, exceptionMessage, typeName);
			logList.add(surveyLog);
		}
		
		return logList;
	}

}
