package mileage;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long>{
    Optional<Message> findByMemberId(Long memberId);

}