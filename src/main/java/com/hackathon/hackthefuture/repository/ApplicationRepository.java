package com.hackathon.hackthefuture.repository;
import com.hackathon.hackthefuture.domain.Application;
import com.hackathon.hackthefuture.domain.Client;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Application entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findAllByStatus(String status);

    List<Application> findAllByClient(Client client);
}
