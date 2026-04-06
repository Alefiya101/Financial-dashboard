package in.alu.service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.alu.dto.MonthlyTrendDTO;
import in.alu.model.FinancialRecord;
import in.alu.repository.FinancialRecordRepository;

@Service
public class FinancialRecordService {

    @Autowired
    private FinancialRecordRepository recordRepository;

    /**
     * 1. PAGINATION LOGIC
     * Returns a slice of data based on page number and size.
     */
    public Page<FinancialRecord> getPagedRecords(Long userId, Pageable pageable) {
        return recordRepository.findByUserId(userId, pageable);
    }

    /**
     * 2. BASIC CRUD
     */
    public FinancialRecord addRecord(FinancialRecord record) {
        return recordRepository.save(record);
    }

    public List<FinancialRecord> getAllRecordsByUserId(Long userId) {
        return recordRepository.findByUserId(userId);
    }

    public void deleteRecord(Long id) {
        FinancialRecord record = recordRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Record not found"));
        record.setDeleted(true); // Changes state, doesn't destroy data
        recordRepository.save(record);
    }
    
 
    /**
     * 3. SUMMARY LOGIC (Using BigDecimal for 100% Precision)
     */
    public Map<String, Object> getUserSummary(Long userId) {
        List<FinancialRecord> records = recordRepository.findByUserId(userId);

        // Sum Income using BigDecimal.add
        BigDecimal totalIncome = records.stream()
                .filter(r -> "INCOME".equalsIgnoreCase(r.getType()))
                .map(FinancialRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Sum Expenses using BigDecimal.add
        BigDecimal totalExpense = records.stream()
                .filter(r -> "EXPENSE".equalsIgnoreCase(r.getType()))
                .map(FinancialRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Group Expenses by Category using BigDecimal reducing
        Map<String, BigDecimal> categoryTotals = records.stream()
                .filter(r -> "EXPENSE".equalsIgnoreCase(r.getType()))
                .collect(Collectors.groupingBy(
                        FinancialRecord::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, FinancialRecord::getAmount, BigDecimal::add)
                ));

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpense", totalExpense);
        summary.put("netBalance", totalIncome.subtract(totalExpense));
        summary.put("categoryBreakdown", categoryTotals);

        return summary;
    }

    /**
     * 4. YEARLY TREND LOGIC (High-Precision for Charts)
     */
    public List<MonthlyTrendDTO> getYearlyTrend(Long userId, int year) {
        List<FinancialRecord> yearRecords = recordRepository.findByUserIdAndYear(userId, year);
        List<MonthlyTrendDTO> trend = new ArrayList<>();

        for (Month month : Month.values()) {
            BigDecimal income = yearRecords.stream()
                    .filter(r -> r.getCreatedAt().getMonth() == month && "INCOME".equalsIgnoreCase(r.getType()))
                    .map(FinancialRecord::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal expense = yearRecords.stream()
                    .filter(r -> r.getCreatedAt().getMonth() == month && "EXPENSE".equalsIgnoreCase(r.getType()))
                    .map(FinancialRecord::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // This requires your MonthlyTrendDTO to accept (String, BigDecimal, BigDecimal)
            trend.add(new MonthlyTrendDTO(month.toString(), income, expense));
        }
        return trend;
    }
}