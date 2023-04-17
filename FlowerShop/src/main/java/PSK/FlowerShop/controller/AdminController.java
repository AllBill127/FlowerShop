package PSK.FlowerShop.controller;

import PSK.FlowerShop.entities.Admin;
import PSK.FlowerShop.request.AdminRequest;
import PSK.FlowerShop.service.AdminService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AdminRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        if(adminService.findAdminByUsernameAndPassword(username, password) == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad credentials.");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("You are logged in!");
    }

    //For testing reasons-
    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody AdminRequest adminRequest) {
        Admin admin = new Admin();
        admin.setUsername(adminRequest.getUsername());
        admin.setPassword(adminRequest.getPassword());
        adminService.createAdmin(admin);
        return new ResponseEntity<>(admin, HttpStatus.CREATED);
    }
}