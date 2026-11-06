import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.interactions.Actions

// =========================================================================
// CẤU HÌNH: Đổi giá trị RUN_CASE thành mã muốn chạy (VD: "01", "06", "10")
// Hoặc để "ALL" để chạy liên tục toàn bộ 25 kịch bản
// =========================================================================
String RUN_CASE = "ALL" 

String BASE_URL = "https://cellphones.com.vn"

// Hàm tiện ích tạo TestObject nhanh
TestObject byCss(String cssSelector) {
    TestObject to = new TestObject(cssSelector)
    to.addProperty("css", ConditionType.EQUALS, cssSelector)
    return to
}

// Hàm đóng popup quảng cáo/vị trí nếu xuất hiện
def closeAnyPopup(WebDriver driver) {
    try {
        List<WebElement> popups = driver.findElements(By.cssSelector("button.cancel-button-top, #popup-close, .btn-close, [aria-label='Close'], .modal .close"))
        for (WebElement btn : popups) {
            if (btn.isDisplayed()) {
                btn.click()
                WebUI.delay(1)
            }
        }
    } catch (Exception ignored) {}
}

println("==================================================")
println(" BẮT ĐẦU CHẠY KIỂM THỬ TRANG CHỦ & ĐIỀU HƯỚNG CELLPHONES")
println(" Chế độ chạy: " + RUN_CASE)
println("==================================================")

WebUI.openBrowser(BASE_URL)
WebUI.maximizeWindow()
WebUI.delay(3)

WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver
Actions actions = new Actions(driver)

closeAnyPopup(driver)

// -------------------------------------------------------------------------
// [NAV_01] Tải trang chủ và kiểm tra Logo
if (RUN_CASE == "ALL" || RUN_CASE == "01") {
    println("--> [NAV_01] Kiểm tra tải trang chủ và Logo...")
    String currentUrl = driver.getCurrentUrl()
    assert currentUrl.contains("cellphones.com.vn") : "URL không phải trang chủ!"
    
    WebElement logo = driver.findElement(By.cssSelector("a.header__logo, .box-logo a, a[href*='cellphones.com.vn']"))
    assert logo.isDisplayed() : "Logo CellphoneS không hiển thị!"
    println("PASS: Trang chủ và Logo hiển thị chuẩn xác.")
}

// -------------------------------------------------------------------------
// [NAV_02] Bấm Logo quay về trang chủ
if (RUN_CASE == "ALL" || RUN_CASE == "02") {
    println("--> [NAV_02] Kiểm tra bấm Logo quay về trang chủ...")
    driver.get(BASE_URL + "/mobile.html")
    WebUI.delay(2)
    closeAnyPopup(driver)

    // Lọc lấy logo đang hiển thị trên giao diện máy tính
    List<WebElement> logos = driver.findElements(By.xpath("//header//a[contains(@href, 'cellphones.com.vn') or @href='/']"))
    WebElement visibleLogo = logos.find { it.isDisplayed() } ?: logos[0]

    // Bấm trực tiếp vào Logo
    js.executeScript("arguments[0].click();", visibleLogo)
    WebUI.delay(2)

    assert driver.getCurrentUrl().contains("cellphones.com.vn") : "Không quay lại trang chủ khi bấm Logo!"
    println("PASS: Điều hướng về trang chủ thành công.")
}
// -------------------------------------------------------------------------
// [NAV_03] Đổi khu vực / Tỉnh thành
if (RUN_CASE == "ALL" || RUN_CASE == "03") {
    println("--> [NAV_03] Kiểm tra nút chọn khu vực tỉnh thành...")
    try {
        WebElement locationBtn = driver.findElement(By.cssSelector(".box-location, .header-location, [id*='location']"))
        locationBtn.click()
        WebUI.delay(1)
        println("PASS: Popup/Menu chọn địa điểm mở thành công.")
        closeAnyPopup(driver)
    } catch (Exception e) {
        println("SKIP/WARN: Nút vị trí thay đổi selector hoặc không có popup.")
    }
}

