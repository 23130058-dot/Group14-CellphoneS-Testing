import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

// 1. Dữ liệu tài khoản test thử
String Phone_Number = "0987654321"
String Password = "MatKhau@123"

// 2. Mở trình duyệt và vào trang đăng nhập Smember
WebUI.openBrowser('https://smember.com.vn/login')
WebUI.maximizeWindow()
WebUI.delay(2)

// Lấy WebDriver của Katalon đang chạy
WebDriver driver = DriverFactory.getWebDriver()

// 3. Điền Số điện thoại
def phoneInput = driver.findElement(By.xpath("//input[@placeholder='Nhập số điện thoại của bạn']"))
phoneInput.click()
phoneInput.clear()
phoneInput.sendKeys(Phone_Number)

// 4. Điền Mật khẩu
def passInput = driver.findElement(By.xpath("//input[@placeholder='Nhập mật khẩu của bạn']"))
passInput.click()
passInput.clear()
passInput.sendKeys(Password)

// 5. Bấm nút Submit Đăng nhập
driver.findElement(By.xpath("//button[@type='submit']")).click()

// 6. Chờ 3 giây để quan sát kết quả rồi đóng trình duyệt
WebUI.delay(3)
WebUI.closeBrowser()