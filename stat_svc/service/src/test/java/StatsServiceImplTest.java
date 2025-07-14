import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.HitDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.module.Hit;
import ru.practicum.module.Stat;
import ru.practicum.repository.StatsRepository;
import ru.practicum.service.StatsServiceImpl;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatsServiceImplTest {

    @Mock
    private StatsRepository repository;

    @Spy
    private HitMapper hitMapper;

    @InjectMocks
    private StatsServiceImpl service;

    private final LocalDateTime now = LocalDateTime.now();
    private final HitDto dto = HitDto.builder()
            .app("ewn-svc")
            .uri("/test")
            .ip("127.0.0.1")
            .timestamp(now)
            .build();

    private final Hit hit = Hit.builder()
            .app("ewn-svc")
            .uri("/test")
            .ip("127.0.0.1")
            .timestamp(now)
            .build();

    private final Stat stat = new Stat("ewn-svc", "/test", 10L);

    @Test
    void saveHit() {
        when(hitMapper.mapHitDto(any(HitDto.class))).thenReturn(hit);
        when(hitMapper.mapHit(any(Hit.class))).thenReturn(dto);
        when(repository.save(hit)).thenReturn(hit);

        HitDto result = service.saveStats(dto);
        assertEquals(dto.getApp(), result.getApp());
        assertEquals(dto.getUri(), result.getUri());
        assertEquals(dto.getIp(), result.getIp());

        verify(repository, times(1)).save(hit);
    }
}