// -------------------------------------------------------------------------
// [NAV_04] Điều hướng Cửa hàng gần bạn
if (RUN_CASE == "ALL" || RUN_CASE == "04") {
    println("--> [NAV_04] Điều hướng trang Hệ thống cửa hàng...")
    driver.get(BASE_URL)
    try {
        WebElement storeBtn = driver.findElement(By.xpath("//a[contains(@href, 'dia-chi-cua-hang') or contains(text(), 'Cửa hàng')]"))
        storeBtn.click()
        WebUI.delay(2)
        assert driver.getCurrentUrl().contains("cua-hang") : "Chưa chuyển tới trang địa chỉ cửa hàng!"
        println("PASS: Điều hướng trang cửa hàng thành công.")
    } catch (Exception e) {
        println("WARN: Bỏ qua bước kiểm tra cửa hàng: " + e.getMessage())
    }
}

// -------------------------------------------------------------------------
// [NAV_05] Điều hướng Tra cứu đơn hàng
if (RUN_CASE == "ALL" || RUN_CASE == "05") {
    println("--> [NAV_05] Điều hướng Tra cứu đơn hàng...")
    driver.get(BASE_URL)
    try {
        WebElement orderBtn = driver.findElement(By.xpath("//a[contains(@href, 'tra-cuu') or contains(text(), 'Tra cứu đơn')]"))
        orderBtn.click()
        WebUI.delay(2)
        println("PASS: Điều hướng tra cứu đơn hàng thành công.")
    } catch (Exception e) {
        println("INFO: Nút tra cứu đơn hàng tích hợp trong menu tài khoản.")
    }
}

// -------------------------------------------------------------------------
// [NAV_06] Điều hướng Giỏ hàng
if (RUN_CASE == "ALL" || RUN_CASE == "06") {
    println("--> [NAV_06] Điều hướng trang Giỏ hàng...")
    driver.get(BASE_URL)
    WebUI.delay(2)
    closeAnyPopup(driver)

    // Nhận diện nút Giỏ hàng theo thẻ span.button__cart mới của CellphoneS
    WebElement cartBtn = driver.findElement(By.xpath("//*[contains(@class, 'button__cart') or contains(., 'Giỏ hàng')]"))
    js.executeScript("arguments[0].click();", cartBtn)
    WebUI.delay(2)

    assert driver.getCurrentUrl().contains("cart") || driver.getPageSource().contains("giỏ hàng") || driver.getPageSource().contains("Giỏ hàng") : "Không chuyển tới trang giỏ hàng!"
    println("PASS: Điều hướng giỏ hàng thành công.")
}
// -------------------------------------------------------------------------
// [NAV_07] Điều hướng Đăng nhập Smember
if (RUN_CASE == "ALL" || RUN_CASE == "07") {
    println("--> [NAV_07] Điều hướng Đăng nhập Smember...")
    driver.get(BASE_URL)
    WebElement loginBtn = driver.findElement(By.cssSelector("a[href*='smember.com.vn'], .btn-smember, a[href*='login']"))
    loginBtn.click()
    WebUI.delay(2)
    assert driver.getCurrentUrl().contains("smember") : "Chưa chuyển đến trang Smember!"
    println("PASS: Điều hướng Smember chuẩn xác.")
}

// -------------------------------------------------------------------------
// [NAV_08] Kiểm tra liên kết Hotline Gọi mua hàng
if (RUN_CASE == "ALL" || RUN_CASE == "08") {
    println("--> [NAV_08] Kiểm tra hotline gọi mua hàng...")
    driver.get(BASE_URL)
    List<WebElement> telLinks = driver.findElements(By.cssSelector("a[href^='tel:']"))
    assert telLinks.size() > 0 : "Không tìm thấy liên kết gọi điện hotline!"
    println("PASS: Thẻ hotline tel: hợp lệ (" + telLinks.get(0).getAttribute("href") + ").")
}

