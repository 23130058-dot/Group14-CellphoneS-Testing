package com.cellphones.cart

/**
 * TẬP TRUNG TOÀN BỘ XPATH Ở ĐÂY.
 * Các xpath dưới đây là xpath DỰ ĐOÁN theo text hiển thị -> cần dùng Spy Web
 * kiểm tra lại với giao diện thực tế của cellphones.com.vn rồi sửa tại file này.
 * Phần ROW_* là xpath TƯƠNG ĐỐI so với 1 dòng sản phẩm trong giỏ (bắt đầu bằng //).
 */
class CartLocators {

	// ---------- Header ----------
	static final String HEADER_LOGIN = "(//*[normalize-space(text())='Đăng nhập'])[1]"
//	static final List<String> HEADER_CART_CANDIDATES = [
//		"(//a[contains(@href,'/cart') or contains(@href,'gio-hang') or contains(@href,'checkout')])[1]",
//		"(//*[self::a or self::button or self::div][contains(normalize-space(.),'Giỏ hàng')][string-length(normalize-space(.)) < 20])[1]",
//		"(//*[self::a or self::button or self::div][contains(translate(@class,'CART','cart'),'cart')])[1]"
//	]
	
	static final List<String> HEADER_CART_CANDIDATES = [
	// 1. Thẻ a chứa span 'hidden lg:inline-block' (từ TestCase Studio)
	"//span[contains(@class,'hidden') and contains(@class,'lg:inline-block') and contains(text(),'Giỏ hàng')]/ancestor::a",

	// 2. Thẻ a chứa link /cart
	"(//a[contains(@href,'/cart') or contains(@href,'gio-hang')])[1]",

	// 3. Thẻ a/button nhỏ có chữ 'Giỏ hàng' (KHÔNG dùng div, giới hạn độ dài)
	"(//*[self::a or self::button][contains(normalize-space(.),'Giỏ hàng')][string-length(normalize-space(.)) < 30])[1]",

	// 4. Dự phòng theo class, chỉ lấy thẻ a/button
	"(//*[self::a or self::button][contains(@class,'cart')])[1]"
]
	static final String CART_BADGE   = "(//a[contains(@href,'/cart')])[1]//*[contains(@class,'count') or contains(@class,'badge') or contains(@class,'quantity')]"

	// ---------- Popup đăng nhập ----------
	static final String LOGIN_PHONE    = "//input[contains(@placeholder,'số điện thoại') or @type='tel']"
	static final String LOGIN_PASSWORD = "//input[@type='password']"
	static final String LOGIN_SUBMIT   = "(//*[contains(@class,'modal') or contains(@class,'popup') or contains(@class,'auth') or self::form]//*[self::button or self::input[@type='submit'] or self::a][contains(normalize-space(.),'Đăng nhập') or @type='submit'])[last()]"

	// ---------- Popup YÊU CẦU đăng nhập (tiêu đề Smember + 2 nút Đăng ký / Đăng nhập) ----------
	static final String LOGIN_REQUIRED_TEXT = "//*[contains(normalize-space(.),'Vui lòng đăng nhập tài khoản Smember')][not(*[contains(normalize-space(.),'Vui lòng đăng nhập tài khoản Smember')])]"
	// Khung popup = phần tử sâu nhất chứa cả câu thông báo lẫn nút Đăng nhập
	static final String LOGIN_REQUIRED_POPUP = "(//*[contains(normalize-space(.),'Vui lòng đăng nhập tài khoản Smember')][.//*[normalize-space(text())='Đăng nhập']])[last()]"
	static final String LOGIN_REQUIRED_TITLE = LOGIN_REQUIRED_POPUP + "//*[normalize-space(text())='Smember']"
	static final String LOGIN_REQUIRED_BTN_LOGIN    = LOGIN_REQUIRED_POPUP + "//*[self::button or self::a or self::div][normalize-space(text())='Đăng nhập']"
	static final String LOGIN_REQUIRED_BTN_REGISTER = LOGIN_REQUIRED_POPUP + "//*[self::button or self::a or self::div][normalize-space(text())='Đăng ký']"
	
