package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;
import hey.model.HeyBean;

public class RegisterAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String mail = null, username = null, password = null;

	@Override
	public String execute() {

		if(this.username != null && !username.equals("") && this.password != null && !password.equals("") && this.mail != null && !mail.equals("")) {
			this.getHeyBean().setUsername(this.username);
			this.getHeyBean().setPassword(this.password);
			this.getHeyBean().setMail(this.mail);
			try {
				if(this.getHeyBean().Register()==true) {
					return SUCCESS;
				}else {
					return LOGIN;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ERROR;
		}
		else
			return ERROR;
	}
	
	public void setUsername(String username) {
		this.username = username; // will you sanitize this input? maybe use a prepared statement?
	}

	public void setPassword(String password) {
		this.password = password; // what about this input? 
	}
	
	public void setMail(String mail) {
		this.mail = mail; // what about this input? 
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
