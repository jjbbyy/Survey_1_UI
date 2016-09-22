package com.atguigu.survey.utils;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;

import com.atguigu.survey.entities.manager.Auth;
import com.atguigu.survey.entities.manager.Res;
import com.atguigu.survey.entities.manager.Role;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class DataprocessUtils {
	
	/**
	 * 根据日志表名List生成子查询字符串
	 * SELECT * FROM `survey_log_2016_08` UNION SELECT * FROM `survey_log_2016_09`
	 * @param tableNameList
	 * @return
	 */
	public static String genearteSubQuery(List<String> tableNameList) {
		
		StringBuilder builder = new StringBuilder();
		
		for (String tableName : tableNameList) {
			
			builder.append("SELECT * FROM ").append(tableName).append(" UNION ");
			
		}
		
		return builder.substring(0, builder.lastIndexOf(" UNION "));
	}
	
	/**
	 * 根据偏移量创建日志数据库表的表名
	 * @param offset
	 * 	-1：上一个月
	 * 	0：当前月
	 * 	1：下一个月
	 * @return
	 */
	public static String generateTableName(int offset) {
		
		//1.获取日历对象
		Calendar calendar = Calendar.getInstance();
		
		//2.在“月”上面添加偏移量
		calendar.add(Calendar.MONTH, offset);
		
		//3.转换为Date类型
		Date time = calendar.getTime();
		
		//4.格式化
		return "survey_log_" + new SimpleDateFormat("yyyy_MM").format(time);
	}
	
	/**
	 * 计算权限码数组并转换为字符串返回
	 * @param roleSet
	 * @param maxPos
	 * @return
	 */
	public static String calculateCodeArr(Set<Role> roleSet, int maxPos) {
		
		//1.创建一个int类型的数组，用于保存最终的结果
		//数组长度是最后一个元素索引+1
		int length = maxPos + 1;
		
		int [] codeArr = new int[length];
		
		//2.深度遍历roleSet
		for (Role role : roleSet) {
			
			Set<Auth> authSet = role.getAuthSet();
			
			for (Auth auth : authSet) {
				Set<Res> resSet = auth.getResSet();
				for (Res res : resSet) {
					
					//3.获取当前资源对象的权限码、权限位
					int resCode = res.getResCode();
					int resPos = res.getResPos();
					
					//4.以resPos为下标从codeArr数组中取出旧值
					int oldValue = codeArr[resPos];
					
					//5.将oldValue和resCode做或运算，得到新值
					int newValue = oldValue | resCode;
					
					//6.将newValue放回codeArr数组
					codeArr[resPos] = newValue;
					
					//codeArr[res.getResPos()] = codeArr[resPos] | res.getResCode();
				}
			}
			
		}
		
		//7.将codeArr转换为String返回
		return convertIntArr(codeArr);
	}
	
	/**
	 * 将int类型的数组转换为字符串
	 * @param codeArr
	 * @return
	 */
	public static String convertIntArr(int [] codeArr) {
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < codeArr.length; i++) {
			int code = codeArr[i];
			builder.append(code).append(",");
		}
		
		return builder.substring(0, builder.lastIndexOf(","));
	}
	
	/**
	 * 将servletPath中附加数值部分去掉
	 * @param servletPath
	 * @return
	 */
	public static String cutUrl(String servletPath) {
		
		//1.根据/拆分字符串
		String[] split = servletPath.split("/");
		
		//2.在确定servletPath有效部分就是三节的前提下，取下标为1、2、3的元素
		String result = "/" + split[1] + "/" + split[2] + "/" + split[3];
		
		return result;
	}
	
	/**
	 * 深度复制的工具方法
	 * 要求输入参数和返回值的类型都是Serializable，以支持序列化和反序列化
	 * @param source
	 * @return
	 */
	public static Serializable deeplyCopy(Serializable source) {
		
		//1.声明变量
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bais = null;
		ByteArrayOutputStream baos = null;
		
		//2.try...catch...finally结构
		try{
			
			//3.创建字节数组输出流
			baos = new ByteArrayOutputStream();
			
			//4.根据字节数组输出流创建对象输出流
			oos = new ObjectOutputStream(baos);
			
			//5.将源对象执行序列化操作，本质是通过对象输出流写入到了字节数组输出流中
			oos.writeObject(source);
			
			//6.通过字节数组输出流获取保存了序列化数据的字节数组
			byte[] byteArray = baos.toByteArray();
			
			//7.根据字节数组创建字节数组输入流
			bais = new ByteArrayInputStream(byteArray);
			
			//8.根据字节数组输入流创建对象输入流
			ois = new ObjectInputStream(bais);
			
			//9.通过对象输入流将字节数组中的对象反序列化回内存
			return (Serializable) ois.readObject();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return null;
	}
	
	/**
	 * 检查bagOrderList集合中是否包含重复的元素
	 * @param bagOrderList
	 * @return 返回true表示没有重复的数据
	 */
	public static boolean checkListDuplicate(List<Integer> bagOrderList) {
		
		Set<Integer> bagOrderSet = new HashSet<>(bagOrderList);
		
		return bagOrderSet.size() == bagOrderList.size();
	}
	
	/**
	 * 将图片压缩按本来的长宽比例压缩为100宽度的jpg图片
	 * @param inputStream
	 * @param realPath /surveyLogos目录的真实路径，后面没有斜杠
	 * @return 将生成的文件路径返回 surveyLogos/4198393905112.jpg
	 */
	public static String resizeImages(InputStream inputStream, String realPath) {
		
		OutputStream out = null;
		
		try {
			//1.构造原始图片对应的Image对象
			BufferedImage sourceImage = ImageIO.read(inputStream);

			//2.获取原始图片的宽高值
			int sourceWidth = sourceImage.getWidth();
			int sourceHeight = sourceImage.getHeight();
			
			//3.计算目标图片的宽高值
			int targetWidth = sourceWidth;
			int targetHeight = sourceHeight;
			
			if(sourceWidth > 100) {
				//按比例压缩目标图片的尺寸
				targetWidth = 100;
				targetHeight = sourceHeight/(sourceWidth/100);
				
			}
			
			//4.创建压缩后的目标图片对应的Image对象
			BufferedImage targetImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
			
			//5.绘制目标图片
			targetImage.getGraphics().drawImage(sourceImage, 0, 0, targetWidth, targetHeight, null);
			
			//6.构造目标图片文件名
			String targetFileName = System.nanoTime() + ".jpg";
			
			//7.创建目标图片对应的输出流
			out = new FileOutputStream(realPath+"/"+targetFileName);
			
			//8.获取JPEG图片编码器
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			
			//9.JPEG编码
			encoder.encode(targetImage);
			
			//10.返回文件名
			return "surveyLogos/"+targetFileName;
			
		} catch (Exception e) {
			
			return null;
		} finally {
			//10.关闭流
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	/**
	 * 对字符串进行MD5加密
	 * @param source
	 * @return
	 */
	public static String md5(String source) {
		
		if(source == null || source.length() == 0) {
			return null;
		}
		
		try {
			//1.准备工作
			StringBuilder builder = new StringBuilder();
			
			char[] characters = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
			
			//2.获取源字符串对应的字节数组
			byte[] sourceBytes = source.getBytes();
			
			//3.创建MessageDigest对象
			MessageDigest digest = MessageDigest.getInstance("md5");
			
			//4.执行加密
			byte[] targetBytes = digest.digest(sourceBytes);
			
			//5.遍历targetBytes
			for (int i = 0; i < targetBytes.length; i++) {
				byte b = targetBytes[i];
				
				//6.运算得到高四位、低四位的值
				int high = (b >> 4) & 15;
				int low = b & 15;
				
				//7.转换为字符
				char highChar = characters[high];
				char lowChar = characters[low];
				
				//8.拼接字符串
				builder.append(highChar).append(lowChar);
				
			}
			
			return builder.toString();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 将字符串数组转换为字符串
	 * @param value
	 * @return
	 */
	public static String convertValueArrToContent(String[] value) {
		
		if(value == null || value.length == 0) {
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < value.length; i++) {
			String val = value[i];
			builder.append(val).append(",");
		}
		
		return builder.substring(0, builder.lastIndexOf(","));
	}

	public static Map<String,String[]> mergeAnswers(
			Map<Integer, Map<String, String[]>> allBagMap,
			Integer bagId,
			HttpServletRequest request) {
		
		//1.通过request对象获取封装请求参数的Map
		//Tomcat处理每一次请求时都是使用同一个paramFromRequest
		//会将以前保存的请求参数清空
		//然后保存本次的请求参数
		Map<String,String[]> paramFromRequest = request.getParameterMap();
		
		//2.重新创建一个Map对象，保存paramFromRequest中的数据
		Map<String,String[]> param = new HashMap<>(paramFromRequest);
		
		//3.将新创建的Map对象保存到allBagMap中，这样就不会丢失以前包裹提交的答案数据了
		allBagMap.put(bagId, param);
		
		//4.将新的param对象返回
		return param;
	}

	/**
	 * 修饰Chart对象，并将数据写入到输出流中
	 * @param chart
	 * @param outputStream
	 */
	public static void processChart(JFreeChart chart,
			ServletOutputStream outputStream) {
		
		Font font = new Font("宋体", Font.PLAIN, 20);
		
		chart.getTitle().setFont(font);
		chart.getLegend().setItemFont(font);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(font);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0},{1}/{3},{2}"));
		plot.setForegroundAlpha(0.6f);
		
		try {
			ChartUtilities.writeChartAsJPEG(outputStream, chart, 400, 300);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证权限
	 * @param codeArrStr
	 * @param res
	 * @return
	 * 	true：有权限
	 * 	false：没权限
	 */
	public static boolean checkAuthority(String codeArrStr, Res res) {
		
		//1.从Res对象中获取权限码、权限位
		int resCode = res.getResCode();
		int resPos = res.getResPos();
		
		//2.将codeArrStr转换为int数组
		int[] codeArr = convertCodeArr(codeArrStr);
		
		//3.以resPos作为下标从codeArr取出对应的元素
		int code = codeArr[resPos];
		
		//4.将code和resCode做与运算
		int result = code & resCode;
		
		//5.检查result是否为0
		return result != 0;
	}
	
	/**
	 * 将字符串还原为int数组
	 * @param codeArrStr
	 * @return
	 */
	public static int[] convertCodeArr(String codeArrStr) {
		
		String[] split = codeArrStr.split(",");
		int[] codeArr = new int[split.length];
		
		for (int i = 0; i < split.length; i++) {
			String codeStr = split[i];
			int code = Integer.parseInt(codeStr);
			codeArr[i] = code;
		}
		
		return codeArr;
	}

}
