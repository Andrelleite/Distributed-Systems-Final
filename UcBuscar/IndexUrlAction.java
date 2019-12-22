package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import hey.model.HeyBean;

public class IndexUrlAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String indexurl;
	private String password = null;

	@Override
	public String execute() {
		if(this.indexurl != null && !indexurl.equals("") ) {
			this.getHeyBean().setIndex(this.indexurl);
			try {
				if(this.getHeyBean().IndexUrl()==true) {
					session.put("loggedin", true); // this marks the user as logged in
					return SUCCESS;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return ERROR;
		}else if(this.password != null && !password.equals("")) {
			if(session.get("password").equals(this.password)) {
				session.put("certificate",this.password);
				return SUCCESS;
			}else {
				return ERROR;

			}
		}else {
			return ERROR;
		}
	}
	
	public void setIndex(String indexurl) {
		this.indexurl = indexurl;
	}
	
	public void setPassword(String password) {
		this.password = password;
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
