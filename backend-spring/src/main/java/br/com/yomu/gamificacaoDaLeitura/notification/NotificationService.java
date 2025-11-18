package br.com.yomu.gamificacaoDaLeitura.notification;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import br.com.yomu.gamificacaoDaLeitura.repository.UsuariosRepository;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UsuariosRepository usuariosRepository;

    public NotificationService(NotificationRepository notificationRepository,
                               UsuariosRepository usuariosRepository) {
        this.notificationRepository = notificationRepository;
        this.usuariosRepository = usuariosRepository;
    }

    public NotificationModel create(UUID usuarioId, String titulo, String mensagem) {
        Usuario usuario = usuariosRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + usuarioId));
        NotificationModel n = new NotificationModel();
        n.setUsuario(usuario);
        n.setTitulo(titulo);
        n.setMensagem(mensagem);
        return notificationRepository.save(n);
    }

    public List<NotificationModel> listAll(UUID usuarioId) {
        Usuario usuario = usuariosRepository.findById(usuarioId).orElseThrow();
        return notificationRepository.findByUsuarioOrderByCriadoEmDesc(usuario);
    }

    public List<NotificationModel> listUnread(UUID usuarioId) {
        Usuario usuario = usuariosRepository.findById(usuarioId).orElseThrow();
        return notificationRepository.findByUsuarioAndLidoFalseOrderByCriadoEmDesc(usuario);
    }

    public void markAsRead(UUID id) {
        NotificationModel n = notificationRepository.findById(id).orElseThrow();
        n.setLido(true);
        notificationRepository.save(n);
    }

    public void markAllAsRead(UUID usuarioId) {
        listUnread(usuarioId).forEach(n -> {
            n.setLido(true);
            notificationRepository.save(n);
        });
    }

    public void delete(UUID id) {
        notificationRepository.deleteById(id);
    }
}
