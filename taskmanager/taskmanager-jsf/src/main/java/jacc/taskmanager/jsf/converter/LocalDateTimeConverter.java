package jacc.taskmanager.jsf.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/***
 * copy from
 * https://www.javacodegeeks.com/2015/06/utilizing-the-java-8-date-time-api-with-jsf-and-java-ee-7.html
 *
 */
@FacesConverter(value = "localDateTimeConverter")
public class LocalDateTimeConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return LocalDate.parse(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		LocalDate dateValue = (LocalDate) value;

		return dateValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
}
