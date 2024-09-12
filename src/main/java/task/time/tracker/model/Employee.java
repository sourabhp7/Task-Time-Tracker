package task.time.tracker.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	
	private Long id;
	
	private String name;
	
	private String emailId;
	
	private LocalDate dateOfBirth;
	
	private String mobileNo;
	
	private String bloodGroup;
	
	private String address;
	
	private String city;
	
	private String resgistrationNo;
	
	private String imageUrl;
	
	@Column(name = "is_active")
	private Boolean isActive = true;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(name = "created_by")
	private String createdBy;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;
	
	

}
