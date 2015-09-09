package user;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
@Entity
//@Named
//@RequestScoped
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	@NotEmpty
	@NotNull
	@Length(min = 5, max = 10)
	private String name;
	@NotEmpty
	@NotNull
	@Length(min = 5, max = 10)
	@Pattern(regexp = ".*\\d.*")
	private String passwd;

	@Min(0)
	@Max(150)
	private int age;

	private Date birthDay;

	private String country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String password) {
		this.passwd = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the birthDay
	 */
	public Date getBirthDay() {
		return birthDay;
	}

	/**
	 * @param birthDay
	 *            the birthDay to set
	 */
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", passwd=" + passwd
				+ ", age=" + age + ", birthDay=" + birthDay + ", country="
				+ country + "]";
	}
}
