import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import com.cellphones.cart.CartConfig as C
import com.cellphones.cart.CartKeywords as K
import com.cellphones.cart.CartLocators as L

/* Cart-01: Thêm vào giỏ khi chưa đăng nhập -> Popup yêu cầu đăng nhập, chặn thêm SP */
K.startBrowser()
try {
	K.openProduct(C.PRODUCT_A_URL)
	K.checkEq(K.getCartBadge(), 0, 'Bước 2: Icon giỏ hàng hiển thị số 0')
	K.clickAddToCart()
	K.verifyLoginPopupDisplayed()
	K.checkEq(K.getCartBadge(), 0, 'Bước 3: Số trên icon giỏ hàng vẫn là 0')
} finally {
	K.stopBrowser()
}
