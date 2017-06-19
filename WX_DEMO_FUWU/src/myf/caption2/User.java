package myf.caption2;
/**
 * 用户实体类
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
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
