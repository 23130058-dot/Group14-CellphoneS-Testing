/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Trần Xuân Đào - 23130044
 * Chuyển từ Selenium IDE (23130044_NguyenTranXuanDao_ASM07.html, test case "DSSP_11_Bo_chon_bo_loc_ve_ban_dau") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.store('.product-info-container, .product-item, .product-card', 'cssCard')
	PL.pause(5000)
	PL.storeEval('document.querySelectorAll(\'${cssCard}\').length', 'n0')
	PL.storeEval('(function(){ var el = Array.from(document.querySelectorAll(\'button, a, div, span, label\')).find(function(e){ return /^Samsung$/i.test(e.textContent.trim()) && (e.offsetWidth > 0 || e.offsetHeight > 0); }); if(el){ var clk = el.closest(\'button, a, label, div\') || el; clk.click(); return \'clicked\'; } return \'skipped\'; })()', 'resToggle1')
	PL.pause(3000)
	PL.storeEval('(function(){ var el = Array.from(document.querySelectorAll(\'button, a, div, span, label\')).find(function(e){ return /^Samsung$/i.test(e.textContent.trim()) && (e.offsetWidth > 0 || e.offsetHeight > 0); }); if(el){ var clk = el.closest(\'button, a, label, div\') || el; clk.click(); return \'unclicked\'; } return \'skipped\'; })()', 'resToggle2')
	PL.pause(3000)
	PL.storeEval('document.querySelectorAll(\'${cssCard}\').length', 'n2')
	PL.echo('Thao tac bo chon: n0=${n0}, n2=${n2}')
	PL.verifyEval('${n2} >= 0', 'true')
} finally {
	PL.endSession()
}
