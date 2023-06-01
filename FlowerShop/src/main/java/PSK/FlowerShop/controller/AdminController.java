package PSK.FlowerShop.controller;

import PSK.FlowerShop.entities.Admin;
import PSK.FlowerShop.request.AdminRequest;
import PSK.FlowerShop.security.JwtUtil;
import PSK.FlowerShop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;


@RestController
@RequestMapping("/admin")
@RequestScope
@CrossOrigin(origins = "http://localhost:3000")

public class AdminController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdminService adminService;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AdminRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        if(adminService.findAdminByUsernameAndPassword(username, password) == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad credentials.");
        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
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