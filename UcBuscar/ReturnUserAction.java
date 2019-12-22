package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import hey.model.HeyBean;

public class ReturnUserAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;


	@Override
	public String execute() {
		session.put("certificate", ""); // this marks the user as logged in
		this.getHeyBean().setAdminStatus(false);
		this.getHeyBean().setClientState("TRUE");
		return SUCCESS;
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
