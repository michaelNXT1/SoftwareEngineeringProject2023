package Repositories;

import BusinessLayer.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberRepository {
    void addMember(Member memberDTO);
}
