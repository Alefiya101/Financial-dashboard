package in.alu.dto;

import java.math.BigDecimal;
import java.util.Map;

public class DashboardSummaryDTO {
    private BigDecimal totalBalance;
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private Map<String, BigDecimal> categoryBreakdown; // e.g., {"Food": 500.00, "Rent": 1200.00}

    // Constructors, Getters, and Setters
    public DashboardSummaryDTO(BigDecimal totalBalance, BigDecimal totalIncome, 
                               BigDecimal totalExpenses, Map<String, BigDecimal> categoryBreakdown) {
        this.totalBalance = totalBalance;
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.categoryBreakdown = categoryBreakdown;
    }

	public BigDecimal getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	public BigDecimal getTotalExpenses() {
		return totalExpenses;
	}

	public void setTotalExpenses(BigDecimal totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public Map<String, BigDecimal> getCategoryBreakdown() {
		return categoryBreakdown;
	}

	public void setCategoryBreakdown(Map<String, BigDecimal> categoryBreakdown) {
		this.categoryBreakdown = categoryBreakdown;
	}

    // Standard Getters/Setters here...
}