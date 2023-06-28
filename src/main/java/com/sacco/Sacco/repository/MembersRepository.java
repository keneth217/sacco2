package com.sacco.Sacco.repository;

import com.sacco.Sacco.models.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members,Long> {
    Members findById(long id);
    Members findByIdNo(String idNo);
    Members findByFullNameAndIdNo(String fullName, String idNo);
    List<Members> findByFullNameContainingIgnoreCase(String fullName);

    Members findByFullName(String fullName);
}
