package PSK.FlowerShop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;
    private String username;
    private String password;

    public Admin() {
        id= UUID.randomUUID();
    }

    public Admin(String username , String password) {
        this.username  = username ;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, user name='%s', password='%s']",
                id.toString(), username , password);
    }

}