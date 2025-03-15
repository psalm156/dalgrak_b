package springbootApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootApplication.domain.Timer;

public interface TimerRepository extends JpaRepository<Timer, Integer> {
}
