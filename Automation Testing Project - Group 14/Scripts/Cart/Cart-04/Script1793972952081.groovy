import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import com.cellphones.cart.CartConfig as C
import com.cellphones.cart.CartKeywords as K
import com.cellphones.cart.CartLocators as L

/* Cart-04: Mở trang Giỏ hàng sau đăng nhập -> hiển thị đúng dữ liệu của tài khoản */
K.startBrowser()
try {
	K.loginViaHeader()
	K.resetCart()
	String nameA = K.addProduct(C.PRODUCT_A_URL)
	WebUI.navigateToUrl(C.BASE_URL)
	K.clickHeaderCart()
	K.verifyUrlContains('/cart')
	K.check(K.findRowByName(nameA) > 0, "Giỏ hàng hiển thị sản phẩm đã lưu: ${nameA}")
} finally {
	K.stopBrowser()
}
