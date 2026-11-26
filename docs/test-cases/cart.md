# Test Cases: Cart (FT-05)

| Item | Value |
|---|---|
| Application under test | Automation Exercise (https://automationexercise.com) |
| Feature | FT-05 - Shopping cart |
| Related work items | PROJECT-23 (test plan and exploration), PROJECT-24 (test cases) |
| Last updated | 2026-11-26 |

## Notes

- No requirements document is available. Expected results come from direct observation during exploration (see PROJECT-23). Cases of type **Observation** describe behavior that was observed but cannot be confirmed as correct.
- **Auto** shows whether the case is a candidate for Selenium automation.
- The public site can change at any time. Prices in the data set are the values seen during exploration. The tests read names and prices at run time and compare relationships (the total of a row equals the price times the quantity).
- Advertising can add underlined words, small tags and side panels to product names (for example the word `Dress` after a product name). The tests open products by the id in the URL and ignore text added by advertising.
- Each case starts with an empty cart and a logged-out user unless the case says otherwise.
- Cases about the cart after login or registration are written in `login.md` (TC-LOGIN-006) and `registration.md` (TC-REG-012). Quantity `0` and `-1` are written in `products.md` (TC-PROD-017).
- Products added while the user is logged out stay in the cart after login. When an account is deleted, its cart is deleted with it.
- The advertising popup can appear on any page change. Closing it is the job of the test framework and is not written as a step in each case.

## Test data

| Product | Price | Details page |
|---|---|---|
| Blue Top | Rs. 500 | `/product_details/1` |
| Blue Cotton Indie Mickey | Rs. 1530 | `/product_details/21` |
| Rose Pink Embroidered Maxi | Rs. 2300 | `/product_details/38` |

## Test cases

| ID | Title | Type | Priority | Auto | Precondition | Steps | Expected result | Feature |
|---|---|---|---|---|---|---|---|---|
| TC-CART-001 | An empty cart shows a message and a link to the products | Positive | Medium | Yes | The cart is empty | 1. Open `https://automationexercise.com/view_cart`.<br>2. Read the message.<br>3. Click the word `here` in the message. | The message `Cart is empty! Click here to buy products.` is displayed, and the word `here` is a highlighted link. After the click the Products page `https://automationexercise.com/products` is displayed. | FT-05 |
| TC-CART-002 | One product in the cart shows the right price, quantity and total | Positive | High | Yes | The cart is empty | 1. Open the details page of `Blue Top` (`/product_details/1`).<br>2. Enter the quantity `1` and click **Add to cart**.<br>3. Click **View Cart**. | The cart table has the columns Item, Description, Price, Quantity and Total. It has one row for `Blue Top` with the category `Women > Tops` under the name, the price Rs. 500, the quantity 1 and the total Rs. 500. | FT-05 |
| TC-CART-003 | Several products in the cart show the right total for each row | Positive | High | Yes | The cart is empty | 1. Add `Blue Top` with the quantity `2`.<br>2. Add `Blue Cotton Indie Mickey` with the quantity `1`.<br>3. Add `Rose Pink Embroidered Maxi` with the quantity `3`.<br>4. Open `/view_cart`. | The cart has three rows, one for each product. In each row the total equals the price times the quantity (Rs. 1000, Rs. 1530 and Rs. 6900). | FT-05 |
| TC-CART-004 | The cart has no row with the overall total | Observation | Low | No | The cart has two or three products (see TC-CART-003) | 1. Open `/view_cart`.<br>2. Look below the last product row. | There is a total for each row but no line with the total of the whole cart. No requirement is available to confirm whether this is intended. | FT-05 |
| TC-CART-005 | The quantity cannot be changed in the cart | Observation | Low | No | The cart has at least one product | 1. Open `/view_cart`.<br>2. Click the quantity of a product. | The quantity cannot be edited and nothing changes. No requirement is available to confirm whether this is intended. | FT-05 |
| TC-CART-006 | The product image and name in the cart open the product details | Positive | Low | Yes | The cart has `Blue Top` | 1. Open `/view_cart`.<br>2. Click the **Item** area of the `Blue Top` row.<br>3. Go back to the cart and click the **Description** area of the same row. | Each click opens the details page of `Blue Top`. | FT-05 |
| TC-CART-007 | Remove one product from the cart | Positive | High | Yes | The cart has two products (see TC-CART-003) | 1. Open `/view_cart`.<br>2. Click the **x** button of one product row. | The row disappears and the other row stays. No message or confirmation is displayed. | FT-05 |
| TC-CART-008 | Removing the last product shows the empty cart message | Positive | Medium | Yes | The cart has one product | 1. Open `/view_cart`.<br>2. Click the **x** button of the only row. | The message `Cart is empty! Click here to buy products.` is displayed. | FT-05 |
| TC-CART-009 | The cart is kept after the page is reloaded | Positive | Medium | Yes | The cart has at least one product | 1. Open `/view_cart` and note the rows.<br>2. Reload the page. | The same rows, quantities and totals are displayed. An empty cart still shows the empty cart message after a reload. | FT-05 |
| TC-CART-010 | The cart after the browser is closed and opened again | Observation | Low | No | The user is logged out and the cart has one product | 1. In a normal browser window, close the browser and open it again, then open `/view_cart`.<br>2. Repeat the test in a private (incognito) window. | In a normal window the product is still in the cart. In a private window the cart is empty. Closing a private window deletes the browser data, so this result does not show how the site stores the cart. | FT-05 |
| TC-CART-011 | Proceed To Checkout while logged out shows a login prompt | Positive | High | Yes | The user is logged out and the cart has one product | 1. Open `/view_cart`.<br>2. Click **Proceed To Checkout**. | A popup with the title **Checkout** is displayed. It has the text `Register / Login account to proceed on checkout.`, a link **Register / Login** and a button **Continue On Cart**. | FT-05 |
| TC-CART-012 | The Register / Login link in the prompt opens the login page | Positive | Medium | Yes | The **Checkout** popup is displayed (see TC-CART-011) | 1. Click **Register / Login** in the popup. | The login page `https://automationexercise.com/login` is displayed. | FT-05 |
| TC-CART-013 | Continue On Cart closes the prompt and stays on the cart | Positive | Medium | Yes | The **Checkout** popup is displayed (see TC-CART-011) | 1. Click **Continue On Cart** in the popup. | The popup closes and the cart page stays open with the same products. | FT-05 |
