package myf.caption2;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * JSON数据格式转换
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>8 4, 2016			muyunfei			新建</p>
 */
public class JsonTestMain {
public static void main(String[] args) {
	//对象转json对象
	JsonTestMain testMain = new JsonTestMain();
	User user =  testMain.new User("muyunfei",20160804);
	JSONObject obj2Json = JSONObject.fromObject(user);
	//User对象必须有get方法
	//输出信息{"name":"muyunfei","userId":20160804}
	System.out.println(obj2Json);
	
	//JSON对象转字符串对象
	//输出信息{"name":"muyunfei","userId":20160804}
	System.out.println(obj2Json.toString());
	
	//List转JSON数组
	List<Object> list = new ArrayList<Object>();
	list.add("list1");
	list.add(123L);
	list.add(111);
	JSONArray jsonArray = JSONArray.fromObject(list);
	//输出信息["list1",123,111]
	System.out.println(jsonArray);
	
	//JSON数组转字符串
	//输出信息["list1",123,111]
	System.out.println(jsonArray.toString());
}

public class User{
	private String name;//字符串
	private long  userId;//数字
	public User(){};
	public User(String name,long userId){
		this.name = name;
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public long getUserId() {
		return userId;
	}
}
}
