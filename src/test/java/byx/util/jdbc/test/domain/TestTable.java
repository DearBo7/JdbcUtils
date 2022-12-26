package byx.util.jdbc.test.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wb
 */
public class TestTable implements Serializable {
	private Integer id;
	private String userName;
	private String password;
	private Integer age;
	private Date createTime;
	private BigDecimal amount;
	private Double score;
	private Date createDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "TestTable{" +
				", id=" + id +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", age=" + age +
				", createTime=" + (createTime != null ? dateformat.format(createTime) : null) +
				", createDate=" + (createDate != null ? dateformat.format(createDate) : null) +
				", amount=" + amount +
				", score=" + score +
				'}';
	}
}
