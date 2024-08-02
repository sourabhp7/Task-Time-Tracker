package task.time.tracker.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private RoleEntity role;

	@ManyToOne
	private PermissionEntity permission;

	@Override
	public int hashCode() {
		return Objects.hash(permission, role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolePermissionId other = (RolePermissionId) obj;
		return Objects.equals(permission, other.permission) && Objects.equals(role, other.role);
	}

}