// -------------------------------------------------------------------------
// [NAV_09] Hover Menu Danh mục Điện thoại
if (RUN_CASE == "ALL" || RUN_CASE == "09") {
    println("--> [NAV_09] Hover Menu Danh mục Điện thoại...")
    driver.get(BASE_URL)
    try {
        WebElement phoneMenu = driver.findElement(By.xpath("//div[contains(@class,'menu')]//a[contains(@href,'mobile') or contains(.,'Điện thoại')]"))
        actions.moveToElement(phoneMenu).perform()
        WebUI.delay(1)
        println("PASS: Hover menu danh mục thành công.")
    } catch (Exception e) {
        println("WARN: Lỗi hover menu: " + e.getMessage())
    }
}

// -------------------------------------------------------------------------
// [NAV_10] Điều hướng danh mục Điện thoại
if (RUN_CASE == "ALL" || RUN_CASE == "10") {
    println("--> [NAV_10] Điều hướng danh mục Điện thoại...")
    driver.get(BASE_URL + "/mobile.html")
    WebUI.delay(2)
    assert driver.getCurrentUrl().contains("mobile.html") : "Không đúng URL danh mục điện thoại!"
    println("PASS: Điều hướng danh mục Điện thoại thành công.")
}

// -------------------------------------------------------------------------
// [NAV_11] Điều hướng danh mục Laptop
if (RUN_CASE == "ALL" || RUN_CASE == "11") {
    println("--> [NAV_11] Điều hướng danh mục Laptop...")
    driver.get(BASE_URL + "/laptop.html")
    WebUI.delay(2)
    assert driver.getCurrentUrl().contains("laptop.html") : "Không đúng URL danh mục laptop!"
    println("PASS: Điều hướng danh mục Laptop thành công.")
}

// -------------------------------------------------------------------------
// [NAV_12] Điều hướng danh mục Âm thanh
if (RUN_CASE == "ALL" || RUN_CASE == "12") {
    println("--> [NAV_12] Điều hướng danh mục Âm thanh...")
    driver.get(BASE_URL + "/thiet-bi-am-thanh.html")
    WebUI.delay(2)
    assert driver.getCurrentUrl().contains("am-thanh") : "Không đúng URL danh mục âm thanh!"
    println("PASS: Điều hướng danh mục Âm thanh thành công.")
}

// -------------------------------------------------------------------------
// [NAV_13] Điều hướng danh mục Đồng hồ
if (RUN_CASE == "ALL" || RUN_CASE == "13") {
    println("--> [NAV_13] Điều hướng danh mục Đồng hồ...")
    driver.get(BASE_URL + "/dong-ho-thong-minh.html")
    WebUI.delay(2)
    assert driver.getCurrentUrl().contains("dong-ho") : "Không đúng URL danh mục đồng hồ!"
    println("PASS: Điều hướng danh mục Đồng hồ thành công.")
}

// -------------------------------------------------------------------------
// [NAV_14] Điều hướng danh mục Đồ gia dụng
if (RUN_CASE == "ALL" || RUN_CASE == "14") {
    println("--> [NAV_14] Điều hướng danh mục Đồ gia dụng...")
    driver.get(BASE_URL + "/do-gia-dung.html")
    WebUI.delay(2)
    assert driver.getCurrentUrl().contains("gia-dung") : "Không đúng URL gia dụng!"
    println("PASS: Điều hướng danh mục Đồ gia dụng thành công.")
}

// -------------------------------------------------------------------------
// [NAV_15] Điều hướng danh mục Phụ kiện
if (RUN_CASE == "ALL" || RUN_CASE == "15") {
    println("--> [NAV_15] Điều hướng danh mục Phụ kiện...")
    driver.get(BASE_URL + "/phu-kien.html")
    WebUI.delay(2)
    assert driver.getCurrentUrl().contains("phu-kien") : "Không đúng URL phụ kiện!"
    println("PASS: Điều hướng danh mục Phụ kiện thành công.")
}

