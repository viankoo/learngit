/**  
 * All rights Reserved, Designed By www.tydic.com
 * @Title:  Hello.java   
 * @Package cn.guwei.web   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: gw  
 * @date:   2017年5月22日 下午4:51:08   
 * @version V1.0 
 * @Copyright: 2017 www.tydic.com Inc. All rights reserved.
 * 注意：本内容仅限于上海黑马内部传阅，禁止外泄以及用于其他的商业目
 */
package cn.guwei.web;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @ClassName: Hello
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: gw
 * @date: 2017年5月22日 下午4:51:08
 * @param:
 * @Copyright: 2017 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于上海黑马内部传阅，禁止外泄以及用于其他的商业目
 */
public class Hello extends ActionSupport {

	public String hello() throws Exception {
		System.out.println("hello struts!");
		return SUCCESS;
	}

}
