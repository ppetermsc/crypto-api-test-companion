# Crypto API Test Companion 🚀

A professional test automation framework for cryptocurrency APIs featuring built-in rate limiting,
detailed logging, and **Allure Framework** for beautiful test reporting.

## 📊 Reports & Visualization
- **Allure HTML Reports**: Interactive dashboards with test results, timelines, and analytics
- **API Request/Response Logging**: Allure captures all RestAssured requests
- **Test Categorization**: Grouped by features, stories, and severity
- **Historical Trends**: Track test execution over time

## 🎯 Purpose
- Test Binance & CoinGecko APIs
- Showcase test automation architecture
- Demonstrate CI/CD integration

## 🧪 Test Results
- **24 integration tests** - Full API coverage
- **0 failures, 0 errors** - All tests pass successfully
- **~14 minute execution** - Includes realistic API delays
- **Rate limit handling** - No 429 errors during full suite run
- **Null-safe validation** - Graceful handling of API inconsistencies

## 🔧 Key Features
- **API Testing** - Real API calls with rate limit handling (8s Binance, 30s CoinGecko)
- **Logging** - SLF4J with timestamp formatting, configurable via `simplelogger.properties`
- **Reporting** - Allure Framework for beautiful HTML reports and test visualization
- **Configuration** - Centralized in `ApiEndpoints.java`, easy to add new exchanges
- **Documentation** - Comprehensive JavaDoc comments, HTML generation via `mvn javadoc:javadoc`

## 📈 CI/CD Pipeline
- Automatic tests on push/pull request
- Java 11 & 17 matrix testing
- Daily scheduled runs
- Test artifact storage
- Allure reports generation

## 🛠️ Tech Stack
- **Java 11+** - Core language
- **TestNG** - Testing framework
- **RestAssured** - REST API testing
- **Allure** - Test reporting & visualization
- **SLF4J** - Structured logging
- **Lombok** - Reduce boilerplate
- **Maven** - Build, dependencies & JavaDocs generation

## 👤 Author
**Peter Pestriakov** - QA Automation Engineer

## 📄 License
MIT License - free for commercial and personal use.

Ready to extend with more exchanges (Coinbase, Kraken) and advanced features.

**To generate code documentation:** `mvn javadoc:javadoc`

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher
- Apache Maven 3.8+
- Allure CLI (recommended for viewing reports)

### 1. Run Tests
```bash
# Run all tests (takes ~14 minutes due to API rate limits)
mvn test

# Run only fast smoke tests
mvn test -Dtestng.file=src/test/resources/testng-fast.xml

# Run a single test
mvn test -Dtest=BinanceApiTests#testBinanceApiPing

Generate & View Allure Reports

# Complete workflow: test → generate → open report
mvn clean test -Dallure.results.directory=target/allure-results allure:report allure:serve

# Or step by step:
# a) Run tests with Allure data collection
mvn clean test -Dallure.results.directory=target/allure-results

# b) Generate HTML report
mvn allure:report
# Report available at: target/site/allure-maven/index.html

# c) Open with Allure CLI
allure open target/allure-report/

Build & Documentation
bash
# Compile the project
mvn clean compile

# Generate comprehensive Javadoc
mvn javadoc:javadoc
# Documentation available at: target/site/apidocs/index.html


📁 Project Structure
src/main/java/
├── clients/           # API clients (Binance, CoinGecko)
├── constants/         # Configuration (ApiEndpoints)
└── models/           # DTOs (Currency, Ticker, ExchangeInfo)

src/test/java/
├── tests/            # Test classes
├── listeners/        # TestNG listener
└── dataproviders/    # Parameterized test data