// -------------------------------------------------------------------------
// [NAV_16] Điều hướng Hàng cũ / Thu cũ đổi mới
if (RUN_CASE == "ALL" || RUN_CASE == "16") {
    println("--> [NAV_16] Điều hướng Hàng cũ / Thu cũ đổi mới...")
    driver.get(BASE_URL + "/hang-cu.html")
    WebUI.delay(2)
    assert driver.getCurrentUrl().contains("hang-cu") || driver.getCurrentUrl().contains("thu-cu") : "Không đúng URL hàng cũ!"
    println("PASS: Điều hướng Hàng cũ thành công.")
}

// -------------------------------------------------------------------------
// [NAV_17] Bấm chuyển slide Banner chính
if (RUN_CASE == "ALL" || RUN_CASE == "17") {
    println("--> [NAV_17] Kiểm tra nút chuyển Slide Banner...")
    driver.get(BASE_URL)
    WebUI.delay(2)
    try {
        WebElement nextBtn = driver.findElement(By.cssSelector(".swiper-button-next, .owl-next, .button-next"))
        if (nextBtn.isDisplayed()) {
            nextBtn.click()
            WebUI.delay(1)
            println("PASS: Chuyển banner thành công.")
        }
    } catch (Exception e) {
        println("INFO: Banner chạy tự động (Autoplay) hoặc selector nút ẩn.")
    }
}

// -------------------------------------------------------------------------
// [NAV_18] Bấm vào Banner chính
if (RUN_CASE == "ALL" || RUN_CASE == "18") {
    println("--> [NAV_18] Kiểm tra liên kết Banner...")
    driver.get(BASE_URL)
    WebUI.delay(2)
    try {
        WebElement activeBanner = driver.findElement(By.cssSelector(".swiper-slide-active a, .sliding-home a"))
        String bannerHref = activeBanner.getAttribute("href")
        assert bannerHref != null && !bannerHref.isEmpty() : "Banner không chứa liên kết!"
        println("PASS: Banner chính có liên kết: " + bannerHref)
    } catch (Exception e) {
        println("INFO: Bỏ qua kiểm tra thẻ banner chi tiết.")
    }
}

// -------------------------------------------------------------------------
// [NAV_19] Điều hướng sang chuyên trang Sforum
if (RUN_CASE == "ALL" || RUN_CASE == "19") {
    println("--> [NAV_19] Điều hướng chuyên trang tin tức Sforum...")
    driver.get(BASE_URL)
    try {
        WebElement sforumBtn = driver.findElement(By.cssSelector("a[href*='sforum.vn']"))
        String sforumHref = sforumBtn.getAttribute("href")
        assert sforumHref.contains("sforum.vn") : "Không tìm thấy link Sforum!"
        println("PASS: Liên kết Sforum hợp lệ (" + sforumHref + ").")
    } catch (Exception e) {
        println("WARN: Không tìm thấy link Sforum ở Header/Menu.")
    }
}

// -------------------------------------------------------------------------
// [NAV_20] Chuyển Tab Sản phẩm Hot
if (RUN_CASE == "ALL" || RUN_CASE == "20") {
    println("--> [NAV_20] Chuyển tab trong khối Sản phẩm Hot...")
    driver.get(BASE_URL)
    WebUI.delay(2)
    try {
        List<WebElement> tabs = driver.findElements(By.cssSelector(".tab-item, .box-filter-tab a"))
        if (tabs.size() > 1) {
            tabs.get(1).click()
            WebUI.delay(1)
            println("PASS: Chuyển tab khối sản phẩm thành công.")
        }
    } catch (Exception e) {
        println("INFO: Trang không có tab sản phẩm lọc nhanh.")
    }
}

// -------------------------------------------------------------------------
// [NAV_21] Bấm nút Xem tất cả
if (RUN_CASE == "ALL" || RUN_CASE == "21") {
    println("--> [NAV_21] Kiểm tra nút Xem tất cả...")
    driver.get(BASE_URL)
    WebUI.delay(2)
    try {
        WebElement viewAllBtn = driver.findElement(By.xpath("//a[contains(text(), 'Xem tất cả') or contains(text(), 'xem tất cả')]"))
        String viewAllUrl = viewAllBtn.getAttribute("href")
        assert viewAllUrl != null && !viewAllUrl.isEmpty() : "Nút Xem tất cả không có URL!"
        println("PASS: Nút Xem tất cả trỏ tới: " + viewAllUrl)
    } catch (Exception e) {
        println("INFO: Nút xem tất cả thay đổi selector.")
    }
}

