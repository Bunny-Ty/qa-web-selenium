# Test Cases: Checkout and Payment (FT-08)

| Item | Value |
|---|---|
| Application under test | Automation Exercise (https://automationexercise.com) |
| Feature | FT-08 - Checkout and payment |
| Related work items | PROJECT-23 (test plan and exploration), PROJECT-24 (test cases) |
| Last updated | 2026-11-26 |

## Notes

- No requirements document is available. Expected results come from direct observation during exploration (see PROJECT-23). Cases of type **Observation** describe behavior that was observed but cannot be confirmed as correct.
- Cases with **Auto** = `No (Defect candidate)` show a reasonable expected result and the behavior that was observed. They are possible bugs that are not confirmed yet (see the test plan, Section 5.4). They are not automated until the behavior is confirmed.
- **Auto** shows whether the case is a candidate for Selenium automation.
- Only fake card data is used. Real card numbers are never used.
- Messages for empty required fields are browser validation bubbles, not messages of the website. The tests check that a bubble appears on the right field and that the form is not submitted, not the text.
- Credentials are never stored in this repository. `<TEST_EMAIL>` and `<TEST_PASSWORD>` come from the local configuration (GitHub Secrets in CI). The account was registered with the "Valid account data" of `registration.md`.
- Checkout needs a logged-in user. Each case starts with the user logged in and `Blue Top` (Rs. 500) in the cart unless the case says otherwise. The checkout for a logged-out user is in `cart.md` (TC-CART-011 to TC-CART-013).
- The order amount is read at run time from the Total Amount line, so the tests compare relationships and do not use fixed amounts.
- The advertising popup can appear on any page change. Closing it is the job of the test framework and is not written as a step in each case.

## Test data

| Data set | Value |
|---|---|
| Fake card | Name on Card `Test User`, Card Number `1111 2222 3333 4444`, CVC `311`, Expiration month `03`, Expiration year `2030` |
| Invalid card numbers | `a`, `1111`, `123456789012345678901234567890` (30 digits) |
| Order comment | 100 characters of text |
| Product | `Blue Top`, Rs. 500 (`/product_details/1`) |

## Test cases

| ID | Title | Type | Priority | Auto | Precondition | Steps | Expected result | Feature |
|---|---|---|---|---|---|---|---|---|
| TC-CHECKOUT-001 | The checkout page shows the addresses, the order and the total | Positive | High | Yes | The user is logged in and `Blue Top` is in the cart | 1. Open `/view_cart`.<br>2. Click **Proceed To Checkout**. | The URL is `https://automationexercise.com/checkout` and the breadcrumb is `Home > Checkout`. The **Address Details** area has `Your delivery address` and `Your billing address`. **Review Your Order** shows the product with its quantity and its total. A **Total Amount** line is shown. A comment area with the text `If you would like to add a comment about your order, please write it in the field below.` and a **Place Order** button are shown. | FT-08 |
| TC-CHECKOUT-002 | The delivery and billing addresses match the registration data | Positive | High | Yes | The checkout page is open (see TC-CHECKOUT-001) | 1. Read `Your delivery address`.<br>2. Read `Your billing address`. | Both addresses show the data entered at registration: the title and name, the address, the state, the city, the zipcode, the country and the mobile number. | FT-08 |
| TC-CHECKOUT-003 | The order comment field accepts text | Positive | Low | Yes | The checkout page is open | 1. Enter the "Order comment" text in the comment field. | The 100 characters are accepted. The limit for longer text is not known. | FT-08 |
| TC-CHECKOUT-004 | Place Order opens the payment page | Positive | High | Yes | The checkout page is open | 1. Click **Place Order**. | The URL is `https://automationexercise.com/payment`. The page has the fields Name on Card, Card Number, CVC and Expiration (month and year). | FT-08 |
| TC-CHECKOUT-005 | Payment is blocked when a payment field is empty (data-driven) | Negative | Medium | Yes | The payment page is open | 1. Leave all fields empty and click the confirm button.<br>2. Fill Name on Card and click the confirm button.<br>3. Also fill Card Number and click the confirm button.<br>4. Also fill CVC and click the confirm button. | Step 1: a browser validation bubble appears on Name on Card. Step 2: a bubble appears on Card Number. Step 3: a bubble appears on CVC. Step 4: a bubble appears on Expiration. In each case the order is not placed. | FT-08 |
| TC-CHECKOUT-006 | Place an order with fake card data | Positive | High | Yes | The payment page is open and the order total was noted on the checkout page | 1. Fill the payment fields with the "Fake card" data.<br>2. Click the confirm button. | The message `Your order has been placed successfully!` is displayed in a green box above the confirm button. The user is taken to `https://automationexercise.com/payment_done/` followed by a number, and the number equals the order total. The page shows `Congratulations! Your order has been confirmed!`. | FT-08 |
| TC-CHECKOUT-007 | After the order, Download Invoice and Continue work | Positive | Medium | Yes | An order was placed (see TC-CHECKOUT-006) | 1. Click **Download Invoice**.<br>2. Open the downloaded file.<br>3. Click **Continue**. | Step 1: a file with the extension `.txt` is downloaded. Step 2: the file has one line `Hi` followed by the account name, `Your total purchase amount is` followed by the order total, and `Thank you.`. Step 3: the home page `https://automationexercise.com/` is displayed. | FT-08 |
| TC-CHECKOUT-008 | The cart is empty after the order | Positive | High | Yes | An order was placed (see TC-CHECKOUT-006) | 1. Open `/view_cart`. | The cart is empty. | FT-08, FT-05 |
| TC-CHECKOUT-009 | The Proceed To Checkout button is not shown when the cart is empty | Positive | Medium | Yes | The user is logged in and the cart is empty | 1. Open `/view_cart`. | The message `Cart is empty! Click here to buy products.` is displayed and there is no **Proceed To Checkout** button. | FT-08, FT-05 |
| TC-CHECKOUT-010 | The product image and name on the checkout page cannot be clicked | Observation | Low | No | The checkout page is open | 1. Click the Item area of a product row.<br>2. Click the Description area of the same row. | Nothing happens. On the cart page the same clicks open the product details (see TC-CART-006). No requirement is available to confirm whether this difference is intended. | FT-08 |
| TC-CHECKOUT-011 | An invalid card number must be rejected (data-driven) | Negative | High | No (Defect candidate) | The payment page is open and the order total is not zero | 1. Fill Name on Card, CVC and Expiration with the "Fake card" data.<br>2. Enter a value from the "Invalid card numbers" data set in Card Number.<br>3. Click the confirm button.<br>4. Repeat from a new order for each value in the data set. | **Expected:** the order is not placed and a message tells the user that the card number is not valid.<br>**Observed for `a`, `1111` and the 30-digit number:** the order is placed. The message `Your order has been placed successfully!` is displayed and the user is taken to the **Order Placed** page. | FT-08 |
| TC-CHECKOUT-012 | An order must not be placed when the cart is empty | Negative | High | No (Defect candidate) | The user is logged in and the cart is empty | 1. Type `https://automationexercise.com/checkout` in the address bar and press Enter.<br>2. Click **Place Order**.<br>3. Fill the payment fields with the "Fake card" data and click the confirm button. | **Expected:** the checkout page is not opened with an empty cart, or the order is refused with a message.<br>**Observed:** the checkout page opens with an empty order table and `Total Amount` Rs. 0. The order is placed and the user is taken to `https://automationexercise.com/payment_done/0`. The page shows `Congratulations! Your order has been confirmed!` with the **Download Invoice** and **Continue** buttons. | FT-08, FT-05 |
