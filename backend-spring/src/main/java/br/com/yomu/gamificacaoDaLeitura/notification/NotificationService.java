package br.com.yomu.gamificacaoDaLeitura.notification;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public List<NotificationModel> getUserNotifications(String email) {
        return repository.findByUserEmail(email);
    }

    public NotificationModel createNotification(NotificationModel notification) {
        return repository.save(notification);
    }

    public void deleteNotification(Long id) {
        repository.deleteById(id);
    }
}
