package com.bren.orderflow.repository;

import com.bren.orderflow.model.entity.Notification;
import com.bren.orderflow.model.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByStatus(NotificationStatus status);
}