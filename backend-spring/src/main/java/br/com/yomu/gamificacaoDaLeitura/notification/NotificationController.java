package br.com.yomu.gamificacaoDaLeitura.notification;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{usuarioId}/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public List<NotificationModel> getAll(@PathVariable String usuarioId) {
        return service.listAll(UUID.fromString(usuarioId));
    }

    @GetMapping("/unread")
    public List<NotificationModel> getUnread(@PathVariable String usuarioId) {
        return service.listUnread(UUID.fromString(usuarioId));
    }

    @PostMapping
    public NotificationModel create(@PathVariable String usuarioId,
                                    @RequestParam String titulo,
                                    @RequestParam String mensagem) {
        return service.create(UUID.fromString(usuarioId), titulo, mensagem);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markRead(@PathVariable String usuarioId, @PathVariable String id) {
        service.markAsRead(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/readall")
    public ResponseEntity<Void> markAllRead(@PathVariable String usuarioId) {
        service.markAllAsRead(UUID.fromString(usuarioId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String usuarioId, @PathVariable String id) {
        service.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}
