import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import com.cellphones.cart.CartKeywords as Cart
import internal.GlobalVariable

// ============================================================
//  CẤU HÌNH: Chọn test case muốn chạy
//  Đổi giá trị RUN_CASE thành 1 trong các mã dưới đây
//  Hoặc "ALL" để chạy toàn bộ
// ============================================================
String RUN_CASE = "ALL"
// Các giá trị hợp lệ:
//  "ALL"   → chạy tất cả
//  "07"    → Cart-07: Thêm SP, verify thông tin
//  "08"    → Cart-08: Thêm cùng SP, SL +1
//  "09"    → Cart-09: Xóa 1 sản phẩm
//  "10"    → Cart-10: Xóa SP cuối → giỏ trống
//  "11"    → Cart-11: Tăng/Giảm SL, tổng tiền đúng
//  "12"    → Cart-12: Khi SL=1, nút là Thùng rác
//  "13"    → Cart-13: Từ Thùng rác → [-] khi tăng
//  "20"    → Cart-20: Từ [-] → Thùng rác khi giảm
//  "21"    → Cart-21: Voucher hợp lệ
//  "22"    → Cart-22: Voucher không tồn tại
//  "23"    → Cart-23: Voucher hết hạn
//  "24"    → Cart-24: Hủy voucher
//  "25"    → Cart-25: Voucher chưa đủ min order

// ============================================================
//  HÀM TIỆN ÍCH
// ============================================================

def log(msg) {
    WebUI.comment(msg)
    println(msg)
}

def runCase(String caseId, Closure testBody) {
    if (RUN_CASE != "ALL" && RUN_CASE != caseId) return

    log("\n═══════════════════════════════════════════")
    log("  BẮT ĐẦU: TC_Cart_${caseId}")
    log("═══════════════════════════════════════════")

    WebUI.openBrowser('')
    WebUI.maximizeWindow()

    try {
        Cart.loginSmember()
        testBody.call()
        log("✓✓✓ TC_Cart_${caseId} PASS")
    } catch (Exception e) {
        log("✗✗✗ TC_Cart_${caseId} FAIL: ${e.message}")
        WebUI.takeScreenshot("TC_Cart_${caseId}_fail.png")
        if (RUN_CASE != "ALL") throw e
    } finally {
        WebUI.closeBrowser()
    }
}

// ============================================================
//  CART-07: Thêm SP, verify thông tin
// ============================================================
def tc07() {
    Cart.clearCart()

    WebUI.navigateToUrl(GlobalVariable.TEST_PRODUCT_URL)
    WebUI.waitForPageLoad(GlobalVariable.TIMEOUT_DEFAULT)
    Cart.selectProductAttributes('Đen', '128GB')

    WebUI.scrollToElement(findTestObject('CellphoneS/ProductDetailPage/btn_AddToCart'),
                          GlobalVariable.TIMEOUT_DEFAULT)
    WebUI.click(findTestObject('CellphoneS/ProductDetailPage/btn_AddToCart'))
    WebUI.delay(2)

    Cart.openCartPage()
    WebUI.verifyElementPresent(findTestObject('CellphoneS/CartPage/lbl_ProductName'), 10)

    String name = WebUI.getText(findTestObject('CellphoneS/CartPage/lbl_ProductName'))
    log("  Product name: ${name}")
    assert name != null && !name.isEmpty() : "Tên sản phẩm rỗng"

    String price = WebUI.getText(findTestObject('CellphoneS/CartPage/lbl_ProductPrice'))
    log("  Price: ${price}")
    assert price.contains('₫') : "Giá không đúng: ${price}"

    String img = WebUI.getAttribute(findTestObject('CellphoneS/CartPage/img_Product'), 'src')
    log("  Image: ${img}")
    assert img != null && !img.isEmpty() : "Ảnh không hiển thị"

    String variant = WebUI.getText(findTestObject('CellphoneS/CartPage/lbl_ProductVariant'))
    log("  Variant: ${variant}")
    assert variant.contains('Đen') || variant.contains('128') : "Màu/dung lượng sai: ${variant}"
}

