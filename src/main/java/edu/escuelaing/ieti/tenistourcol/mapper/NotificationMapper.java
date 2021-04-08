package edu.escuelaing.ieti.tenistourcol.mapper;

import edu.escuelaing.ieti.tenistourcol.model.Notification;
import edu.escuelaing.ieti.tenistourcol.repository.NotificationEntity;

public class NotificationMapper {
    private NotificationMapper() {
    }

    public static NotificationEntity map(final Notification notification) {


        final NotificationEntity notificationEntity = new NotificationEntity();

        notificationEntity.setId(notification.getId());
        notificationEntity.setMailPlayer(notification.getMailPlayer());
        notificationEntity.setTournament(notification.getTournament());
        notificationEntity.setPlayer(notification.getPlayerId());
        notificationEntity.setDate(notification.getDate());
        return notificationEntity;
    }

    public static Notification map(final NotificationEntity notificationEntity) {

        Notification  notification = new Notification();

        notification.setId(notificationEntity.getId());
        notification.setTournament(notificationEntity.getTournament());
        notification.setPlayerId(notificationEntity.getPlayer());
        notification.setMailPlayer(notificationEntity.getMailPlayer());
        notification.setDate(notificationEntity.getDate());
        return notification;
    }
}
