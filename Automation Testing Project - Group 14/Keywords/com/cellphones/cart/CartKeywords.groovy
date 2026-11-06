package com.cellphones.cart

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.NoAlertPresentException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * Các hàm dùng chung cho test case module Giỏ hàng.
 * Dùng trong test case:  import com.cellphones.cart.CartKeywords as K
 */
class CartKeywords {

	static final int T = CartConfig.TIMEOUT

	// =====================================================================
	// 1. TIỆN ÍCH CHUNG
	// =====================================================================
	static TestObject xp(String xpath) {
		TestObject to = new TestObject('dyn')
		to.addProperty('xpath', ConditionType.EQUALS, xpath)
		return to
	}

	static WebDriver driver() { return DriverFactory.getWebDriver() }

	static List<WebElement> findAll(String xpath) { return driver().findElements(By.xpath(xpath)) }

	static boolean anyDisplayed(String xpath) {
		for (WebElement e : findAll(xpath)) {
			try { if (e.isDisplayed()) return true } catch (Exception ex) { }
		}
		return false
	}

	/** Chờ tối đa sec giây cho đến khi có phần tử hiển thị. */
	static boolean isVisible(String xpath, int sec = 3) {
		for (int i = 0; i < sec * 2; i++) {
			if (anyDisplayed(xpath)) return true
			WebUI.delay(0.5)
		}
		return false
	}

	static boolean isAbsentNow(String xpath) { return !anyDisplayed(xpath) }

	static void pause(int sec = CartConfig.ACTION_DELAY) { WebUI.delay(sec) }

	static long parseMoney(String s) {
		String d = (s ?: '').replaceAll('[^0-9]', '')
		return d ? d.toLong() : 0L
	}

	static void click(String xpath) {
		WebUI.waitForElementClickable(xp(xpath), T)
		WebUI.click(xp(xpath))
	}

	static void jsClick(String xpath) {
		WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(driver().findElement(By.xpath(xpath))))
	}

	// ---- Kiểm tra (hard assert, có log rõ ràng) ----
	static void check(boolean cond, String msg) {
		if (cond) KeywordUtil.markPassed('PASS: ' + msg)
		else KeywordUtil.markFailedAndStop('FAIL: ' + msg)
	}

	static void checkEq(Object actual, Object expected, String msg) {
		check(actual == expected, msg + " | actual=[${actual}] expected=[${expected}]")
	}

	static void checkText(String text, String msg = null) {
		check(WebUI.verifyTextPresent(text, false, FailureHandling.OPTIONAL), (msg ?: 'Hiển thị text') + ": '" + text + "'")
	}

	// =====================================================================
	// 2. TRÌNH DUYỆT
	// =====================================================================
