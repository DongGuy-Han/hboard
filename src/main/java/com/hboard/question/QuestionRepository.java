package com.hboard.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAll(Pageable pageable);

    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join Answer a on a.question=q "
            + "where "
            + "q.title like %:kw% "
            + "or q.content like %:kw% "
            + "or a.content like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
