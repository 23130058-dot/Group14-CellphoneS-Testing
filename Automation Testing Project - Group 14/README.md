# Automation Testing Project - Group 14 (CellphoneS)

Katalon Studio project kiểm thử https://cellphones.com.vn — Nhóm 14.

## Mở project
Katalon Studio → **File → Open Project** → chọn thư mục này (file `Automation Testing Project - Group 14.prj`).

## Cấu trúc
| Thư mục | Nội dung | Người thực hiện |
|---|---|---|
| `Test Cases/Search` | 23 test case `TC_Search_SPxx` (mỗi ID trong sheet "Search Product" 1 test case; logic ở `SearchRunner`) | Văn Võ Chí Đạt |
| `Test Cases/Login` | `TC_Login_Smember` | Lê Thanh Trường |
| `Test Cases/ProductList/Dao_23130044` | 19 test case `TC_DSSP_xx` (chuyển từ Selenium IDE) | Nguyễn Trần Xuân Đào |
| `Test Cases/ProductList/Huy_23130136` | 19 test case `TC_DSSP_xx` (chuyển từ Selenium IDE) | Nguyễn Hữu Huy |
| `Keywords/com/cellphones` | `SearchKeywords`, `SearchRunner`, `productlist/ProductListKeywords` | |
| `Test Suites` | `TS_Search`, `TS_Login`, `TS_ProductList_Dao`, `TS_ProductList_Huy`, `TS_Group14_Full` (62 test case) | |
| `_NeedsFix` | Phần chưa chạy được (Cart của Nguyễn Thanh Tu, TC_Homepage_Navigation của Trường) | |

## Chạy
Mở `Test Suites/TS_Group14_Full` → chọn Chrome → Run. Có thể chạy riêng từng suite hoặc từng test case.

## Còn thiếu / cần xử lý (`_NeedsFix`)
- **Cart (Nguyễn Thanh Tu):** `CartKeywords.groovy` trên git bị hỏng (mất toàn bộ dấu `/`, không có `package`/`class`). `TC_Cart_Script.groovy` còn cần: class `com.cellphones.cart.CartKeywords`, các Test Object trong `Object Repository/CellphoneS/...`, và Global Variable `TEST_PRODUCT_URL`, `TIMEOUT_DEFAULT`, `VOUCHER_VALID`, `VOUCHER_INVALID`, `VOUCHER_EXPIRED`.
- **TC_Homepage_Navigation (Trường):** trên git chỉ có file `.tc` rỗng, chưa có script.
