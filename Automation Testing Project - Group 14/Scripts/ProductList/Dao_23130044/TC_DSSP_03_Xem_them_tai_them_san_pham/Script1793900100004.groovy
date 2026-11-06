/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Trần Xuân Đào - 23130044
 * Chuyển từ Selenium IDE (23130044_NguyenTranXuanDao_ASM07.html, test case "DSSP_03_Xem_them_tai_them_san_pham") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.store('.product-info-container, .product-item, .product-card', 'cssCard')
	PL.pause(5000)
	PL.storeEval('document.querySelectorAll(\'${cssCard}\').length', 'n0')
	PL.echo('So the san pham ban dau n0=${n0}')
	PL.storeEval('(function(){ var btns = Array.prototype.filter.call(document.querySelectorAll(\'a, button, div, span\'), function(e){ return /xem thêm/i.test(e.textContent.trim()) && (e.offsetWidth > 0 || e.offsetHeight > 0); }); if(btns.length > 0){ var b = btns[0].closest(\'a, button\') || btns[0]; b.scrollIntoView({behavior:\'instant\', block:\'center\'}); b.click(); return \'clicked\'; } return \'skipped\'; })()', 'clickMore')
	PL.pause(4000)
	PL.storeEval('document.querySelectorAll(\'${cssCard}\').length', 'n1')
	PL.echo('n0=${n0} n1=${n1} clickMore=${clickMore}')
	PL.verifyEval('${n1} >= ${n0}', 'true')
} finally {
	PL.endSession()
}
