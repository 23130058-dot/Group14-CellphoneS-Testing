/**
 * ASM8 - Test case: TC_Search (data-driven) - chức năng "Tìm kiếm sản phẩm" - CellphoneS
 * SV: Văn Võ Chí Đạt - 23130058 - Nhóm 14
 *
 * Tự đọc dữ liệu từ Data Files/search_data.csv (cột: tcId, keyword, token, mode) - KHÔNG cần Data Binding.
 * Mỗi dòng = 1 lần chạy (ID SPxx tương ứng sheet "Search Product" trong file Excel).
 * Muốn chạy riêng vài dòng: đặt ONLY_IDS = ['SP01', 'SP03'] (để rỗng = chạy tất cả).
 * Phần xử lý từng dòng (switch theo mode) giữ nguyên từ bản ASM7.
 */
import com.cellphones.SearchKeywords as SK
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

List<String> ONLY_IDS = []

// ---- đọc CSV (hỗ trợ dấu " bao quanh giá trị) ----
List<List<String>> parseCsv(String text) {
	List<List<String>> rows = []
	List<String> row = []
	StringBuilder cell = new StringBuilder()
	boolean quoted = false
	for (int i = 0; i < text.length(); i++) {
		char c = text.charAt(i)
		if (quoted) {
			if (c == '"') {
				if (i + 1 < text.length() && text.charAt(i + 1) == '"') { cell.append('"'); i++ } else { quoted = false }
			} else {
				cell.append(c)
			}
		} else if (c == '"') {
			quoted = true
		} else if (c == ',') {
			row.add(cell.toString()); cell = new StringBuilder()
		} else if (c == '\n' || c == '\r') {
			if (c == '\r' && i + 1 < text.length() && text.charAt(i + 1) == '\n') { i++ }
			row.add(cell.toString()); cell = new StringBuilder()
			if (row.size() > 1 || row[0] != '') { rows.add(row) }
			row = []
		} else {
			cell.append(c)
		}
	}
	if (cell.length() > 0 || !row.isEmpty()) { row.add(cell.toString()); rows.add(row) }
	return rows
}

File csv = new File(RunConfiguration.getProjectDir() + '/Data Files/search_data.csv')
List<List<String>> data = parseCsv(csv.getText('UTF-8')).drop(1)   // bỏ dòng tiêu đề
List<String> failures = []

for (List<String> r : data) {
	String tcId = r[0]
	String keyword = r.size() > 1 ? r[1] : ''
	String token = r.size() > 2 ? r[2] : ''
	String mode = r.size() > 3 ? r[3] : ''
	if (!ONLY_IDS.isEmpty() && !ONLY_IDS.contains(tcId)) { continue }
	try {
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
	
	} catch (Throwable e) {
		failures.add(tcId + ': ' + e.getMessage())
		KeywordUtil.logInfo('FAILED ' + tcId + ': ' + e.getMessage())
	}
}

if (!failures.isEmpty()) {
	KeywordUtil.markFailed(failures.size() + ' dòng thất bại:\n' + failures.join('\n'))
}
