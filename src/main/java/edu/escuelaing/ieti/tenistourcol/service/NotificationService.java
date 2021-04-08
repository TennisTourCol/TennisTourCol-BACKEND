package edu.escuelaing.ieti.tenistourcol.service;

import edu.escuelaing.ieti.tenistourcol.model.Notification;
import edu.escuelaing.ieti.tenistourcol.model.Response;

public interface NotificationService {
    Response getNotificationByPlayerId(String idPlayer);
    Response addNotification(Notification notification);
}

