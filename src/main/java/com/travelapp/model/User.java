package com.travelapp.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelapp.model.enumeration.GENDER;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class User extends DateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Size(max = 40)
	private String name;
	@NotBlank
	@Size(max = 15)
	private String username;
	@NaturalId
	@NotBlank
	@Size(max = 40)
	@Email
	private String email;
	@NotBlank
	@Size(max = 100)
	private String password;
	@Column(name = "birth_date", nullable = true)
	private LocalDate birthDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender", nullable = true)
	private GENDER gender;

	@Column(name = "phone", nullable = true)
	private String phone;

	@Size(min = 2)
	@Column(name = "address", nullable = true)
	private String address;
	@OneToMany(mappedBy = "user")
	@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Comment> comments = new HashSet<>();


	@OneToMany(mappedBy = "user")
	@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Rate> rates = new HashSet<>();

	@OneToMany(mappedBy = "user")
	@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Booking> bookings = new HashSet<>();
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public User() {

	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public GENDER getGender() {
		return gender;
	}

	public void setGender(GENDER gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}

	public User(String name, String username, String email, String password) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		if (user.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), user.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}
	public User addBooking(Booking booking) {
		this.bookings.add(booking);
		booking.setUser(this);
		return this;
	}

	public User removeBooking(Booking booking) {
		this.bookings.remove(booking);
		booking.setUser(null);
		return this;
	}

	public User bookings(Set<Booking> bookings) {
		this.bookings = bookings;
		return this;
	}
	public User comments(Set<Comment> comments) {
		this.comments = comments;
		return this;
	}

	public User addComment(Comment comment) {
		this.comments.add(comment);
		comment.setUser(this);
		return this;
	}

	public User removeComment(Comment comment) {
		this.comments.remove(comment);
		comment.setUser(null);
		return this;
	}
	public User address(String address) {
		this.address = address;
		return this;
	}
	public User gender(GENDER gender) {
		this.gender = gender;
		return this;
	}

}
