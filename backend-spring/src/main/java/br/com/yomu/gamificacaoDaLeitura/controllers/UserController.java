package br.com.yomu.gamificacaoDaLeitura.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserRepository userRepository;
    
    @PostMapping("/new")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByEmail(userModel.getEmail());
        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email já cadastrado");
        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getSenha().toCharArray());
        userModel.setSenha(passwordHashred);
        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginModel loginModel) {
        var user = this.userRepository.findByEmail(loginModel.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email não cadastrado");
        }

        String password = loginModel.getSenha();

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getSenha());
        if (!result.verified) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha inválida");
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