//	static void startBrowser() {
//		WebUI.openBrowser('')
//		WebUI.maximizeWindow()
//		WebUI.navigateToUrl(CartConfig.BASE_URL)
//		WebUI.waitForPageLoad(T)
//		pause(1)
//	}
	static void startBrowser() {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		WebUI.navigateToUrl(CartConfig.BASE_URL)
		WebUI.waitForPageLoad(T)
		pause(2)
	}

	static void stopBrowser() {
		try { WebUI.closeBrowser() } catch (Exception e) { }
	}

	/** Mở Chrome với profile cố định để giữ cookie khi đóng/mở lại (Cart-5). */
	static void startBrowserWithProfile() {
		RunConfiguration.setWebDriverPreferencesProperty('args', ['user-data-dir=' + CartConfig.PROFILE_DIR])
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
	}

	static void resetBrowserArgs() {
		RunConfiguration.setWebDriverPreferencesProperty('args', [])
	}

	/** Mở trình duyệt thứ 2 độc lập (Cart-6), trả về driver để đóng sau. */
	static WebDriver openSecondBrowser() {
		System.setProperty('webdriver.chrome.driver', DriverFactory.getChromeDriverPath())
		WebDriver d2 = new ChromeDriver()
		DriverFactory.changeWebDriver(d2)
		d2.manage().window().maximize()
		WebUI.navigateToUrl(CartConfig.BASE_URL)
		WebUI.waitForPageLoad(T)
		return d2
	}

	static void switchTo(WebDriver d) { DriverFactory.changeWebDriver(d) }

	static String getCurrentUrl() { return driver().getCurrentUrl() }

	static void verifyUrlContains(String part) {
		check(getCurrentUrl().contains(part), "URL chứa '${part}' | actual=${getCurrentUrl()}")
	}

	static void verifyCurrentUrlIs(String expectedUrl) {
		String a = getCurrentUrl().split('[?#]')[0].replaceAll('/+$', '')
		String e = expectedUrl.split('[?#]')[0].replaceAll('/+$', '')
		check(a == e, "Đang ở đúng trang | actual=${a} expected=${e}")
	}

	// =====================================================================
	// 3. ĐĂNG NHẬP
	// =====================================================================
	static boolean isLoggedIn() { return !anyDisplayed(CartLocators.HEADER_LOGIN) }

	static void openLoginPopupFromHeader() {
	click(CartLocators.HEADER_LOGIN)
	pause(1)

	// Header mở popup Smember (2 nút Đăng ký / Đăng nhập) -> phải bấm tiếp [Đăng nhập] trong popup
	if (isVisible(CartLocators.LOGIN_REQUIRED_TEXT, 3)) {
		try {
			click(CartLocators.LOGIN_REQUIRED_BTN_LOGIN)
		} catch (Exception e) {
			jsClick(CartLocators.LOGIN_REQUIRED_BTN_LOGIN)   // dự phòng nếu click thường bị chặn
		}
		pause(1)
	}

	// Nếu form đăng nhập mở ở tab mới thì chuyển sang tab đó
	List<String> handles = new ArrayList<>(driver().getWindowHandles())
	if (handles.size() > 1) driver().switchTo().window(handles.get(handles.size() - 1))
	KeywordUtil.logInfo("Sau khi bấm Đăng nhập -> URL: ${getCurrentUrl()} | số tab: ${handles.size()}")

	check(isVisible(CartLocators.LOGIN_PHONE, T), 'Form nhập SĐT/Mật khẩu xuất hiện')
}

	static void verifyLoginPopupDisplayed() {
		check(isVisible(CartLocators.LOGIN_REQUIRED_TEXT, T), 'Popup yêu cầu đăng nhập hiển thị thông báo "Vui lòng đăng nhập tài khoản Smember..."')
		check(anyDisplayed(CartLocators.LOGIN_REQUIRED_TITLE), 'Popup hiển thị tiêu đề Smember')
		check(anyDisplayed(CartLocators.LOGIN_REQUIRED_BTN_REGISTER), 'Popup hiển thị nút [Đăng ký]')
		check(anyDisplayed(CartLocators.LOGIN_REQUIRED_BTN_LOGIN), 'Popup hiển thị nút [Đăng nhập]')
	}
	
	/** Bấm nút [Đăng nhập] trên popup yêu cầu đăng nhập để chuyển sang form nhập SĐT/mật khẩu. */
	static void clickLoginOnRequiredPopup() {
		click(CartLocators.LOGIN_REQUIRED_BTN_LOGIN)
		pause(1)
		check(isVisible(CartLocators.LOGIN_PHONE, T), 'Form nhập SĐT/Mật khẩu xuất hiện sau khi bấm [Đăng nhập]')
	}

	static void submitLogin(String phone, String password) {
		WebUI.setText(xp(CartLocators.LOGIN_PHONE), phone)
		WebUI.setText(xp(CartLocators.LOGIN_PASSWORD), password)
		check(WebUI.getAttribute(xp(CartLocators.LOGIN_PHONE), 'value') == phone, 'SĐT được điền vào ô input')
		click(CartLocators.LOGIN_SUBMIT)
	}

	static void waitLoginPopupClosed() {
		boolean closed = false
		for (int i = 0; i < T * 2; i++) {
			if (!anyDisplayed(CartLocators.LOGIN_PHONE)) { closed = true; break }
			WebUI.delay(0.5)
		}
		check(closed, 'Popup đăng nhập tự động đóng sau khi đăng nhập thành công')
		pause(1)
	}

	static void loginViaHeader() {
		if (isLoggedIn()) return
		openLoginPopupFromHeader()
		pause(1)
		submitLogin(CartConfig.PHONE, CartConfig.PASSWORD)
		waitLoginPopupClosed()
		check(isLoggedIn(), 'Đăng nhập thành công')
	}

	/** Tìm nút Giỏ hàng trên header (thử lần lượt các xpath ứng viên). */
