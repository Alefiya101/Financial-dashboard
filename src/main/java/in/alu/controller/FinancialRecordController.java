package in.alu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.alu.dto.MonthlyTrendDTO;
import in.alu.model.FinancialRecord;
import in.alu.service.FinancialRecordService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/records")
public class FinancialRecordController {

    @Autowired
    private FinancialRecordService recordService;

    // 1. CREATE: Add new financial entry
    @PostMapping
    public ResponseEntity<FinancialRecord> addRecord(@RequestBody FinancialRecord record) {
        return ResponseEntity.ok(recordService.addRecord(record));
    }

    // 2. READ (PAGED): Get records in chunks with Sorting
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<FinancialRecord>> getPagedRecords(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(recordService.getPagedRecords(userId, pageable));
    }

    // 3. ANALYTICS: Get total balance and category breakdown
    @GetMapping("/user-summary/{userId}")
    public ResponseEntity<Map<String, Object>> getUserSummary(@PathVariable Long userId) {
        return ResponseEntity.ok(recordService.getUserSummary(userId));
    }

    // 4. TREND: Get 12-month data for charts
    @GetMapping("/trend/{userId}/{year}")
    public ResponseEntity<List<MonthlyTrendDTO>> getYearlyTrend(
            @PathVariable Long userId, @PathVariable int year) {
        return ResponseEntity.ok(recordService.getYearlyTrend(userId, year));
    }

    // 5. EXPORT: Download CSV for accounting
    @GetMapping("/export/{userId}")
    public void exportToCSV(@PathVariable Long userId, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"records.csv\"");
        
        List<FinancialRecord> records = recordService.getAllRecordsByUserId(userId);
        StringBuilder builder = new StringBuilder();
        builder.append("ID,Amount,Type,Category,Description,Date\n");
        
        for (FinancialRecord r : records) {
            builder.append(r.getId()).append(",")
                   .append(r.getAmount()).append(",")
                   .append(r.getType()).append(",")
                   .append(r.getCategory()).append(",")
                   .append(r.getDescription()).append(",")
                   .append(r.getCreatedAt()).append("\n");
        }
        response.getWriter().write(builder.toString());
    }

    // 6. DELETE: Soft delete a record
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id); // Ensure your service handles the "deleted" flag
        return ResponseEntity.ok("Record deleted successfully (Soft Delete)");
    }
}