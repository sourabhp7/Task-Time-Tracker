package task.time.tracker.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@AssociationOverrides({ @AssociationOverride(name = "pk.role", joinColumns = @JoinColumn(name = "role_id")),
		@AssociationOverride(name = "pk.permission", joinColumns = @JoinColumn(name = "permission_id")) })
public class RolePermissionEntity {

	@EmbeddedId
	private RolePermissionId pk = new RolePermissionId();

	@Column(name = "is_active")
	private Boolean isActive = true;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
