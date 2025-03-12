package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.bean.ContactBean;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactRepository extends JpaRepository<ContactBean, Integer> {

    List<ContactBean> findByStatus(String status);

    @Query("SELECT c from ContactBean c WHERE c.status = :status")
    //@Query(nativeQuery = true, value = "SELECT * FROM contact_msg c WHERE c.status = :status")
    Page<ContactBean> findByStatusWithQuery(String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE ContactBean c SET c.status=:status WHERE c.contactId=:contactId")
    int updateStatusById(String status, int contactId);

    Page<ContactBean> findOpenMsgs(String status, Pageable pageable);

    @Transactional
    @Modifying
    int updateMsgStatus(String status, int contactId);
}
