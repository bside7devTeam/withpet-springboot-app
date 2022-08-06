package org.gig.withpet.api.controller.v1.eventHandler;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.alarmMessage.PushSender;
import org.gig.withpet.core.domain.alarmMessage.dto.MessageDto;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 2022/08/06
 */
@Component
@RequiredArgsConstructor
public class PushEventHandler {

    private final PushSender pushSender;

    @Async
    @EventListener
    public void sendPush(MessageDto messageDto) {
        try {
            pushSender.send(messageDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