// ============================================================
//  CART-08: Thêm cùng SP lần 2, SL +1, không tạo dòng mới
// ============================================================
def tc08() {
    Cart.clearCart()
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.openCartPage()
    WebUI.verifyElementPresent(findTestObject('CellphoneS/CartPage/lbl_ProductName'), 10)

    assert Cart.countCartRows() == 1 : "Precondition sai: phải có 1 dòng"
    assert Cart.getQuantityAtRow(1) == 1 : "Precondition sai: SL phải = 1"

    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.openCartPage()

    int rows = Cart.countCartRows()
    int qty = Cart.getQuantityAtRow(1)
    log("  Rows=${rows}, Qty=${qty}")

    assert rows == 1 : "Không được tạo dòng mới, thực tế ${rows}"
    assert qty == 2 : "SL phải = 2, thực tế ${qty}"
}

// ============================================================
//  CART-09: Xóa 1 SP, tổng tiền giảm
// ============================================================
def tc09() {
    Cart.clearCart()

    // Tạo 2 dòng: SP A + SP B
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.openCartPage()
    WebUI.verifyElementPresent(findTestObject('CellphoneS/CartPage/lbl_ProductName'), 10)

    int rowsBefore = Cart.countCartRows()
    int totalBefore = Cart.getTotalPayment()
    log("  Before: rows=${rowsBefore}, total=${totalBefore}")
    assert rowsBefore >= 1

    // Xóa 1 sản phẩm (giảm SL xuống 0)
    int qty = Cart.getQuantityAtRow(1)
    for (int i = 0; i < qty; i++) {
        Cart.clickDecreaseAtRow(1)
    }
    WebUI.delay(2)

    int rowsAfter = Cart.countCartRows()
    int totalAfter = Cart.getTotalPayment()
    log("  After: rows=${rowsAfter}, total=${totalAfter}")

    assert rowsAfter == rowsBefore - 1 : "Phải giảm 1 dòng (${rowsBefore} → ${rowsAfter})"
    assert totalAfter < totalBefore : "Tổng tiền phải giảm"
}

// ============================================================
//  CART-10: Xóa SP cuối → giỏ trống
// ============================================================
def tc10() {
    Cart.clearCart()
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.openCartPage()
    WebUI.verifyElementPresent(findTestObject('CellphoneS/CartPage/lbl_ProductName'), 10)

    assert Cart.countCartRows() == 1 : "Precondition: phải có 1 dòng"

    Cart.clickDecreaseAtRow(1)
    WebUI.delay(3)

    int rowsAfter = Cart.countCartRows()
    boolean hasEmptyMsg = WebUI.verifyElementPresent(
        findTestObject('CellphoneS/CartPage/lbl_EmptyCart'), 5, FailureHandling.OPTIONAL)
    log("  Rows=${rowsAfter}, EmptyMsg=${hasEmptyMsg}")

    assert rowsAfter == 0 : "Giỏ phải trống, thực tế ${rowsAfter} dòng"
    assert hasEmptyMsg : "Phải hiển thị thông báo giỏ hàng trống"
}

// ============================================================
//  CART-11: Tăng/Giảm SL, tổng tiền cập nhật đúng
// ============================================================
def tc11() {
    Cart.clearCart()
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.openCartPage()
    WebUI.verifyElementPresent(findTestObject('CellphoneS/CartPage/lbl_ProductName'), 10)

    int qty0 = Cart.getQuantityAtRow(1)
    int total0 = Cart.getTotalPayment()
    assert qty0 == 2 : "Precondition: SL phải = 2, thực tế ${qty0}"

    // Tăng [+]
    Cart.clickIncreaseAtRow(1)
    int qty1 = Cart.getQuantityAtRow(1)
    int total1 = Cart.getTotalPayment()
    log("  After [+]: qty=${qty1}, total=${total1}")
    assert qty1 == 3 : "Sau [+] SL phải = 3, thực tế ${qty1}"
    assert total1 > total0 : "Tổng tiền phải tăng"

    // Giảm [-]
    Cart.clickDecreaseAtRow(1)
    int qty2 = Cart.getQuantityAtRow(1)
    int total2 = Cart.getTotalPayment()
    log("  After [-]: qty=${qty2}, total=${total2}")
    assert qty2 == 2 : "Sau [-] SL phải = 2, thực tế ${qty2}"
    assert total2 == total0 : "Tổng tiền phải khôi phục về ${total0}"
}

