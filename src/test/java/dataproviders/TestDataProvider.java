package dataproviders;

import org.testng.annotations.DataProvider;

/**
 * Data provider class for test data management
 * Provides test data for parameterized tests
 *
 * @author Peter Pestriakov
 * @version 1.0
 * @since 2025
 */
public class TestDataProvider {

    /**
     * Provides major cryptocurrency symbols for testing
     *
     * @return 2D array of cryptocurrency symbols
     */
    @DataProvider(name = "majorCryptocurrencies")
    public Object[][] provideMajorCryptocurrencies() {
        return new Object[][] {
                {"BTCUSDT"},
                {"ETHUSDT"},
                {"ADAUSDT"},
                {"DOTUSDT"},
                {"XRPUSDT"}
        };
    }

    /**
     * Provides cryptocurrency IDs for CoinGecko API testing
     *
     * @return 2D array of cryptocurrency IDs
     */
    @DataProvider(name = "cryptoIds")
    public Object[][] provideCryptoIds() {
        return new Object[][] {
                {"bitcoin"},
                {"ethereum"},
                {"cardano"},
                {"polkadot"},
                {"ripple"}
        };
    }

    /**
     * Provides trading pairs with expected price ranges
     *
     * @return 2D array of trading pairs and price validators
     */
    @DataProvider(name = "tradingPairsWithPriceRanges")
    public Object[][] provideTradingPairsWithPriceRanges() {
        return new Object[][] {
                {"BTCUSDT", 1000.0, 1000000.0},    // BTC between $1K and $1M
                {"ETHUSDT", 100.0, 10000.0},       // ETH between $100 and $10K
                {"ADAUSDT", 0.1, 10.0},            // ADA between $0.1 and $10
                {"DOTUSDT", 1.0, 100.0},           // DOT between $1 and $100
                {"XRPUSDT", 0.1, 10.0}             // XRP between $0.1 and $10
        };
    }

    /**
     * Provides response time thresholds for performance testing
     *
     * @return 2D array of timeout values
     */
    @DataProvider(name = "responseTimeThresholds")
    public Object[][] provideResponseTimeThresholds() {
        return new Object[][] {
                {1000L},  // 1 second
                {2000L},  // 2 seconds
                {3000L},  // 3 seconds
                {5000L}   // 5 seconds
        };
    }

    /**
     * Provides test data for currency conversion testing
     *
     * @return 2D array of currency pairs
     */
    @DataProvider(name = "currencyPairs")
    public Object[][] provideCurrencyPairs() {
        return new Object[][] {
                {"bitcoin", "usd"},
                {"ethereum", "usd"},
                {"bitcoin", "eur"},
                {"ethereum", "eur"},
                {"bitcoin", "jpy"}
        };
    }

    /**
     * Provides page sizes for pagination testing
     *
     * @return 2D array of page sizes
     */
    @DataProvider(name = "pageSizes")
    public Object[][] providePageSizes() {
        return new Object[][] {
                {5},
                {10},
                {20},
                {50}
        };
    }

    /**
     * Provides invalid symbols for error handling testing
     *
     * @return 2D array of invalid symbols
     */
    @DataProvider(name = "invalidSymbols")
    public Object[][] provideInvalidSymbols() {
        return new Object[][] {
                {"INVALIDPAIR"},
                {"NOTREAL"},
                {"TEST123"},
                {"WRONGSYMBOL"}
        };
    }
}