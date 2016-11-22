package backingbeans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value="registrationBean")
@RequestScoped
public class RegistrationBean implements Serializable {

	private static final long serialVersionUID = 7134740539132414943L;

}
