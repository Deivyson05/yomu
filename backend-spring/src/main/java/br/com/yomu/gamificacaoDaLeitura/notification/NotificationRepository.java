package br.com.yomu.gamificacaoDaLeitura.notification;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.yomu.gamificacaoDaLeitura.model.Usuario;

public interface NotificationRepository extends JpaRepository<NotificationModel, UUID> {
    List<NotificationModel> findByUsuarioOrderByCriadoEmDesc(Usuario usuario);
    List<NotificationModel> findByUsuarioAndLidoFalseOrderByCriadoEmDesc(Usuario usuario);
}