// ============================================================
//  CART-12: Khi SL = 1, nút giảm là Thùng rác
// ============================================================
def tc12() {
    Cart.clearCart()
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.openCartPage()
    WebUI.verifyElementPresent(findTestObject('CellphoneS/CartPage/lbl_ProductName'), 10)

    assert Cart.getQuantityAtRow(1) == 1 : "Precondition: SL phải = 1"

    String btnType = Cart.getDecreaseButtonType(1)
    log("  Button type: ${btnType}")
    assert btnType == "trash" : "Khi SL=1 phải là Thùng rác, thực tế: ${btnType}"
}

// ============================================================
//  CART-13: Từ Thùng rác → [-] khi tăng SL
// ============================================================
def tc13() {
    Cart.clearCart()
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.openCartPage()
    WebUI.verifyElementPresent(findTestObject('CellphoneS/CartPage/lbl_ProductName'), 10)

    assert Cart.getQuantityAtRow(1) == 1
    assert Cart.getDecreaseButtonType(1) == "trash" : "Trước khi tăng phải là Thùng rác"

    Cart.clickIncreaseAtRow(1)

    assert Cart.getQuantityAtRow(1) == 2
    String typeAfter = Cart.getDecreaseButtonType(1)
    log("  Button type sau tăng: ${typeAfter}")
    assert typeAfter == "minus" : "Sau [+] phải là [-], thực tế: ${typeAfter}"
}

// ============================================================
//  CART-20: Từ [-] → Thùng rác khi giảm SL
// ============================================================
def tc20() {
    Cart.clearCart()
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.openCartPage()
    WebUI.verifyElementPresent(findTestObject('CellphoneS/CartPage/lbl_ProductName'), 10)

    assert Cart.getQuantityAtRow(1) == 2
    assert Cart.getDecreaseButtonType(1) == "minus" : "Khi SL=2 phải là [-]"

    Cart.clickDecreaseAtRow(1)

    assert Cart.getQuantityAtRow(1) == 1
    String typeAfter = Cart.getDecreaseButtonType(1)
    log("  Button type sau giảm: ${typeAfter}")
    assert typeAfter == "trash" : "Sau [-] phải là Thùng rác, thực tế: ${typeAfter}"
}

// ============================================================
//  CART-21: Voucher hợp lệ → giảm đúng
// ============================================================
def tc21() {
    Cart.clearCart()
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.openCartPage()
    WebUI.verifyElementPresent(findTestObject('CellphoneS/CartPage/lbl_ProductName'), 10)

    int subTotal = Cart.getSubTotal()
    log("  SubTotal: ${subTotal}")
    assert subTotal >= 500000 : "Precondition: đơn phải >= 500k, thực tế ${subTotal}"

    Cart.applyVoucher(GlobalVariable.VOUCHER_VALID)

    int discount = Cart.getDiscountAmount()
    int total = Cart.getTotalPayment()
    log("  Discount=${discount}, Total=${total}")

    assert discount > 0 : "Không áp dụng được voucher"
    assert total == subTotal - discount :
        "Tổng sai: ${total} != ${subTotal} - ${discount}"
}

// ============================================================
//  CART-22: Voucher không tồn tại → báo lỗi
// ============================================================
def tc22() {
    Cart.clearCart()
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.openCartPage()
    WebUI.verifyElementPresent(findTestObject('CellphoneS/CartPage/lbl_ProductName'), 10)

    int subTotalBefore = Cart.getSubTotal()

    Cart.applyVoucher(GlobalVariable.VOUCHER_INVALID)

    boolean hasError = WebUI.verifyElementPresent(
        findTestObject('CellphoneS/CartPage/lbl_VoucherErrorMessage'),
        5, FailureHandling.OPTIONAL)
    assert hasError : "Không hiển thị thông báo lỗi"

    String msg = WebUI.getText(findTestObject('CellphoneS/CartPage/lbl_VoucherErrorMessage'))
    log("  Error msg: ${msg}")

    String lower = msg.toLowerCase()
    assert lower.contains('không hợp lệ') || lower.contains('không tồn tại') :
        "Thông báo lỗi không đúng: ${msg}"

    assert Cart.getSubTotal() == subTotalBefore
    assert Cart.getDiscountAmount() == 0 : "Không được giảm tiền khi voucher sai"
}

