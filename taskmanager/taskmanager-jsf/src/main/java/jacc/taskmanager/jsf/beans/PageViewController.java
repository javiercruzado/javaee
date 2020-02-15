package jacc.taskmanager.jsf.beans;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class PageViewController implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nextPage;

	@Inject
	private TaskController taskController;

	@PostConstruct
	public void init() {
		setNextPage("list"); // Default include.
	}

	public void goToNextPage(String nextPage) {

		FacesContext fc = FacesContext.getCurrentInstance();

		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String tabIndex = params.get("tabMenuIndex");

		if (tabIndex != null && "1".contentEquals(tabIndex)) {
			nextPage = "newtask";
			taskController.clearTask();
		}

		this.setNextPage(nextPage);
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}
}