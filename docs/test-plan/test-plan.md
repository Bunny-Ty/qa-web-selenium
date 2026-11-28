# Test Plan: Automation Exercise Web E2E

| Field | Value |
| --- | --- |
| Project | qa-web-selenium |
| Story | PROJECT-23 |
| Application under test | https://automationexercise.com |
| Status | Draft |
| Last updated | 2026-11-25 |

## 1. Introduction

Automation Exercise is a public demo e-commerce website built for practicing test automation. It offers product browsing, search, a shopping cart, account registration, checkout, a contact form and a newsletter subscription.

This plan describes how the main user flows of the site will be tested manually and then automated with Selenium. It is based on an exploratory session carried out between 2026-11-22 and 2026-11-25 (see Section 10).

The site has no published specification. Expected results in this project come from the behavior observed during exploration and from common e-commerce expectations. Where the observed behavior looks inconsistent with those expectations, it is recorded as a defect candidate and is not treated as a confirmed bug.

## 2. Objectives

- Verify that the main customer flows work end to end: browse, search, add to cart, register, log in, check out.
- Verify input validation on the forms (signup, login, review, payment, contact, subscription).
- Build a maintainable automated regression suite: Selenium, Java, TestNG, Page Object Model, data-driven tests, Allure reporting.
- Document findings clearly, separating confirmed defects from observations that still need checking.
- Identify the risks that the site brings to test automation (advertising popups, injected third-party content, changing data) and handle them in the framework.

## 3. Scope

### 3.1 In scope

- Functional UI testing of the features listed in Section 4, on a desktop browser (Chrome).
- Positive, negative and boundary tests on forms and quantities.
- State checks across pages (cart contents after login, after checkout, after refresh).
- Automated end-to-end tests for the high-priority flows.

### 3.2 Out of scope

- API testing of the endpoints listed on the site's API page (planned as a separate project).
- Performance, load and security testing.
- Cross-browser and mobile or responsive testing.
- Content and behavior of third-party advertising (Google ads, injected links, AI-generated side panels).
- Accessibility and localization testing.
- Real payment processing (only fake card data is used).

## 4. Test Items and Features

| ID | Feature | Main pages | Priority |
| --- | --- | --- | --- |
| FT-01 | Home page and navigation | `/`, header, footer | Medium |
| FT-02 | Product listing, category and brand filtering | `/products`, `/category_products/{id}`, `/brand_products/{name}` | Medium |
| FT-03 | Product search | `/products?search=...` | High |
| FT-04 | Product details and product review | `/product_details/{id}` | High |
| FT-05 | Shopping cart | `/view_cart` | High |
| FT-06 | Account registration and deletion | `/login` (signup), `/signup`, `/account_created`, `/delete_account` | High |
| FT-07 | Login and logout | `/login` | High |
| FT-08 | Checkout and payment | `/checkout`, `/payment`, `/payment_done/{amount}` | High |
| FT-09 | Contact us | `/contact_us` | Medium |
| FT-10 | Newsletter subscription (footer) | all pages | Low |
| FT-11 | Static pages (Test Cases, API list, unknown URL handling) | `/test_cases`, `/api_list` | Low |

## 5. Test Approach

### 5.1 Exploratory testing

A guided exploratory session covered the whole site, with the main flows (product details, cart, registration and login, checkout) explored in the most detail. Each observation was recorded with the real wording and URLs seen on the date of the session. The results are summarized in Section 10.

### 5.2 Test case design

- Test cases are written in `docs/test-cases` with IDs in the form `TC-<AREA>-<nnn>` (for example `TC-CART-001`).
- Techniques: equivalence partitioning and boundary values (quantity `0`, `-1`, `1`, `100`, `1000`), negative testing (empty, malformed and oversized input), and state-based checks for the cart and the login state.
- Each test case has an **Auto** column (Yes, No, or No with the note "Defect candidate"). Cases for defect candidates are not automated until the behavior is confirmed.
- Each case is linked to a feature (FT-xx).

### 5.3 Automation

- Stack: Selenium WebDriver, Java 23, Maven, TestNG, Allure, Page Object Model with a shared `BaseTest`.
- Data-driven tests use TestNG `@DataProvider`.
- Tests read data at run time (product names, prices, quantities) and check relationships (for example line total equals price times quantity) rather than hard-coded values. Fixed data that cannot be avoided is kept in a separate file with the date it was observed.
- Browser-native validation bubbles are not compared by text, because their language depends on the browser.
- Waits are conditional, never fixed sleeps. Timed messages (about 5 seconds) are checked as soon as they appear.
- The browser alert on the contact form (`Press OK to proceed!`) is handled with alert handling.
- Search is triggered with the search button, not with the Enter key (see FND-07).
- The Google full-screen advertising popup can block a click and leaves `#google_vignette` in the URL. After every click that navigates, the framework waits for the target URL or for the popup, closes the popup with its `Close` button (found in the visible, wide iframe) and then waits for the target URL. The popup does not always appear: in the runs so far (2026-11-26 and 2026-11-27) it appeared on the first navigation of a new browser session and not on the later ones, which is an observation and not a rule.

