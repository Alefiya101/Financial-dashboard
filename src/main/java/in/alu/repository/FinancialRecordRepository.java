package in.alu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import in.alu.model.FinancialRecord;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
    
    /**
     * PAGINATION: Fetches only non-deleted records for the user.
     * Essential for the main record table.
     */
    @Query("SELECT f FROM FinancialRecord f WHERE f.user.id = :userId AND f.deleted = false")
    Page<FinancialRecord> findByUserId(@Param("userId") Long userId, Pageable pageable);

    /**
     * FULL LIST: Fetches all active records for CSV exports or internal logic.
     */
    @Query("SELECT f FROM FinancialRecord f WHERE f.user.id = :userId AND f.deleted = false")
    List<FinancialRecord> findByUserId(@Param("userId") Long userId);

    /**
     * MONTHLY SUMMARY: Used for filtered dashboard views.
     */
    @Query("SELECT f FROM FinancialRecord f WHERE f.user.id = :userId AND f.deleted = false " +
           "AND YEAR(f.createdAt) = :year AND MONTH(f.createdAt) = :month")
    List<FinancialRecord> findByUserIdAndMonth(@Param("userId") Long userId, 
                                               @Param("year") int year, 
                                               @Param("month") int month);

    /**
     * YEARLY TREND: Powers the 12-month analytics chart.
     */
    @Query("SELECT f FROM FinancialRecord f WHERE f.user.id = :userId AND f.deleted = false " +
           "AND YEAR(f.createdAt) = :year")
    List<FinancialRecord> findByUserIdAndYear(@Param("userId") Long userId, 
                                              @Param("year") int year);
}