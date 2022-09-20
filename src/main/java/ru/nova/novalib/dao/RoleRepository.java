package ru.nova.novalib.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nova.novalib.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