### 5.4 Defect handling

Findings are classified as follows:

| Class | Meaning | Action |
| --- | --- | --- |
| Defect candidate | Behavior that appears inconsistent with reasonable expectations, not yet confirmed | Not automated as a pass or fail check; keep in the findings list; confirm before reporting |
| Observation | Unusual or notable behavior that may be intended | Record only |
| Confirmed bug | Reproduced reliably and clearly wrong | Create a bug report (`BUG-<area>-<nnn>`) |

A defect candidate becomes a confirmed bug only after it is reproduced at least twice in a fresh session with the advertising popup closed, preferably also by an automated check. Because this is a public site that can change at any time, the date of each observation is always recorded.

### 5.5 ID conventions

| Code | Meaning | Where it is used |
| --- | --- | --- |
| `PROJECT-nn` | Jira story | Commits and Jira |
| `FT-nn` | Feature under test | Section 4 and test cases |
| `TC-<AREA>-nnn` | Test case, for example `TC-CART-001` | Test cases (PROJECT-24) |
| `BUG-<area>-nnn` | Confirmed bug report | Created only after a defect candidate is confirmed |
| `FND-nn` | Defect candidate: possible bug, not yet confirmed | Section 10.1 only |
| `OBS-nn` | Observation: notable behavior, probably not a bug | Section 10.2 only |

When a defect candidate (`FND-nn`) is confirmed, it gets a bug report with a `BUG-<area>-nnn` ID and the plan is updated to point to it.

## 6. Test Environment

| Item | Value |
| --- | --- |
| Application | https://automationexercise.com (public, shared, not under our control) |
| Browser | Google Chrome, desktop |
| Operating system | Windows |
| IDE | IntelliJ IDEA (code), VS Code (documents) |
| Build and test | Maven, TestNG |
| Reporting | Allure |
| CI | GitHub Actions (planned) |

## 7. Test Data

- Only fake data is used: emails such as `name@example.com`, fake names and addresses, fake card numbers. No real personal data is entered.
- Account strategy: tests that need an account create a new one at run time (unique `@example.com` email, generated password) and delete it with Delete Account in `@AfterMethod(alwaysRun = true)`. A new account has an empty cart, so each test is independent. Risk: if registration is broken, the tests that need a login fail too, so the registration tests are read first when a report shows many failures.
- No credentials are committed. Passwords are generated at run time, so no secrets are needed in CI.
- Product data (names, prices, counts) can change at any time on the public site, so values from exploration are examples only.

## 8. Risks and Mitigation

| Risk | Impact | Mitigation |
| --- | --- | --- |
| Full-screen Google advertising popup appears on almost every page change and blocks clicks on elements behind it; its close button sits inside the ad frame | Flaky tests, click interception errors | Add a "close popup if present" step before interactions; try with real Selenium runs in PROJECT-25; use locators not affected by ads |
| Ads inject text and links into page titles and product names | `getText` may differ from the real text | Compare with `textContent` where needed; use locators independent of injected text |
| The public site can change without notice | Expected values become stale | Read data at run time; check relationships; date all fixed data |
| Shared public site: accounts and data are not isolated | Interference between runs | Use unique fake emails per run if accounts are created in tests; clean up with Delete Account |
| Third-party site: registering accounts creates data on a site we do not own | Ethical and cleanup concerns | Fake data only; delete test accounts after use |
| No specification: expected results are inferred | Disputed expected results | Classify uncertain items as defect candidate or observation; confirm before reporting |
| Browser-dependent validation messages | Text assertions fail on other languages | Do not assert text of native browser bubbles; assert the field state or that the form is not submitted |
| Timed messages disappear after about 5 seconds | Missed assertions | Use explicit waits for visibility and read the text immediately |
| Site availability or slowness | Intermittent failures | Retry policy for environment failures; keep tests independent |

## 9. Entry and Exit Criteria

### 9.1 Entry criteria

- The site is reachable.
- The exploration is complete and the findings are recorded.
- This test plan is reviewed.
- For automated execution: the framework builds and a test account strategy is in place.

### 9.2 Exit criteria

