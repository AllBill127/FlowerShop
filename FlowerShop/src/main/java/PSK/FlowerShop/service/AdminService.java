package PSK.FlowerShop.service;

import PSK.FlowerShop.entities.Admin;
import PSK.FlowerShop.repository.AdminRepository;
import PSK.FlowerShop.request.AdminRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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