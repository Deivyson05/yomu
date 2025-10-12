package br.com.yomu.gamificacaoDaLeitura.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserModel, String> {
    UserModel findByEmail(String email);
}
