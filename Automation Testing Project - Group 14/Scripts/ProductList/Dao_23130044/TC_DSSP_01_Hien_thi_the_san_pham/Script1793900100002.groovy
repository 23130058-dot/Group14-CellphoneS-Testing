/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Trần Xuân Đào - 23130044
 * Chuyển từ Selenium IDE (23130044_NguyenTranXuanDao_ASM07.html, test case "DSSP_01_Hien_thi_the_san_pham") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.store('.product-info-container, .product-item, .product-card', 'cssCard')
	PL.store('.product__price--show, .product__price, [class*="price"]', 'cssPrice')
	PL.pause(5000)
	PL.storeEval('document.querySelectorAll(\'${cssCard}\').length', 'n0')
	PL.echo('So the san pham hien thi ban dau n0=${n0}')
	PL.verifyEval('${n0} > 0', 'true')
	PL.storeEval('document.querySelectorAll(\'${cssCard} h3, ${cssCard} .product__name, ${cssCard} [class*="name"]\').length', 'nName')
	PL.storeEval('document.querySelectorAll(\'${cssCard} img\').length', 'nImg')
	PL.storeEval('document.querySelectorAll(\'${cssPrice}\').length', 'nPrice')
	PL.verifyEval('${nName} > 0', 'true')
	PL.verifyEval('${nImg} > 0', 'true')
	PL.verifyEval('${nPrice} > 0', 'true')
} finally {
	PL.endSession()
}
