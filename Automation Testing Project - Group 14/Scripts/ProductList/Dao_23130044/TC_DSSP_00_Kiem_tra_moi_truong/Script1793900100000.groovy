/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Trần Xuân Đào - 23130044
 * Chuyển từ Selenium IDE (23130044_NguyenTranXuanDao_ASM07.html, test case "DSSP_00_Kiem_tra_moi_truong") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.pause(5000)
	PL.waitForElementPresentXpath('//body', 15000)
	PL.assertTextPresent('Điện thoại')
	PL.storeEval('document.querySelectorAll(\'.product-info-container, .product-item, .product-card\').length', 'nCard')
	PL.echo('Dem duoc test moi truong: nCard=${nCard}')
} finally {
	PL.endSession()
}
