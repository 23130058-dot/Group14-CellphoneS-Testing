/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Trần Xuân Đào - 23130044
 * Chuyển từ Selenium IDE (23130044_NguyenTranXuanDao_ASM07.html, test case "DSSP_00b_Kham_pha_bo_loc") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.pause(5000)
	PL.storeEval('(function(){ var cards = document.querySelectorAll(\'.product-info-container, .product-item, .product-card\'); return cards.length > 0 ? \'OK\' : \'EMPTY\'; })()', 'resFilter')
	PL.echo('Kiem tra danh sach san pham: ${resFilter}')
	PL.verifyEval('\'${resFilter}\'===\'OK\'', 'true')
} finally {
	PL.endSession()
}
