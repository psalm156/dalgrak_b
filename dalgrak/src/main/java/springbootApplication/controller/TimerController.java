package springbootApplication.controller;

import springbootApplication.domain.Timer;
import springbootApplication.service.TimerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timers")
@Tag(name = "Timers", description = "CRUD operations for timers")
public class TimerController {

    private final TimerService timerService;

    public TimerController(TimerService timerService) {
        this.timerService = timerService;
    }


    @PostMapping
    @Operation(summary = "Create a new timer")
    public ResponseEntity<Timer> createTimer(@RequestBody Timer timer) {
        Timer createdTimer = timerService.createTimer(timer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTimer);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing timer")
    public ResponseEntity<Timer> updateTimer(@PathVariable Long id, @RequestBody Timer updatedTimer) {
        try {
            Timer timer = timerService.updateTimer(id, updatedTimer);
            return ResponseEntity.ok(timer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a timer by ID")
    public ResponseEntity<Void> deleteTimer(@PathVariable Long id) {
        timerService.deleteTimer(id);
        return ResponseEntity.noContent().build();
    }
}
