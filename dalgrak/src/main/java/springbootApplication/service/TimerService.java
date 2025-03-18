package springbootApplication.service;

import org.springframework.stereotype.Service;

import springbootApplication.domain.Timer;
import springbootApplication.repository.TimerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TimerService {

    private final TimerRepository timerRepository;

    public TimerService(TimerRepository timerRepository) {
        this.timerRepository = timerRepository;
    }

    public Timer createTimer(Timer timer) {
        return timerRepository.save(timer);
    }

    public Timer updateTimer(Long id, Timer updatedTimer) {
        return timerRepository.findById(id)
                .map(timer -> {
                    timer.setRemainingTime(updatedTimer.getRemainingTime());
                    timer.setStatus(updatedTimer.getStatus());
                    return timerRepository.save(timer);
                }).orElseThrow(() -> new RuntimeException("Timer not found"));
    }

    public void deleteTimer(Long id) {
        timerRepository.deleteById(id);
    }
}
