package springbootApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootApplication.domain.Timer;
import springbootApplication.domain.TimerStatus;
import springbootApplication.repository.TimerRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TimerServiceTest {

    @Mock
    private TimerRepository timerRepository;

    @InjectMocks
    private TimerService timerService;

    private Timer timer;
    private Long timerId;

    @BeforeEach
    void setUp() {
        timerId = 1L;
        timer = new Timer();
        timer.setId(timerId);
        timer.setRemainingTime(60);
        timer.setStatus(TimerStatus.RUNNING);
    }

    @Test
    void testGetAllTimers() {
        when(timerRepository.findAll()).thenReturn(List.of(timer));

        List<Timer> timers = timerService.getAllTimers();

        assertNotNull(timers);
        assertEquals(1, timers.size());
        assertEquals(timerId, timers.get(0).getId());
    }

    @Test
    void testGetTimerById_Found() {
        when(timerRepository.findById(timerId)).thenReturn(Optional.of(timer));

        Optional<Timer> foundTimer = timerService.getTimerById(timerId);

        assertTrue(foundTimer.isPresent());
        assertEquals(timerId, foundTimer.get().getId());
    }

    @Test
    void testGetTimerById_NotFound() {
        when(timerRepository.findById(timerId)).thenReturn(Optional.empty());

        Optional<Timer> foundTimer = timerService.getTimerById(timerId);

        assertFalse(foundTimer.isPresent());
    }

    @Test
    void testCreateTimer() {
        when(timerRepository.save(timer)).thenReturn(timer);

        Timer createdTimer = timerService.createTimer(timer);

        assertNotNull(createdTimer);
        assertEquals(timerId, createdTimer.getId());
        assertEquals(60, createdTimer.getRemainingTime());
    }

    @Test
    void testUpdateTimer_Found() {
        Timer updatedTimer = new Timer();
        updatedTimer.setRemainingTime(45);
        updatedTimer.setStatus(TimerStatus.PAUSED);

        when(timerRepository.findById(timerId)).thenReturn(Optional.of(timer));
        when(timerRepository.save(timer)).thenReturn(timer);
        
        Timer result = timerService.updateTimer(timerId, updatedTimer);

        assertNotNull(result);
        assertEquals(45, result.getRemainingTime());
        assertEquals(TimerStatus.PAUSED, result.getStatus());
    }

    @Test
    void testUpdateTimer_NotFound() {
        Timer updatedTimer = new Timer();
        updatedTimer.setRemainingTime(45);
        updatedTimer.setStatus(TimerStatus.PAUSED); 

        when(timerRepository.findById(timerId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            timerService.updateTimer(timerId, updatedTimer);
        });
        
        assertEquals("Timer not found", exception.getMessage());
    }

    @Test
    void testDeleteTimer() {
        doNothing().when(timerRepository).deleteById(timerId);

        timerService.deleteTimer(timerId);

        verify(timerRepository, times(1)).deleteById(timerId);
    }
}
