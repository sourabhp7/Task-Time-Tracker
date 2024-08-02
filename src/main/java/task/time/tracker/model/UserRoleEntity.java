package task.time.tracker.model;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_role_entity")
@Data 
@AllArgsConstructor
@NoArgsConstructor
@AssociationOverrides({ @AssociationOverride(name = "pk.userEntity", joinColumns = @JoinColumn(name = "user_id")),
		@AssociationOverride(name = "pk.roleEntity", joinColumns = @JoinColumn(name = "role_id")) })
public class UserRoleEntity {

	@EmbeddedId
	private UserRoleId pk = new UserRoleId();

	@Column(name = "is_active")
	private Boolean isActive = true;

	@CreationTimestamp
	private Date createdAt;

	@UpdateTimestamp
	private Date updatedAt;
}
