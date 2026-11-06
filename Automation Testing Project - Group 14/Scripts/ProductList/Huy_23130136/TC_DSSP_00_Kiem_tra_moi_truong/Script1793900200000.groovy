/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Hữu Huy - 23130136
 * Chuyển từ Selenium IDE (23130136_NguyenHuuHuy_ASM07.html, test case "DSSP_00_Kiem_tra_moi_truong") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.pause(5000)
	PL.echo('Buoc 1: nhom lenh Selenium')
	PL.waitForElementPresentXpath('//body', 15000)
	PL.assertTextPresent('Điện thoại')
	PL.echo('Buoc 2: storeEval doc DOM')
	PL.storeEval('window.document.querySelectorAll(\'.product-info-container, .product-info, .product-item\').length', 'nCard')
	PL.echo('So the san pham=${nCard}')
} finally {
	PL.endSession()
}
