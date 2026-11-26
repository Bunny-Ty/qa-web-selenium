# Test Cases: Product Search (FT-03)

| Item | Value |
|---|---|
| Application under test | Automation Exercise (https://automationexercise.com) |
| Feature | FT-03 - Product search |
| Related work items | PROJECT-23 (test plan and exploration), PROJECT-24 (test cases) |
| Last updated | 2026-11-26 |

## Notes

- No requirements document is available. Expected results come from direct observation during exploration (see PROJECT-23). Cases of type **Observation** describe behavior that was observed but cannot be confirmed as correct.
- Cases with **Auto** = `No (Defect candidate)` show a reasonable expected result and the behavior that was observed. They are possible bugs that are not confirmed yet (see the test plan, Section 5.4). They are not automated until the behavior is confirmed.
- **Auto** shows whether the case is a candidate for Selenium automation.
- The public site can change at any time. The number of search results is not fixed: the tests check the rule (every product shown has the keyword in its name) and not a fixed number. Numbers in this file are the values seen during exploration.
- The search is started with the search (magnifier) button. Pressing Enter does not start a search (see TC-SEARCH-013), so the tests never use the Enter key.
- Advertising can add underlined words and small tags to product names. The tests ignore text added by advertising.
- Each case starts on the Products page (`https://automationexercise.com/products`) with an empty cart and a logged-out user unless the case says otherwise.
- The advertising popup can appear on any page change. Closing it is the job of the test framework and is not written as a step in each case.

## Test data

| Data set | Value |
|---|---|
| Keyword | `top` (14 products seen), `TOP` |
| No match | `zzzz` |
| Two words | `blue top`, `sleeveless dress` |
| Two phrases together | `blue top, sleeveless dress` (the two phrases separated by a comma) |
| Category or brand words | `women`, `polo` |
| Long text | 100 times the letter `a` |
| Special characters | `#`, `&`, `%`, `+`, `@#$`, `H&M` |
| Spaces | ` top` (space before), `top ` (space after), ` top ` (both), only spaces |

## Test cases

| ID | Title | Type | Priority | Auto | Precondition | Steps | Expected result | Feature |
|---|---|---|---|---|---|---|---|---|
| TC-SEARCH-001 | Search shows only the products that have the keyword in their name | Positive | High | Yes | The Products page is open | 1. Enter `top` in the search field.<br>2. Click the search button.<br>3. Read the name of each product shown. | The section title changes to `Searched Products`. The URL is `https://automationexercise.com/products?search=top`. The search field still shows `top`. Every product shown has `top` in its name (14 products were seen during exploration). | FT-03 |
| TC-SEARCH-002 | Search does not depend on capital letters | Positive | Medium | Yes | The Products page is open | 1. Search for `top` and note the products.<br>2. Search for `TOP`. | Both searches show the same products. | FT-03 |
| TC-SEARCH-003 | Search for a word that no product has shows no products | Negative | Medium | Yes | The Products page is open | 1. Enter `zzzz` in the search field.<br>2. Click the search button. | The section title is `Searched Products` and no product is shown. | FT-03 |
| TC-SEARCH-004 | Search with an empty field shows all products | Positive | Medium | Yes | The Products page is open | 1. Leave the search field empty.<br>2. Click the search button. | The section title is `All Products` and all products are shown (the same number as in TC-PROD-001). | FT-03 |
| TC-SEARCH-005 | Search with two words shows products with the whole phrase (data-driven) | Positive | Medium | Yes | The Products page is open | 1. Enter a value from the "Two words" data set.<br>2. Click the search button.<br>3. Repeat for each value in the data set. | Products are shown, and each product has the whole phrase in its name. | FT-03 |
| TC-SEARCH-006 | Search with two phrases separated by a comma shows no products | Observation | Low | No | The Products page is open | 1. Enter `blue top, sleeveless dress` in the search field.<br>2. Click the search button. | The URL is `https://automationexercise.com/products?search=blue%20top,%20sleeveless%20dress`. The section title is `Searched Products` and no product is shown. No requirement is available to confirm how a search with several phrases should work. | FT-03 |
| TC-SEARCH-007 | Search with a category or brand word shows products | Observation | Low | No | The Products page is open | 1. Enter `women` in the search field and click the search button.<br>2. Enter `polo` and click the search button. | Products are shown in both cases. It is not known whether the search also uses the category or the brand, because these words may already be part of the product names. | FT-03 |
| TC-SEARCH-008 | Add to cart and View Product work on the search results | Positive | Medium | Yes | The search results for `top` are displayed | 1. Move the mouse over a product and click **Add to cart**.<br>2. Click **Continue Shopping**.<br>3. Click **View Product** on a product. | Step 1: the popup **Added!** with the text `Your product has been added to cart.` is displayed. Step 3: the details page of that product is displayed. The behavior is the same as on the All Products page. | FT-03, FT-02 |
| TC-SEARCH-009 | A product added from the search results stays in the cart after login | Positive | Medium | Yes | The user is logged out, an account `<TEST_EMAIL>` exists, and the cart is empty | 1. Search for `top` and add one product to the cart.<br>2. Open the login page and log in with `<TEST_EMAIL>` and `<TEST_PASSWORD>`.<br>3. Open `/view_cart`. | The product added in step 1 is still in the cart. | FT-03, FT-05 |
| TC-SEARCH-010 | The browser Back button returns to the page before the search | Positive | Low | No | The Products page is open | 1. Search for `top`.<br>2. Click the Back button of the browser. | The page that was open before the search is displayed. This case is checked by hand because the advertising popup appears again after Back and makes automation unstable. | FT-03 |
| TC-SEARCH-011 | Search with a very long text does not break the page | Boundary | Low | Yes | The Products page is open | 1. Enter the "Long text" value in the search field.<br>2. Click the search button. | No product is shown. There is no error and the page does not hang. The search field keeps the whole text. | FT-03 |
| TC-SEARCH-012 | Search with spaces gives results that are not stable | Observation | Low | No | The Products page is open | 1. Search for only spaces.<br>2. Search for `top ` (space after).<br>3. Search for ` top` (space before). | Step 1: all products are shown, URL `https://automationexercise.com/products?search=`. Step 2: products are shown and the space is cut from the URL (`?search=top`). Step 3: products were shown on 2026-11-23 and no product was shown on 2026-11-22, so the behavior is not stable. The tests do not check spaces before or after the keyword. | FT-03 |
| TC-SEARCH-013 | Pressing Enter in the search field must start the search | Negative | Medium | No (Defect candidate) | The Products page is open | 1. Enter `top` in the search field.<br>2. Press the Enter key (do not click the search button). | **Expected:** the search starts, the same as when the search button is clicked.<br>**Observed:** nothing happens. The URL does not change, the section title stays `All Products` and no search result is shown. | FT-03 |
| TC-SEARCH-014 | Special characters in the search text must be kept and searched (data-driven) | Negative | Medium | No (Defect candidate) | The Products page is open | 1. Enter a value from the "Special characters" data set in the search field.<br>2. Click the search button.<br>3. Read the URL, the search field and the products.<br>4. Repeat for each value in the data set. | **Expected:** the search field keeps every typed character, the URL encodes the special characters, and the products match the whole text.<br>**Observed:** the URL does not encode the characters. For `#`, `&` and `+` the search field loses the character, the URL is `?search=#`, `?search=&` or `?search=+`, and products are shown. For `%` no product is shown. For `@#$` the field keeps only `@` (URL `?search=@`) and no product is shown. For `H&M` only the part `H` is searched (URL `?search=H&M`), so the products shown are not the 5 products of the brand H&M. | FT-03 |
