package in.alu.dto;

import java.math.BigDecimal;

public class MonthlyTrendDTO {
    private String monthName; // e.g., "JANUARY"
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;

    public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	public BigDecimal getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(BigDecimal totalExpense) {
		this.totalExpense = totalExpense;
	}

	public MonthlyTrendDTO(String monthName, BigDecimal totalIncome, BigDecimal totalExpense) {
        this.monthName = monthName;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
    }

    // Getters and Setters...
}