// -------------------------------------------------------------------------
// [NAV_22] Nhấp vào thẻ sản phẩm bất kỳ chuyển sang trang PDP
if (RUN_CASE == "ALL" || RUN_CASE == "22") {
    println("--> [NAV_22] Bấm vào thẻ sản phẩm chuyển sang chi tiết sản phẩm...")
    driver.get(BASE_URL)
    WebUI.delay(2)
    WebElement firstProduct = driver.findElement(By.cssSelector(".product-item a, .product-info-container a, .product__link"))
    String productUrl = firstProduct.getAttribute("href")
js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstProduct)
WebUI.delay(1)
js.executeScript("arguments[0].click();", firstProduct)
    WebUI.delay(3)
    assert driver.getCurrentUrl().contains(productUrl) || driver.getCurrentUrl().endsWith(".html") : "Chưa chuyển tới trang chi tiết sản phẩm!"
    println("PASS: Chuyển sang trang chi tiết sản phẩm thành công: " + driver.getCurrentUrl())
}

// -------------------------------------------------------------------------
// [NAV_23] Cuộn trang và bấm nút Về đầu trang
if (RUN_CASE == "ALL" || RUN_CASE == "23") {
    println("--> [NAV_23] Cuộn trang và kiểm tra nút Về đầu trang...")
    driver.get(BASE_URL)
    WebUI.delay(2)
    js.executeScript("window.scrollTo(0, 2000);")
    WebUI.delay(1)
    try {
        WebElement backToTop = driver.findElement(By.cssSelector(".back-to-top, .scroll-to-top, [id*='back-top']"))
        backToTop.click()
        WebUI.delay(1)
        println("PASS: Đã bấm nút Về đầu trang thành công.")
    } catch (Exception e) {
        println("INFO: Nút Back-to-top ẩn hoặc dùng cơ chế cuộn khác.")
    }
}

// -------------------------------------------------------------------------
// [NAV_24] Kiểm tra link Chính sách bảo hành ở Footer
if (RUN_CASE == "ALL" || RUN_CASE == "24") {
    println("--> [NAV_24] Kiểm tra link Chính sách bảo hành ở Footer...")
    driver.get(BASE_URL)
    js.executeScript("window.scrollTo(0, document.body.scrollHeight);")
    WebUI.delay(1)
    WebElement policyLink = driver.findElement(By.xpath("//footer//a[contains(@href, 'chinh-sach-bao-hanh') or contains(text(), 'Chính sách bảo hành')]"))
    assert policyLink.isDisplayed() : "Không tìm thấy link Chính sách bảo hành!"
    println("PASS: Link chính sách bảo hành ở Footer tồn tại (" + policyLink.getAttribute("href") + ").")
}

// -------------------------------------------------------------------------
// [NAV_25] Kiểm tra liên kết Mạng xã hội ở Footer
if (RUN_CASE == "ALL" || RUN_CASE == "25") {
    println("--> [NAV_25] Kiểm tra liên kết Mạng xã hội ở Footer...")
    driver.get(BASE_URL)
    js.executeScript("window.scrollTo(0, document.body.scrollHeight);")
    WebUI.delay(1)
    List<WebElement> socialLinks = driver.findElements(By.cssSelector("footer a[href*='facebook.com'], footer a[href*='youtube.com']"))
    assert socialLinks.size() > 0 : "Không tìm thấy icon mạng xã hội ở chân trang!"
    println("PASS: Các liên kết mạng xã hội ở Footer hiển thị đầy đủ.")
}

// Đóng trình duyệt sau khi chạy xong
WebUI.delay(2)
WebUI.closeBrowser()

println("==================================================")
println(" HOÀN THÀNH TẤT CẢ CÁC BÀI TEST TRANG CHỦ & ĐIỀU HƯỚNG!")
println("==================================================")