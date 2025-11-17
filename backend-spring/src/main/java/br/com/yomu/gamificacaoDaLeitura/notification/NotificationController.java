package br.com.yomu.gamificacaoDaLeitura.notification;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping("/{email}")
    public List<NotificationModel> getUserNotifications(@PathVariable String email) {
        return service.getUserNotifications(email);
    }

    @PostMapping
    public NotificationModel create(@RequestBody NotificationModel notification) {
        return service.createNotification(notification);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteNotification(id);
    }
}
