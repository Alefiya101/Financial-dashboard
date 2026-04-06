🏦 Financial Dashboard API

A high-precision, scalable Spring Boot backend for managing personal finances. This API handles transaction tracking, real-time analytics, and data persistence with a strong emphasis on Financial Integrity and Enterprise Scalability.

🚀 Key Technical Features

1. High-Precision Financial Math
Unlike standard applications that use double or float, this system utilizes java.math.BigDecimal for all monetary values.

Why: Prevents floating point errors (binary rounding issues).
Implementation: Uses custom stream reductions:
.reduce(BigDecimal.ZERO, BigDecimal::add)
Impact: Ensures every cent is accurately accounted for.

2. Enterprise Pagination
Supports page, size, and dynamic sort parameters.
Loads only required records to prevent memory overflow.

3. Logic-Based Soft Delete
Uses is_deleted boolean column.
Maintains audit trail and keeps UI clean.

4. Automated Analytics Engine
- Dashboard Summary: total income, expenses, balance
- Category Breakdown
- 12-month trend for charts

🛠️ Tech Stack
Java 17
Spring Boot 3.x
MySQL
Maven

📂 API Endpoints

Financial Records:
POST /api/records
GET /api/records/user/{id}
DELETE /api/records/{id}
GET /api/records/export/{id}

Analytics:
GET /api/records/user-summary/{id}
GET /api/records/trend/{id}/{year}

⚙️ Database
amount: DECIMAL(19,2)
is_deleted: BOOLEAN
created_at: TIMESTAMP

🧪 Testing
Base URL: http://localhost:8081

Sample JSON:
{
  "amount": 1500.50,
  "type": "INCOME",
  "category": "Salary",
  "description": "Monthly Pay",
  "user": { "id": 1 }
}

👨‍💻 Developed By
Alefiya Hirani
Focus: Backend Engineering & Financial Data Integrity

