# Test Cases: Products, Product Details and Reviews (FT-02, FT-04)

| Item | Value |
|---|---|
| Application under test | Automation Exercise (https://automationexercise.com) |
| Features | FT-02 - Product listing, category and brand filtering; FT-04 - Product details and product review |
| Related work items | PROJECT-23 (test plan and exploration), PROJECT-24 (test cases) |
| Last updated | 2026-11-26 |

## Notes

- No requirements document is available. Expected results come from direct observation during exploration (see PROJECT-23). Cases of type **Observation** describe behavior that was observed but cannot be confirmed as correct.
- Cases with **Auto** = `No (Defect candidate)` show a reasonable expected result and the behavior that was observed. They are possible bugs that are not confirmed yet (see the test plan, Section 5.4). They are not automated until the behavior is confirmed.
- **Auto** shows whether the case is a candidate for Selenium automation.
- The public site can change at any time. Product counts and prices in the data sets below are the values seen during exploration. Where possible the tests compare relationships (for example, the number of products on a brand page equals the number shown next to the brand name) and read values at run time.
- Page titles are not asserted, because their capital letters and spaces are not consistent between pages. The tests check the URL and the products shown.
- Advertising can add underlined words, small tags and side panels to titles and product names (for example the word `Dress` after a product name). Products are identified by name or by the id in the URL, and the tests ignore text added by advertising.
- Messages for empty required fields and for badly formatted emails are browser validation bubbles, not messages of the website. The tests check that a bubble appears on the right field and that the form is not submitted, not the text.
- Each case starts with an empty cart unless the case says otherwise. The user is logged out, because browsing and the cart do not need an account.
- The advertising popup can appear on any page change. Closing it is the job of the test framework and is not written as a step in each case.

## Test data

| Product | Price | Category | Brand | Details page |
|---|---|---|---|---|
| Blue Top | Rs. 500 | Women > Tops | Polo | `/product_details/1` |
| Blue Cotton Indie Mickey | Rs. 1530 | Kids > Dress | Biba | `/product_details/21` |
| Rose Pink Embroidered Maxi | Rs. 2300 | Kids > Dress | Madame | `/product_details/38` |

| Category (sub-category) | Page | Products seen |
|---|---|---|
| Women > Dress | `/category_products/1` | 3 |
| Women > Tops | `/category_products/2` | 6 |
| Women > Saree | `/category_products/7` | 3 |
| Men > Tshirts | `/category_products/3` | 6 |
| Men > Jeans | `/category_products/6` | 3 |
| Kids > Dress | `/category_products/4` | 6 |
| Kids > Tops & Shirts | `/category_products/5` | 7 |

| Brand | Page | Products seen |
|---|---|---|
| Polo | `/brand_products/Polo` | 6 |
| H&M | `/brand_products/H&M` | 5 |
| Madame | `/brand_products/Madame` | 5 |
| Mast & Harbour | `/brand_products/Mast%20&%20Harbour` | 3 |
| Babyhug | `/brand_products/Babyhug` | 4 |
| Allen Solly Junior | `/brand_products/Allen%20Solly%20Junior` | 3 |
| Kookie Kids | `/brand_products/Kookie%20Kids` | 3 |
| Biba | `/brand_products/Biba` | 5 |

| Data set | Value |
|---|---|
| Review | Your Name `Test User`, Email Address `test@example.com`, review text `Test review` |
| Invalid review email | `abc` |
| Invalid quantities | `0`, `-1` |
| Large quantities | `100`, `1000` |

## Test cases

| ID | Title | Type | Priority | Auto | Precondition | Steps | Expected result | Feature |
|---|---|---|---|---|---|---|---|---|
| TC-PROD-001 | The number of products on All Products equals the sum of the brand counts | Positive | Medium | Yes | The home page or the Products page is open | 1. Open `https://automationexercise.com/products`.<br>2. Count the product cards.<br>3. Add up the numbers next to each brand name in the Brands list. | The number of product cards equals the sum of the brand numbers (34 when seen during exploration). There is no **load more** button and no page numbers. | FT-02 |
| TC-PROD-002 | A category page shows the products of the chosen sub-category (data-driven) | Positive | Medium | Yes | The Products page is open | 1. In the Category list, click **Women**, **Men** or **Kids** to open the group.<br>2. Click a sub-category from the "Category" data set.<br>3. Count the product cards.<br>4. Repeat for each sub-category in the data set. | The URL is the page of the sub-category from the data set. The page shows only products of that sub-category and the number of cards equals the "Products seen" value. | FT-02 |
| TC-PROD-003 | A brand page shows the products of the chosen brand (data-driven) | Positive | Medium | Yes | The Products page is open | 1. In the Brands list, note the number next to a brand from the "Brand" data set.<br>2. Click the brand name.<br>3. Count the product cards.<br>4. Repeat for each brand in the data set. | The URL is the page of the brand from the data set. The number of product cards equals the number shown next to the brand name. | FT-02 |
| TC-PROD-004 | The breadcrumb shows the path and only **Products** can be clicked (data-driven) | Positive | Low | Yes | The Products page is open | 1. Open the category page `Women > Dress` and read the breadcrumb.<br>2. Click each part of the breadcrumb.<br>3. Open the brand page `Biba` and repeat steps 1 and 2. | The category page shows `Products > Women > Dress` and the brand page shows `Products > Biba`. Only **Products** can be clicked, and it opens `https://automationexercise.com/products`. The other parts cannot be clicked. | FT-02 |
| TC-PROD-005 | Changing from a brand page to a category page does not keep the old filter | Positive | Medium | Yes | The Products page is open | 1. Open the brand page `Biba`.<br>2. In the Category list, open `Women > Dress`. | The page shows only the products of `Women > Dress` (the number in the "Category" data set). The brand filter is no longer applied. | FT-02 |
| TC-PROD-006 | Opening a category group closes the group that was open | Observation | Low | No | The Products page is open | 1. Click **Women** in the Category list.<br>2. Click **Men**. | The **Women** group closes and the **Men** group opens, so only one group is open at a time. No requirement is available to confirm whether this is intended. | FT-02 |
| TC-PROD-007 | Add to cart from a product list shows the confirmation popup (data-driven) | Positive | High | Yes | The cart is empty | 1. Open a page from this list: All Products, the brand page `Polo`, the category page `Women > Tops`.<br>2. Move the mouse over a product card.<br>3. Click **Add to cart** on the orange overlay. | A popup with the title **Added!** and the text `Your product has been added to cart.` is displayed. It has a **View Cart** link and a **Continue Shopping** button. The product is in the cart (see TC-CART-002). | FT-02, FT-05 |
| TC-PROD-008 | Add to cart from Recommended items on the home page | Positive | Medium | Yes | The home page is open and the cart is empty | 1. Scroll down to **Recommended items**.<br>2. Click **Add to cart** on a product card. | The popup with the title **Added!** and the text `Your product has been added to cart.` is displayed, with a **View Cart** link and a **Continue Shopping** button. The product is added to the cart. | FT-02, FT-05 |
| TC-PROD-009 | Clicking a Recommended items card does not open the product details | Observation | Low | No | The home page is open | 1. Scroll down to **Recommended items**.<br>2. Click on a product card (not on **Add to cart**). | The page does not go to the product details page. No requirement is available to confirm whether this is intended. | FT-02 |
| TC-PROD-010 | View Product opens the details page of the chosen product | Positive | High | Yes | The Products page is open | 1. Note the name and the price of `Blue Top` on its card.<br>2. Click **View Product** on that card. | The URL is `/product_details/` followed by a number. The details page shows the same name and the same price as the card (`Blue Top`, Rs. 500). | FT-02, FT-04 |
| TC-PROD-011 | The details page shows the product information (data-driven) | Positive | High | Yes | The cart is empty | 1. Open a details page from the "Product" data set (`/product_details/1`, `/product_details/21`, `/product_details/38`).<br>2. Read the page. | The page shows the product name, the price, **Category**, **Availability** (`In Stock`), **Condition** (`New`) and **Brand**, with the values from the data set. A **Quantity** field and an **Add to cart** button are shown. | FT-04 |
| TC-PROD-012 | Large quantities are added to the cart with the right total (data-driven) | Boundary | High | Yes | The details page of `Blue Top` is open and the cart is empty | 1. Enter a quantity from the "Large quantities" data set.<br>2. Click **Add to cart**.<br>3. Open `/view_cart`. | The product is in the cart with the entered quantity. The total of the row equals the price times the quantity (Rs. 500 times 100 is Rs. 50000, and Rs. 500 times 1000 is Rs. 500000). | FT-04, FT-05 |
| TC-PROD-013 | Adding the same product twice adds to the quantity | Positive | High | Yes | The details page of `Blue Top` is open and the cart is empty | 1. Enter the quantity `1` and click **Add to cart**, then click **Continue Shopping**.<br>2. Enter the quantity `2` and click **Add to cart**.<br>3. Open `/view_cart`. | The cart has one row for `Blue Top` with the quantity 3 and the total Rs. 1500. There is no second row. | FT-04, FT-05 |
| TC-PROD-014 | The popup buttons View Cart and Continue Shopping work | Positive | Medium | Yes | The details page of `Blue Top` is open and the cart is empty | 1. Click **Add to cart**.<br>2. Click **Continue Shopping**.<br>3. Click **Add to cart** again.<br>4. Click **View Cart**. | Step 2: the popup closes and the details page stays open. Step 4: the cart page `https://automationexercise.com/view_cart` is displayed. | FT-04, FT-05 |
| TC-PROD-015 | Add to cart with an empty quantity adds nothing | Negative | Medium | Yes | The details page of `Blue Top` is open and the cart is empty | 1. Clear the Quantity field.<br>2. Click **Add to cart**.<br>3. Open `/view_cart`. | The confirmation popup is not displayed and no warning bubble is shown. The cart is empty. | FT-04, FT-05 |
| TC-PROD-016 | The Quantity field accepts the letter `e` but not other letters | Observation | Low | No | The details page of `Blue Top` is open and the cart is empty | 1. Try to type `abc` in the Quantity field.<br>2. Type `e` in the field.<br>3. Click **Add to cart**.<br>4. Open `/view_cart`. | Step 1: the letters are not accepted. Step 2: the letter `e` is accepted. Step 3: the confirmation popup is not displayed. Step 4: the cart is empty. No requirement is available to confirm whether this is intended. | FT-04 |
| TC-PROD-017 | Quantity `0` and `-1` must not be added to the cart (data-driven) | Boundary | High | No (Defect candidate) | The details page of `Blue Top` is open and the cart is empty | 1. Enter a quantity from the "Invalid quantities" data set.<br>2. Click **Add to cart**.<br>3. Click **Continue Shopping**.<br>4. Open `/view_cart`.<br>5. Repeat from an empty cart for each value. | **Expected:** the product is not added and a message tells the user to enter a valid quantity.<br>**Observed:** the **Added!** popup is displayed. After **Continue Shopping** the field still shows the entered value. The cart has `Blue Top` with the quantity `0` (total 0) or `-1` (total is a negative amount). | FT-04, FT-05 |
| TC-PROD-018 | Submitting a review with an empty field is blocked (data-driven) | Negative | Medium | Yes | A details page is open and the **Write Your Review** form is visible | 1. Leave all fields empty and click **Submit**.<br>2. Reload the page, fill only **Your Name** and click **Submit**.<br>3. Reload the page, fill **Your Name** and **Email Address**, leave **Add Review Here!** empty and click **Submit**. | Step 1: a browser validation bubble appears on **Your Name**. Step 2: a bubble appears on **Email Address**. Step 3: a bubble appears on **Add Review Here!**. In each case the form is not submitted. | FT-04 |
| TC-PROD-019 | Submitting a review with an invalid email is blocked | Negative | Medium | Yes | A details page is open and the **Write Your Review** form is visible | 1. Fill **Your Name** and **Add Review Here!** with the review data.<br>2. Enter `abc` in **Email Address**.<br>3. Click **Submit**. | A browser validation bubble appears on **Email Address**. The form is not submitted. | FT-04 |
| TC-PROD-020 | Submit a valid review | Positive | Medium | Yes | A details page is open and the **Write Your Review** form is visible | 1. Fill the form with the "Review" data.<br>2. Click **Submit**.<br>3. Look at the area below the review text field.<br>4. Wait about 5 seconds. | The line `Thank you for your review.` is displayed below the review area. After about 5 seconds the line disappears and the fields are back to their empty state. | FT-04 |
| TC-PROD-021 | A submitted review is not displayed on the page | Observation | Low | No | A review was submitted (see TC-PROD-020) | 1. Reload the details page.<br>2. Open another product and come back to the first product. | The review is not displayed on the page in either case. No requirement is available to confirm whether reviews should be displayed. | FT-04 |
