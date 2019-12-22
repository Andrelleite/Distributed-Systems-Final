/**
 * Raul Barbosa 2014-11-07
 */
package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;
import hey.model.HeyBean;

public class MessageAction extends ActionSupport implements SessionAware {
	
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String message = null;

	@Override
	public String execute() {
		System.out.println("------------------------------------------------------>"+this.message);
		if(this.message != null && !message.equals("")) {
			System.out.println("-----> "+session);
			session.put("message", message);
			session.put("loggedin", true); // this marks the user as logged in
			return SUCCESS;
		}else {
			return ERROR;
		}
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public HeyBean getHeyBean() throws RemoteException {
		if(!session.containsKey("heyBean"))
			this.setHeyBean(new HeyBean());
		return (HeyBean) session.get("heyBean"); // makes object reference to existing object created in session
	}

	public void setHeyBean(HeyBean heyBean) {
		this.session.put("heyBean", heyBean);
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
}
