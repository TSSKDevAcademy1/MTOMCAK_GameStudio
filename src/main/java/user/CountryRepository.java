package user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
@Named
@ApplicationScoped
public class CountryRepository {
	private List<String> countries = new ArrayList<String>();
	Locale[] locales = Locale.getAvailableLocales();
	  @PostConstruct
	    public void init() {
			for (Locale obj : locales) {
				if ((obj.getDisplayCountry() != null) && (!"".equals(obj.getDisplayCountry()))) {
					countries.add(obj.getDisplayName());
				}
			}
			Collections.sort(countries);
	    }
	    public List<String> getCountries() {
	        return countries;
	    } 
} 