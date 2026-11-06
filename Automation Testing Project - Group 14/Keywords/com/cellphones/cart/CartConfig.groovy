package com.cellphones.cart

/**
 * CẤU HÌNH + DỮ LIỆU TEST CHO MODULE GIỎ HÀNG (cellphones.com.vn)
 * >>> CẬP NHẬT các giá trị đánh dấu TODO trước khi chạy <<<
 */
class CartConfig {

	static final String BASE_URL = 'https://cellphones.com.vn'
	static final String CART_URL = BASE_URL + '/cart'

	// ---- Tài khoản Smember dùng test 
	static final String PHONE    = '0337426240'   // TODO
	static final String PASSWORD = 'Taikhoancellphonesmember123@' // TODO (có thể thay bằng WebUI.setEncryptedText)

	// ---- Sản phẩm test (copy URL trang chi tiết sản phẩm) ----
	static final String PRODUCT_A_URL = 'https://cellphones.com.vn/iphone-13.html'      // TODO: SP giá ~500k, mua được nhiều
	static final String PRODUCT_B_URL = 'https://cellphones.com.vn/dien-thoai-samsung-galaxy-z-flip-8.html'      // TODO: SP khác A
	static final String PRODUCT_LIMIT_URL = 'https://cellphones.com.vn/vong-deo-tay-thong-minh-huawei-band-11-pro.html' // TODO: SP có giới hạn mua tối đa (Cart-14..17)
	static final String PRODUCT_CHEAP_URL = 'https://cellphones.com.vn/TODO-sp-re.html'       // TODO: SP giá ~200k (Cart-25)
	static final String PRODUCT_VARIANT_URL = 'https://cellphones.com.vn/iphone-13.html' // TODO: ĐT có chọn màu + dung lượng (Cart-7)
	static final String COLOR = 'Đen'
	static final String CAPACITY = '128GB'

	// ---- Mã giảm giá (theo test case) ----
	static final String VOUCHER_VALID = 'CPS100K'
	static final long   VOUCHER_VALID_DISCOUNT = 100000L
	static final String VOUCHER_INVALID = 'SAIMAR321'
	static final String VOUCHER_EXPIRED = 'EXPIRED2023'
	static final String VOUCHER_MIN500K = 'MIN500K'

	// ---- Thông báo mong đợi (lấy từ cột Expected Output; sửa lại nếu site hiển thị khác) ----
	static final String MSG_ADDED          = 'Đã thêm sản phẩm vào giỏ hàng'
	static final String MSG_VOUCHER_OK     = 'thành công'
	static final String MSG_VOUCHER_INVALID = 'Mã giảm giá không tồn tại hoặc không hợp lệ'
	static final String MSG_VOUCHER_EXPIRED = 'Mã giảm giá đã hết hạn sử dụng'
	static final String MSG_VOUCHER_MIN    = 'Đơn hàng chưa đạt giá trị tối thiểu để áp dụng mã'
	static final String MSG_B2B_TITLE      = 'Đăng ký nhận tư vấn giá tốt nhất'
	static final String MSG_B2B_MAX        = 'Số lượng sản phẩm đã đạt đến mức tối đa'
	static final String MSG_B2B_REQUIRED   = 'Vui lòng kiểm tra tên công ty'
	static final String MSG_B2B_SUCCESS    = 'thành công'
	static final String MSG_EMPTY_CART     = 'Giỏ hàng trống'
	static final String BTN_EMPTY_CART     = 'Xem thêm sản phẩm'

	// ---- Form B2B (dữ liệu giả - đừng dùng dữ liệu thật vì form sẽ gửi cho CellphoneS) ----
	static final String B2B_COMPANY = 'Cong ty TNHH Test Automation'
	static final String B2B_NAME    = 'Nguyen Test'
	static final String B2B_PHONE   = '0900000001'
	static final String B2B_EMAIL   = 'autotest@example.com'
	static final int    CAPTCHA_MANUAL_WAIT_SEC = 30 // thời gian để tự tick reCAPTCHA (Cart-17)

	// ---- Khác ----
	static final int TIMEOUT = 10
	static final int ACTION_DELAY = 2               // giây chờ sau mỗi thao tác AJAX
	static final String PROFILE_DIR = 'C:/katalon_profiles/cellphones' // Chrome profile cho Cart-5
}
