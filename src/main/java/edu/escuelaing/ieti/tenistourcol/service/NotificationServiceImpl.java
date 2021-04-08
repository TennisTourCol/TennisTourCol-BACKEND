package edu.escuelaing.ieti.tenistourcol.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.escuelaing.ieti.tenistourcol.mapper.NotificationMapper;
import edu.escuelaing.ieti.tenistourcol.model.Notification;
import edu.escuelaing.ieti.tenistourcol.model.Response;
import edu.escuelaing.ieti.tenistourcol.model.SuccessResponse;
import edu.escuelaing.ieti.tenistourcol.repository.NotificationEntity;
import edu.escuelaing.ieti.tenistourcol.repository.NotificationRepository;
import edu.escuelaing.ieti.tenistourcol.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();


    @Override
    public Response getNotificationByPlayerId(String idPlayer) {

        List<Notification> noti = new ArrayList<>();
        List<NotificationEntity> notiEntities = notificationRepository.findByPlayer(idPlayer);
        notiEntities.forEach(notificationEntity -> noti.add(NotificationMapper.map(notificationEntity)));
        if(noti.size()>0) {
            return new SuccessResponse(new Date(), 200, "Se encontraron las notificaciones  del jugaor" + idPlayer, gson.toJson(noti));
        } else {
            throw new NotFoundException("No se encontró ningún jugador " + idPlayer);
        }
    }


    @Override
    public Response addNotification(Notification notification) {
        NotificationEntity notificationEntity = NotificationMapper.map(notification);
        notificationRepository.save(notificationEntity);
        return new SuccessResponse(new Date(), 200, "Se creo la notificacion "+notification.getId(), gson.toJson(NotificationMapper.map(notificationEntity)));
    }
}
