package com.cellphones

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.Keys

/**
 * ASM7 - Custom keywords cho chức năng "Tìm kiếm sản phẩm" - CellphoneS
 * SV: Văn Võ Chí Đạt - 23130058 - Nhóm 14
 * Đặt file tại: Keywords/com/cellphones/SearchKeywords.groovy
 * Nếu website đổi giao diện, chỉ cần sửa các selector CSS ở đầu class.
 */
public class SearchKeywords {

	static final String BASE_URL = 'https://cellphones.com.vn/'
	static final String SEARCH_INPUT = "input#inp-search, input[placeholder*='Bạn muốn mua gì'], input[type='search'], input[name='search'], input[name='q']"
	static final String SEARCH_BUTTON = "button.btn-search, form button[type='submit']"
	static final String PRODUCT_ITEM = '.product-info-container, .product-item, .product__link, div.product-info'
	static final String POPUP_CLOSE = "button.cancel-button-top, #popup-close, .modal button.close, .btn-close, [aria-label='Close']"
	static final List<String> NO_RESULT = ['không tìm thấy', 'không có kết quả', 'không có sản phẩm', 'rất tiếc']

	// ---------------------------------------------------------------- tiện ích
	static TestObject css(String selector) {
		TestObject to = new TestObject('css_' + selector.hashCode())
		to.addProperty('css', ConditionType.EQUALS, selector)
		return to
	}

	static String js(String script) {
		def result = WebUI.executeJavaScript(script, null)
		return result == null ? '' : result.toString()
	}

	static void check(boolean condition, String failMessage) {
		if (!condition) {
			KeywordUtil.markFailedAndStop(failMessage)
		} else {
			KeywordUtil.logInfo('OK')
		}
	}

	@Keyword
	static String pageText() {
		return js('return document.body.innerText').toLowerCase()
	}

	@Keyword
	static int countProducts() {
		String script = 'var n = document.querySelectorAll("' + PRODUCT_ITEM + '").length;' +
				'if (n === 0) { n = Array.from(document.querySelectorAll("a[href$=\'.html\']")).filter(function (a) {' +
				' return a.querySelector("img") && !a.closest("header,footer,nav"); }).length; } return n;'
		return js(script).toInteger()
	}

	@Keyword
	static boolean isErrorPage() {
		String title = WebUI.getWindowTitle().toLowerCase()
		return ['404', '500', 'error', 'not found'].any { title.contains(it) } || pageText().contains('internal server error')
	}

	@Keyword
	static void closePopups() {
		WebUI.executeJavaScript('document.querySelectorAll("' + POPUP_CLOSE + '").forEach(function (e) { if (e.offsetParent !== null) { e.click(); } });', null)
	}

	@Keyword
	static void waitResults() {
		for (int i = 0; i < 20; i++) {
			if (countProducts() > 0 || NO_RESULT.any { pageText().contains(it) }) {
				return
			}
			Thread.sleep(500)
		}
	}

	// ---------------------------------------------------------------- thao tác
	@Keyword
	static void openHome() {
		WebUI.navigateToUrl(BASE_URL)
		WebUI.waitForPageLoad(15)
		closePopups()
		WebUI.delay(1)
	}

	@Keyword
	static void search(String keyword, boolean useButton = false) {
		TestObject box = css(SEARCH_INPUT)
		WebUI.waitForElementVisible(box, 15)
		WebUI.click(box)
		WebUI.sendKeys(box, Keys.chord(Keys.CONTROL, 'a'))
		WebUI.sendKeys(box, Keys.chord(Keys.DELETE))
		if (keyword) {
			WebUI.sendKeys(box, keyword)
		}
		WebUI.delay(1)
		if (useButton) {
			WebUI.click(css(SEARCH_BUTTON))
		} else {
			WebUI.sendKeys(box, Keys.chord(Keys.ENTER))
		}
		WebUI.waitForPageLoad(15)
		closePopups()
		waitResults()
		WebUI.delay(1)
	}

	@Keyword
	static void clickFirstProduct() {
		String script = 'var items = document.querySelectorAll("' + PRODUCT_ITEM + '"); var a = null;' +
				'if (items.length > 0) { a = items[0].tagName === "A" ? items[0] : items[0].querySelector("a[href]"); }' +
				'if (!a) { var c = Array.from(document.querySelectorAll("a[href$=\'.html\']")).filter(function (x) {' +
				' return x.querySelector("img") && !x.closest("header,footer,nav"); }); a = c[0]; }' +
				'if (a) { a.scrollIntoView({block: "center"}); a.click(); return true; } return false;'
		check(js(script) == 'true', 'Không tìm thấy sản phẩm nào để click')
		WebUI.waitForPageLoad(15)
		WebUI.delay(2)
		closePopups()
	}

	@Keyword
	static boolean clickLoadMore() {
		String script = 'var b = Array.from(document.querySelectorAll("button,a")).filter(function (e) {' +
				' return e.innerText && e.innerText.indexOf("Xem thêm") >= 0 && e.offsetParent !== null; })[0];' +
				'if (b) { b.scrollIntoView({block: "center"}); b.click(); return true; } return false;'
		return js(script) == 'true'
	}

	@Keyword
	static String searchBoxValue() {
		return js('var i = document.querySelector("' + SEARCH_INPUT + '"); return i ? i.value : "";')
	}

	// ---------------------------------------------------------------- kiểm tra
	@Keyword
	static void verifyResults(String token) {
		check(!isErrorPage(), 'Trang kết quả bị lỗi')
		check(WebUI.getUrl().replaceAll('/+$', '') != BASE_URL.replaceAll('/+$', ''), 'Chưa chuyển sang trang kết quả')
		check(countProducts() > 0, 'Không có sản phẩm nào trong kết quả')
		check(pageText().contains(token.toLowerCase()), "Kết quả không chứa từ khóa '${token}'")
	}

	@Keyword
	static void verifyNoAlert() {
		boolean present = WebUI.verifyAlertPresent(1, FailureHandling.OPTIONAL)
		if (present) {
			WebUI.dismissAlert()
			KeywordUtil.markFailedAndStop('Script bị thực thi: xuất hiện hộp thoại alert')
		}
	}
}
