package messageservice;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Stateless
public class MessageService {
		
	public void errorMsg(String id, String message) {
		FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", message));
	}

	public void successMsg(String id, String message) {
		FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_INFO, "", message));
	}

}
