package PSK.FlowerShop.service;

import PSK.FlowerShop.entities.Admin;
import PSK.FlowerShop.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin findAdminByUsernameAndPassword(String username, String password) {
        return adminRepository.findByUsernameAndPassword(username, password);
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
}