- All planned test cases for high-priority features (FT-03 to FT-08) are executed.
- Every failure is classified as a defect candidate, confirmed bug, test issue or environment issue.
- Confirmed bugs have bug reports.
- The Allure report is generated for the final run.
- This plan and the test cases are updated to match the actual results.

## 10. Initial Findings

Source: exploratory session, 2026-11-22 to 2026-11-25, on https://automationexercise.com. All items below are observations at that time. **No item is a confirmed bug yet.** Section 10.1 lists the possible bugs (defect candidates); once an item is confirmed it gets a bug report (see Section 5.4).

### 10.1 Defect candidates (possible bugs, to be confirmed)

| ID | Feature | Observed behavior |
| --- | --- | --- |
| FND-01 | FT-04, FT-05 | Add to cart accepts Quantity `0` and `-1`. The popup reports success. The cart shows quantity `0` or `-1` and a total of `0` or a negative amount. |
| FND-02 | FT-08 | With an empty cart, `/checkout` can be opened by URL and an order can be placed with a total of Rs. 0 (`/payment_done/0`). |
| FND-03 | FT-08 | Payment accepts invalid card numbers (letters, too short, too long) and completes the order. |
| FND-04 | FT-06 | Signup accepts letters in Zipcode and Mobile Number. |
| FND-05 | FT-06 | Signup accepts a date of birth that does not exist (31 February). |
| FND-06 | FT-03 | Special characters in search (`#`, `&`, `%`, `+`, `@#$`) are not encoded in the URL, the search box loses characters, and results do not match (for example searching `H&M` returns results for `H` and not the 5 products of the brand). Probably one common cause, not yet confirmed. |
| FND-07 | FT-03 | Pressing Enter in the search box does not search; only the search button works. |

### 10.2 Observations

| ID | Feature | Observed behavior |
| --- | --- | --- |
| OBS-01 | FT-03 | A leading space in the search term gave different results on 2026-11-22 and 2026-11-23. A trailing space is trimmed. |
| OBS-02 | FT-02 | Page title casing and spacing are inconsistent (for example `WOMEN - DRESS PRODUCTS` against `Women - Tops Products`, and a double space after `-`). Not known whether this comes from CSS or from the text itself. |
| OBS-03 | FT-01 | One card in Recommended items shows `Rs. 1000` in place of the product name; the page source has the correct name. The source of the change is unknown (web script, ads or browser extension). |
| OBS-04 | FT-04 | The Quantity field accepts `e` but not `abc`; Add to cart with `e` adds nothing to the cart. |
| OBS-05 | FT-04 | A submitted review shows `Thank you for your review.` for about 5 seconds, the form is cleared, and the review is not displayed on the page. |
| OBS-06 | FT-05 | The cart has no total row and the quantity cannot be edited in the cart. |
| OBS-07 | FT-06 | No limit on password length (1 and 100 characters both accepted). |
| OBS-08 | FT-07 | Wrong password and unknown email show the same message. A logged-in user opening `/login` is sent to the home page. Login with an uppercase email is rejected. |
| OBS-09 | FT-08 | The downloaded invoice is a `.txt` file with only the name and total amount; the number at the end of `/payment_done/{n}` matches the total. |
| OBS-10 | FT-09 | Only the Email field is required in the contact form; a file up to 20 MB was accepted (larger files were not tried). |
| OBS-11 | FT-10 | Subscribing with the same email again succeeds with no duplicate warning; the success message disappears after about 5 seconds and the field is cleared. |
| OBS-12 | FT-11 | An unknown URL redirects to the home page; there is no 404 page. |
| OBS-13 | FT-05 | The cart page `/view_cart` has the browser tab title `Automation Exercise - Checkout` (seen 2026-11-27). |
| OBS-14 | FT-06 | The **Account Deleted!** page `/delete_account` has the browser tab title `Automation Exercise - Account Created` (seen twice on 2026-11-28). |

### 10.3 Items to verify again

- Whether FND-06 and the search findings share one cause (URL encoding).
- Whether the double space in the page headings (OBS-02) comes from an injected ad element. On 2026-11-27 the browser tab title (`getTitle`, `document.title` and `textContent`) was read on 8 pages (`/`, `/products`, `/login`, `/view_cart`, `/contact_us`, `/test_cases`, `/api_list`, `/product_details/1`) and was the same in all three, with a single space after `-`. The headings of the category and brand pages were not read yet.
- The source of the `Rs. 1000` text (OBS-03).
- File size limit above 20 MB in the contact form.
- Behavior with a leading space in search (OBS-01), which was not consistent between two sessions.

### 10.4 Notes on automation risk

Advertising popups, injected text and links, timed messages and native browser alerts were all observed during exploration (see Section 8). They are handled in the framework design, and no bug is raised for the advertising behavior.
