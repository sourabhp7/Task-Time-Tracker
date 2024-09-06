package task.time.tracker.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "task_entity")
@Data
public class TaskEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String taskName;

	private String notes;

	private LocalDateTime deadLineDate;

	private String status; 

	private LocalDateTime estimatedTime;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "priority_id")
	private Priority priority;

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
