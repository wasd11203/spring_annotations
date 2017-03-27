package cn.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 测试SessionAttribute 注解
 * 该注解 不能写在方法上
 * @author 99
 *
 */
//@SessionAttributes(value={"hello","count"} , types={User.class}) : 声明将模型中属性名为hello , count 的属性 存放到session中,将类型为 User 的对象存放到session中
@SessionAttributes(value={"hello","count","MapKey"} , types={User.class})
@Controller("hello2Controller")
public class Hello2Controller {
	
	@ModelAttribute("hello")
	public String getHello(){
		return "Hello";
	}
	
	@ModelAttribute("count")
	public int getCount(){
		return 10;
	}
	
	@ModelAttribute("user")
	public User getUser(){
		return new User("AA", 13);
	}
	
	@RequestMapping(value="/testSessionAttr.do")
	public void getSessionAttr(Map<String, Object> map,@ModelAttribute("hello") String hello , 
								@ModelAttribute("user") User user ,
								@ModelAttribute("count") int counts ,
								Writer writer , HttpServletRequest request) throws IOException{
		map.put("MapKey", "MapValue");
		writer.write(hello + "!" + user + " counts: " + counts);
		HttpSession session = request.getSession();
		Enumeration<String> enume = session.getAttributeNames();
		writer.write("<br />");
		while (enume.hasMoreElements())
        {
			String name = enume.nextElement().toString();
			writer.write(name + ":" +session.getAttribute(name) + "<br />");
		}
	}

}