//	static WebElement headerCartEl() {
//		for (int attempt = 0; attempt < T * 2; attempt++) {
//			for (String xp : CartLocators.HEADER_CART_CANDIDATES) {
//				for (WebElement e : findAll(xp)) {
//					try { if (e.isDisplayed()) return e } catch (Exception ex) { }
//				}
//			}
//			WebUI.delay(0.5)
//		}
//		return null
//	}
	
	static WebElement headerCartEl() {
	for (int attempt = 0; attempt < T * 2; attempt++) {
		for (String xp : CartLocators.HEADER_CART_CANDIDATES) {
			List<WebElement> elements = findAll(xp)
			for (WebElement e : elements) {
				try {
					if ((e.getText() ?: '').length() > 40) continue   // khung lớn -> bỏ qua
					if (e.isDisplayed()) return e
				} catch (Exception ex) { }
			}
			// Fallback: có trong DOM nhưng chưa hiển thị -> trả về phần tử đầu tiên có text ngắn
			for (WebElement e : elements) {
				try {
					if ((e.getText() ?: '').length() <= 40) return e
				} catch (Exception ex) { }
			}
		}
		WebUI.delay(0.5)
	}
	return null
}

	static void clickHeaderCart() {
		WebElement el = headerCartEl()
		if (el == null) {
			KeywordUtil.markFailedAndStop('Không tìm thấy nút Giỏ hàng trên header -> dùng Spy Web lấy xpath và đưa vào CartLocators.HEADER_CART_CANDIDATES')
		}
		try { el.click() } catch (Exception e) {
			((JavascriptExecutor) driver()).executeScript('arguments[0].click()', el)
		}
		pause(1)
	}

	static int getCartBadge() {
	WebElement el = headerCartEl()
	if (el == null) return 0
	try {
		// Chỉ xét các phần tử lá (không có con) trong nút giỏ hàng
		for (WebElement c : el.findElements(By.xpath('.//*[not(*)]'))) {
			String t = (c.getText() ?: '').trim()
			if (t ==~ /\d{1,3}/) return t.toInteger()
		}
	} catch (Exception e) {
		KeywordUtil.logInfo('Không đọc được badge giỏ hàng: ' + e.message)
	}
	return 0   // không có badge = giỏ trống
}

	// =====================================================================
	// 4. TRANG SẢN PHẨM
	// =====================================================================
	static void openProduct(String url) {
		WebUI.navigateToUrl(url)
		WebUI.waitForPageLoad(T)
		check(isVisible(CartLocators.ADD_TO_CART, T), 'Trang chi tiết sản phẩm hiển thị nút [Thêm vào giỏ]')
	}

	static String getProductName() { return WebUI.getText(xp(CartLocators.PDP_TITLE)).trim() }

	static void selectVariant(String text) {
		click(CartLocators.variantOption(text))
		pause(1)
	}

	static void clickAddToCart() {
		click(CartLocators.ADD_TO_CART)
		pause()
	}

	/** Thêm sản phẩm `times` lần, trả về tên sản phẩm. */
	static String addProduct(String url, int times = 1) {
		String name = null
		for (int i = 0; i < times; i++) {
			openProduct(url)
			if (name == null) name = getProductName()
			clickAddToCart()
		}
		return name
	}

	// =====================================================================
	// 5. TRANG GIỎ HÀNG
	// =====================================================================
	static void openCartPage() {
		WebUI.navigateToUrl(CartConfig.CART_URL)
		WebUI.waitForPageLoad(T)
		pause(1)
	}

	static String rowXp(int i) { return '(' + CartLocators.ROW + ')[' + i + ']' }

	static int rowCount() { return findAll(CartLocators.ROW).size() }

	static WebElement rowEl(int i) { return driver().findElement(By.xpath(rowXp(i))) }

	static String getRowText(int i) { return rowEl(i).getText() }

	/** Trả về chỉ số dòng (bắt đầu từ 1) chứa tên SP; 0 nếu không thấy. */
