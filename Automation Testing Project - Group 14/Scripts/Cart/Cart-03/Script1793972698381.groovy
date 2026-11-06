import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import com.cellphones.cart.CartConfig as C
import com.cellphones.cart.CartKeywords as K
import com.cellphones.cart.CartLocators as L

/* Cart-03: Bấm icon giỏ hàng khi chưa đăng nhập -> Popup yêu cầu đăng nhập */
K.startBrowser()
try {
	K.clickHeaderCart()
	K.verifyLoginPopupDisplayed()
	if (K.getCurrentUrl().contains('/cart') && !K.anyDisplayed(L.LOGIN_REQUIRED_POPUP)) {
		KeywordUtil.markFailedAndStop('Vào được trang giỏ hàng khi chưa đăng nhập')
	}
} finally {
	K.stopBrowser()
}
