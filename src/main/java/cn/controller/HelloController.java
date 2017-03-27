package cn.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller("helloController")
public class HelloController {
	
	@RequestMapping("/hello.do")
	public String hello(){
		return "Hello";
	}
	
	// spring/BB/login.do: 该请求中，login.do 的上级目录名 作为username变量的值保存到username中
	// @requestMapping : 注解中支持通配符 "*"
	// @PathVariable:该注解表示获取  URI 中的指定位置的参数
	@RequestMapping(value="*/{username}/login.do",method=RequestMethod.GET)
	public String getLogin(@PathVariable("username") String username){
		System.out.println(username);
		return "Hello";
	}
	
	// @RequestHeader("HeaderAttrName") : 该注解 用于获取请求头中 指定属性 的值
	// 该注解 与 @CookieValue("CookieName") 注解用法一致, 用于获取 Cookie中 指定属性 的值
	// @RequestHeader("host") : 获取 请求头中的host 的值
	@RequestMapping(value="/testRequestHeaderInfo.do")
	public String getRequestHeaderInfo(@RequestHeader("host") String host){
		System.out.println(host);
		return "Hello";
	}
	
	//@RequestParam("RequestParamName") 获取请求中携带的指定名字的参数 的值
	@RequestMapping(value="/testRequestParam.do")
	public String getRequestParam(@RequestParam("username") String name){
		System.out.println(name);
		return "Hello";
	}
	
	//@RequestBody : 该注解 获取 请求 的 请求体
	@RequestMapping("/testRequestBody.do")
	public String getRequestBodyInfo(@RequestBody String body){
		System.out.println(body);
		return "Hello";
	}
	
	
	// params : 表示指定 进入该方法的请求 携带的参数 规则
	// params={"username=AA","password","!params3"} : 表示 该请求中 必须存在username 参数并且值为 AA , 必须存在 password 参数值为任意，必须不能存在 params3 参数 才能进入该方法处理该请求
	@RequestMapping(value="/testParams.do",params={"username=AA","password","!params3"})
	public String getParams(){
		System.out.println("test RequestParams ... ...");
		return "Hello";
	}
	
	// method : 表示发送请求时使用的请求方式 规则
	// method={RequestMethod.GET,RequestMethod.POST} : 表示 请求发送时 使用的方式 要是其中一种
	@RequestMapping(value="/testMethod.do" , method={RequestMethod.GET,RequestMethod.POST})
	public String getMethod(){
		System.out.println("test RequestMethod ... ...");
		return "Hello";
	}
	
	// headers : 表示 请求头必须包含 指定的内容 ，才允许进入该方法处理请求
	// headers={"host=localhost:8080","Accept"} : 接受请求时,如果请求头中的host 属性值是 localhost:8080(如果端口号是80 ,可省略) ,并且 有Accept 属性,那么就执行方法处理请求
	@RequestMapping(value="/testHeaders.do" , headers={"host=localhost:8080","Accept"})
	public String getHeaders(){
		System.out.println("test Headers ... ... ");
		return "Hello";
	}
	
	/*****************测试ModelAttribute注解*******************************/
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
	
	// @ModelAttribute 主要有两种使用方式:
	//	一种是标注在方法上，
	//	一种是标注在 Controller 方法参数上。
	// 当 @ModelAttribute 标记在方法上的时候，
	// 该方法将在处理器方法执行之前执行，
	// 如果 Controller类 上存在 @SessoinAttribute 注解时,会将返回的对象存放在session中
	// 如果没有,则将返回的对象存放在 模型中
	// 属性名称可以使用 @ModelAttribute(“attributeName”) 在标记方法的时候指定，
	// 若未指定，则使用返回类型的类名称（首字母小写）作为属性名称。
	@RequestMapping(value="/testModelAttr.do" )
	public void getModelAttr(@ModelAttribute("hello") String hello ,
							 @ModelAttribute("user") User user , 
							 @ModelAttribute("count") int counts ,Writer writer, HttpSession session) throws IOException{
		writer.write(hello + "!" + user.getUsername() + " counts: " + counts);
		Enumeration<String> enume = session.getAttributeNames();
		while (enume.hasMoreElements())
        {
			writer.write(enume.nextElement() + "\r" );
		}
	}
	
	
	
}

/***************测试ModelAttribute 注解时使用的对象类************************/
class User{
	private String username;
	private int age;
	
	public User(String username,int age){
		this.username = username;
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "[username=" + username + ", age=" + age + "]";
	}
}
