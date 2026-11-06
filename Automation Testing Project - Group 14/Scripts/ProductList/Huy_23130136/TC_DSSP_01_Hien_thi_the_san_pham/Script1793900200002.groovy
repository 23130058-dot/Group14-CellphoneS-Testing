/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Hữu Huy - 23130136
 * Chuyển từ Selenium IDE (23130136_NguyenHuuHuy_ASM07.html, test case "DSSP_01_Hien_thi_the_san_pham") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.store('.product-info-container, .product-info, .product-item', 'cssCard')
	PL.store('.product__price--show, .product-price, .price', 'cssPrice')
	PL.store('samsung', 'brand')
	PL.store('4000', 'pMinK')
	PL.store('7000', 'pMaxK')
	PL.store('4000000', 'pMin')
	PL.store('7000000', 'pMax')
	PL.pause(6000)
	PL.storeEval('Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)}).length', 'n0')
	PL.echo('So the san pham hien thi ban dau n0=${n0}')
	PL.storeEval('(Number(storedVars[\'n0\'] || \'${n0}\') > 0) ? \'PASS\' : \'FAIL: Trang hien thi the san pham\'', 'kq1')
	PL.echo('KIEM TRA Trang hien thi the san pham: ${kq1}')
	PL.storeEval('window.document.querySelectorAll(\'${cssCard} h3, ${cssCard} .product__name\').length', 'nName')
	PL.storeEval('window.document.querySelectorAll(\'${cssCard} img\').length', 'nImg')
	PL.storeEval('window.document.querySelectorAll(\'${cssPrice}\').length', 'nPrice')
	PL.storeEval('(Number(storedVars[\'nName\'] || \'${nName}\') > 0) ? \'PASS\' : \'FAIL: The co ten\'', 'kq2')
	PL.echo('KIEM TRA The co ten: ${kq2}')
	PL.storeEval('(Number(storedVars[\'nImg\'] || \'${nImg}\') > 0) ? \'PASS\' : \'FAIL: The co anh\'', 'kq3')
	PL.echo('KIEM TRA The co anh: ${kq3}')
	PL.storeEval('(Number(storedVars[\'nPrice\'] || \'${nPrice}\') > 0) ? \'PASS\' : \'FAIL: The co gia\'', 'kq4')
	PL.echo('KIEM TRA The co gia: ${kq4}')
} finally {
	PL.endSession()
}
