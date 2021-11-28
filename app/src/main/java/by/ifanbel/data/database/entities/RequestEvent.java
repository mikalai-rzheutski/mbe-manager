package by.ifanbel.data.database.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
public class RequestEvent {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "id", nullable = false)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private String principalName;

	private String principalRole;

	private String url;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getPrincipalRole() {
		return principalRole;
	}

	public void setPrincipalRole(String principalRole) {
		this.principalRole = principalRole;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public RequestEvent(String principalName, String principalRole, String url) {
		this.date = new Date(System.currentTimeMillis());
		this.principalName = principalName;
		this.principalRole = principalRole;
		this.url = url;
	}
}