// ============================================================
//  CART-23: Voucher hết hạn → báo lỗi
// ============================================================
def tc23() {
    Cart.clearCart()
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.openCartPage()
    WebUI.verifyElementPresent(findTestObject('CellphoneS/CartPage/lbl_ProductName'), 10)

    int subTotalBefore = Cart.getSubTotal()
    Cart.applyVoucher(GlobalVariable.VOUCHER_EXPIRED)

    boolean hasError = WebUI.verifyElementPresent(
        findTestObject('CellphoneS/CartPage/lbl_VoucherErrorMessage'),
        5, FailureHandling.OPTIONAL)
    assert hasError : "Không hiển thị thông báo lỗi"

    String msg = WebUI.getText(findTestObject('CellphoneS/CartPage/lbl_VoucherErrorMessage'))
    log("  Error msg: ${msg}")

    String lower = msg.toLowerCase()
    assert lower.contains('hết hạn') : "Thông báo phải có 'hết hạn', thực tế: ${msg}"

    assert Cart.getSubTotal() == subTotalBefore
    assert Cart.getDiscountAmount() == 0
}

// ============================================================
//  CART-24: Hủy voucher → tổng tiền khôi phục
// ============================================================
def tc24() {
    Cart.clearCart()
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.openCartPage()
    WebUI.verifyElementPresent(findTestObject('CellphoneS/CartPage/lbl_ProductName'), 10)

    int subTotal0 = Cart.getSubTotal()

    // Áp voucher thành công trước
    Cart.applyVoucher(GlobalVariable.VOUCHER_VALID)
    int discount = Cart.getDiscountAmount()
    assert discount > 0 : "Precondition: phải áp được voucher"

    // Hủy voucher
    WebUI.click(findTestObject('CellphoneS/CartPage/btn_RemoveVoucher'))
    WebUI.delay(2)

    int discountAfter = Cart.getDiscountAmount()
    int totalAfter = Cart.getTotalPayment()
    log("  Sau hủy: discount=${discountAfter}, total=${totalAfter}")

    assert discountAfter == 0 : "Discount phải về 0, thực tế ${discountAfter}"
    assert totalAfter == subTotal0 : "Tổng phải khôi phục về ${subTotal0}, thực tế ${totalAfter}"
}

// ============================================================
//  CART-25: Voucher chưa đủ min order → báo lỗi
// ============================================================
def tc25() {
    Cart.clearCart()
    Cart.addProductToCart(GlobalVariable.TEST_PRODUCT_URL)
    Cart.openCartPage()
    WebUI.verifyElementPresent(findTestObject('CellphoneS/CartPage/lbl_ProductName'), 10)

    int subTotal = Cart.getSubTotal()
    log("  SubTotal: ${subTotal}")
    assert subTotal < 500000 : "Precondition: đơn phải < 500k để test min-order"

    Cart.applyVoucher(GlobalVariable.VOUCHER_MIN_ORDER)

    boolean hasError = WebUI.verifyElementPresent(
        findTestObject('CellphoneS/CartPage/lbl_VoucherErrorMessage'),
        5, FailureHandling.OPTIONAL)
    assert hasError : "Không hiển thị thông báo lỗi min-order"

    String msg = WebUI.getText(findTestObject('CellphoneS/CartPage/lbl_VoucherErrorMessage'))
    log("  Error msg: ${msg}")

    String lower = msg.toLowerCase()
    assert lower.contains('tối thiểu') || lower.contains('chưa đủ') || lower.contains('giá trị đơn') :
        "Thông báo không đúng: ${msg}"

    assert Cart.getDiscountAmount() == 0
}

// ============================================================
//  CHẠY TEST
// ============================================================
runCase("07", { tc07() })
runCase("08", { tc08() })
runCase("09", { tc09() })
runCase("10", { tc10() })
runCase("11", { tc11() })
runCase("12", { tc12() })
runCase("13", { tc13() })
runCase("20", { tc20() })
runCase("21", { tc21() })
runCase("22", { tc22() })
runCase("23", { tc23() })
runCase("24", { tc24() })
runCase("25", { tc25() })

log("\n═══════════════════════════════════════════")
log("  HOÀN TẤT TẤT CẢ TEST CASES")
log("═══════════════════════════════════════════")