package PSK.FlowerShop.repository;

import java.util.List;
import java.util.UUID;

import PSK.FlowerShop.entities.Admin;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {

    Admin findByUsernameAndPassword(String username, String password);

}