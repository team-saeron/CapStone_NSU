package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import togethers.togethers.dto.AlarmDto;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService{

    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void alarmByMessage(AlarmDto alarmDto) {
        messagingTemplate.convertAndSend("/sub/" + alarmDto.getId(), alarmDto);
    }

}