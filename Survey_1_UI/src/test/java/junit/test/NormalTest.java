package junit.test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.atguigu.survey.entities.Employee;
import com.atguigu.survey.utils.DataprocessUtils;

public class NormalTest {
	
	@Test
	public void testGenearteSubQuery() {
		List<String> list = Arrays.asList("aaa","bbb","ccc");
		String subQuery = DataprocessUtils.genearteSubQuery(list);
		System.out.println(subQuery);
	}
	
	@Test
	public void testGenerateTableName() {
		for(int i = -50; i < 50; i++) {
			String tableName = DataprocessUtils.generateTableName(i);
			System.out.println(i+"→"+tableName);
		}
	}
	
	@Test
	public void testConvertIntArr() {
		int [] codeArr = new int[]{1,2,3,4,5,6};
		String convertIntArr = DataprocessUtils.convertIntArr(codeArr);
		System.out.println(convertIntArr);
	}
	
	@Test
	public void testUrlCut() {
		String url = "/guest/question/removeQuestion/33/118";
		String[] split = url.split("/");
		String first = split[0];
		System.out.println(first.length());
		
		String result = "/" + split[1] + "/" + split[2] + "/" + split[3];
		System.out.println(result);
	}
	
	@Test
	public void testMove02() {
		int i = -2147483648;
		int j = i << 1;
		System.out.println(j);
		int k = j << 1;
		System.out.println(k);
	}
	
	@Test
	public void testMove() {
		int i = 1 << 30;
		System.out.println(i);
		i = 1 << 31;
		System.out.println(i);
		i = 1 << 32;
		System.out.println(i);
	}
	
	@Test
	public void testMd5AdminPwd() {
		String pwd = "atguigu";
		String md5 = DataprocessUtils.md5(pwd);
		System.out.println(md5);
	}
	
	@Test
	public void testConvert() {
		String[] arr = new String[]{"a","b","c"};
		String content = DataprocessUtils.convertValueArrToContent(arr);
		System.out.println(content);
	}
	
	@Test
	public void testDeeplyCopy() {
		Employee source = new Employee(5, "Justin");
		Employee target = (Employee) DataprocessUtils.deeplyCopy(source);
		
		System.out.println("源对象HashCode："+source.hashCode());
		System.out.println("目标对象HashCode："+target.hashCode());
		
		System.out.println(source);
		System.out.println(target);
	}
	
	@Test
	public void testListDuplicate() {
		List<Integer> bagOrderList = Arrays.asList(1,2,3,4,3,6);
		boolean duplicate = DataprocessUtils.checkListDuplicate(bagOrderList);
		System.out.println(duplicate);
	}
	
	@Test
	public void testMd5Utils() {
		String md5 = DataprocessUtils.md5("123123");
		System.out.println(md5);
	}
	
	@Test
	public void testConvertMd5ByteArray() throws NoSuchAlgorithmException {
		StringBuilder builder = new StringBuilder();
		
		char[] characters = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		
		String source = "12345609876543";
		
		byte[] bytes = source.getBytes();
		
		MessageDigest digest = MessageDigest.getInstance("md5");
		byte[] targetBytes = digest.digest(bytes);
		
		for (int i = 0; i < targetBytes.length; i++) {
			byte b = targetBytes[i];
			
			//高四位值
			int highValue = (b >> 4) & 15;
			
			//低四位值
			int lowValue = b & 15;
			
			//转换16进制字符：高低数值直接作为字符数组的下标从数组中取值即可
			//System.out.println("高："+highValue);
			//System.out.println("低："+lowValue);
			
			char highChar = characters[highValue];
			char lowChar = characters[lowValue];
			
			builder.append(highChar).append(lowChar);
			
		}
		
		System.out.println(builder);
	}
	
	@Test
	public void testMessageDigest() throws NoSuchAlgorithmException {
		
		String source = "12345609876543";
		
		byte[] bytes = source.getBytes();
		
		MessageDigest digest = MessageDigest.getInstance("md5");
		byte[] targetBytes = digest.digest(bytes);
		
		System.out.println(targetBytes.length);
	}
	
	@Test
	public void test() {
		System.out.println(30 % 500);
	}

}
