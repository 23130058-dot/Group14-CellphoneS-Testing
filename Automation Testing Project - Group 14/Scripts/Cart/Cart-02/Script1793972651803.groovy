import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import com.cellphones.cart.CartConfig as C
import com.cellphones.cart.CartKeywords as K
import com.cellphones.cart.CartLocators as L

/* Cart-02: Thêm vào giỏ -> đăng nhập từ Popup -> quay lại đúng trang sản phẩm */
K.startBrowser()
try {
	K.openProduct(C.PRODUCT_A_URL)
	K.clickAddToCart()
	K.verifyLoginPopupDisplayed()
	K.clickLoginOnRequiredPopup()          // popup chỉ có 2 nút -> bấm [Đăng nhập] để ra form SĐT/Mật khẩu
	K.submitLogin(C.PHONE, C.PASSWORD)
	K.check(K.isLoggedIn(), 'Đăng nhập thành công')
	K.verifyCurrentUrlIs(C.PRODUCT_A_URL)
} finally {
	K.stopBrowser()
}