//	static int findRowByName(String name) {
//		String key = name.toLowerCase().trim()
//		if (key.length() > 25) key = key.substring(0, 25)
//		int n = rowCount()
//		for (int i = 1; i <= n; i++) {
//			if (getRowText(i).toLowerCase().contains(key)) return i
//		}
//		return 0
//	}
	/** Trả về chỉ số dòng (bắt đầu từ 1) chứa tên SP; 0 nếu không thấy. */
	static int findRowByName(String name) {
		if (!name) return 0
		
		// Lấy từ khóa chính (loại bỏ bớt các từ chung như "Điện thoại", "Chính hãng...")
		String cleanName = name.replaceAll("(?i)Điện thoại|Chính hãng|VN/A", "").trim().toLowerCase()
		
		// Lấy 15 ký tự đặc trưng nhất
		String key = cleanName.length() > 15 ? cleanName.substring(0, 15).trim() : cleanName
		
		int n = rowCount()
		for (int i = 1; i <= n; i++) {
			String rowText = getRowText(i).toLowerCase()
			if (rowText.contains(key)) {
				return i
			}
		}
		return 0
	}

	static WebElement rowChild(int i, String relXp) {
		return driver().findElement(By.xpath(rowXp(i) + relXp))
	}

	static int getQty(int i) {
		WebElement q = rowChild(i, CartLocators.ROW_QTY)
		String v = q.getAttribute('value') ?: q.getText()
		v = (v ?: '').replaceAll('[^0-9]', '')
		return v ? v.toInteger() : 0
	}

	static long getUnitPrice(int i) { return parseMoney(rowChild(i, CartLocators.ROW_PRICE).getText()) }

	static long getGrandTotal() { return parseMoney(WebUI.getText(xp(CartLocators.GRAND_TOTAL))) }

	static boolean isRowImageDisplayed(int i) {
		WebElement img = rowChild(i, CartLocators.ROW_IMAGE)
		return img.isDisplayed() && ((Number) ((JavascriptExecutor) driver()).executeScript('return arguments[0].naturalWidth', img)) > 0
	}

	static boolean hasTrash(int i) { return !findAll(rowXp(i) + CartLocators.ROW_TRASH).isEmpty() }
	static boolean hasMinus(int i) { return !findAll(rowXp(i) + CartLocators.ROW_MINUS).isEmpty() }

	static void clickPlus(int i) {
		rowChild(i, CartLocators.ROW_PLUS).click()
		pause()
	}

	static void clickMinus(int i) {
		rowChild(i, CartLocators.ROW_MINUS).click()
		pause()
	}

	static void clickTrash(int i) {
		rowChild(i, CartLocators.ROW_TRASH).click()
		pause(1)
		if (anyDisplayed(CartLocators.CONFIRM_DELETE)) click(CartLocators.CONFIRM_DELETE)
		pause()
	}

	/** Xóa sạch giỏ hàng (precondition). */
	static void resetCart() {
		openCartPage()
		int guard = 40
		while (rowCount() > 0 && guard-- > 0) {
			while (getQty(1) > 1 && guard-- > 0) clickMinus(1)
			clickTrash(1)
		}
		check(rowCount() == 0, 'Precondition: giỏ hàng đã được dọn trống')
	}

	static void verifyEmptyCart() {
		checkEq(rowCount(), 0, 'Giỏ hàng không còn sản phẩm')
		checkText(CartConfig.MSG_EMPTY_CART, 'Giao diện giỏ hàng trống')
		check(anyDisplayed(CartLocators.EMPTY_CART_BUTTON), "Hiển thị nút [${CartConfig.BTN_EMPTY_CART}]")
	}

	// =====================================================================
	// 6. MÃ GIẢM GIÁ
	// =====================================================================
	static void applyVoucher(String code) {
		WebUI.setText(xp(CartLocators.VOUCHER_INPUT), code)
		click(CartLocators.VOUCHER_APPLY)
		pause()
	}

	static String getVoucherInput() { return WebUI.getAttribute(xp(CartLocators.VOUCHER_INPUT), 'value') }

	static void removeVoucher() {
		click(CartLocators.VOUCHER_REMOVE)
		pause()
	}

	// =====================================================================
	// 7. POPUP B2B
	// =====================================================================
	static boolean isB2BVisible() { return anyDisplayed(CartLocators.B2B_POPUP) }

	/** Bấm [+] cho tới khi popup B2B hiện. Trả về số lượng tối đa (trước lần bấm gây ra popup). */
	static int increaseUntilLimit(int row, int maxClicks = 20) {
		for (int i = 0; i < maxClicks; i++) {
			int q = getQty(row)
			clickPlus(row)
			if (isB2BVisible()) return q
		}
		KeywordUtil.markFailedAndStop("Bấm [+] ${maxClicks} lần vẫn không chạm giới hạn tối đa -> kiểm tra lại PRODUCT_LIMIT_URL")
		return -1
	}

	/** Precondition chung cho Cart-14..17: có popup B2B đang hiển thị. Trả về qty tối đa. */