	// ---------- Trang chi tiết sản phẩm ----------
	static final String PDP_TITLE   = "//h1"
	static final String ADD_TO_CART = "//*[self::button or self::a][contains(normalize-space(.),'Thêm vào giỏ')]"
	static String variantOption(String text) {
		return "(//*[self::a or self::button or self::div][contains(@class,'item') or contains(@class,'variant') or contains(@class,'option')][normalize-space(.)='" + text + "'])[1]"
	}

	// ---------- Trang giỏ hàng ----------
	static final String ROW        = "//div[contains(@class,'cart-item') or contains(@class,'product-item')]"
	static final String ROW_NAME   = "//*[self::a or self::h3 or self::p][contains(@class,'name') or contains(@class,'title')]"
	static final String ROW_PRICE  = "//*[contains(@class,'price') and not(contains(@class,'old')) and not(contains(@class,'original'))]"
	static final String ROW_IMAGE  = "//img"
	static final String ROW_QTY    = "//input[contains(@class,'qty') or contains(@class,'quantity') or @type='number' or @type='text']"
	static final String ROW_PLUS   = "//button[contains(@class,'plus') or contains(@class,'increase') or normalize-space(.)='+']"
	static final String ROW_MINUS  = "//button[contains(@class,'minus') or contains(@class,'decrease') or normalize-space(.)='-' or normalize-space(.)='−']"
	static final String ROW_TRASH  = "//*[contains(@class,'trash') or contains(@class,'delete') or contains(@class,'remove')][self::button or self::a or self::div or self::span or self::i or self::svg]"
	static final String CONFIRM_DELETE = "//button[normalize-space(.)='Xóa' or normalize-space(.)='Đồng ý']"

	static final String GRAND_TOTAL = "(//*[contains(normalize-space(text()),'Tổng tiền')]/following-sibling::*[1])[last()]"
	static final String EMPTY_CART_BUTTON = "//*[self::a or self::button][contains(normalize-space(.),'Xem thêm sản phẩm')]"

	// ---------- Mã giảm giá ----------
	static final String VOUCHER_INPUT  = "//input[contains(@placeholder,'mã giảm giá') or contains(@placeholder,'Mã giảm giá') or contains(@placeholder,'ưu đãi')]"
	static final String VOUCHER_APPLY  = "//button[normalize-space(.)='Áp dụng']"
	static final String VOUCHER_REMOVE = "//*[contains(@class,'voucher') or contains(@class,'coupon') or contains(@class,'discount')]//*[self::button or self::a or self::span or self::i][contains(normalize-space(.),'Xóa') or contains(normalize-space(.),'Hủy') or contains(@class,'remove') or contains(@class,'close')]"
	static final String DISCOUNT_LINE  = "//*[contains(normalize-space(text()),'Giảm giá')]/following-sibling::*[1]"

	// ---------- Popup B2B ----------
	static final String B2B_POPUP   = "//*[contains(normalize-space(.),'Đăng ký nhận tư vấn giá tốt nhất')][contains(@class,'modal') or contains(@class,'popup') or @role='dialog']"
	static final String B2B_CLOSE   = B2B_POPUP + "//*[contains(@class,'close') or normalize-space(.)='×' or normalize-space(.)='X']"
	static final String B2B_COMPANY = B2B_POPUP + "//input[contains(@placeholder,'công ty') or contains(@name,'company')]"
	static final String B2B_NAME    = B2B_POPUP + "//input[contains(@placeholder,'quý khách') or contains(@name,'name')]"
	static final String B2B_PHONE   = B2B_POPUP + "//input[contains(@placeholder,'điện thoại') or @type='tel']"
	static final String B2B_EMAIL   = B2B_POPUP + "//input[contains(@placeholder,'mail') or @type='email']"
	static final String B2B_SUBMIT  = B2B_POPUP + "//button[normalize-space(.)='Đăng ký']"
	static final String RECAPTCHA_FRAME = "//iframe[contains(@title,'reCAPTCHA')]"
	static final String RECAPTCHA_BOX   = "//div[@class='recaptcha-checkbox-border']"
}
