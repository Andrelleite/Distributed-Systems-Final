package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import hey.model.HeyBean;

public class SearchAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String search = null;
	private ArrayList<String> results;

	@Override
	public String execute() {

		if(this.search != null && !search.equals("")) {
			results = new ArrayList<>();
			System.out.println(this.search);
			this.getHeyBean().setSearch(this.search);
			try {
				if(this.getHeyBean().SearchGuest()==true) {
					session.put("loggedin", false); // this marks the user as logged in
					return SUCCESS;
				}else {
					System.out.println("ganda chouriço");
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return SUCCESS;
		}
		else
			return ERROR;
	}
	

	
	public void setSearch(String search) {
		this.search = search; // what about this input? 
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
