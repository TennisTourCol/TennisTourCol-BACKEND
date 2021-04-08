package edu.escuelaing.ieti.tenistourcol.controller;

import edu.escuelaing.ieti.tenistourcol.model.Notification;
import edu.escuelaing.ieti.tenistourcol.model.Response;
import edu.escuelaing.ieti.tenistourcol.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/notification")
@Validated
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response> getNotifitacionByPlayerId(@PathVariable String id){
        return ResponseEntity.ok(notificationService.getNotificationByPlayerId(id));
    }

    @PostMapping
    public ResponseEntity addNotification(@Valid @RequestBody Notification notification){
        return ResponseEntity.ok(notificationService.addNotification(notification));
    }
}

