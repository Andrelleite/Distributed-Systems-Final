package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.rmi.RemoteException;
import java.util.Map;
import javax.websocket.Session;
import hey.model.HeyBean;

public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, password = null;

	@Override
	public String execute() {
		// any username is accepted without confirmation (should check using RMI)
		if(this.username != null && !username.equals("") && this.password != null && !password.equals("")) {
			this.getHeyBean().setUsername(this.username);
			this.getHeyBean().setPassword(this.password);
			this.getHeyBean().setClientState("FALSE");
			System.out.println(this.getHeyBean());
			try {
				if(this.getHeyBean().Login()==true) {
					session.put("username", username);
					session.put("password", password);
					session.put("loggedin", true); // this marks the user as logged in
					session.put("certificate", ""); // this marks the user as logged in

					return SUCCESS;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return LOGIN;
		}
		else
			return LOGIN;
	}
	
	public void setUsername(String username) {
		this.username = username; // will you sanitize this input? maybe use a prepared statement?
	}

	public void setPassword(String password) {
		this.password = password; // what about this input? 
	}
	
	public HeyBean getHeyBean() {
		if(!session.containsKey("heyBean"))
			try {
				this.setHeyBean(new HeyBean());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return (HeyBean) session.get("heyBean");
	}

	public void setHeyBean(HeyBean heyBean) {
		this.session.put("heyBean", heyBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
