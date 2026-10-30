/**
 * ASM7 - Test case: TC_Search (data-driven) - chức năng "Tìm kiếm sản phẩm" - CellphoneS
 * SV: Văn Võ Chí Đạt - 23130058 - Nhóm 14
 *
 * Dán nội dung này vào Test Cases/TC_Search (tab Script).
 * Variables (tab Variable, kiểu String, để trống giá trị mặc định): tcId, keyword, token, mode
 * Dữ liệu: search_data.csv, mỗi dòng = 1 lần chạy (ID SPxx tương ứng sheet "Search Product" trong file Excel)
 */
import com.cellphones.SearchKeywords as SK
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

String kw = keyword ?: ''
String tk = (token ?: '').toLowerCase()
String home = SK.BASE_URL.replaceAll('/+$', '')

KeywordUtil.logInfo("=== ${tcId} | mode=${mode} | keyword='${kw}' ===")

WebUI.openBrowser('')
WebUI.maximizeWindow()

try {
	SK.openHome()

	switch (mode) {

		// SP01-SP04, SP07-SP10: từ khóa hợp lệ -> có sản phẩm liên quan
		case ['valid', 'valid_button', 'valid_pad', 'valid_gap']:
			String typed = kw
			if (mode == 'valid_pad') {
				typed = '   ' + kw + '   '                  // SP07: khoảng trắng đầu/cuối
			} else if (mode == 'valid_gap') {
				typed = kw.replace(' ', '      ')            // SP08: nhiều khoảng trắng giữa các từ
			}
			SK.search(typed, mode == 'valid_button')         // SP02: bấm biểu tượng kính lúp
			SK.verifyResults(tk)
			break

		// SP05: không phân biệt hoa/thường
		case 'case_insensitive':
			SK.search(kw.toUpperCase())
			int upper = SK.countProducts()
			SK.openHome()
			SK.search(kw.toLowerCase())
			int lower = SK.countProducts()
			SK.check(upper > 0 && upper == lower, "Kết quả khác nhau: HOA=${upper}, thường=${lower}")
			break

		// SP13: từ khóa không tồn tại
		case 'notfound':
			SK.search(kw)
			SK.check(!SK.isErrorPage(), 'Trang kết quả bị lỗi')
			SK.check(SK.countProducts() == 0, 'Từ khóa không tồn tại nhưng vẫn có sản phẩm')
			SK.check(SK.NO_RESULT.any { SK.pageText().contains(it) }, 'Không thấy thông báo không tìm thấy')
			break

		// SP14-SP20: đầu vào bất thường -> không lỗi, không thực thi script
		case ['robust', 'robust_empty', 'robust_spaces', 'robust_long']:
			String badInput = kw
			if (mode == 'robust_empty') {
				badInput = ''                                // SP14: để trống
			} else if (mode == 'robust_spaces') {
				badInput = '     '                           // SP15: chỉ khoảng trắng
			} else if (mode == 'robust_long') {
				badInput = 'a' * 255                         // SP20: 255 ký tự
			}
			SK.search(badInput)
			SK.verifyNoAlert()
			SK.check(!SK.isErrorPage(), 'Hệ thống báo lỗi khi nhập đầu vào bất thường')
			SK.check(WebUI.getUrl().startsWith(SK.BASE_URL), 'Bị chuyển ra ngoài website')
			break

		// SP22: click sản phẩm -> trang chi tiết
		case 'open_detail':
			SK.search(kw)
			String resultsUrl = WebUI.getUrl()
			SK.clickFirstProduct()
			SK.check(WebUI.getUrl() != resultsUrl, 'Không chuyển sang trang chi tiết')
			SK.check(!SK.isErrorPage(), 'Trang chi tiết bị lỗi')
			SK.check(SK.pageText().contains(tk), "Trang chi tiết không chứa '${tk}'")
			break

		// SP23: [Xem thêm] -> hiện thêm sản phẩm
		case 'load_more':
			SK.search(kw)
			int before = SK.countProducts()
			if (!SK.clickLoadMore()) {
				KeywordUtil.markWarning('Không có nút [Xem thêm] (có thể website dùng phân trang) - bỏ qua')
				break
			}
			boolean more = false
			for (int i = 0; i < 20 && !more; i++) {
				Thread.sleep(500)
				more = SK.countProducts() > before
			}
			SK.check(more, "Số sản phẩm không tăng sau khi bấm Xem thêm (trước: ${before})")
			break

		// SP24: từ khóa vẫn hiển thị sau khi tìm
		case 'keyword_shown':
			SK.search(kw)
			String boxValue = SK.searchBoxValue().toLowerCase()
			SK.check(boxValue.contains(kw.toLowerCase()) || SK.pageText().contains(kw.toLowerCase()),
				"Từ khóa '${kw}' không còn hiển thị sau khi tìm")
			break

		// SP25: tìm kiếm ngay trên trang chi tiết sản phẩm
		case 'search_from_detail':
			SK.search(kw)
			SK.clickFirstProduct()
			SK.search('Samsung')
			SK.check(SK.countProducts() > 0, 'Không có kết quả khi tìm từ trang chi tiết')
			SK.check(SK.pageText().contains('samsung'), 'Kết quả không chứa samsung')
			break

		// SP27: nút Back của trình duyệt
		case 'back':
			SK.search(kw)
			WebUI.back()
			WebUI.waitForPageLoad(15)
			SK.check(!SK.isErrorPage(), 'Trang bị lỗi sau khi Back')
			SK.check(WebUI.getUrl().replaceAll('/+$', '') == home, 'Back không quay về trang chủ: ' + WebUI.getUrl())
			break

		// SP28: tìm liên tiếp nhiều từ khóa (ngăn cách bằng |)
		case 'consecutive':
			List<String> kws = kw.split('\\|') as List
			List<String> tks = tk.split('\\|') as List
			kws.eachWithIndex { k, i ->
				SK.search(k)
				SK.check(SK.countProducts() > 0, "Không có kết quả cho '${k}'")
				SK.check(SK.pageText().contains(tks[i]), "Kết quả của '${k}' không chứa '${tks[i]}'")
			}
			break

		default:
			KeywordUtil.markFailedAndStop("mode không hợp lệ: ${mode}")
	}
} finally {
	WebUI.closeBrowser()
}