//	static int prepareB2BPopup() {
//		loginViaHeader()
//		resetCart()
//		addProduct(CartConfig.PRODUCT_LIMIT_URL)
//		openCartPage()
//		return increaseUntilLimit(1)
//	}
	

	static void closeB2BByX() {
		click(CartLocators.B2B_CLOSE)
		pause(1)
	}

	static void closeB2BByEsc() {
		new Actions(driver()).sendKeys(Keys.ESCAPE).perform()
		pause(1)
	}

	static void fillB2BForm(String company, String name, String phone, String email) {
		WebUI.setText(xp(CartLocators.B2B_COMPANY), company)
		WebUI.setText(xp(CartLocators.B2B_NAME), name)
		WebUI.setText(xp(CartLocators.B2B_PHONE), phone)
		WebUI.setText(xp(CartLocators.B2B_EMAIL), email)
	}

	/**
	 * reCAPTCHA KHÔNG thể tự động hóa ổn định. Hàm này thử tick checkbox,
	 * sau đó dừng CAPTCHA_MANUAL_WAIT_SEC giây để tester tự giải (nếu có ảnh thử thách).
	 */
	static void tickRecaptchaOrWaitManual() {
		try {
			WebUI.switchToFrame(xp(CartLocators.RECAPTCHA_FRAME), T)
			WebUI.click(xp(CartLocators.RECAPTCHA_BOX))
		} catch (Exception e) {
			KeywordUtil.markWarning('Không click được reCAPTCHA tự động: ' + e.message)
		} finally {
			try { WebUI.switchToDefaultContent() } catch (Exception e) { }
		}
		KeywordUtil.logInfo("Chờ ${CartConfig.CAPTCHA_MANUAL_WAIT_SEC}s để tự giải reCAPTCHA (nếu cần)...")
		WebUI.delay(CartConfig.CAPTCHA_MANUAL_WAIT_SEC)
	}

	// =====================================================================
	// 8. BẢO MẬT / LAYOUT
	// =====================================================================
	static boolean isAlertPresent() {
		try { driver().switchTo().alert(); return true } catch (NoAlertPresentException e) { return false }
	}

	static boolean hasHorizontalOverflow() {
		return (Boolean) ((JavascriptExecutor) driver()).executeScript(
			'return document.documentElement.scrollWidth > window.innerWidth + 1')
	}
}
