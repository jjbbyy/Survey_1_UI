package junit.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.survey.component.dao.i.EmployeeDao;
import com.atguigu.survey.component.dao.i.SurveyLogDao;
import com.atguigu.survey.component.service.i.EmployeeService;
import com.atguigu.survey.component.service.i.SurveyService;
import com.atguigu.survey.entities.Employee;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.log.router.KeyBinder;
import com.atguigu.survey.utils.GlobalNames;

public class IOCTest {
	
	private ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-context.xml");
	private EmployeeDao employeeDao = ioc.getBean(EmployeeDao.class);
	private EmployeeService employeeService = ioc.getBean(EmployeeService.class);
	private SurveyService surveyService = ioc.getBean(SurveyService.class);
	private SurveyLogDao surveyLogDao = ioc.getBean(SurveyLogDao.class);
	
	@Test
	public void testLogCount() {
		KeyBinder.bindKey(GlobalNames.DATASOURCE_LOG);
		int logCount = surveyLogDao.getLogCount();
		System.out.println(logCount);
	}
	
	@Test
	public void testTableNameList() {
		List<String> list = surveyLogDao.getAllTableName();
		for (String tableName : list) {
			System.out.println(tableName);
		}
	}
	
	@Test
	public void testSessionFactory() {
		
	}
	
	@Test
	public void testDeeplyRemove() {
		Integer surveyId = 2;
		surveyService.removeDeeply(surveyId);
	}
	
	@Test
	public void testSaveSurvey() {
		User user = new User();
		user.setUserId(1);
		for(int i = 0; i < 100; i++) {
			surveyService.saveEntity(new Survey(null, "GoodSurvey"+i, false, "res_static/logo.gif", user));
		}
	}
	
	@Test
	public void testServiceUpdateEntity() {
		Employee employee = new Employee(7, "KKKKKKKKKKKKKKK");
		
		employeeService.updateEntity(employee);
	}
	
	@Test
	public void testLimitedBySql() {
		String sql = "select emp_id,emp_name from survey_emps";
		List<Object[]> list = employeeDao.getLimitedListBySql(sql, 2, 5);
		for (Object[] objects : list) {
			List<Object> asList = Arrays.asList(objects);
			System.out.println(asList);
		}
	}
	
	@Test
	public void testRecordNoBySql() {
		String sql = "select count(*) from survey_emps";
		int count = employeeDao.getTotalRecordNoBySql(sql);
		System.out.println(count);
	}
	
	@Test
	public void testLimitedByHql() {
		int pageNo = 2;
		int pageSize = 5;
		List<Employee> list = employeeDao.getLimitedListByHql("From Employee", pageNo, pageSize);
		for (Employee employee : list) {
			System.out.println(employee);
		}
	}
	
	@Test
	public void testRecordNoByHql() {
		int count = employeeDao.getTotalRecordNoByHql("select count(*) from Employee");
		System.out.println(count);
	}
	
	@Test
	public void testGetListBySql() {
		String sql = "select emp_id,emp_name from survey_emps where emp_id<?";
		List list = employeeDao.getListBySql(sql, 6);
		for (Object object : list) {
			//System.out.println(object);
			Object[] values = (Object[]) object;
			List<Object> listValue = Arrays.asList(values);
			System.out.println(listValue);
		}
	}
	
	@Test
	public void testGetListByHql() {
		String hql = "from Employee e where e.empId<?";
		List<Employee> list = employeeDao.getListByHql(hql, 6);
		for (Employee employee : list) {
			System.out.println(employee);
		}
	}
	
	@Test
	public void testGetEntityByHql() {
		String hql = "from Employee e where e.empId=?";
		Employee employee = employeeDao.getEntityByHql(hql, 8);
		System.out.println(employee);
	}
	
	@Test
	public void testGetEntityById() {
		Employee employee = employeeDao.getEntityById(5);
		System.out.println(employee);
	}
	
	@Test
	public void testBatchUpdate() {
		long currentTimeMillis1 = System.currentTimeMillis();
		String sql = "insert into survey_emps(EMP_NAME) values(?)";
		Object[][] params = new Object[30][1];
		for(int i = 0; i < 30; i++) {
			params[i] = new Object[]{"empName-----AAA"+i};
		}
		employeeDao.batchUpdate(sql, params);
		long currentTimeMillis2 = System.currentTimeMillis();
		System.out.println(currentTimeMillis2-currentTimeMillis1);
	}
	
	@Test
	public void testUpdateBySql() {
		String sql = "update survey_emps set EMP_NAME=? where EMP_ID=?";
		
		employeeDao.updateEntityBySql(sql, "HappyName", 2);
	}
	
	@Test
	public void testUpdateByHql() {
		String hql = "update Employee e set e.empName=? where e.empId=?";
		employeeDao.updateEntityByHql(hql, "GoodName", 2);
	}
	
	@Test
	public void testUpdate() {
		employeeDao.updateEntity(new Employee(2, "NewEmpName"));
	}
	
	@Test
	public void testRemove() {
		employeeDao.removeEntity(1);
	}
	
	@Test
	public void testSave() {
		Employee t = new Employee(null, "empName02");
		Integer empId = employeeDao.saveEntity(t);
		System.out.println("empId="+empId);
	}
	
	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ioc.getBean(DataSource.class);
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
	}

}
